package nelsonalfo.tmdbunittestsapp.screens.list;

import java.util.List;

import nelsonalfo.tmdbunittestsapp.models.MovieResume;


/**
 * Created by nelso on 27/12/2017.
 */

public interface MovieListContract {
    interface View {
        void setPresenter(Presenter presenter);

        void showCantRequestTheMoviesMessage();

        void showMovies(List<MovieResume> movies);

        void showThereIsNoMovies();

        void showConnectionProblemsMessage();

        void showUnknownErrorMessage();
    }

    interface Presenter {

        void callApi();
    }
}
