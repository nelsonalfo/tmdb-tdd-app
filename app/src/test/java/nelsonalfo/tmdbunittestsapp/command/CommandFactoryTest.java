package nelsonalfo.tmdbunittestsapp.command;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import nelsonalfo.tmdbunittestsapp.api.TheMovieDbRestApi;
import nelsonalfo.tmdbunittestsapp.models.MovieResume;
import nelsonalfo.tmdbunittestsapp.models.TmdbConfiguration;

import static com.google.common.truth.Truth.assertThat;


/**
 * Created by nelso on 30/12/2017.
 */
public class CommandFactoryTest {
    private static final String EXPECTED_EXCEPTION_MESSAGE = "Cant create the command without a TheMovieDbRestApi instance";

    @Mock
    private TheMovieDbRestApi service;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void createCommandGetMovies_theServiceIsSet_createAnInstanceOfTheCommand() throws Exception {
        Command<List<MovieResume>> command = CommandFactory.createCommandGetMovies(service);

        assertThat(command).isNotNull();
    }

    @Test
    public void createCommandGetMovies_theServiceIsNull_throwException() throws Exception {
        try {
            CommandFactory.createCommandGetMovies(null);
            Assert.fail("Is expected to throw an IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            assertThat(ex).hasMessageThat().isEqualTo(EXPECTED_EXCEPTION_MESSAGE);
        }
    }

    @Test
    public void createCommandGetConfiguration_theServiceIsSet_createAnInstanceOfTheCommand() throws Exception {
        Command<TmdbConfiguration> command = CommandFactory.createCommandGetConfiguration(service);

        assertThat(command).isNotNull();
    }

    @Test
    public void createCommandGetConfiguration_theServiceIsNull_throwException() throws Exception {
        try {
            CommandFactory.createCommandGetConfiguration(null);
            Assert.fail("Is expected to throw an IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            assertThat(ex).hasMessageThat().isEqualTo(EXPECTED_EXCEPTION_MESSAGE);
        }
    }
}