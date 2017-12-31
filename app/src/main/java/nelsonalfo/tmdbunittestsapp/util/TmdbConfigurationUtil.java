package nelsonalfo.tmdbunittestsapp.util;

import nelsonalfo.tmdbunittestsapp.models.TmdbConfiguration;


/**
 * Created by nelso on 31/12/2017.
 */

public class TmdbConfigurationUtil {
    private final TmdbConfiguration configuration;

    public TmdbConfigurationUtil(TmdbConfiguration configuration) {
        if (configuration == null) {
            throw new IllegalArgumentException("The param can not be null");
        }

        this.configuration = configuration;
    }

    public String getPosterImageUrl(String partialImageUrl) {
        handlePosterImageDependencies(partialImageUrl);

        final String posterSize = configuration.images.posterSizes.get(0);
        final String baseUrl = configuration.images.baseUrl;

        return baseUrl + posterSize + partialImageUrl;
    }

    private void handlePosterImageDependencies(String partialImageUrl) {
        if (configuration.images == null) {
            throw new IllegalArgumentException("The configuration dont have images information");
        }

        if (configuration.images.posterSizes == null || configuration.images.posterSizes.isEmpty()) {
            throw new IllegalArgumentException("The configuration dont have poster sizes");
        }

        if (configuration.images.baseUrl == null || configuration.images.baseUrl.isEmpty()) {
            throw new IllegalArgumentException("The configuration dont have base url");
        }

        if (partialImageUrl == null || partialImageUrl.isEmpty()) {
            throw new IllegalArgumentException("The param can not be null or empty");
        }
    }
}
