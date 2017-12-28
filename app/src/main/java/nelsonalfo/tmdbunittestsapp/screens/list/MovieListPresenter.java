package nelsonalfo.tmdbunittestsapp.screens.list;

import java.util.List;

import nelsonalfo.tmdbunittestsapp.api.TheMovieDbRestApi;
import nelsonalfo.tmdbunittestsapp.command.Command;
import nelsonalfo.tmdbunittestsapp.command.CommandFactory;
import nelsonalfo.tmdbunittestsapp.models.MovieResume;


/**
 * Created by nelso on 27/12/2017.
 */

public class MovieListPresenter implements MovieListContract.Presenter, Command.Listener<List<MovieResume>> {
    private MovieListContract.View view;
    private TheMovieDbRestApi service;
    private Command<List<MovieResume>> command;


    public MovieListPresenter(MovieListContract.View view, TheMovieDbRestApi service) {
        this.view = view;
        this.service = service;
        this.command = CommandFactory.createCommandGetMovies(service);
    }

    @Override
    public void callApi() {
        command.setListener(this);
        command.run();
    }

    @Override
    public void receiveValue(List<MovieResume> value) {

    }

    @Override
    public void notifyError(String errorStatus) {

    }
}
