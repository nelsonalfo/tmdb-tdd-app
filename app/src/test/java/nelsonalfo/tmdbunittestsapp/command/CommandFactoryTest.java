package nelsonalfo.tmdbunittestsapp.command;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import nelsonalfo.tmdbunittestsapp.api.TheMovieDbRestApi;
import nelsonalfo.tmdbunittestsapp.command.detail.MovieDetailCommand;
import nelsonalfo.tmdbunittestsapp.command.list.GetConfigurationCommand;
import nelsonalfo.tmdbunittestsapp.command.list.GetMoviesCommand;

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
        GetMoviesCommand command = CommandFactory.createGetMoviesCommand(service);

        assertThat(command).isNotNull();
    }

    @Test
    public void createCommandGetMovies_theServiceIsNull_throwException() throws Exception {
        try {
            CommandFactory.createGetMoviesCommand(null);
            Assert.fail("Is expected to throw an IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            assertThat(ex).hasMessageThat().isEqualTo(EXPECTED_EXCEPTION_MESSAGE);
        }
    }

    @Test
    public void createCommandGetConfiguration_theServiceIsSet_createAnInstanceOfTheCommand() throws Exception {
        GetConfigurationCommand command = CommandFactory.createGetConfigurationCommand(service);

        assertThat(command).isNotNull();
    }

    @Test
    public void createCommandGetConfiguration_theServiceIsNull_throwException() throws Exception {
        try {
            CommandFactory.createGetConfigurationCommand(null);
            Assert.fail("Is expected to throw an IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            assertThat(ex).hasMessageThat().isEqualTo(EXPECTED_EXCEPTION_MESSAGE);
        }
    }

    @Test
    public void createCommandGetMovieDetails_theServiceIsSet_createAnInstanceOfTheCommand() throws Exception {
        int movieId = 1;

        MovieDetailCommand command = CommandFactory.createGetMovieDetailCommand(service, movieId);

        assertThat(command).isNotNull();
    }

    @Test
    public void createCommandGetMovieDetails_theServiceIsNull_throwException() throws Exception {
        int movieId = 1;
        try {
            CommandFactory.createGetMovieDetailCommand(null, movieId);
            Assert.fail("Is expected to throw an IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            assertThat(ex).hasMessageThat().isEqualTo(EXPECTED_EXCEPTION_MESSAGE);
        }
    }
}