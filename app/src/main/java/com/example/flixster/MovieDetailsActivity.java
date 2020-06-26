package com.example.flixster;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.flixster.databinding.ActivityMovieDetailsBinding;
import com.example.flixster.models.Movie;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import org.parceler.Parcels;

public class MovieDetailsActivity extends YouTubeBaseActivity {

    public static final String VIDEO_ID_TAG = "videoId";

    String videoId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityMovieDetailsBinding binding = ActivityMovieDetailsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        // get movie object that was passed from clicking on a item in list
        Intent intent = getIntent();
        Movie movie = Parcels.unwrap(intent.getParcelableExtra("movie"));
        assert movie != null;  // make sure object passed is valid
        videoId = intent.getStringExtra(VIDEO_ID_TAG);

        // calculate the vote average from max 10 to max 5
        float average = movie.getVote_average().floatValue() / 2.0f;
        int movieId = movie.getMovie_id();

        // set all properties of MovieDetails
        binding.title.setText(movie.getTitle());
        binding.synopsis.setText(movie.getOverview());
        binding.ratingBar.setRating(average);
        binding.rating.setText(String.valueOf(movie.getVote_average()));
        binding.release.setText(String.format("Release date: %s", movie.getRelease_date()));
        Glide.with(this).load(movie.getPoster_path()).into(binding.poster);

        // load the youtube video to view
        String youtube_key = getString(R.string.youtube_api_key); // private key
        playTrailerVideo(youtube_key);
    }

    // Function to play the video from Youtube when user click on it
    private void playTrailerVideo(String youtube_key) {
        YouTubePlayerView player = findViewById(R.id.player);
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
