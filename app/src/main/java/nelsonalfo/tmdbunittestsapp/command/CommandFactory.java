package nelsonalfo.tmdbunittestsapp.command;

import nelsonalfo.tmdbunittestsapp.api.TheMovieDbRestApi;
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


    public static GetMoviesCommand createCommandGetMovies(TheMovieDbRestApi service) {
        if (service == null) throw new IllegalArgumentException(EXCEPTION_MESSAGE);
        return new GetMoviesCommand(service);
    }

    public static GetConfigurationCommand createCommandGetConfiguration(TheMovieDbRestApi service) {
        if (service == null) throw new IllegalArgumentException(EXCEPTION_MESSAGE);
        return new GetConfigurationCommand(service);
    }
}
