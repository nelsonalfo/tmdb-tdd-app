package nelsonalfo.tmdbunittestsapp.command.detail;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.io.IOException;

import nelsonalfo.tmdbunittestsapp.api.ApiStatus;
import nelsonalfo.tmdbunittestsapp.api.TheMovieDbRestApi;
import nelsonalfo.tmdbunittestsapp.models.Constants;
import nelsonalfo.tmdbunittestsapp.models.MovieDetail;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;


/**
 * Created by nelso on 31/12/2017.
 */
public class MovieDetailCommandTest {
    private static final String EXPECTED_EXECUTE_EXCEPTION_MESSAGE = "An instance of TheMovieDbRestApi and an instance of MovieDetailCommand.Listener are required";
    private static final String FAIL_EXCEPTION_TEST_MESSAGE = "Expected to throw IllegalArgumentException";

    @Mock
    private TheMovieDbRestApi service;
    @Mock
    private Call<MovieDetail> caller;
    @Mock
    private MovieDetailCommand.Listener listener;
    @Mock
    private ResponseBody errorBody;

    private int movieId;
    private MovieDetailCommand command;


    @Before
    public void setUp() throws Exception {
        initMocks(this);

        movieId = 1;

        command = new MovieDetailCommand(service, movieId);
        command.setListener(listener);
    }

    @Test
    public void run_serviceIsSetAndListenerIsSet_callGetMoviesApi() throws Exception {
        when(service.getMovieDetail(anyInt(), anyString())).thenReturn(caller);

        command.execute();

        verify(service).getMovieDetail(eq(movieId), eq(Constants.API_KEY));
        verify(caller).enqueue(eq(command));
    }

    @Test
    public void run_serviceIsNull_throwAnException() throws Exception {
        command = new MovieDetailCommand(null, movieId);

        try {
            command.execute();
            Assert.fail(FAIL_EXCEPTION_TEST_MESSAGE);
        } catch (IllegalArgumentException ex) {
            assertThat(ex).hasMessageThat().isEqualTo(EXPECTED_EXECUTE_EXCEPTION_MESSAGE);
        }
    }


    @Test
    public void run_listenerIsNull_throwAnException() throws Exception {
        command.setListener(null);

        try {
            command.execute();
            Assert.fail(FAIL_EXCEPTION_TEST_MESSAGE);
        } catch (IllegalArgumentException ex) {
            assertThat(ex).hasMessageThat().isEqualTo(EXPECTED_EXECUTE_EXCEPTION_MESSAGE);
        }
    }

    @Test
    public void onResponse_theApiReturnedAListOfMovies_returnTheGivenList() throws Exception {
        MovieDetail movieDetail = new MovieDetail();
        Response<MovieDetail> response = Response.success(movieDetail);

        command.onResponse(caller, response);

        verify(listener).receiveMovieDetail(eq(movieDetail));
    }

    @Test
    public void onResponse_theResponseIsNull_notifyAnError() throws Exception {
        Response<MovieDetail> response = Response.success(null);

        command.onResponse(caller, response);

        verify(listener, never()).receiveMovieDetail(any(MovieDetail.class));
        verify(listener).notifyError(eq(ApiStatus.NO_RESULT));
    }

    @Test
    public void onResponse_listenerNotSet_doNothing() throws Exception {
        Response<MovieDetail> response = Response.success(new MovieDetail());

        command.setListener(null);
        command.onResponse(caller, response);

        verify(listener, never()).receiveMovieDetail(any(MovieDetail.class));
        verify(listener, never()).notifyError(anyString());
    }

    @Test
    public void onResponse_theApiReturnAnServerError_notifyTheError() throws Exception {
        Response<MovieDetail> errorResponse = Response.error(ApiStatus.Code.SERVER_ERROR, errorBody);

        command.onResponse(caller, errorResponse);

        verify(listener, never()).receiveMovieDetail(any(MovieDetail.class));
        verify(listener).notifyError(eq(ApiStatus.SERVER_ERROR));
    }

    @Test
    public void onResponse_theApiReturnAnClientError_notifyTheError() throws Exception {
        Response<MovieDetail> errorResponse = Response.error(ApiStatus.Code.CLIENT_ERROR, errorBody);

        command.onResponse(caller, errorResponse);

        verify(listener, never()).receiveMovieDetail(any(MovieDetail.class));
        verify(listener).notifyError(eq(ApiStatus.CLIENT_ERROR));
    }

    @Test
    public void onResponse_theApiReturnAnUnsatisfiableRequestError_notifyTheError() throws Exception {
        Response<MovieDetail> errorResponse = Response.error(ApiStatus.Code.UNSATISFIABLE_REQUEST_ERROR, errorBody);

        command.onResponse(caller, errorResponse);

        verify(listener, never()).receiveMovieDetail(any(MovieDetail.class));
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