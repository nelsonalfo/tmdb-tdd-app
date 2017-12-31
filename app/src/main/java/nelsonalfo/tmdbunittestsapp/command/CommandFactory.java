package nelsonalfo.tmdbunittestsapp.command;

import nelsonalfo.tmdbunittestsapp.api.TheMovieDbRestApi;
import nelsonalfo.tmdbunittestsapp.command.detail.MovieDetailCommand;
import nelsonalfo.tmdbunittestsapp.command.list.GetConfigurationCommand;
import nelsonalfo.tmdbunittestsapp.command.list.GetMoviesCommand;


/**
 * Created by nelso on 28/12/2017.
 */

public class CommandFactory {
    private static final String EXCEPTION_MESSAGE = "Cant create the command without a TheMovieDbRestApi instance";


    public CommandFactory() {
        throw new IllegalArgumentException("Cant be instantiated");
    }


    public static GetMoviesCommand createGetMoviesCommand(TheMovieDbRestApi service) {
        if (service == null) throw new IllegalArgumentException(EXCEPTION_MESSAGE);
        return new GetMoviesCommand(service);
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
