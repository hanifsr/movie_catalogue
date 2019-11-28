package com.hanifsr.moviecatalogue.data.source.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.hanifsr.moviecatalogue.data.source.local.DatabaseContract.MovieColumns.MOVIE_ID;
import static com.hanifsr.moviecatalogue.data.source.local.DatabaseContract.TABLE_NAME_MOVIE;
import static com.hanifsr.moviecatalogue.data.source.local.DatabaseContract.TABLE_NAME_TV_SHOW;
import static com.hanifsr.moviecatalogue.data.source.local.DatabaseContract.TITLE;
import static com.hanifsr.moviecatalogue.data.source.local.DatabaseContract.TvShowColumns.TV_SHOW_ID;

public class MovieHelper {

	private static final String DATABASE_TABLE_MOVIE = TABLE_NAME_MOVIE;
	private static final String DATABASE_TABLE_TV_SHOW = TABLE_NAME_TV_SHOW;
	private static DatabaseHelper databaseHelper;
	private static MovieHelper INSTANCE;

	private static SQLiteDatabase database;

	private MovieHelper(Context context) {
		databaseHelper = new DatabaseHelper(context);
	}

	public static MovieHelper getInstance(Context context) {
		if (INSTANCE == null) {
			synchronized (SQLiteOpenHelper.class) {
				if (INSTANCE == null) {
					INSTANCE = new MovieHelper(context);
				}
			}
		}
		return INSTANCE;
	}

	public void open() throws SQLException {
		database = databaseHelper.getWritableDatabase();
	}

	public void close() {
		databaseHelper.close();

		if (database.isOpen()) {
			database.close();
		}
	}

	public Cursor queryAllMovie() {
		return database.query(DATABASE_TABLE_MOVIE, null,
				null,
				null,
				null,
				null,
				TITLE + " ASC",
				null
		);
	}

	public Cursor queryAllTvShow() {
		return database.query(DATABASE_TABLE_TV_SHOW, null,
				null,
				null,
				null,
				null,
				TITLE + " ASC",
				null
		);
	}

	public Cursor queryMovieById(int movieId) {
		return database.query(DATABASE_TABLE_MOVIE, null,
				MOVIE_ID + " = ?",
				new String[]{String.valueOf(movieId)},
				null,
				null,
				null,
				null
		);
	}

	public Cursor queryTvShowById(int tvShowId) {
		return database.query(DATABASE_TABLE_TV_SHOW, null,
				TV_SHOW_ID + " = ?",
				new String[]{String.valueOf(tvShowId)},
				null,
				null,
				null,
				null
		);
	}

	public long insertMovie(ContentValues values) {
		return database.insert(DATABASE_TABLE_MOVIE, null, values);
	}

	public long insertTvShow(ContentValues values) {
		return database.insert(DATABASE_TABLE_TV_SHOW, null, values);
	}

	public int deleteMovieById(int movieId) {
		return database.delete(DATABASE_TABLE_MOVIE, MOVIE_ID + " = ?", new String[]{String.valueOf(movieId)});
	}

	public int deleteTvShowById(int tvShowId) {
		return database.delete(DATABASE_TABLE_TV_SHOW, TV_SHOW_ID + " = ?", new String[]{String.valueOf(tvShowId)});
	}
}
