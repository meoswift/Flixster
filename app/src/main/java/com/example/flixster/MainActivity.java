package com.example.flixster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.flixster.adapters.MoviesAdapter;
import com.example.flixster.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

public class MainActivity extends AppCompatActivity {

    public static String NOW_PLAYING_URL = "https://api.themoviedb.org/3/movie/now_playing?api_key=";
    public static final String TAG = "MainActivity";

    List<Movie> movieList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String movie_key = getString(R.string.movie_api_key);

        // initialize an empty movie list
        movieList = new ArrayList<>();

        // Lookup the recyclerview in activity layout
        RecyclerView moviesRV = findViewById(R.id.movies_rv);

        // Create a movie adapter to bind model into view
        final MoviesAdapter adapter = new MoviesAdapter(getApplicationContext(), movieList);
        // Set adapter to populate the RecyclerView
        moviesRV.setAdapter(adapter);
        // set RecyclerView to Linear so user can scroll
        moviesRV.setLayoutManager(new LinearLayoutManager(this));

        // Create client so we can retrieve JSON object and parse into a list of movies
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(NOW_PLAYING_URL + movie_key, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON object) {
                JSONObject json = object.jsonObject;
                try {
                    // retrieve results array from now_playing json object
                    JSONArray results = json.getJSONArray("results");
                    // parse json array into a movie list of type List<Movie>
                    movieList.addAll(Movie.jsonToList(results));
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    Log.e(TAG, "Error getting results array.");
                }
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response,
                                  Throwable throwable) {
                Log.d(TAG, "Failed to fetch MoviesAPI");
            }
        });
    }
}