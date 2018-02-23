package nelsonalfo.tmdbunittestsapp.api;

import nelsonalfo.tmdbunittestsapp.models.MovieDetail;
import nelsonalfo.tmdbunittestsapp.models.MoviesResponse;
import nelsonalfo.tmdbunittestsapp.models.TmdbConfiguration;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


/**
 * Created by nelso on 26/12/2017.
 */
public interface TheMovieDbRestApi {
    @GET("discover/movie")
    Call<MoviesResponse> getMovies(@Query("sort_by") String sortBy, @Query("api_key") String apiKey);

    @GET("movie/top_rated")
    Call<MoviesResponse> getTopRatedMovies(@Query("api_key") String apiKey);

    @GET("movie/upcoming")
    Call<MoviesResponse> getUpcomingMovies(@Query("api_key") String apiKey);

    @GET("movie/{movie_id}")
    Call<MovieDetail> getMovieDetail(@Path("movie_id") Integer movieId, @Query("api_key") String apiKey);

    @GET("configuration")
    Call<TmdbConfiguration> getConfiguration(@Query("api_key") String apiKey);
}
