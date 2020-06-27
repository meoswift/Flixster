package com.example.flixster.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.flixster.MovieDetailsActivity;
import com.example.flixster.R;
import com.example.flixster.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
import okhttp3.Headers;

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
        holder.overview.setText(movie.getShortenedOverview());
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
                getTrailerId(movie);
            }
        }

        // when user click on backdrop poster, start playing the movie trailer
        private void getTrailerId(final Movie movie) {
            // create the url with movie id and api key
            int id = movie.getMovie_id();
            String api_key = context.getString(R.string.movie_api_key);
            String url = "https://api.themoviedb.org/3/movie/" + id + "/videos?api_key=" + api_key;

            AsyncHttpClient client = new AsyncHttpClient();
            client.get(url, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Headers headers, JSON data) {
                    JSONObject json = data.jsonObject;
                    try {
                        // start a new intent
                        Intent intent = new Intent(context, MovieDetailsActivity.class);
                        
                        // get the results array from retrieved json data
                        JSONArray results = json.getJSONArray("results");
                        String id = null;
                        if (results.length() != 0)
                            // get the first video id in the list of possible trailers
                            id = results.getJSONObject(0).getString("key");

                        // pass video id to next activity to play the correct trailer
                        intent.putExtra("videoId", id);
                        // pass movie object as data to new activity
                        intent.putExtra("movie", Parcels.wrap(movie));
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        // start activity
                        context.startActivity(intent);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int statusCode, Headers headers, String response,
                                      Throwable throwable) {
                }
            });
        }
    }
}
