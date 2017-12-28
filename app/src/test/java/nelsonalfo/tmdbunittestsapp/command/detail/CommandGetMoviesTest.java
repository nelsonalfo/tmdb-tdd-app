package nelsonalfo.tmdbunittestsapp.command.detail;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

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
    private ResponseBody responseBody;
    @Mock
    private Command.Listener<List<MovieResume>> listener;

    private CommandGetMovies command;


    @Before
    public void setUp() throws Exception {
        initMocks(this);

        command = new CommandGetMovies(service);
        command.setListener(listener);
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
        } catch (IllegalArgumentException ex) {
            assertThat(ex).hasMessageThat().isEqualTo("An instance of TheMovieDbRestApi class is required");
        }
    }

    @Test
    public void onResponse_theApiReturnedTheRequestedMovies_returnTheListOfMovies() throws Exception {
        MoviesResponse body = new MoviesResponse();
        body.results = Collections.singletonList(new MovieResume());
        Response<MoviesResponse> successResponse = Response.success(body);

        command.onResponse(caller, successResponse);

        verify(listener).onReturnValue(eq(body.results));
        verify(listener, never()).onError();
    }

    @Test
    public void onResponse_noListenerSet_doNothing() throws Exception {
        Response<MoviesResponse> successResponse = Response.success(new MoviesResponse());
        command.setListener(null);

        command.onResponse(caller, successResponse);

        verify(listener, never()).onReturnValue(ArgumentMatchers.<MovieResume>anyList());
        verify(listener, never()).onError();
    }

    @Test
    public void onResponse_theBodyOfTheResponseIsNull_notifyAnErrorHappened() throws Exception {
        Response<MoviesResponse> successResponse = Response.success(null);

        command.onResponse(caller, successResponse);

        verify(listener).onError();
    }

    @Test
    public void onResponse_theApiReturnedAnError_notifyAnErrorHappened() throws Exception {
        Response<MoviesResponse> errorResponse = Response.error(500, responseBody);

        command.onResponse(caller, errorResponse);

        verify(listener).onError();
        verify(listener, never()).onReturnValue(ArgumentMatchers.<MovieResume>anyList());
    }

    @Test
    public void onFailure_anNetworkErrorHappened_notAnErrorHappened() throws Exception {
        IOException networkError = new IOException();

        command.onFailure(caller, networkError);

        verify(listener).onError();
    }

    @Test
    public void onFailure_noListenerSet_doNothing() throws Exception {
        IOException networkError = new IOException();
        command.setListener(null);

        command.onFailure(caller, networkError);

        verify(listener, never()).onError();
    }
}