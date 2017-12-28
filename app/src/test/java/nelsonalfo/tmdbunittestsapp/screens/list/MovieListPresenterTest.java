package nelsonalfo.tmdbunittestsapp.screens.list;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.List;

import nelsonalfo.tmdbunittestsapp.api.TheMovieDbRestApi;
import nelsonalfo.tmdbunittestsapp.command.Command;
import nelsonalfo.tmdbunittestsapp.command.CommandFactory;
import nelsonalfo.tmdbunittestsapp.models.MovieResume;

import static org.mockito.Mockito.verify;


/**
 * Created by nelso on 27/12/2017.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(CommandFactory.class)
public class MovieListPresenterTest {
    @Mock
    MovieListContract.View view;
    @Mock
    private TheMovieDbRestApi service;
    @Mock
    private Command<List<MovieResume>> command;

    private MovieListPresenter presenter;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(CommandFactory.class);

        PowerMockito.when(CommandFactory.createCommandGetMovies(service)).thenReturn(command);

        presenter = new MovieListPresenter(view, service);
    }

    @Test
    public void callGetMoviesApi_serviceIsSet_runTheCommand() throws Exception {
        presenter.callApi();

        verify(command).setListener(presenter);
        verify(command).run();
    }
}