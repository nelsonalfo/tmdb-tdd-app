package nelsonalfo.tmdbunittestsapp.screens.list;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.List;

import nelsonalfo.tmdbunittestsapp.api.ApiStatus;
import nelsonalfo.tmdbunittestsapp.command.list.GetConfigurationCommand;
import nelsonalfo.tmdbunittestsapp.command.list.GetMoviesCommand;
import nelsonalfo.tmdbunittestsapp.models.MovieResume;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;


/**
 * Created by nelso on 27/12/2017.
 */
@RunWith(PowerMockRunner.class)
public class MovieListPresenterTest {
    @Mock
    MovieListContract.View view;
    @Mock
    private GetMoviesCommand moviesCommand;
    @Mock
    private GetConfigurationCommand configCommand;
    @Captor
    private ArgumentCaptor<List<MovieResume>> moviesArgumentCaptor;

    private MovieListPresenter presenter;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        presenter = new MovieListPresenter(view, moviesCommand, configCommand);
    }

    @Test
    public void createPresenter_noViewSet_throwException() throws Exception {
        try {
            presenter = new MovieListPresenter(null, moviesCommand, null);
        } catch (IllegalArgumentException ex) {
            assertThat(ex).hasMessageThat().isEqualTo("The params are needed");
        }
    }

    @Test
    public void createPresenter_noGetMoviesCommandSet_throwException() throws Exception {
        try {
            presenter = new MovieListPresenter(null, moviesCommand, null);
        } catch (IllegalArgumentException ex) {
            assertThat(ex).hasMessageThat().isEqualTo("The params are needed");
        }
    }

    @Test
    public void createPresenter_noGetConfigurationCommandSet_throwException() throws Exception {
        try {
            presenter = new MovieListPresenter(view, moviesCommand, null);
        } catch (IllegalArgumentException ex) {
            assertThat(ex).hasMessageThat().isEqualTo("The params are needed");
        }
    }

    @Test
    public void callApi_configCommandIsSet_runTheCommand() throws Exception {
        presenter.callApi();

        //TODO revisar como puedo puedo manejar mas de un listener dentro del presentardor y poder probarlos.
        // Quiza la respuesta este en delegar la resposabilida a otra clase
    }

    @Test
    public void receiveValue_listOfMovies_showListOfMovies() throws Exception {
        ArrayList<MovieResume> movies = new ArrayList<>();
        MovieResume movie = new MovieResume(1, "It");
        movies.add(movie);

        presenter.receiveMovies(movies);

        verify(view).showMovies(moviesArgumentCaptor.capture());
        List<MovieResume> returnedMovies = moviesArgumentCaptor.getValue();
        assertThat(returnedMovies).isNotEmpty();
        assertThat(returnedMovies).hasSize(1);
        assertThat(returnedMovies).contains(movie);
    }

    @Test
    public void receiveValue_listOfMoviesIsNull_showNoMoviesMessage() throws Exception {

        presenter.receiveMovies(null);

        verify(view, never()).showMovies(ArgumentMatchers.<MovieResume>anyList());
        verify(view).showThereIsNoMovies();
    }

    @Test
    public void receiveValue_listOfMoviesIsEmpty_showNoMoviesMessage() throws Exception {
        ArrayList<MovieResume> movies = new ArrayList<>();

        presenter.receiveMovies(movies);

        verify(view, never()).showMovies(ArgumentMatchers.<MovieResume>anyList());
        verify(view).showThereIsNoMovies();
    }

    @Test
    public void notifyError_serverError_showConnectionProblemsMessage() throws Exception {
        String serverError = ApiStatus.SERVER_ERROR;

        presenter.notifyError(serverError);

        verify(view).showConnectionProblemsMessage();
    }

    @Test
    public void notifyError_clientError_showNoMoviesMessage() throws Exception {
        String clientError = ApiStatus.CLIENT_ERROR;

        presenter.notifyError(clientError);

        verify(view).showThereIsNoMovies();
    }

    @Test
    public void notifyError_networkError_showConnectionProblemsMessage() throws Exception {
        String networkError = ApiStatus.NETWORK_ERROR;

        presenter.notifyError(networkError);

        verify(view).showConnectionProblemsMessage();
    }

    @Test
    public void notifyError_noResults_showNoMoviesMessage() throws Exception {
        String noResultError = ApiStatus.NO_RESULT;

        presenter.notifyError(noResultError);

        verify(view).showThereIsNoMovies();
    }

    @Test
    public void notifyError_statusCodeIsNull_showUnknownErrorMessage() throws Exception {
        presenter.notifyError(null);

        verify(view).showUnknownErrorMessage();
    }

    @Test
    public void notifyError_statusCodeIsEmpty_showUnknownErrorMessage() throws Exception {
        presenter.notifyError("");

        verify(view).showUnknownErrorMessage();
    }
}