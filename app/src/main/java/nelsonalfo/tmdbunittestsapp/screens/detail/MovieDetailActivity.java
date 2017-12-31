package nelsonalfo.tmdbunittestsapp.screens.detail;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import nelsonalfo.tmdbunittestsapp.R;


public class MovieDetailActivity extends AppCompatActivity implements MovieDetailContract.View {
    public static final String ARG_MOVIE_ID = "ARG_MOVIE_ID";

    private MovieDetailContract.Presenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        setPresenter(new MovieDetailPresenter());
    }

    @Override
    public void setPresenter(MovieDetailContract.Presenter presenter) {
        this.presenter = presenter;
    }
}
