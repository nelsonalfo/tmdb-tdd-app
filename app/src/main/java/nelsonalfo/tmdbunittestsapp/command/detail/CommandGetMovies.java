package nelsonalfo.tmdbunittestsapp.command.detail;

import android.support.annotation.NonNull;

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

public class CommandGetMovies implements Command<List<MovieResume>>, Callback<MoviesResponse> {
    private final TheMovieDbRestApi service;
    private Listener<List<MovieResume>> listener;


    public CommandGetMovies(TheMovieDbRestApi service) {

        this.service = service;
    }

    @Override
    public void run() throws IllegalArgumentException {
        if (service == null || listener == null) {
            throw new IllegalArgumentException("An instance of TheMovieDbRestApi and an instance of Command.Listener are required");
        }

        Call<MoviesResponse> caller = service.getMovies(Constants.MOST_POPULAR_MOVIES, Constants.API_KEY);
        caller.enqueue(this);
    }

    @Override
    public void setListener(Listener<List<MovieResume>> listener) {
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
            listener.receiveValue(moviesResponse.results);
        } else {
            listener.notifyError(ApiStatus.NO_RESULT);
        }
    }

    private void handleError(@NonNull Response<MoviesResponse> response) {
        switch (response.code()) {
            case ApiStatus.Code.SERVER_ERROR:
                listener.notifyError(ApiStatus.SERVER_ERROR);
                break;
        }
    }

    @Override
    public void onFailure(@NonNull Call<MoviesResponse> call, @NonNull Throwable ex) {
        //TODO
    }
}
