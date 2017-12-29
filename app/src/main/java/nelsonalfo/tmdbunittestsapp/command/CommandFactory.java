package nelsonalfo.tmdbunittestsapp.command;

import java.util.List;

import nelsonalfo.tmdbunittestsapp.api.TheMovieDbRestApi;
import nelsonalfo.tmdbunittestsapp.command.detail.CommandGetMovies;
import nelsonalfo.tmdbunittestsapp.models.MovieResume;


/**
 * Created by nelso on 28/12/2017.
 */

public class CommandFactory {
    public CommandFactory() {
        throw new IllegalArgumentException("Cant be instantiated");
    }

    public static Command<List<MovieResume>> createCommandGetMovies(TheMovieDbRestApi service){
        return new CommandGetMovies(service);
    }
}
