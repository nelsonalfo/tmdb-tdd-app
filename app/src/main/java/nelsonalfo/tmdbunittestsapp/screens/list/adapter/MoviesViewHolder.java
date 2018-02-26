package nelsonalfo.tmdbunittestsapp.screens.list.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import nelsonalfo.tmdbunittestsapp.R;
import nelsonalfo.tmdbunittestsapp.models.MovieResume;
import nelsonalfo.tmdbunittestsapp.util.TmdbConfigurationUtil;


/**
 * Created by nelso on 27/12/2017.
 */

class MoviesViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.movie_poster)
    ImageView moviePoster;
    @BindView(R.id.move_title)
    TextView moveTitle;
    @BindView(R.id.move_description)
    TextView moveDescription;


    public MoviesViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(TmdbConfigurationUtil configurationUtil, MovieResume movieResume) {
        String posterImageUrl = configurationUtil.getPosterImageUrl(movieResume.posterPath);
        Picasso.with(itemView.getContext()).load(posterImageUrl).into(moviePoster);

        moveTitle.setText(movieResume.title);
        moveDescription.setText(movieResume.overview);
    }
}
