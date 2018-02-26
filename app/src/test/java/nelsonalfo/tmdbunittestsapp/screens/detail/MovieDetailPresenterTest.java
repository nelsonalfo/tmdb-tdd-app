package nelsonalfo.tmdbunittestsapp.screens.detail;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.Arrays;

import nelsonalfo.tmdbunittestsapp.api.ApiStatus;
import nelsonalfo.tmdbunittestsapp.command.detail.MovieDetailCommand;
import nelsonalfo.tmdbunittestsapp.models.Genre;
import nelsonalfo.tmdbunittestsapp.models.MovieDetail;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;


/**
 * Created by nelso on 31/12/2017.
 */
public class MovieDetailPresenterTest {
    private static final String FAIL_EXCEPTION_MESSAGE = "Is expected to throw an IllegalArgumentException";
    private static final String EXPECTED_EXCEPTION_MESSAGE = "All the params are needed";

    @Mock
    private MovieDetailContract.View view;
    @Mock
    private MovieDetailCommand command;

    private MovieDetailPresenter presenter;


    @Before
    public void setUp() throws Exception {
        initMocks(this);

        presenter = new MovieDetailPresenter(view, command);
    }

    @Test
    public void createPresenter_allParamsAreSet_initializePresenter() throws Exception {
        presenter = new MovieDetailPresenter(view, command);

        verify(command).setListener(eq(presenter));
    }

    @Test
    public void createPresenter_noViewSet_throwException() throws Exception {
        try {
            presenter = new MovieDetailPresenter(null, command);
            Assert.fail(FAIL_EXCEPTION_MESSAGE);
        } catch (IllegalArgumentException ex) {
            assertThat(ex).hasMessageThat().isEqualTo(EXPECTED_EXCEPTION_MESSAGE);
        }
    }

    @Test
    public void createPresenter_noCommandSet_throwException() throws Exception {
        try {
            presenter = new MovieDetailPresenter(view, null);
            Assert.fail(FAIL_EXCEPTION_MESSAGE);
        } catch (IllegalArgumentException ex) {
            assertThat(ex).hasMessageThat().isEqualTo(EXPECTED_EXCEPTION_MESSAGE);
        }
    }

    @Test
    public void callApi_commandIsSet_runTheCommand() throws Exception {
        presenter.callApi();

        verify(command).execute();
    }

    @Test
    public void receiveMovieDetail_movieDetailReturned_showTheDetail() throws Exception {
        final MovieDetail movieDetail = new MovieDetail();
        movieDetail.originalTitle = "It";
        movieDetail.backdropPath = "/tcheoA2nPATCm2vvXw2hVQoaEFD.jpg";
        movieDetail.posterPath = "/9E2y5Q7WlCVNEhP5GiVTjhEhx1o.jpg";
        movieDetail.voteAverage = 7.2;
        movieDetail.voteCount = 6726;
        movieDetail.genres = Arrays.asList(new Genre("Drama"), new Genre("Horror"), new Genre("Thriller"));
        movieDetail.overview = "In a small town in Maine, seven children known as The Losers Club come face to face with life problems," +
                " bullies and a monster that takes the shape of a clown called Pennywise.";

        presenter.receiveMovieDetail(movieDetail);

        verify(view).showMovieDetail(eq(movieDetail));
    }

    @Test
    public void receiveMovieDetail_movieDetailIsNull_finishAndShowNoDetailsMessage() throws Exception {
        presenter.receiveMovieDetail(null);

        verify(view, never()).showMovieDetail(any(MovieDetail.class));
        verify(view).finishAndShowNoDetailsMessage();
    }

    @Test
    public void notifyError_serverError_finishAndShowConnectionProblemsMessage() throws Exception {
        String serverError = ApiStatus.SERVER_ERROR;

        presenter.notifyError(serverError);

        verify(view).finishAndShowConnectionProblemsMessage();
    }

    @Test
    public void notifyError_clientError_finishAndShowNoDetailsMessage() throws Exception {
        String clientError = ApiStatus.CLIENT_ERROR;

        presenter.notifyError(clientError);

        verify(view).finishAndShowNoDetailsMessage();
    }

    @Test
    public void notifyError_networkError_finishAndShowConnectionProblemsMessage() throws Exception {
        String networkError = ApiStatus.NETWORK_ERROR;

        presenter.notifyError(networkError);

        verify(view).finishAndShowConnectionProblemsMessage();
    }

    @Test
    public void notifyError_noResults_finishAndShowNoDetailsMessage() throws Exception {
        String noResultError = ApiStatus.NO_RESULT;

        presenter.notifyError(noResultError);

        verify(view).finishAndShowNoDetailsMessage();
    }

    @Test
    public void notifyError_statusCodeIsNull_finisAndShowUnknownErrorMessage() throws Exception {
        presenter.notifyError(null);

        verify(view).finishAndShowUnknownErrorMessage();
    }

    @Test
    public void notifyError_statusCodeIsEmpty_finisAndShowUnknownErrorMessage() throws Exception {
        presenter.notifyError("");

        verify(view).finishAndShowUnknownErrorMessage();
    }
}