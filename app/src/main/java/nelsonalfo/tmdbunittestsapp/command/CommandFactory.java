package nelsonalfo.tmdbunittestsapp.command;

import nelsonalfo.tmdbunittestsapp.api.TheMovieDbRestApi;
import nelsonalfo.tmdbunittestsapp.command.detail.MovieDetailCommand;
import nelsonalfo.tmdbunittestsapp.command.list.GetConfigurationCommand;
import nelsonalfo.tmdbunittestsapp.command.list.GetMoviesCommand;
import nelsonalfo.tmdbunittestsapp.models.Constants;


/**
 * Created by nelso on 28/12/2017.
 */

public class CommandFactory {
    private static final String EXCEPTION_MESSAGE = "Cant create the command without a TheMovieDbRestApi instance";


    public CommandFactory() {
        throw new IllegalArgumentException("Cant be instantiated");
    }


    public static GetMoviesCommand createGetPopularMoviesCommand(TheMovieDbRestApi service) {
        if (service == null) {
            throw new IllegalArgumentException(EXCEPTION_MESSAGE);
        }

        final GetMoviesCommand command = new GetMoviesCommand(service);
        command.setCategory(Constants.MOST_POPULAR_MOVIES);

        return command;
    }

    public static GetMoviesCommand createGetTopRatedMoviesCommand(TheMovieDbRestApi service) {
        if (service == null) {
            throw new IllegalArgumentException(EXCEPTION_MESSAGE);
        }

        final GetMoviesCommand command = new GetMoviesCommand(service);
        command.setCategory(Constants.TOP_RATED_MOVIES);

        return command;
    }

    public static GetMoviesCommand createGetUpcomingMoviesCommand(TheMovieDbRestApi service) {
        if (service == null) {
            throw new IllegalArgumentException(EXCEPTION_MESSAGE);
        }

        final GetMoviesCommand command = new GetMoviesCommand(service);
        command.setCategory(Constants.UPCOMING_MOVIES);

        return command;
    }

    public static GetConfigurationCommand createGetConfigurationCommand(TheMovieDbRestApi service) {
        if (service == null) throw new IllegalArgumentException(EXCEPTION_MESSAGE);
        return new GetConfigurationCommand(service);
    }

    public static MovieDetailCommand createGetMovieDetailCommand(TheMovieDbRestApi service, int movieId) {
        if (service == null) throw new IllegalArgumentException(EXCEPTION_MESSAGE);
        return new MovieDetailCommand(service, movieId);
    }


}
