package nelsonalfo.tmdbunittestsapp.api;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static nelsonalfo.tmdbunittestsapp.models.Constants.API_BASE_URL;


/**
 * Created by nelso on 26/12/2017.
 */

public class ApiServiceGenerator {
    private static HttpLoggingInterceptor logging = new HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY);

    private final static OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
            .addInterceptor(logging);


    private final static Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create());

    private final static Retrofit retrofit = builder
            .client(httpClient.build())
            .build();

    public static TheMovieDbRestApi createClient() {
        return retrofit.create(TheMovieDbRestApi.class);
    }
}
