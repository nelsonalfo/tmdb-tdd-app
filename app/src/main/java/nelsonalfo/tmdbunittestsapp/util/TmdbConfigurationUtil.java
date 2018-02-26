package nelsonalfo.tmdbunittestsapp.util;

import nelsonalfo.tmdbunittestsapp.models.TmdbConfiguration;


/**
 * Created by nelso on 31/12/2017.
 */

public class TmdbConfigurationUtil {
    private final TmdbConfiguration configuration;

    public TmdbConfigurationUtil(TmdbConfiguration configuration) throws IllegalArgumentException {
        if (configuration == null) {
            throw new IllegalArgumentException("The param can not be null");
        }

        this.configuration = configuration;
    }

    public String getPosterImageUrl(String posterPath) {
        handlePosterImageDependencies(posterPath);

        final String posterSize = configuration.images.posterSizes.get(0);
        final String baseUrl = configuration.images.baseUrl;

        return baseUrl + posterSize + posterPath;
    }

    private void handlePosterImageDependencies(String posterPath) {
        handleImageDependencies();

        if (posterPath == null || posterPath.isEmpty()) {
            throw new IllegalArgumentException("The param can not be null or empty");
        }

        if (configuration.images.posterSizes == null || configuration.images.posterSizes.isEmpty()) {
            throw new IllegalArgumentException("The configuration dont have poster sizes");
        }
    }

    private void handleImageDependencies() {
        if (configuration.images == null) {
            throw new IllegalArgumentException("The configuration dont have images information");
        }

        if (configuration.images.baseUrl == null || configuration.images.baseUrl.isEmpty()) {
            throw new IllegalArgumentException("The configuration dont have base url");
        }
    }

    public String getBackdropImageUrl(String backdropPath) {
        handleBackdropImageDependencies(backdropPath);

        final String backdropSize = configuration.images.backdropSizes.get(0);
        final String baseUrl = configuration.images.baseUrl;

        return baseUrl + backdropSize + backdropPath;
    }

    private void handleBackdropImageDependencies(String backdropPath) {
        handleImageDependencies();

        if (backdropPath == null || backdropPath.isEmpty()) {
            throw new IllegalArgumentException("The param can not be null or empty");
        }

        if (configuration.images.backdropSizes == null || configuration.images.backdropSizes.isEmpty()) {
            throw new IllegalArgumentException("The configuration dont have backdrop sizes");
        }
    }

    public TmdbConfiguration getConfiguration() {
        return configuration;
    }
}
