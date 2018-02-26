package nelsonalfo.tmdbunittestsapp.command.detail;

import android.support.annotation.NonNull;

import java.io.IOException;

import nelsonalfo.tmdbunittestsapp.api.ApiStatus;
import nelsonalfo.tmdbunittestsapp.api.TheMovieDbRestApi;
import nelsonalfo.tmdbunittestsapp.command.Command;
import nelsonalfo.tmdbunittestsapp.models.Constants;
import nelsonalfo.tmdbunittestsapp.models.MovieDetail;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by nelso on 31/12/2017.
 */
public class MovieDetailCommand implements Command, Callback<MovieDetail> {
    private static final String EXCEPTION_MESSAGE = "An instance of TheMovieDbRestApi and an instance of MovieDetailCommand.Listener are required";

    private final TheMovieDbRestApi service;
    private final int movieId;
    private Listener listener;

    public MovieDetailCommand(TheMovieDbRestApi service, int movieId) {
        this.service = service;
        this.movieId = movieId;
    }

    @Override
    public void execute() {
        if (service == null || listener == null) {
            throw new IllegalArgumentException(EXCEPTION_MESSAGE);
        }

        service.getMovieDetail(movieId, Constants.API_KEY).enqueue(this);
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    @Override
    public void onResponse(@NonNull Call<MovieDetail> call, @NonNull Response<MovieDetail> response) {
        if (listener != null) {
            if (response.isSuccessful()) {
                handleSuccess(response);
            } else {
                handleError(response);
            }
        }
    }

    private void handleSuccess(@NonNull Response<MovieDetail> response) {
        MovieDetail movieDetail = response.body();

        if (movieDetail != null) {
            listener.receiveMovieDetail(movieDetail);
        } else {
            listener.notifyError(ApiStatus.NO_RESULT);
        }
    }

    private void handleError(@NonNull Response<MovieDetail> response) {
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
    public void onFailure(@NonNull Call<MovieDetail> call, @NonNull Throwable ex) {
        if (listener == null) {
            return;
        }

        if (ex instanceof IOException) {
            listener.notifyError(ApiStatus.NETWORK_ERROR);
        }
    }

    public interface Listener extends Command.Listener {
        void receiveMovieDetail(MovieDetail movieDetail);
    }
}
