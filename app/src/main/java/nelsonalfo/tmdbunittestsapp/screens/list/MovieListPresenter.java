package nelsonalfo.tmdbunittestsapp.screens.list;

import java.util.List;

import nelsonalfo.tmdbunittestsapp.api.ApiStatus;
import nelsonalfo.tmdbunittestsapp.command.list.GetConfigurationCommand;
import nelsonalfo.tmdbunittestsapp.command.list.GetMoviesCommand;
import nelsonalfo.tmdbunittestsapp.models.MovieResume;
import nelsonalfo.tmdbunittestsapp.models.TmdbConfiguration;


/**
 * Created by nelso on 27/12/2017.
 */

public class MovieListPresenter implements MovieListContract.Presenter, GetMoviesCommand.Listener, GetConfigurationCommand.Listener {
    private MovieListContract.View view;
    private final GetMoviesCommand moviesCommand;
    private final GetConfigurationCommand configCommand;


    public MovieListPresenter(MovieListContract.View view, GetMoviesCommand moviesCommand, GetConfigurationCommand configCommand) throws IllegalArgumentException {
        if (view == null || moviesCommand == null || configCommand == null) {
            throw new IllegalArgumentException("All the params are needed");
        }

        this.view = view;

        this.configCommand = configCommand;
        this.configCommand.setListener(this);

        this.moviesCommand = moviesCommand;
        this.moviesCommand.setListener(this);
    }

    @Override
    public void callApi() {
        configCommand.setListener(this);
        configCommand.execute();
    }

    @Override
    public void receiveConfiguration(TmdbConfiguration configuration) {
        if (configuration != null) {
            view.setConfiguration(configuration);
            moviesCommand.execute();
        } else {
            view.showThereIsNoMovies();
        }
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
