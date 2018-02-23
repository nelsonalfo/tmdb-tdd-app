package nelsonalfo.tmdbunittestsapp.screens.detail;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import nelsonalfo.tmdbunittestsapp.R;
import nelsonalfo.tmdbunittestsapp.api.ApiServiceGenerator;
import nelsonalfo.tmdbunittestsapp.api.TheMovieDbRestApi;
import nelsonalfo.tmdbunittestsapp.command.CommandFactory;
import nelsonalfo.tmdbunittestsapp.command.detail.MovieDetailCommand;


public class MovieDetailActivity extends AppCompatActivity implements MovieDetailContract.View {
    public static final String ARG_MOVIE_ID = "ARG_MOVIE_ID";

    private static final int NO_ID = -1;

    private MovieDetailContract.Presenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        int movieId = getIntent().getIntExtra(ARG_MOVIE_ID, NO_ID);
        TheMovieDbRestApi service = new ApiServiceGenerator(this).createClient();
        final MovieDetailCommand movieDetailCommand = CommandFactory.createGetMovieDetailCommand(service, movieId);

        setPresenter(new MovieDetailPresenter());
    }

    @Override
    public void setPresenter(MovieDetailContract.Presenter presenter) {
        this.presenter = presenter;
    }
}
