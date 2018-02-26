package nelsonalfo.tmdbunittestsapp.screens.detail;

import nelsonalfo.tmdbunittestsapp.command.detail.MovieDetailCommand;
import nelsonalfo.tmdbunittestsapp.models.MovieDetail;


/**
 * Created by nelso on 31/12/2017.
 */

public interface MovieDetailContract {
    interface View {
        void setPresenter(Presenter presenter);

        void showMovieDetail(MovieDetail movieDetail);

        void finishAndShowNoDetailsMessage();

        void finishAndShowConnectionProblemsMessage();

        void finishAndShowUnknownErrorMessage();
    }

    interface Presenter extends MovieDetailCommand.Listener {

        void callApi();
    }
}
