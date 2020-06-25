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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import okhttp3.Headers;

public class MovieDetailsActivity extends AppCompatActivity {

    public static final String VIDEO_ID = "video_id";

    TextView tv_title;
    TextView synopsis;
    RatingBar rb_vote;
    TextView release_date;
    ImageView trailer;

    Movie movie;
    float average;
    int movie_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        tv_title = findViewById(R.id.tv_title);
        synopsis = findViewById(R.id.synopsis);
        rb_vote = findViewById(R.id.rb_vote_avg);
        release_date = findViewById(R.id.release);
        trailer = findViewById(R.id.trailer_player);

        // get movie object that was passed from clicking on a item in list
        Intent intent = getIntent();
        movie = Parcels.unwrap(intent.getParcelableExtra("movie"));
        assert movie != null;  // make sure object passed is valid

        // calculate the vote average from max 10 to max 5
        average = movie.getVote_average().floatValue() / 2.0f;
        movie_id = movie.getMovie_id();

        // set all properties of MovieDetails
        tv_title.setText(movie.getTitle());
        synopsis.setText(movie.getOverview());
        rb_vote.setRating(average);
        release_date.setText(String.format("Release date: %s", movie.getRelease_date()));

        Glide.with(this).load(movie.getBackdrop_path()).into(trailer);
    }

    public void onShowTrailer(View view) {

        //TODO: Get trailer of movie from another endpoint and show in details
        String MOVIE_TRAILER_URL = "https://api.themoviedb.org/3/movie/" + movie_id +
                "/videos?api_key=cb46ec90fde8f6fb9edc536bcfbbf6f5";
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(MOVIE_TRAILER_URL, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Headers headers, JSON data) {
                JSONObject json = data.jsonObject;
                try {
                    JSONArray results = json.getJSONArray("results");
                    String first_id = results.getJSONObject(0).getString("key");

                    Intent intent = new Intent(getApplicationContext(), MovieTrailerActivity.class);
                    intent.putExtra(VIDEO_ID, first_id);
                    startActivity(intent);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.e("MovieDetailsActivity", "Error retrieving JSON from movie endpoint");
            }
        });
    }
}