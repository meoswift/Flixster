package com.example.flixster.models;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import org.parceler.Parcel;
import java.util.ArrayList;
import java.util.List;

@Parcel // so we can later pass the entire Movie object to another activity
public class Movie {
    // TODO: Fetch image size from Config endpoint instead of hardcoding
    public static final String BASE_IMG_URL = "https://image.tmdb.org/t/p/w342/";

    String poster_path;
    String backdrop_path;
    String title;
    String overview;
    String release_date;
    Double vote_average;
    int movie_id;

    // no-arg, empty constructor required for Parceler
    public Movie() {};

    // initialize a movie with needed properties like poster, backdrop, title, overview
    // get these properties from json object passed in
    public Movie(JSONObject json) throws JSONException {
        this.poster_path = json.getString("poster_path");
        this.backdrop_path = json.getString("backdrop_path");
        this.title = json.getString("title");
        this.overview = json.getString("overview");
        this.vote_average = json.getDouble("vote_average");
        this.release_date = json.getString("release_date");
        this.movie_id = json.getInt("id");
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
        return String.format("%s%s", BASE_IMG_URL, poster_path);
    }

    public String getBackdrop_path() {
        return String.format("%s%s", BASE_IMG_URL, backdrop_path);
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public Double getVote_average() {
        return vote_average;
    }

    public String getRelease_date() {
        return release_date;
    }

    public int getMovie_id() {
        return movie_id;
    }
}