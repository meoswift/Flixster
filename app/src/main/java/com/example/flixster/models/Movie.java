package com.example.flixster.models;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Movie {
    public static final String BASE_IMG_URL = "https://image.tmdb.org/t/p/w342/";
    String poster_path;
    String backdrop_path;
    String title;
    String overview;

    // initialize a movie with needed properties like poster, backdrop, title, overview
    // get these properties from json object passed in
    public Movie(JSONObject json) throws JSONException {
        this.poster_path = json.getString("poster_path");
        this.backdrop_path = json.getString("backdrop_path");
        this.title = json.getString("title");
        this.overview = json.getString("overview");
    }

    // create a list of movie objects by parsing the JSONArray of movies
    public static List<Movie> jsonToList(JSONArray jsonMovies) throws JSONException {
        List<Movie> movies = new ArrayList<>();

        for (int i = 0; i < jsonMovies.length(); i++) {
            // create one movie object with properties
            JSONObject movie_obj = jsonMovies.getJSONObject(i);
            Movie movie = new Movie(movie_obj);

            // add movie to movies list
            movies.add(movie);
        }

        return movies;
    }

    public String getPoster_path() {
        String path = BASE_IMG_URL + poster_path;
        return path;
    }

    public String getBackdrop_path() {
        String path = BASE_IMG_URL + backdrop_path;
        return path;
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

}