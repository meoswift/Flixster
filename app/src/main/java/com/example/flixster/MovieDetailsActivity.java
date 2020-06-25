package com.example.flixster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.flixster.models.Movie;

import org.parceler.Parcels;

public class MovieDetailsActivity extends AppCompatActivity {

    TextView tv_title;
    TextView synopsis;
    RatingBar rb_vote;
    TextView release_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        tv_title = findViewById(R.id.tv_title);
        synopsis = findViewById(R.id.synopsis);
        rb_vote = findViewById(R.id.rb_vote_avg);
        release_date = findViewById(R.id.release);

        // get movie object that was passed from clicking on a item in list
        Intent intent = getIntent();
        Movie movie = Parcels.unwrap(intent.getParcelableExtra("movie"));
        assert movie != null;  // make sure object passed is valid

        // calculate the vote average from max 10 to max 5
        float average = movie.getVote_average().floatValue() / 2.0f;

        // set all properties of MovieDetails
        tv_title.setText(movie.getTitle());
        synopsis.setText(movie.getOverview());
        rb_vote.setRating(average);
        release_date.setText(String.format("Release date: %s", movie.getRelease_date()));
    }
}