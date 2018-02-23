package nelsonalfo.tmdbunittestsapp.api;

import android.content.Context;

import java.io.File;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static nelsonalfo.tmdbunittestsapp.models.Constants.API_BASE_URL;


/**
 * Created by nelso on 26/12/2017.
 */

public class ApiServiceGenerator {
    private static final int MB_10 = 10 * 1024 * 1024;

    private final Retrofit retrofit;

    public ApiServiceGenerator(Context context) {
        final HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
        final File httpCacheDirectory = new File(context.getCacheDir(), "httpCache");
        final Cache cache = new Cache(httpCacheDirectory, MB_10);
        final OfflineCacheInterceptor cacheInterceptor = new OfflineCacheInterceptor();

        final OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                .cache(cache)
                .addInterceptor(loggingInterceptor)
                .addInterceptor(cacheInterceptor);

        final Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create());

        retrofit = builder.client(httpClient.build()).build();
    }

    public TheMovieDbRestApi createClient() {
        return retrofit.create(TheMovieDbRestApi.class);
    }
}
