package com.example.flixster.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.flixster.MovieDetailsActivity;
import com.example.flixster.R;
import com.example.flixster.models.Movie;

import org.parceler.Parcels;

import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

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

        // set all properties of a movie item
        holder.title.setText(movie.getTitle());
        holder.overview.setText(movie.getOverview());
        setPoster(movie, holder);
    }

    // Function to get the poster URL and use Glide to display on screen
    // Use Glide transformations to create rounded corner
    private void setPoster(Movie movie, ViewHolder holder) {
        String poster_url;
        int radius = 20; // corner radius, higher value = more rounded
        int margin = 10; // crop margin, set to 0 for corners with no crop

        // get different poster based on phone orientation
        if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            poster_url = movie.getPoster_path();
        } else {
            poster_url = movie.getBackdrop_path();
        }

        Glide.with(context).load(poster_url)
                .placeholder(R.drawable.flicks_backdrop_placeholder)
                .transform(new RoundedCornersTransformation(radius, margin))
                .into(holder.poster);
    }

    @Override
    public int getItemCount() {
        return movies_list.size();
    }

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
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

            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            // get position of item that was clicked
            int position = getAdapterPosition();

            // make sure position is valid
            if (position != RecyclerView.NO_POSITION) {
                // get object in movie list that will be pass into new activity
                Movie movie = movies_list.get(position);
                // create new intent to new activity
                Intent intent = new Intent(context, MovieDetailsActivity.class);
                // pass movie object as data to new activity
                intent.putExtra("movie", Parcels.wrap(movie));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                // start activity
                context.startActivity(intent);
            }
        }
    }
}
