package com.assignment.manish.movies.persistence;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.assignment.manish.movies.communication.IObserver;
import com.assignment.manish.movies.main.MovieListActivity;
import com.assignment.manish.movies.utils.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DBHelper
{
    private static SqliteDatabaseHelper getInstanse (Context context)
    {
        return new SqliteDatabaseHelper(context);
    }

    public static void insertMovie(DataMovies dataMovies, Context context, IObserver observer)
    {
        SQLiteDatabase db = getInstanse(context).getWritableDatabase();

        String insertQuery="insert into "+Constants.DB_Table.TABLE_MOVIES+" ("+Constants.Movies.ID+","+Constants.Movies.TITLE+"" +
                ","+Constants.Movies.RELEASE_DATE+","+Constants.Movies.POPULARITY+","+Constants.Movies.DATA+") Select ?,?,?,?,? where (Select Changes() = 0)";
        String updateQuery="update "+ Constants.DB_Table.TABLE_MOVIES+" set "+Constants.Movies.TITLE+"=?,"+
                Constants.Movies.RELEASE_DATE+"=?," +
                Constants.Movies.DATA+"=?," +
                Constants.Movies.POPULARITY+"=?"+" where "+
                Constants.Movies.ID+"=?";

        db.execSQL(updateQuery,new String[]{dataMovies.getTitle(),dataMovies.getRelease_date(),dataMovies.toJson(),dataMovies.getPopularity(),dataMovies.getId()});
        db.execSQL(insertQuery,new String[]{
                dataMovies.getId(),dataMovies.getTitle(),dataMovies.getRelease_date(),dataMovies.getPopularity(),dataMovies.toJson()
        });
        if (observer!=null) {
            observer.onObserverCallBack(Constants.Helper_Values.MOVIE_INSERTED,dataMovies.getId());
        }
    }

    public static ArrayList<DataMovies> getDBData(String whereClauseID,String whereClauseValue,String orderBy,Context context)
    {
        String query = "Select * from "+Constants.DB_Table.TABLE_MOVIES+(!whereClauseID.isEmpty()?" where "+whereClauseID+"="+whereClauseValue:"")+(!orderBy.isEmpty()?" order by "+orderBy:"");
        SQLiteDatabase db = getInstanse(context).getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        ArrayList<DataMovies> list = new ArrayList<>();
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            try {
                DataMovies data = new DataMovies(new JSONObject(cursor.getString(cursor.getColumnIndex(Constants.Movies.DATA))));
                data.setIs_fav(cursor.getString(cursor.getColumnIndex(Constants.Movies.IS_FAV)));
                data.setIs_in_wishlist(cursor.getString(cursor.getColumnIndex(Constants.Movies.IS_IN_WISHLIST)));
                list.add(data);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    public static void updateData(DataMovies dataMovies,Context context,IObserver observer)
    {
        SQLiteDatabase db = getInstanse(context).getReadableDatabase();
        String updateQuery="update "+ Constants.DB_Table.TABLE_MOVIES+" set "+Constants.Movies.TITLE+"=?,"+
                Constants.Movies.RELEASE_DATE+"=?," +
                Constants.Movies.DATA+"=?," +
                Constants.Movies.IS_FAV+"=?," +
                Constants.Movies.IS_IN_WISHLIST+"=?," +
                Constants.Movies.POPULARITY+"=?"+" where "+
                Constants.Movies.ID+"=?";

        db.execSQL(updateQuery,new String[]{dataMovies.getTitle(),dataMovies.getRelease_date(),dataMovies.toJson(),dataMovies.getIs_fav()?"T":"F",dataMovies.getIs_in_wishlist()?"T":"F",dataMovies.getPopularity(),dataMovies.getId()});
        if (observer!=null) {
            observer.onObserverCallBack(Constants.Helper_Values.MOVIE_INSERTED,dataMovies.getId());
        }
    }
}
