package com.example.flixster.adapters;

import android.content.Context;
import android.content.res.Configuration;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.flixster.R;
import com.example.flixster.models.Movie;

import java.util.List;

// Create the basic adapter extending from RecyclerView.Adapter
public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> {

    private List<Movie> movies_list;
    Context context;

    public MoviesAdapter(Context context, List<Movie> movies_list) {
        this.movies_list = movies_list;
        this.context = context;
    }

    // Function to inflate a movie item layout from XML adn returning the holder
    // We will attach to the RecyclerView later
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create an inflater that inflates all itemViews in a movie item
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View movie = inflater.inflate(R.layout.movie_item, parent, false);
        ViewHolder movieHolder = new ViewHolder(movie);
        return movieHolder;
    }

    // Function to attach one viewHolder of a movie item to the RecyclerView
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Movie movie = movies_list.get(position);
        String poster_url;

        holder.title.setText(movie.getTitle());
        holder.overview.setText(movie.getOverview());

        if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            poster_url = movie.getPoster_path();
        } else {
            poster_url = movie.getBackdrop_path();
        }

        Glide.with(context).load(poster_url).into(holder.poster);

    }

    @Override
    public int getItemCount() {
        return movies_list.size();
    }

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        TextView overview;
        ImageView poster;

        // Each Movie item holds a title, overview, and poster itemView
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            // Each view element of a ViewHolder can be accessed by ID
            title = itemView.findViewById(R.id.title);
            overview = itemView.findViewById(R.id.overview);
            poster = itemView.findViewById(R.id.poster);
        }
    }
}
