package nelsonalfo.tmdbunittestsapp.screens.list;

import java.util.List;

import nelsonalfo.tmdbunittestsapp.command.Command;
import nelsonalfo.tmdbunittestsapp.models.MovieResume;


/**
 * Created by nelso on 27/12/2017.
 */

public class MovieListPresenter implements MovieListContract.Presenter, Command.Listener<List<MovieResume>> {
    private MovieListContract.View view;
    private Command<List<MovieResume>> command;


    public MovieListPresenter(MovieListContract.View view, Command<List<MovieResume>> command) {
        if(view == null || command == null) {
            throw new IllegalArgumentException("The params are needed");
        }

        this.view = view;
        this.command = command;
    }

    @Override
    public void callApi() {
        if (command == null) {
            throw new IllegalArgumentException("The command is needed");
        }

        command.setListener(this);
        command.run();
    }

    @Override
    public void receiveValue(List<MovieResume> value) {
        view.showMovies(value);
    }

    @Override
    public void notifyError(String errorStatus) {

    }
}
