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
    public static final String BASE_IMG_URL = "https://image.tmdb.org/t/p/w342/";

    String posterPath;
    String backdropPath;
    String title;
    String originalTitle;
    String overview;
    String releaseDate;
    Double voteAverage;
    int voteCount;
    int movieId;

    // no-arg, empty constructor required for Parceler
    public Movie() {};

    // initialize a movie with needed properties like poster, backdrop, title, overview
    // get these properties from json object passed in
    public Movie(JSONObject json) throws JSONException {
        this.posterPath = json.getString("poster_path");
        this.backdropPath = json.getString("backdrop_path");
        this.title = json.getString("title");
        this.originalTitle = json.getString("original_title");
        this.overview = json.getString("overview");
        this.voteAverage = json.getDouble("vote_average");
        this.voteCount = json.getInt("vote_count");
        this.releaseDate = json.getString("release_date");
        this.movieId = json.getInt("id");
    }

    // create a list of movie objects by parsing the JSONArray of movies
    public static List<Movie> jsonToList(JSONArray jsonMovies) throws JSONException {
        List<Movie> movies = new ArrayList<>();

        for (int i = 0; i < jsonMovies.length(); i++) {
            // create one movie object with properties
            JSONObject movieObj = jsonMovies.getJSONObject(i);
            Movie movie = new Movie(movieObj);
            // add movie to movies list
            movies.add(movie);
        }

        return movies;
    }

    public String getPoster_path() {
        return String.format("%s%s", BASE_IMG_URL, posterPath);
    }

    public String getBackdrop_path() {
        return String.format("%s%s", BASE_IMG_URL, backdropPath);
    }

    public String getTitle() {
        return title;
    }

    public String getOriginalTitle() {
        return String.format("Original title: %s", originalTitle);
    }

    public String getOverview() {
        return overview;
    }

    public String getVoteCount() {
        return String.format("Vote count: %s", voteCount);
    }

    public String getVote_average() {
        return String.valueOf(voteAverage);
    }

    public String getRelease_date() {
        return String.format("Release date: %s",releaseDate);
    }

    public int getMovie_id() {
        return movieId;
    }

    public String getShortenedOverview() {
        if (overview.length() > 150) {
            return overview.substring(0, 150) + "...";
        } else {
            return overview;
        }
    }
}