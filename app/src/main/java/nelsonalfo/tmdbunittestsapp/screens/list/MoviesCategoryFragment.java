package nelsonalfo.tmdbunittestsapp.screens.list;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.parceler.Parcels;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import nelsonalfo.tmdbunittestsapp.R;
import nelsonalfo.tmdbunittestsapp.api.ApiServiceGenerator;
import nelsonalfo.tmdbunittestsapp.api.TheMovieDbRestApi;
import nelsonalfo.tmdbunittestsapp.command.CommandFactory;
import nelsonalfo.tmdbunittestsapp.command.list.GetConfigurationCommand;
import nelsonalfo.tmdbunittestsapp.command.list.GetMoviesCommand;
import nelsonalfo.tmdbunittestsapp.models.Constants;
import nelsonalfo.tmdbunittestsapp.models.MovieResume;
import nelsonalfo.tmdbunittestsapp.models.TmdbConfiguration;
import nelsonalfo.tmdbunittestsapp.screens.detail.MovieDetailActivity;
import nelsonalfo.tmdbunittestsapp.screens.list.adapter.MoviesAdapter;
import nelsonalfo.tmdbunittestsapp.util.TmdbConfigurationUtil;


/**
 * Created by nelso on 23/2/2018.
 */

public class MoviesCategoryFragment extends Fragment implements MovieListContract.View, MoviesAdapter.Listener {
    @BindView(R.id.movie_list)
    RecyclerView recyclerView;

    private String movieCategory;
    private TmdbConfigurationUtil configurationUtil;
    private MovieListContract.Presenter presenter;
    private MoviesAdapter adapter;


    public MoviesCategoryFragment() {
    }

    public static MoviesCategoryFragment newInstance(String category) {
        MoviesCategoryFragment fragment = new MoviesCategoryFragment();
        fragment.movieCategory = category;

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_movie_list, container, false);
        ButterKnife.bind(this, rootView);

        setHasOptionsMenu(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        final TheMovieDbRestApi service = new ApiServiceGenerator(getContext()).createClient();
        final GetMoviesCommand moviesCommand = getCommandForGivenCategory(service);
        final GetConfigurationCommand configCommand = CommandFactory.createGetConfigurationCommand(service);

        setPresenter(new MovieListPresenter(this, moviesCommand, configCommand));

        presenter.callApi();

        return rootView;
    }

    private GetMoviesCommand getCommandForGivenCategory(TheMovieDbRestApi service) {
        switch (movieCategory) {
            case Constants.UPCOMING_MOVIES:
                return CommandFactory.createGetUpcomingMoviesCommand(service);
            case Constants.TOP_RATED_MOVIES:
                return CommandFactory.createGetTopRatedMoviesCommand(service);
            default:
                return CommandFactory.createGetPopularMoviesCommand(service);
        }
    }

    @Override
    public void setPresenter(MovieListContract.Presenter presenter) {
        this.presenter = presenter;
        presenter.callApi();
    }

    @Override
    public void showThereIsNoMovies() {
        Toast.makeText(getContext(), R.string.error_no_movies_available, Toast.LENGTH_SHORT).show();
        //TODO Puedo o llamar un toast, o mostrar un texto en pantalla indicando que no hay valores
    }

    @Override
    public void showConnectionProblemsMessage() {
        Toast.makeText(getContext(), R.string.error_connection_problems_cant_get_movies, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showUnknownErrorMessage() {
        Toast.makeText(getContext(), R.string.error_unknown_error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setConfiguration(TmdbConfiguration configuration) {
        configurationUtil = new TmdbConfigurationUtil(configuration);
    }

    @Override
    public void showMovies(List<MovieResume> movies) {
        if (configurationUtil != null) {
            adapter = new MoviesAdapter(configurationUtil, movies);
            adapter.setListener(this);

            recyclerView.setAdapter(adapter);
        }
    }

    @Override
    public void onMovieSelected(MovieResume selectedMovie) {
        final TmdbConfiguration configuration = configurationUtil.getConfiguration();

        final Intent intent = new Intent(getActivity(), MovieDetailActivity.class)
                .putExtra(MovieDetailActivity.ARG_MOVIE_ID, selectedMovie.id)
                .putExtra(MovieDetailActivity.ARG_CONFIGURATION, Parcels.wrap(configuration));

        startActivity(intent);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.movies_search, menu);

        final MenuItem searchMenuItem = menu.findItem(R.id.search);

        configureSearchView(searchMenuItem);
    }

    private void configureSearchView(MenuItem menuItem) {
        final SearchView searchView = (SearchView) menuItem.getActionView();

        switch (movieCategory) {
            case Constants.UPCOMING_MOVIES:
                searchView.setQueryHint(getString(R.string.search_upcoming_movies));
                break;
            case Constants.TOP_RATED_MOVIES:
                searchView.setQueryHint(getString(R.string.search_top_rated_movies));
                break;
            default:
                searchView.setQueryHint(getString(R.string.search_popular_movies));
                break;
        }

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return true;
            }
        });

    }
}
