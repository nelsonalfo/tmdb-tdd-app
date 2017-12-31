package nelsonalfo.tmdbunittestsapp.screens.list;

import java.util.List;

import nelsonalfo.tmdbunittestsapp.api.ApiStatus;
import nelsonalfo.tmdbunittestsapp.command.list.GetConfigurationCommand;
import nelsonalfo.tmdbunittestsapp.command.list.GetMoviesCommand;
import nelsonalfo.tmdbunittestsapp.models.MovieResume;


/**
 * Created by nelso on 27/12/2017.
 */

public class MovieListPresenter implements MovieListContract.Presenter, GetMoviesCommand.Listener {
    private MovieListContract.View view;
    private final GetMoviesCommand command;
    private final GetConfigurationCommand configCommand;


    public MovieListPresenter(MovieListContract.View view, GetMoviesCommand moviesCommand, GetConfigurationCommand configCommand) {
        this.configCommand = configCommand;
        if (view == null || moviesCommand == null) {
            throw new IllegalArgumentException("The params are needed");
        }

        this.view = view;
        this.command = moviesCommand;
    }

    @Override
    public void callApi() {
        command.setListener(this);
        command.run();
    }

    @Override
    public void receiveMovies(List<MovieResume> movies) {
        if (movies != null && !movies.isEmpty()) {
            view.showMovies(movies);
        } else {
            view.showThereIsNoMovies();
        }
    }

    @Override
    public void notifyError(String errorStatus) {
        if (errorStatus != null && !errorStatus.isEmpty()) {
            handleApiErrorStatus(errorStatus);
        } else {
            view.showUnknownErrorMessage();
        }
    }

    private void handleApiErrorStatus(String errorStatus) {
        switch (errorStatus) {
            case ApiStatus.SERVER_ERROR:
                view.showConnectionProblemsMessage();
                break;
            case ApiStatus.CLIENT_ERROR:
                view.showThereIsNoMovies();
                break;
            case ApiStatus.NETWORK_ERROR:
                view.showConnectionProblemsMessage();
                break;
            case ApiStatus.NO_RESULT:
                view.showThereIsNoMovies();
                break;
        }
    }
}
