package com.assignment.manish.movies.persistence;

import com.assignment.manish.movies.utils.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import static com.assignment.manish.movies.utils.Constants.Helper_Values.VALUE_FALSE;

public class DataMovies {
    private JSONObject json;
    private String vote_count;
    private String id ;
    private String video;
    private String vote_average;
    private String title;
    private String popularity;
    private String poster_path;
    private String original_language;
    private String original_title;
    private String genre_ids;
    private String backdrop_path;
    private String adult;
    private String overview;
    private String release_date;
    private String is_fav;
    private String is_in_wishlist;

    public DataMovies(JSONObject moviesJson)
    {
        try {
            this.json = moviesJson;
            this.vote_count = moviesJson.getString(Constants.Movies.VOTE_COUNT);
            this.id = moviesJson.getString(Constants.Movies.ID);
            this.video = moviesJson.getString(Constants.Movies.VIDEO);
            this.vote_average = moviesJson.getString(Constants.Movies.VOTE_AVERAGE);
            this.title = moviesJson.getString(Constants.Movies.TITLE);
            this.popularity = moviesJson.getString(Constants.Movies.POPULARITY);
            this.poster_path = moviesJson.getString(Constants.Movies.POSTER_PATH);
            this.original_language = moviesJson.getString(Constants.Movies.ORIGINAL_LANGUAGE);
            this.original_title = moviesJson.getString(Constants.Movies.ORIGINAL_TITLE);
            this.genre_ids = moviesJson.getString(Constants.Movies.GENRE_IDS);
            this.backdrop_path = moviesJson.getString(Constants.Movies.BACKDROP_PATH);
            this.adult = moviesJson.getString(Constants.Movies.ADULT);
            this.overview = moviesJson.getString(Constants.Movies.OVERVIEW);
            this.release_date = moviesJson.getString(Constants.Movies.RELEASE_DATE);
            this.is_fav = VALUE_FALSE;
            this.is_in_wishlist = VALUE_FALSE;
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getVote_count() {
        return vote_count;
    }

    public void setVote_count(String vote_count) {
        this.vote_count = vote_count;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getVote_average() {
        return vote_average;
    }

    public void setVote_average(String vote_average) {
        this.vote_average = vote_average;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public void setOriginal_language(String original_language) {
        this.original_language = original_language;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public String getGenre_ids() {
        return genre_ids;
    }

    public void setGenre_ids(String genre_ids) {
        this.genre_ids = genre_ids;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public String getAdult() {
        return adult;
    }

    public void setAdult(String adult) {
        this.adult = adult;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public boolean getIs_fav()
    {
        return is_fav.equals(Constants.Helper_Values.VALUE_TRUE);
    }

    public void setIs_fav(String is_fav) {
        this.is_fav = is_fav;
    }

    public boolean getIs_in_wishlist() {
        return is_in_wishlist.equals(Constants.Helper_Values.VALUE_TRUE);
    }

    public void setIs_in_wishlist(String is_in_wishlist) {
        this.is_in_wishlist = is_in_wishlist;
    }

    public String toJson()
    {
        return json.toString();
    }
}
