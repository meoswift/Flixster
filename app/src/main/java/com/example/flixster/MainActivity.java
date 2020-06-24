package com.example.flixster;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.flixster.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import okhttp3.Headers;

public class MainActivity extends AppCompatActivity {

    public static final String NOW_PLAYING_URL = "https://api.themoviedb.org/3/movie/now_playing?api_key=cb46ec90fde8f6fb9edc536bcfbbf6f5";
    public static final String TAG = "MainActivity";

    List<Movie> movie_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AsyncHttpClient client = new AsyncHttpClient();

        client.get(NOW_PLAYING_URL, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON object) {

                JSONObject json = object.jsonObject;

                // after fetching the json object of now playing movies, parse json into
                // a list of movies using the Movie model.
                try {
                    JSONArray results = json.getJSONArray("results");
                    movie_list = Movie.jsonToList(results);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e(TAG, "Error getting results array.");
                }
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.d(TAG, "Failed to fetch MoviesAPI");
            }
        });
    }



}