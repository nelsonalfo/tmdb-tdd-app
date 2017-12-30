package nelsonalfo.tmdbunittestsapp.command;

import java.util.List;

import nelsonalfo.tmdbunittestsapp.api.TheMovieDbRestApi;
import nelsonalfo.tmdbunittestsapp.command.detail.CommandGetConfiguration;
import nelsonalfo.tmdbunittestsapp.command.detail.CommandGetMovies;
import nelsonalfo.tmdbunittestsapp.models.MovieResume;
import nelsonalfo.tmdbunittestsapp.models.TmdbConfiguration;


/**
 * Created by nelso on 28/12/2017.
 */

public class CommandFactory {
    private static final String EXCEPTION_MESSAGE = "Cant create the command without a TheMovieDbRestApi instance";


    public CommandFactory() {
        throw new IllegalArgumentException("Cant be instantiated");
    }


    public static Command<List<MovieResume>> createCommandGetMovies(TheMovieDbRestApi service) {
        if (service == null) throw new IllegalArgumentException(EXCEPTION_MESSAGE);
        return new CommandGetMovies(service);
    }

    public static Command<TmdbConfiguration> createCommandGetConfiguration(TheMovieDbRestApi service) {
        if (service == null) throw new IllegalArgumentException(EXCEPTION_MESSAGE);
        return new CommandGetConfiguration(service);
    }
}
