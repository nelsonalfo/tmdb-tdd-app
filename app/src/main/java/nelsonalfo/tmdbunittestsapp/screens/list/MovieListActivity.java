package nelsonalfo.tmdbunittestsapp.screens.list;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import nelsonalfo.tmdbunittestsapp.R;
import nelsonalfo.tmdbunittestsapp.api.ApiServiceGenerator;
import nelsonalfo.tmdbunittestsapp.api.TheMovieDbRestApi;
import nelsonalfo.tmdbunittestsapp.command.CommandFactory;
import nelsonalfo.tmdbunittestsapp.command.list.GetConfigurationCommand;
import nelsonalfo.tmdbunittestsapp.command.list.GetMoviesCommand;
import nelsonalfo.tmdbunittestsapp.models.MovieResume;
import nelsonalfo.tmdbunittestsapp.models.TmdbConfiguration;
import nelsonalfo.tmdbunittestsapp.screens.detail.MovieDetailActivity;
import nelsonalfo.tmdbunittestsapp.util.TmdbConfigurationUtil;


public class MovieListActivity extends AppCompatActivity implements MovieListContract.View, MoviesAdapter.Listener {
    @BindView(R.id.movie_list)
    RecyclerView recyclerView;

    private TmdbConfigurationUtil configurationUtil;

    private MovieListContract.Presenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);

        ButterKnife.bind(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final TheMovieDbRestApi service = ApiServiceGenerator.createClient();
        final GetMoviesCommand moviesCommand = CommandFactory.createGetMoviesCommand(service);
        final GetConfigurationCommand configCommand = CommandFactory.createGetConfigurationCommand(service);

        setPresenter(new MovieListPresenter(this, moviesCommand, configCommand));

        presenter.callApi();
    }

    @Override
    public void setPresenter(MovieListContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showCantRequestTheMoviesMessage() {
        Toast.makeText(this, "No se puede realizar la consulta", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showThereIsNoMovies() {
        Toast.makeText(this, "showThereIsNoMovies", Toast.LENGTH_SHORT).show();
        //TODO Puedo o llamar un toast, o mostrar un texto en pantalla indicando que no hay valores
    }

    @Override
    public void showConnectionProblemsMessage() {
        Toast.makeText(this, "hay problemas de conexion, no se pudieron obtener las peliculas", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showUnknownErrorMessage() {
        Toast.makeText(this, "Hubo un error desconocido", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setConfiguration(TmdbConfiguration configuration) {
        configurationUtil = new TmdbConfigurationUtil(configuration);
    }

    @Override
    public void showMovies(List<MovieResume> movies) {
        if(configurationUtil != null){
            final MoviesAdapter adapter = new MoviesAdapter(configurationUtil, movies);
            adapter.setListener(this);

            recyclerView.setAdapter(adapter);
        }
    }

    @Override
    public void onMovieSelected(MovieResume selectedMovie) {
        final Intent intent = new Intent(this, MovieDetailActivity.class);
        intent.putExtra(MovieDetailActivity.ARG_MOVIE_ID, selectedMovie.id);

        startActivity(intent);
    }
}
