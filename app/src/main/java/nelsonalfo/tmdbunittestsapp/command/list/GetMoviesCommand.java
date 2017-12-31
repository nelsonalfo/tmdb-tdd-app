package nelsonalfo.tmdbunittestsapp.command.list;

import android.support.annotation.NonNull;

import java.io.IOException;
import java.util.List;

import nelsonalfo.tmdbunittestsapp.api.ApiStatus;
import nelsonalfo.tmdbunittestsapp.api.TheMovieDbRestApi;
import nelsonalfo.tmdbunittestsapp.command.Command;
import nelsonalfo.tmdbunittestsapp.models.Constants;
import nelsonalfo.tmdbunittestsapp.models.MovieResume;
import nelsonalfo.tmdbunittestsapp.models.MoviesResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by nelso on 27/12/2017.
 */

public class GetMoviesCommand implements Command<List<MovieResume>>, Callback<MoviesResponse> {
    private static final String EXCEPTION_MESSAGE = "An instance of TheMovieDbRestApi and an instance of Command.Listener are required";

    private final TheMovieDbRestApi service;
    private Listener listener;


    public GetMoviesCommand(TheMovieDbRestApi service) {
        this.service = service;
    }

    @Override
    public void run() throws IllegalArgumentException {
        if (service == null || listener == null) {
            throw new IllegalArgumentException(EXCEPTION_MESSAGE);
        }

        service.getMovies(Constants.MOST_POPULAR_MOVIES, Constants.API_KEY).enqueue(this);
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    @Override
    public void onResponse(@NonNull Call<MoviesResponse> call, @NonNull Response<MoviesResponse> response) {
        if (listener != null) {
            if (response.isSuccessful()) {
                handleSuccess(response);
            } else {
                handleError(response);
            }
        }
    }

    private void handleSuccess(@NonNull Response<MoviesResponse> response) {
        MoviesResponse moviesResponse = response.body();

        if (moviesResponse != null) {
            listener.receiveMovies(moviesResponse.results);
        } else {
            listener.notifyError(ApiStatus.NO_RESULT);
        }
    }

    private void handleError(@NonNull Response<MoviesResponse> response) {
        switch (response.code()) {
            case ApiStatus.Code.SERVER_ERROR:
                listener.notifyError(ApiStatus.SERVER_ERROR);
                break;
            case ApiStatus.Code.CLIENT_ERROR:
                listener.notifyError(ApiStatus.CLIENT_ERROR);
                break;
        }
    }

    @Override
    public void onFailure(@NonNull Call<MoviesResponse> call, @NonNull Throwable ex) {
        if (listener == null) {
            return;
        }

        if (ex instanceof IOException) {
            listener.notifyError(ApiStatus.NETWORK_ERROR);
        }
    }

    public interface Listener extends Command.Listener {
        void receiveMovies(List<MovieResume> listener);
    }
}
