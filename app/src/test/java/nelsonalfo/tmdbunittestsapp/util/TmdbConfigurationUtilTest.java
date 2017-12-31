package nelsonalfo.tmdbunittestsapp.util;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;

import nelsonalfo.tmdbunittestsapp.models.Images;
import nelsonalfo.tmdbunittestsapp.models.TmdbConfiguration;

import static com.google.common.truth.Truth.assertThat;


/**
 * Created by nelso on 31/12/2017.
 */
public class TmdbConfigurationUtilTest {
    private static final String FAIL_EXCEPTION_MESSAGE = "Expected to throw IllegalArgumentException";
    private static final String EXPECTED_CREATE_CONFIGURATION_UTIL_EXCEPTION_MESSAGE = "The param can not be null";
    private static final String EXPECTED_GET_IMAGE_EXCEPTION_MESSAGE = "The param can not be null or empty";

    private TmdbConfiguration configuration;

    private TmdbConfigurationUtil configurationUtil;


    @Before
    public void setUp() throws Exception {
        configuration = new TmdbConfiguration();
        configuration.images = new Images();
        configuration.images.posterSizes = Collections.singletonList("w92/");
        configuration.images.backdropSizes = Collections.singletonList("w500/");
        configuration.images.baseUrl = "http://image.tmdb.org/t/p/";

        configurationUtil = new TmdbConfigurationUtil(configuration);
    }

    @Test
    public void createConfigurationUtil_noConfigurationSet_throwException() throws Exception {
        try {
            configurationUtil = new TmdbConfigurationUtil(null);
            Assert.fail(FAIL_EXCEPTION_MESSAGE);
        } catch (IllegalArgumentException ex) {
            assertThat(ex).hasMessageThat().isEqualTo(EXPECTED_CREATE_CONFIGURATION_UTIL_EXCEPTION_MESSAGE);
        }
    }

    @Test
    public void getPosterImageUrl_partialPosterImageUrlIsSet_returnTheCompleteUrl() throws Exception {
        String expectedImageUrl = "http://image.tmdb.org/t/p/w92//adw6Lq9FiC9zjYEpOqfq03ituwp.jpg";
        String partialUrl = "/adw6Lq9FiC9zjYEpOqfq03ituwp.jpg";

        String imageUrl = configurationUtil.getPosterImageUrl(partialUrl);

        assertThat(imageUrl).isEqualTo(expectedImageUrl);
    }

    @Test
    public void getPosterImageUrl_partialPosterImageUrlIsNull_throwException() throws Exception {
        try {
            configurationUtil.getPosterImageUrl(null);
            Assert.fail(FAIL_EXCEPTION_MESSAGE);
        } catch (IllegalArgumentException ex) {
            assertThat(ex).hasMessageThat().isEqualTo(EXPECTED_GET_IMAGE_EXCEPTION_MESSAGE);
        }
    }

    @Test
    public void getPosterImageUrl_partialPosterImageUrlIsEmpty_throwException() throws Exception {
        try {
            configurationUtil.getPosterImageUrl("");
            Assert.fail(FAIL_EXCEPTION_MESSAGE);
        } catch (IllegalArgumentException ex) {
            assertThat(ex).hasMessageThat().isEqualTo(EXPECTED_GET_IMAGE_EXCEPTION_MESSAGE);
        }
    }

    @Test
    public void getPosterImageUrl_posterSizesInConfigurationIsNull_throwException() throws Exception {
        String partialUrl = "/adw6Lq9FiC9zjYEpOqfq03ituwp.jpg";
        configuration.images.posterSizes = null;

        try {
            configurationUtil.getPosterImageUrl(partialUrl);
            Assert.fail(FAIL_EXCEPTION_MESSAGE);
        } catch (IllegalArgumentException ex) {
            assertThat(ex).hasMessageThat().isEqualTo("The configuration dont have poster sizes");
        }
    }

    @Test
    public void getPosterImageUrl_posterInfoInConfigurationIsEmpty_throwException() throws Exception {
        String partialUrl = "/adw6Lq9FiC9zjYEpOqfq03ituwp.jpg";
        configuration.images.posterSizes = new ArrayList<>();

        try {
            configurationUtil.getPosterImageUrl(partialUrl);
            Assert.fail(FAIL_EXCEPTION_MESSAGE);
        } catch (IllegalArgumentException ex) {
            assertThat(ex).hasMessageThat().isEqualTo("The configuration dont have poster sizes");
        }
    }

    @Test
    public void getPosterImageUrl_baseUrlInConfigurationIsNull_throwException() throws Exception {
        String partialUrl = "/adw6Lq9FiC9zjYEpOqfq03ituwp.jpg";
        configuration.images.baseUrl = null;

        try {
            configurationUtil.getPosterImageUrl(partialUrl);
            Assert.fail(FAIL_EXCEPTION_MESSAGE);
        } catch (IllegalArgumentException ex) {
            assertThat(ex).hasMessageThat().isEqualTo("The configuration dont have base url");
        }
    }

    @Test
    public void getPosterImageUrl_baseUrlInConfigurationIsEmpty_throwException() throws Exception {
        String partialUrl = "/adw6Lq9FiC9zjYEpOqfq03ituwp.jpg";
        configuration.images.baseUrl = "";

        try {
            configurationUtil.getPosterImageUrl(partialUrl);
            Assert.fail(FAIL_EXCEPTION_MESSAGE);
        } catch (IllegalArgumentException ex) {
            assertThat(ex).hasMessageThat().isEqualTo("The configuration dont have base url");
        }
    }

    @Test
    public void getPosterImageUrl_imageInfoInConfigurationIsNull_throwException() throws Exception {
        String partialUrl = "/adw6Lq9FiC9zjYEpOqfq03ituwp.jpg";
        configuration.images = null;

        try {
            configurationUtil.getPosterImageUrl(partialUrl);
            Assert.fail(FAIL_EXCEPTION_MESSAGE);
        } catch (IllegalArgumentException ex) {
            assertThat(ex).hasMessageThat().isEqualTo("The configuration dont have images information");
        }
    }
}