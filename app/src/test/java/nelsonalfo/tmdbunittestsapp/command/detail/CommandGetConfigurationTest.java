package nelsonalfo.tmdbunittestsapp.command.detail;

import com.google.common.truth.Truth;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.io.IOException;

import nelsonalfo.tmdbunittestsapp.api.ApiStatus;
import nelsonalfo.tmdbunittestsapp.api.TheMovieDbRestApi;
import nelsonalfo.tmdbunittestsapp.command.Command;
import nelsonalfo.tmdbunittestsapp.models.Constants;
import nelsonalfo.tmdbunittestsapp.models.TmdbConfiguration;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;


/**
 * Created by nelso on 30/12/2017.
 */
public class CommandGetConfigurationTest {
    private static final String EXPECTED_EXCEPTION_MESSAGE = "An instance of TheMovieDbRestApi and an instance of Command.Listener are required";

    @Mock
    TheMovieDbRestApi service;
    @Mock
    private Call<TmdbConfiguration> caller;
    @Mock
    private Command.Listener<TmdbConfiguration> listener;
    @Mock
    private ResponseBody errorBody;

    private CommandGetConfiguration command;


    @Before
    public void setUp() throws Exception {
        initMocks(this);

        when(service.getConfiguration(anyString())).thenReturn(caller);

        command = new CommandGetConfiguration(service);
        command.setListener(listener);
    }

    @Test
    public void run_serviceIsSetAndListenerIsSet_callConfigurationApi() throws Exception {

        command.run();

        verify(service).getConfiguration(eq(Constants.API_KEY));
        verify(caller).enqueue(eq(command));
    }

    @Test
    public void run_serviceNotSet_throwException() throws Exception {
        command = new CommandGetConfiguration(null);

        try {
            command.run();
            Assert.fail("The method is expected to throw an IllegalArgumentException");
        }catch (IllegalArgumentException ex){
            Truth.assertThat(ex).hasMessageThat().isEqualTo(EXPECTED_EXCEPTION_MESSAGE);
        }
    }

    @Test
    public void run_listenerNotSet_throwException() throws Exception {
        command.setListener(null);

        try {
            command.run();
            Assert.fail("The method is expected to throw an IllegalArgumentException");
        }catch (IllegalArgumentException ex){
            Truth.assertThat(ex).hasMessageThat().isEqualTo(EXPECTED_EXCEPTION_MESSAGE);
        }
    }

    @Test
    public void onResponse_theApiReturnedTheConfiguration_returnTheGivenConfiguration() throws Exception {
        TmdbConfiguration configuration = new TmdbConfiguration();
        Response<TmdbConfiguration> response = Response.success(configuration);

        command.onResponse(caller, response);

        verify(listener).receiveValue(eq(configuration));
    }

    @Test
    public void onResponse_theResponseIsNull_notifyAnError() throws Exception {
        Response<TmdbConfiguration> response = Response.success(null);

        command.onResponse(caller, response);

        verify(listener, never()).receiveValue(any(TmdbConfiguration.class));
        verify(listener).notifyError(eq(ApiStatus.NO_RESULT));
    }

    @Test
    public void onResponse_listenerNotSet_doNothing() throws Exception {
        Response<TmdbConfiguration> response = Response.success(new TmdbConfiguration());

        command.setListener(null);
        command.onResponse(caller, response);

        verify(listener, never()).receiveValue(any(TmdbConfiguration.class));
        verify(listener, never()).notifyError(anyString());
    }

    @Test
    public void onResponse_theApiReturnAnServerError_notifyTheError() throws Exception {
        Response<TmdbConfiguration> errorResponse = Response.error(ApiStatus.Code.SERVER_ERROR, errorBody);

        command.onResponse(caller, errorResponse);

        verify(listener, never()).receiveValue(any(TmdbConfiguration.class));
        verify(listener).notifyError(eq(ApiStatus.SERVER_ERROR));
    }

    @Test
    public void onResponse_theApiReturnAnClientError_notifyTheError() throws Exception {
        Response<TmdbConfiguration> errorResponse = Response.error(ApiStatus.Code.CLIENT_ERROR, errorBody);

        command.onResponse(caller, errorResponse);

        verify(listener, never()).receiveValue(any(TmdbConfiguration.class));
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