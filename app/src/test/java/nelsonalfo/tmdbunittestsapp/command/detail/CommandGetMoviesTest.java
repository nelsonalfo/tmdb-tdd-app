package nelsonalfo.tmdbunittestsapp.command.detail;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;

import nelsonalfo.tmdbunittestsapp.api.TheMovieDbRestApi;
import nelsonalfo.tmdbunittestsapp.models.Constants;
import nelsonalfo.tmdbunittestsapp.models.MoviesResponse;
import retrofit2.Call;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;


/**
 * Created by nelso on 27/12/2017.
 */
public class CommandGetMoviesTest {
    @Mock
    private TheMovieDbRestApi service;
    @Mock
    private Call<MoviesResponse> caller;

    private CommandGetMovies command;


    public CommandGetMoviesTest() {
    }

    @Before
    public void setUp() throws Exception {
        initMocks(this);

        command = new CommandGetMovies(service);
    }

    @Test
    public void run_serviceIsSet_callGetMoviesApi() throws Exception {
        when(service.getMovies(anyString(), anyString())).thenReturn(caller);

        command.run();

        verify(service).getMovies(eq(Constants.MOST_POPULAR_MOVIES), eq(Constants.API_KEY));
        verify(caller).enqueue(eq(command));
    }

    @Test
    public void run_serviceIsNull_throwAnException() throws Exception {
        command = new CommandGetMovies(null);

        try {
            command.run();
        }catch (IllegalArgumentException ex){
            assertThat(ex).hasMessageThat().isEqualTo("An instance of TheMovieDbRestApi class is required");
        }
    }

    @Test
    @Ignore
    public void returnValue() throws Exception {
        //TODO
    }
}