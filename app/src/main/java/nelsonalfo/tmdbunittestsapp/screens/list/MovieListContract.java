package nelsonalfo.tmdbunittestsapp.screens.list;

import java.util.List;

import nelsonalfo.tmdbunittestsapp.models.MovieResume;
import nelsonalfo.tmdbunittestsapp.models.TmdbConfiguration;


/**
 * Created by nelso on 27/12/2017.
 */

public interface MovieListContract {
    interface View {
        void setPresenter(Presenter presenter);

        void showMovies(List<MovieResume> movies);

        void showThereIsNoMovies();

        void showConnectionProblemsMessage();

        void showUnknownErrorMessage();

        void setConfiguration(TmdbConfiguration configuration);
    }

    interface Presenter {

        void callApi();
    }
}
