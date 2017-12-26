package nelsonalfo.tmdbunittetsapp.api;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static nelsonalfo.tmdbunittetsapp.models.Constants.API_BASE_URL;


/**
 * Created by nelso on 26/12/2017.
 */

public class ApiClient {

    public static TheMovieDbRestApi create() {
        final OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        final Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create());

        final Retrofit retrofit = builder
                .client(httpClient.build())
                .build();

        return retrofit.create(TheMovieDbRestApi.class);
    }
}
