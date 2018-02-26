package nelsonalfo.tmdbunittestsapp.command.detail;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;

import java.io.IOException;
import java.util.Collections;

import nelsonalfo.tmdbunittestsapp.api.ApiStatus;
import nelsonalfo.tmdbunittestsapp.api.TheMovieDbRestApi;
import nelsonalfo.tmdbunittestsapp.command.list.GetMoviesCommand;
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
public class GetMoviesCommandTest {
    private static final String EXPECTED_EXCEPTION_MESSAGE = "An instance of TheMovieDbRestApi and an instance of GetMoviesCommand.Listener are required";

    @Mock
    private TheMovieDbRestApi service;
    @Mock
    private Call<MoviesResponse> caller;
    @Mock
    private GetMoviesCommand.Listener listener;
    @Mock
    private ResponseBody errorBody;

    private GetMoviesCommand command;


    public GetMoviesCommandTest() {
    }

    @Before
    public void setUp() throws Exception {
        initMocks(this);

        command = new GetMoviesCommand(service);
        command.setListener(listener);
    }

    @Test
    public void execute_serviceIsSetAndListenerIsSetAndCategoryIsMostPopular_callGetPopularMoviesApi() throws Exception {
        when(service.getMovies(anyString(), anyString())).thenReturn(caller);
        command.setCategory(Constants.MOST_POPULAR_MOVIES);

        command.execute();

        verify(service).getMovies(eq(Constants.MOST_POPULAR_MOVIES), eq(Constants.API_KEY));
        verify(caller).enqueue(eq(command));
    }

    @Test
    public void execute_serviceIsSetAndListenerIsSetAndCategoryIsNull_callGetPopularMoviesApi() throws Exception {
        when(service.getMovies(anyString(), anyString())).thenReturn(caller);
        command.setCategory(null);

        command.execute();

        verify(service).getMovies(eq(Constants.MOST_POPULAR_MOVIES), eq(Constants.API_KEY));
        verify(caller).enqueue(eq(command));
    }

    @Test
    public void execute_serviceIsSetAndListenerIsSetAndCategoryIsEmpty_callGetPopularMoviesApi() throws Exception {
        when(service.getMovies(anyString(), anyString())).thenReturn(caller);
        command.setCategory("");

        command.execute();

        verify(service).getMovies(eq(Constants.MOST_POPULAR_MOVIES), eq(Constants.API_KEY));
        verify(caller).enqueue(eq(command));
    }

    @Test
    public void execute_serviceIsSetAndListenerIsSetAndCategoryIsNotSet_callGetPopularMoviesApi() throws Exception {
        when(service.getMovies(anyString(), anyString())).thenReturn(caller);

        command.execute();

        verify(service).getMovies(eq(Constants.MOST_POPULAR_MOVIES), eq(Constants.API_KEY));
        verify(caller).enqueue(eq(command));
    }

    @Test
    public void execute_serviceIsSetAndListenerIsSetAndCategoryIsTopRatedt_callGetTopRatedMoviesApi() throws Exception {
        when(service.getTopRatedMovies(anyString())).thenReturn(caller);
        command.setCategory(Constants.TOP_RATED_MOVIES);

        command.execute();

        verify(service).getTopRatedMovies(eq(Constants.API_KEY));
        verify(caller).enqueue(eq(command));
    }


    @Test
    public void execute_serviceIsNull_throwAnException() throws Exception {
        command = new GetMoviesCommand(null);

        try {
            command.execute();
        } catch (IllegalArgumentException ex) {
            assertThat(ex).hasMessageThat().isEqualTo(EXPECTED_EXCEPTION_MESSAGE);
        }
    }


    @Test
    public void execute_listenerIsNull_throwAnException() throws Exception {
        command.setListener(null);

        try {
            command.execute();
        } catch (IllegalArgumentException ex) {
            assertThat(ex).hasMessageThat().isEqualTo(EXPECTED_EXCEPTION_MESSAGE);
        }
    }

    @Test
    public void onResponse_theApiReturnedAListOfMovies_returnTheGivenList() throws Exception {
        MoviesResponse body = new MoviesResponse();
        body.results = Collections.singletonList(new MovieResume());
        Response<MoviesResponse> response = Response.success(body);

        command.onResponse(caller, response);

        verify(listener).receiveMovies(eq(body.results));
    }

    @Test
    public void onResponse_theResponseIsNull_notifyAnError() throws Exception {
        Response<MoviesResponse> response = Response.success(null);

        command.onResponse(caller, response);

        verify(listener, never()).receiveMovies(ArgumentMatchers.<MovieResume>anyList());
        verify(listener).notifyError(eq(ApiStatus.NO_RESULT));
    }

    @Test
    public void onResponse_listenerNotSet_doNothing() throws Exception {
        Response<MoviesResponse> response = Response.success(new MoviesResponse());

        command.setListener(null);
        command.onResponse(caller, response);

        verify(listener, never()).receiveMovies(ArgumentMatchers.<MovieResume>anyList());
        verify(listener, never()).notifyError(anyString());
    }

    @Test
    public void onResponse_theApiReturnAnServerError_notifyTheError() throws Exception {
        Response<MoviesResponse> errorResponse = Response.error(ApiStatus.Code.SERVER_ERROR, errorBody);

        command.onResponse(caller, errorResponse);

        verify(listener, never()).receiveMovies(ArgumentMatchers.<MovieResume>anyList());
        verify(listener).notifyError(eq(ApiStatus.SERVER_ERROR));
    }

    @Test
    public void onResponse_theApiReturnAnClientError_notifyTheError() throws Exception {
        Response<MoviesResponse> errorResponse = Response.error(ApiStatus.Code.CLIENT_ERROR, errorBody);

        command.onResponse(caller, errorResponse);

        verify(listener, never()).receiveMovies(ArgumentMatchers.<MovieResume>anyList());
        verify(listener).notifyError(eq(ApiStatus.CLIENT_ERROR));
    }

    @Test
    public void onResponse_theApiReturnAnUnsatisfiableRequestError_notifyTheError() throws Exception {
        Response<MoviesResponse> errorResponse = Response.error(ApiStatus.Code.UNSATISFIABLE_REQUEST_ERROR, errorBody);

        command.onResponse(caller, errorResponse);

        verify(listener, never()).receiveMovies(ArgumentMatchers.<MovieResume>anyList());
        verify(listener).notifyError(eq(ApiStatus.SERVER_ERROR));
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