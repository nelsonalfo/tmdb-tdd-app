package nelsonalfo.tmdbunittestsapp.screens.detail;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;
import nelsonalfo.tmdbunittestsapp.R;
import nelsonalfo.tmdbunittestsapp.api.ApiServiceGenerator;
import nelsonalfo.tmdbunittestsapp.api.TheMovieDbRestApi;
import nelsonalfo.tmdbunittestsapp.command.CommandFactory;
import nelsonalfo.tmdbunittestsapp.command.detail.MovieDetailCommand;
import nelsonalfo.tmdbunittestsapp.models.MovieDetail;
import nelsonalfo.tmdbunittestsapp.models.TmdbConfiguration;
import nelsonalfo.tmdbunittestsapp.util.TmdbConfigurationUtil;


public class MovieDetailActivity extends AppCompatActivity implements MovieDetailContract.View {
    public static final String ARG_MOVIE_ID = "ARG_MOVIE_ID";
    public static final String ARG_CONFIGURATION = "ARG_CONFIGURATION";

    private static final int NO_ID = -1;

    @BindView(R.id.movie_backdrop_image)
    ImageView movieBackdropImage;

    @BindView(R.id.movie_poster)
    ImageView moviePoster;

    @BindView(R.id.movie_title)
    TextView movieTitle;

    @BindView(R.id.movie_votes)
    TextView movieRankingAndVotes;

    @BindView(R.id.movie_genders)
    TextView movieGenders;

    @BindView(R.id.movie_description)
    TextView movieDescription;

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    @BindView(R.id.movie_detail_container)
    ViewGroup detailContainer;

    private TmdbConfigurationUtil configurationUtil;
    private MovieDetailContract.Presenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        ButterKnife.bind(this);

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        setupTmdbConfigUtil();

        setupPresenter();

        presenter.callApi();
    }

    private void setupTmdbConfigUtil() {
        final TmdbConfiguration configuration = Parcels.unwrap(getIntent().getParcelableExtra(ARG_CONFIGURATION));

        if (configuration != null) {
            configurationUtil = new TmdbConfigurationUtil(configuration);
        }
    }

    private void setupPresenter() {
        final int movieId = getIntent().getIntExtra(ARG_MOVIE_ID, NO_ID);
        final TheMovieDbRestApi service = new ApiServiceGenerator(this).createClient();
        final MovieDetailCommand command = CommandFactory.createGetMovieDetailCommand(service, movieId);

        setPresenter(new MovieDetailPresenter(this, command));
    }

    @Override
    public void setPresenter(MovieDetailContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showMovieDetail(MovieDetail movieDetail) {
        final String backdropImageUrl = configurationUtil.getBackdropImageUrl(movieDetail.backdropPath);
        Picasso.with(this).load(backdropImageUrl).into(movieBackdropImage);

        String posterImageUrl = configurationUtil.getPosterImageUrl(movieDetail.posterPath);
        Picasso.with(this).load(posterImageUrl).into(moviePoster);

        movieTitle.setText(movieDetail.originalTitle);

        movieDescription.setText(movieDetail.overview);

        if (movieDetail.voteCount != null && movieDetail.voteAverage != null) {
            movieRankingAndVotes.setText(getString(R.string.ranking_and_votes, movieDetail.voteAverage, movieDetail.voteCount));
        } else if (movieDetail.voteCount != null) {
            movieRankingAndVotes.setText(getString(R.string.only_votes, movieDetail.voteCount));
        } else if (movieDetail.voteAverage != null) {
            movieRankingAndVotes.setText(getString(R.string.only_ranking, movieDetail.voteAverage));
        } else {
            movieRankingAndVotes.setVisibility(View.GONE);
        }

        final StringBuilder formattedGenres = movieDetail.getFormattedGenres();
        movieGenders.setText(formattedGenres.length() > 0 ?
                getString(R.string.genres_text, formattedGenres.toString()) :
                getString(R.string.genres_text, "N/A"));

        progressBar.setVisibility(View.GONE);
        detailContainer.setVisibility(View.VISIBLE);
    }

    @Override
    public void finishAndShowNoDetailsMessage() {
        Toast.makeText(this, R.string.error_no_movie_details, Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void finishAndShowConnectionProblemsMessage() {
        Toast.makeText(this, R.string.error_connection_problems_cant_get_movie_detail, Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void finishAndShowUnknownErrorMessage() {
        Toast.makeText(this, R.string.error_unknown_error, Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return false;
    }
}
