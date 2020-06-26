package com.example.flixster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.flixster.models.Movie;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import okhttp3.Headers;

public class MovieDetailsActivity extends YouTubeBaseActivity {

    public static final String VIDEO_ID_TAG = "video_id";

    TextView title;
    TextView synopsis;
    RatingBar vote;
    TextView releaseDate;
    YouTubePlayerView player;
    ImageView poster;
    TextView rating;

    Movie movie;
    float average;
    int movieId;
    String videoId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        String youtube_key = getString(R.string.youtube_api_key); // private key

        title = findViewById(R.id.tv_title);
        synopsis = findViewById(R.id.synopsis);
        vote = findViewById(R.id.rb_vote_avg);
        rating = findViewById(R.id.release);
        player = findViewById(R.id.trailer_player);
        poster = findViewById(R.id.poster_small);
        rating = findViewById(R.id.rating);

        // get movie object that was passed from clicking on a item in list
        Intent intent = getIntent();
        movie = Parcels.unwrap(intent.getParcelableExtra("movie"));
        assert movie != null;  // make sure object passed is valid
        videoId = intent.getStringExtra(VIDEO_ID_TAG);

        // calculate the vote average from max 10 to max 5
        average = movie.getVote_average().floatValue() / 2.0f;
        movieId = movie.getMovie_id();

        // set all properties of MovieDetails
        title.setText(movie.getTitle());
        synopsis.setText(movie.getOverview());
        vote.setRating(average);
        rating.setText(String.valueOf(movie.getVote_average()));
        releaseDate.setText(String.format("Release date: %s", movie.getRelease_date()));
        Glide.with(this).load(movie.getPoster_path()).into(poster);
        playTrailerVideo(youtube_key);
    }

    // Function to play the video from Youtube when user click on it
    private void playTrailerVideo(String youtube_key) {
        player.initialize(youtube_key, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.cueVideo(videoId);
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                YouTubeInitializationResult youTubeInitializationResult) {
                Log.e("MovieTrailerActivity", "Error initializing YouTube player");
            }
        });
    }
}
