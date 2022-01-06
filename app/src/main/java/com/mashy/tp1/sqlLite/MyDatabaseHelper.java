package com.mashy.tp1.sqlLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.mashy.tp1.services.classes.Movie;

import java.util.ArrayList;
import java.util.List;


public class MyDatabaseHelper extends SQLiteOpenHelper {


    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "FavoriteMovies";

    // Table name: MOVIE.
    private static final String TABLE_Movie = "Movies";

    private static final String COLUMN_MOVIE_id ="id";
    private static final String COLUMN_MOVIE_title ="title";
    private static final String COLUMN_MOVIE_Overview = "Overview";
    private static final String COLUMN_MOVIE_release_date ="release_date";
    private static final String COLUMN_MOVIE_poster_path ="poster_path";
    private static final String COLUMN_MOVIE_backdrop_path ="backdrop_path";
    //Genre_ids
    private final String TABLE_genre_ids="Genreids";
    private static final String COLUMN_GI_id ="id";
    private static final String COLUMN_GI_genre_id ="genre_id ";
    private static final String COLUMN_GI_movie_id ="movie_id ";

    public MyDatabaseHelper(Context context)  {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Create table
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i("TAG", "onCreate: WAS CALLED");
        // Script.
        String script = "CREATE TABLE " + TABLE_Movie + "("
                + COLUMN_MOVIE_id + " INTEGER PRIMARY KEY," + COLUMN_MOVIE_title + " TEXT,"
                + COLUMN_MOVIE_Overview + " TEXT,"+ COLUMN_MOVIE_release_date + " TEXT,"
                + COLUMN_MOVIE_poster_path + " TEXT,"  + COLUMN_MOVIE_backdrop_path + " TEXT" + ")";


        // Execute Script.
        db.execSQL(script);

        script = "CREATE TABLE " + TABLE_genre_ids + "("
                + COLUMN_GI_id + " INTEGER PRIMARY KEY," + COLUMN_GI_movie_id + " INTEGER,"
                + COLUMN_GI_genre_id + " TEXT" + ")";
        db.execSQL(script);

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Movie);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_genre_ids);
        Log.i("TAG", "onUpgrade: CALLED");

        // Create tables again
        onCreate(db);
    }




    public boolean movieExists(Movie movie)  {
        //SELECT EXISTS(SELECT 1 FROM myTbl WHERE u_tag="tag");
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM "+TABLE_Movie +" WHERE "+COLUMN_MOVIE_id+"= '" +movie.getId()+"'";
        Log.i("query", sql);
        Cursor data = db.rawQuery(sql, null);
        if (data.moveToFirst()) {
            return true;
        } else {
            return false;
        }
    }
    public void addMovie(Movie movie) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_MOVIE_id, movie.getId());
        values.put(COLUMN_MOVIE_title, movie.getTitle());
        values.put(COLUMN_MOVIE_Overview, movie.getOverview());
        values.put(COLUMN_MOVIE_release_date, movie.getRelease_date());
        values.put(COLUMN_MOVIE_poster_path, movie.getPoster_path());
        values.put(COLUMN_MOVIE_backdrop_path, movie.getBackdrop_path());



        // Inserting Row
        db.insert(TABLE_Movie, null, values);

        for(int id : movie.getGenre_ids()){
            ContentValues val = new ContentValues();
            val.put(COLUMN_GI_movie_id,movie.getId());
            val.put(COLUMN_GI_genre_id ,id);
            db.insert(TABLE_genre_ids, null, val);
        }
        // Closing database connection
        db.close();
    }



    public List<Movie> getAllMovies() {

        List<Movie> movieList = new ArrayList<Movie>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_Movie;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {

                Movie movie = new Movie(Integer.parseInt(cursor.getString(0)),
                        cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5),getGenres(cursor.getString(0)));

                // Adding note to list
                movieList.add(movie);
            } while (cursor.moveToNext());
        }

        // return note list
        return movieList;
    }

    private int[] getGenres(String string) {
        int i =0;
        String selectQuery = "SELECT  * FROM " + TABLE_genre_ids + " WHERE "+COLUMN_GI_movie_id
                +" = '" + string +"' ";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        int[] genres = new int[cursor.getCount()];
        if (cursor.moveToFirst()) {
            do {
                genres[i]=Integer.parseInt(cursor.getString(2));
                i++;
            } while (cursor.moveToNext());
        }
        // return note list
        return genres;

    }

    public int getMovieCount() {

        String countQuery = "SELECT  * FROM " + TABLE_Movie;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();

        cursor.close();

        // return count
        return count;
    }


    public void deleteMovie(Movie movie) {

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_Movie, COLUMN_MOVIE_id + " = ?",
                new String[] { String.valueOf(movie.getId()) });
        db.close();
    }

}