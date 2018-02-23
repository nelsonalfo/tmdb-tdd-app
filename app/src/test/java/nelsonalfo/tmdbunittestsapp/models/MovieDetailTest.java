package nelsonalfo.tmdbunittestsapp.models;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static com.google.common.truth.Truth.assertThat;


/**
 * Created by nelso on 23/2/2018.
 */
public class MovieDetailTest {
    private MovieDetail movieDetail;

    @Before
    public void setUp() throws Exception {
        movieDetail = new MovieDetail();
    }

    @Test
    public void getFormattedGenres_haveThreeGenres_returnFormattedString() throws Exception {
        movieDetail.genres = Arrays.asList(new Genre("Drama"), new Genre("Horror"), new Genre("Thriller"));

        StringBuilder formattedGenres = movieDetail.getFormattedGenres();

        assertThat(formattedGenres.toString()).isEqualTo("Drama, Horror, Thriller");
    }

    @Test
    public void getFormattedGenres_oneGenre_returnFormattedString() throws Exception {
        movieDetail.genres = Collections.singletonList(new Genre("Drama"));

        StringBuilder formattedGenres = movieDetail.getFormattedGenres();

        assertThat(formattedGenres.toString()).isEqualTo("Drama");
    }

    @Test
    public void getFormattedGenres_genresIsNull_returnEmptyString() throws Exception {
        movieDetail.genres = null;

        StringBuilder formattedGenres = movieDetail.getFormattedGenres();

        assertThat(formattedGenres.toString()).isEmpty();
    }

    @Test
    public void getFormattedGenres_genresIsEmpty_returnEmptyString() throws Exception {
        movieDetail.genres = new ArrayList<>();

        StringBuilder formattedGenres = movieDetail.getFormattedGenres();

        assertThat(formattedGenres.toString()).isEmpty();
    }

    @Test
    public void getFormattedGenres_HaveThreeGenresButTheFirstDontHaveName_returnFormattedStringWithOnlyTheGenresWithName() throws Exception {
        movieDetail.genres = Arrays.asList(new Genre(), new Genre("Horror"), new Genre("Thriller"));

        StringBuilder formattedGenres = movieDetail.getFormattedGenres();

        assertThat(formattedGenres.toString()).isEqualTo("Horror, Thriller");
    }

    @Test
    public void getFormattedGenres_HaveThreeGenresButTheSecondDontHaveName_returnFormattedStringWithOnlyTheGenresWithName() throws Exception {
        movieDetail.genres = Arrays.asList(new Genre("Drama"), new Genre(), new Genre("Thriller"));

        StringBuilder formattedGenres = movieDetail.getFormattedGenres();

        assertThat(formattedGenres.toString()).isEqualTo("Drama, Thriller");
    }

    @Test
    public void getFormattedGenres_HaveThreeGenresButTheThirdDontHaveName_returnFormattedStringWithOnlyTheGenresWithName() throws Exception {
        movieDetail.genres = Arrays.asList(new Genre("Drama"), new Genre("Horror"), new Genre());

        StringBuilder formattedGenres = movieDetail.getFormattedGenres();

        assertThat(formattedGenres.toString()).isEqualTo("Drama, Horror");
    }

    @Test
    public void getFormattedGenres_HaveTwoGenresButTheFirstIsNull_returnFormattedStringWithOnlyTheGenresWithName() throws Exception {
        movieDetail.genres = Arrays.asList(null, new Genre("Horror"));

        StringBuilder formattedGenres = movieDetail.getFormattedGenres();

        assertThat(formattedGenres.toString()).isEqualTo("Horror");
    }

    @Test
    public void getFormattedGenres_HaveTwoGenresButTheSecondIsNull_returnFormattedStringWithOnlyTheGenresWithName() throws Exception {
        movieDetail.genres = Arrays.asList(new Genre("Horror"), null);

        StringBuilder formattedGenres = movieDetail.getFormattedGenres();

        assertThat(formattedGenres.toString()).isEqualTo("Horror");
    }
}