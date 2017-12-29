package nelsonalfo.tmdbunittestsapp.command.detail;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import nelsonalfo.tmdbunittestsapp.api.ApiStatus;
import nelsonalfo.tmdbunittestsapp.api.TheMovieDbRestApi;
import nelsonalfo.tmdbunittestsapp.command.Command;
import nelsonalfo.tmdbunittestsapp.models.Constants;
import nelsonalfo.tmdbunittestsapp.models.MovieResume;
import nelsonalfo.tmdbunittestsapp.models.MoviesResponse;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
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
    @Mock
    private Command.Listener<List<MovieResume>> listener;
    @Mock
    private ResponseBody errorBody;

    private CommandGetMovies command;


    public CommandGetMoviesTest() {
    }

    @Before
    public void setUp() throws Exception {
        initMocks(this);

        command = new CommandGetMovies(service);
        command.setListener(listener);
    }

    @Test
    public void run_serviceIsSetAndListenerIsSet_callGetMoviesApi() throws Exception {
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
        } catch (IllegalArgumentException ex) {
            assertThat(ex).hasMessageThat().isEqualTo("An instance of TheMovieDbRestApi and an instance of Command.Listener are required");
        }
    }

    @Test
    public void run_listenerIsNull_throwAnException() throws Exception {
        command.setListener(null);

        try {
            command.run();
        } catch (IllegalArgumentException ex) {
            assertThat(ex).hasMessageThat().isEqualTo("An instance of TheMovieDbRestApi and an instance of Command.Listener are required");
        }
    }

    @Test
    public void onResponse_theApiReturnedAListOfMovies_returnTheGivenList() throws Exception {
        MoviesResponse body = new MoviesResponse();
        body.results = Collections.singletonList(new MovieResume());
        Response<MoviesResponse> response = Response.success(body);

        command.onResponse(caller, response);

        verify(listener).receiveValue(eq(body.results));
    }

    @Test
    public void onResponse_theResponseIsNull_notifyAnError() throws Exception {
        Response<MoviesResponse> response = Response.success(null);

        command.onResponse(caller, response);

        verify(listener, never()).receiveValue(ArgumentMatchers.<MovieResume>anyList());
        verify(listener).notifyError(eq(ApiStatus.NO_RESULT));
    }

    @Test
    public void onResponse_listenerNotSet_doNothing() throws Exception {
        Response<MoviesResponse> response = Response.success(new MoviesResponse());

        command.setListener(null);
        command.onResponse(caller, response);

        verify(listener, never()).receiveValue(ArgumentMatchers.<MovieResume>anyList());
        verify(listener, never()).notifyError(anyString());
    }

    @Test
    public void onResponse_theApiReturnAnServerError_notifyTheError() throws Exception {
        Response<MoviesResponse> errorResponse = Response.error(ApiStatus.Code.SERVER_ERROR, errorBody);

        command.onResponse(caller, errorResponse);

        verify(listener, never()).receiveValue(ArgumentMatchers.<MovieResume>anyList());
        verify(listener).notifyError(eq(ApiStatus.SERVER_ERROR));
    }

    @Test
    public void onResponse_theApiReturnAnClientError_notifyTheError() throws Exception {
        Response<MoviesResponse> errorResponse = Response.error(ApiStatus.Code.CLIENT_ERROR, errorBody);

        command.onResponse(caller, errorResponse);

        verify(listener, never()).receiveValue(ArgumentMatchers.<MovieResume>anyList());
        verify(listener).notifyError(eq(ApiStatus.CLIENT_ERROR));
    }

    @Test
    public void onFailure_networkError_notifyTheError() throws Exception {
        IOException exception = new IOException();

        command.onFailure(caller, exception);

        verify(listener).notifyError(eq(ApiStatus.NETWORK_ERROR));
    }

    @Test
    public void onFailure_listenerNotSet_doNothing() throws Exception {
        IOException exception = new IOException();

        command.setListener(null);
        command.onFailure(caller, exception);

        verify(listener, never()).notifyError(anyString());
    }
}