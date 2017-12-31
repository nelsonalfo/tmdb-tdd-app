package nelsonalfo.tmdbunittestsapp.screens.list;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import nelsonalfo.tmdbunittestsapp.R;
import nelsonalfo.tmdbunittestsapp.models.MovieResume;
import nelsonalfo.tmdbunittestsapp.util.TmdbConfigurationUtil;


/**
 * Created by nelso on 27/12/2017.
 */

class MoviesAdapter extends RecyclerView.Adapter<MoviesViewHolder> {
    private final TmdbConfigurationUtil configurationUtil;
    private final List<MovieResume> dataSet;
    private Listener listener;


    public MoviesAdapter(TmdbConfigurationUtil configurationUtil, List<MovieResume> movies) {
        this.configurationUtil = configurationUtil;
        dataSet = movies;
    }


    @Override
    public MoviesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final Context context = parent.getContext();
        final View itemView = LayoutInflater.from(context).inflate(R.layout.item_recycler_view_movie, parent, false);

        return new MoviesViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MoviesViewHolder holder, int position) {
        if (dataSet != null && !dataSet.isEmpty()) {
            final MovieResume movieResume = dataSet.get(position);
            holder.bind(configurationUtil, movieResume);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        final MovieResume selectedMovie = dataSet.get(holder.getAdapterPosition());
                        listener.onMovieSelected(selectedMovie);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return dataSet != null ? dataSet.size() : 0;
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public interface Listener {
        void onMovieSelected(MovieResume selectedMovie);
    }
}
