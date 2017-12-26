package nelsonalfo.tmdbunittetsapp.api;

import nelsonalfo.tmdbunittetsapp.models.MovieDetail;
import nelsonalfo.tmdbunittetsapp.models.MoviesResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


/**
 * Created by nelso on 26/12/2017.
 */

public interface TheMovieDbRestApi {
    @GET("/discover/movie")
    Call<MoviesResponse> getMovies(@Query("sort_by") String sortBy, @Query("api_key") String apiKey);

    @GET("/movie/{movie_id}")
    Call<MovieDetail> getMovieDetail(@Path("movie_id") Integer movieId, @Query("api_key") String apiKey);
}
