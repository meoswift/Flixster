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

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

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
        float average = Float.parseFloat(movie.getVote_average()) / 2.0f;

        // set all properties of MovieDetails
        binding.title.setText(movie.getTitle());
        binding.release.setText(movie.getRelease_date());
        binding.originalTitle.setText(movie.getOriginalTitle());
        binding.ratingBar.setRating(average);
        binding.rating.setText(movie.getVote_average());
        binding.voteCount.setText(movie.getVoteCount());
        binding.synopsis.setText(movie.getOverview());

        Glide.with(this)
                .load(movie.getPoster_path())
                .into(binding.poster);

        // load the youtube video to view
        String youtube_key = getString(R.string.youtube_api_key); // private key
        playTrailerVideo(youtube_key);
    }

    // Function to play the video from Youtube when user click on it
    private void playTrailerVideo(String youtube_key) {
        YouTubePlayerView player = findViewById(R.id.trailer_player);
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
