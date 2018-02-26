package nelsonalfo.tmdbunittestsapp.screens.list.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import java.util.ArrayList;
import java.util.List;

import nelsonalfo.tmdbunittestsapp.R;
import nelsonalfo.tmdbunittestsapp.models.MovieResume;
import nelsonalfo.tmdbunittestsapp.util.TmdbConfigurationUtil;


/**
 * Created by nelso on 27/12/2017.
 */

public class MoviesAdapter extends RecyclerView.Adapter<MoviesViewHolder> implements Filterable {
    private TmdbConfigurationUtil configurationUtil;
    private List<MovieResume> dataSet;
    private List<MovieResume> filteredDataSet;
    private Listener listener;


    public MoviesAdapter(TmdbConfigurationUtil configurationUtil, List<MovieResume> movies) {
        filteredDataSet = new ArrayList<>(movies);

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
        if (filteredDataSet != null && !filteredDataSet.isEmpty()) {
            final MovieResume movieResume = filteredDataSet.get(position);
            holder.bind(configurationUtil, movieResume);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        final MovieResume selectedMovie = filteredDataSet.get(holder.getAdapterPosition());
                        listener.onMovieSelected(selectedMovie);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return filteredDataSet != null ? filteredDataSet.size() : 0;
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected Filter.FilterResults performFiltering(CharSequence charSequence) {
                final Filter.FilterResults filterResults = new Filter.FilterResults();
                filterResults.values = getFilteredMovies(charSequence);

                return filterResults;
            }

            @Override
            @SuppressWarnings("unchecked")
            protected void publishResults(CharSequence charSequence, Filter.FilterResults filterResults) {
                filteredDataSet = (List<MovieResume>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    @NonNull
    private List<MovieResume> getFilteredMovies(CharSequence charSequence) {
        final String textToSearch = charSequence.toString().toLowerCase();
        final List<MovieResume> filterList = new ArrayList<>();

        if (textToSearch.isEmpty()) {
            filterList.addAll(dataSet);
        } else {
            for (MovieResume movie : dataSet) {
                final String movieTitle = movie.title.toLowerCase();

                if (movieTitle.contains(textToSearch)) {
                    filterList.add(movie);
                }
            }
        }

        return filterList;
    }

    public interface Listener {
        void onMovieSelected(MovieResume selectedMovie);
    }
}
