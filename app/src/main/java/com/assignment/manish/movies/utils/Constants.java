package com.assignment.manish.movies.utils;

public class Constants {
    public interface Urls {
        String API = "https://s3-ap-southeast-1.amazonaws.com/brontoo-android/moviedb/movies.json";
        String IMAGE_BASE = "https://image.tmdb.org/t/p/w500/";
    }

    public interface Api_Result {
        String PAGE = "page";
        String TOTAL_RESULTS = "total_results";
        String MOVIES = "movies";
    }

    public interface Movies {
        String VOTE_COUNT = "vote_count";
        String ID = "id";
        String VIDEO = "video";
        String VOTE_AVERAGE = "vote_average";
        String TITLE = "title";
        String POPULARITY = "popularity";
        String POSTER_PATH = "poster_path";
        String ORIGINAL_LANGUAGE = "original_language";
        String ORIGINAL_TITLE = "original_title";
        String GENRE_IDS = "genre_ids";
        String BACKDROP_PATH = "backdrop_path";
        String ADULT = "adult";
        String OVERVIEW = "overview";
        String RELEASE_DATE = "release_date";
        String IS_FAV = "is_fav";
        String IS_IN_WISHLIST = "is_in_wishlist";
        String DATA = "data";
    }

    public interface Helper_Values {
        String VALUE_TRUE = "T";
        String VALUE_FALSE = "F";
        String GET_MOVIE_LIST = "get_movies_list";
        String MOVIE_INSERTED = "movie_inserted";
    }

    public interface DB_Table {
        String TABLE_MOVIES = "MOVIES";
    }
}
