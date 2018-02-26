package nelsonalfo.tmdbunittestsapp.command.list;

import android.support.annotation.NonNull;

import java.io.IOException;

import nelsonalfo.tmdbunittestsapp.api.ApiStatus;
import nelsonalfo.tmdbunittestsapp.api.TheMovieDbRestApi;
import nelsonalfo.tmdbunittestsapp.command.Command;
import nelsonalfo.tmdbunittestsapp.models.Constants;
import nelsonalfo.tmdbunittestsapp.models.TmdbConfiguration;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by nelso on 30/12/2017.
 */
public class GetConfigurationCommand implements Command<TmdbConfiguration>, Callback<TmdbConfiguration> {
    private static final String EXCEPTION_MESSAGE = "An instance of TheMovieDbRestApi and an instance of GetConfigurationCommand.Listener are required";

    private final TheMovieDbRestApi service;
    private Listener listener;


    public GetConfigurationCommand(TheMovieDbRestApi service) {
        this.service = service;
    }

    @Override
    public void execute() {
        if(service == null || listener == null) {
            throw new IllegalArgumentException(EXCEPTION_MESSAGE);
        }

        service.getConfiguration(Constants.API_KEY).enqueue(this);
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    @Override
    public void onResponse(@NonNull Call<TmdbConfiguration> call, @NonNull Response<TmdbConfiguration> response) {
        if (listener != null) {
            if (response.isSuccessful()) {
                handleSuccess(response);
            } else {
                handleError(response);
            }
        }
    }

    private void handleSuccess(@NonNull Response<TmdbConfiguration> response) {
        TmdbConfiguration configuration = response.body();

        if (configuration != null) {
            listener.receiveConfiguration(configuration);
        } else {
            listener.notifyError(ApiStatus.NO_RESULT);
        }
    }

    private void handleError(@NonNull Response<TmdbConfiguration> response) {
        switch (response.code()) {
            case ApiStatus.Code.SERVER_ERROR:
                listener.notifyError(ApiStatus.SERVER_ERROR);
                break;
            case ApiStatus.Code.UNSATISFIABLE_REQUEST_ERROR:
                listener.notifyError(ApiStatus.SERVER_ERROR);
                break;
            case ApiStatus.Code.CLIENT_ERROR:
                listener.notifyError(ApiStatus.CLIENT_ERROR);
                break;
        }
    }

    @Override
    public void onFailure(@NonNull Call<TmdbConfiguration> call, @NonNull Throwable ex) {
        if (listener == null) {
            return;
        }

        if (ex instanceof IOException) {
            listener.notifyError(ApiStatus.NETWORK_ERROR);
        }
    }

    public interface Listener extends Command.Listener {
        void receiveConfiguration(TmdbConfiguration configuration);
    }
}
