package nelsonalfo.tmdbunittestsapp.screens.detail;

import nelsonalfo.tmdbunittestsapp.api.ApiStatus;
import nelsonalfo.tmdbunittestsapp.command.detail.MovieDetailCommand;
import nelsonalfo.tmdbunittestsapp.models.MovieDetail;


/**
 * Created by nelso on 31/12/2017.
 */
public class MovieDetailPresenter implements MovieDetailContract.Presenter {
    private final MovieDetailContract.View view;
    private final MovieDetailCommand command;


    public MovieDetailPresenter(MovieDetailContract.View view, MovieDetailCommand command) {
        if (view == null || command == null) {
            throw new IllegalArgumentException("All the params are needed");
        }

        this.view = view;
        this.command = command;
        this.command.setListener(this);
    }

    @Override
    public void callApi() {
        command.execute();
    }

    @Override
    public void receiveMovieDetail(MovieDetail movieDetail) {
        if (movieDetail == null) {
            view.finishAndShowNoDetailsMessage();
        } else {
            view.showMovieDetail(movieDetail);
        }
    }

    @Override
    public void notifyError(String errorStatus) {
        if (errorStatus != null && !errorStatus.isEmpty()) {
            handleApiErrorStatus(errorStatus);
        } else {
            view.finishAndShowUnknownErrorMessage();
        }
    }

    private void handleApiErrorStatus(String errorStatus) {
        switch (errorStatus) {
            case ApiStatus.SERVER_ERROR:
                view.finishAndShowConnectionProblemsMessage();
                break;
            case ApiStatus.CLIENT_ERROR:
                view.finishAndShowNoDetailsMessage();
                break;
            case ApiStatus.NETWORK_ERROR:
                view.finishAndShowConnectionProblemsMessage();
                break;
            case ApiStatus.NO_RESULT:
                view.finishAndShowNoDetailsMessage();
                break;
        }
    }
}
