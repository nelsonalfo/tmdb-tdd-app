package nelsonalfo.tmdbunittestsapp.screens.list;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.List;

import nelsonalfo.tmdbunittestsapp.command.Command;
import nelsonalfo.tmdbunittestsapp.models.MovieResume;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.verify;


/**
 * Created by nelso on 27/12/2017.
 */
@RunWith(PowerMockRunner.class)
public class MovieListPresenterTest {
    @Mock
    MovieListContract.View view;
    @Mock
    private Command<List<MovieResume>> command;
    @Captor
    private ArgumentCaptor<List<MovieResume>> moviesArgumentCaptor;

    private MovieListPresenter presenter;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        presenter = new MovieListPresenter(view, command);
    }

    @Test
    public void createPresenter_noViewSet_throwException() throws Exception {
        try{
            presenter = new MovieListPresenter(null, command);
        }catch (IllegalArgumentException ex){
            assertThat(ex).hasMessageThat().isEqualTo("The params are needed");
        }
    }

    @Test
    public void createPresenter_noCommandSet_throwException() throws Exception {
        try{
            presenter = new MovieListPresenter(null, command);
        }catch (IllegalArgumentException ex){
            assertThat(ex).hasMessageThat().isEqualTo("The params are needed");
        }
    }

    @Test
    public void callGetMoviesApi_commandIsSet_runTheCommand() throws Exception {
        presenter.callApi();

        verify(command).setListener(presenter);
        verify(command).run();
    }

    @Test
    public void receiveValue_listOfMovies_showListOfMovies() throws Exception {
        ArrayList<MovieResume> movies = new ArrayList<>();
        MovieResume movie = new MovieResume(1, "It");
        movies.add(movie);

        presenter.receiveValue(movies);

        verify(view).showMovies(moviesArgumentCaptor.capture());
        List<MovieResume> returnedMovies = moviesArgumentCaptor.getValue();
        assertThat(returnedMovies).isNotEmpty();
        assertThat(returnedMovies).hasSize(1);
        assertThat(returnedMovies).contains(movie);
    }

    @Test
    @Ignore
    public void receiveValue_listOfMoviesIsNull_showNoMoviesMessage() throws Exception {
        //TODO
    }

    @Test
    @Ignore
    public void receiveValue_listOfMoviesIsEmpty_showNoMoviesMessage() throws Exception {
        //TODO
    }

    @Test
    @Ignore
    public void notifyError_serverError_showConnectionProblemsMessage() throws Exception {
        //TODO
    }

    @Test
    @Ignore
    public void notifyError_clientError_showNoMoviesMessage() throws Exception {
        //TODO
    }

    @Test
    @Ignore
    public void notifyError_networkError_showNoMoviesMessage() throws Exception {
        //TODO
    }

    @Test
    @Ignore
    public void notifyError_noResults_showNoMoviesMessage() throws Exception {
        //TODO
    }
}