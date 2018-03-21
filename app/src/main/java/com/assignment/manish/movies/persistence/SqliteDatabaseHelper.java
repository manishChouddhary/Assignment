package com.assignment.manish.movies.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.assignment.manish.movies.utils.Constants;

public class SqliteDatabaseHelper extends SQLiteOpenHelper {
    public static final int DB_VERSION = 1;
    public static final String DB_NAME = "Assignment";

    public SqliteDatabaseHelper(Context context)
    {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("CREATE TABLE IF NOT EXISTS "+Constants.DB_Table.TABLE_MOVIES +"(" +
                "   "+ Constants.Movies.ID+" NUMBER PRIMARY KEY," +
                "   "+Constants.Movies.TITLE+" TEXT," +
                "   "+Constants.Movies.RELEASE_DATE+" TEXT," +
                "   "+Constants.Movies.POPULARITY+" REAL," +
                "   "+Constants.Movies.DATA+" TEXT," +
                "   "+Constants.Movies.IS_FAV+" TEXT DEFAULT \"F\"," +
                "   "+Constants.Movies.IS_IN_WISHLIST+" TEXT DEFAULT \"F\""+
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
