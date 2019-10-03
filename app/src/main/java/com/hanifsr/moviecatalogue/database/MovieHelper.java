package com.hanifsr.moviecatalogue.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.hanifsr.moviecatalogue.model.Movie;

import java.util.ArrayList;

import static com.hanifsr.moviecatalogue.database.DatabaseContract.MovieColumns.DATE_RELEASE;
import static com.hanifsr.moviecatalogue.database.DatabaseContract.MovieColumns.GENRES;
import static com.hanifsr.moviecatalogue.database.DatabaseContract.MovieColumns.MOVIE_ID;
import static com.hanifsr.moviecatalogue.database.DatabaseContract.MovieColumns.OVERVIEW;
import static com.hanifsr.moviecatalogue.database.DatabaseContract.MovieColumns.POSTER_PATH;
import static com.hanifsr.moviecatalogue.database.DatabaseContract.MovieColumns.TITLE;
import static com.hanifsr.moviecatalogue.database.DatabaseContract.MovieColumns.USER_SCORE;
import static com.hanifsr.moviecatalogue.database.DatabaseContract.TABLE_NAME_MOVIE;
import static com.hanifsr.moviecatalogue.database.DatabaseContract.TABLE_NAME_TV_SHOW;
import static com.hanifsr.moviecatalogue.database.DatabaseContract.TvShowColumns.FIRST_AIR_DATE;
import static com.hanifsr.moviecatalogue.database.DatabaseContract.TvShowColumns.SHOW_ID;

public class MovieHelper {

	private static final String DATABASE_TABLE_MOVIE = TABLE_NAME_MOVIE;
	private static final String DATABASE_TABLE_TV_SHOW = TABLE_NAME_TV_SHOW;
	private static DatabaseHelper databaseHelper;
	public static MovieHelper INSTANCE;

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

	public ArrayList<Movie> getAllMovie() {
		ArrayList<Movie> arrayList = new ArrayList<>();
		Cursor cursor = database.query(DATABASE_TABLE_MOVIE, null,
				null,
				null,
				null,
				null,
				TITLE + " ASC",
				null
		);
		cursor.moveToFirst();
		Movie movie;
		if (cursor.getCount() > 0) {
			do {
				movie = new Movie();
				movie.setId(cursor.getInt(cursor.getColumnIndexOrThrow(MOVIE_ID)));
				movie.setPosterPath(cursor.getString(cursor.getColumnIndexOrThrow(POSTER_PATH)));
				movie.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(TITLE)));
				movie.setGenres(cursor.getString(cursor.getColumnIndexOrThrow(GENRES)));
				movie.setDateRelease(cursor.getString(cursor.getColumnIndexOrThrow(DATE_RELEASE)));
				movie.setUserScore(cursor.getString(cursor.getColumnIndexOrThrow(USER_SCORE)));
				movie.setOverview(cursor.getString(cursor.getColumnIndexOrThrow(OVERVIEW)));

				arrayList.add(movie);
				cursor.moveToNext();
			} while (!cursor.isAfterLast());
		}
		cursor.close();
		return arrayList;
	}

	public int getMovie(int movieId) {
		Cursor cursor = database.query(DATABASE_TABLE_MOVIE, null,
				MOVIE_ID + " = ?",
				new String[]{String.valueOf(movieId)},
				null,
				null,
				null,
				null
		);
		int result = cursor.getCount();
		cursor.close();
		return result;
	}

	public long insertMovie(Movie movie) {
		ContentValues args = new ContentValues();
		args.put(MOVIE_ID, movie.getId());
		args.put(POSTER_PATH, movie.getPosterPath());
		args.put(TITLE, movie.getTitle());
		args.put(GENRES, movie.getGenres());
		args.put(DATE_RELEASE, movie.getDateRelease());
		args.put(USER_SCORE, movie.getUserScore());
		args.put(OVERVIEW, movie.getOverview());

		return database.insert(DATABASE_TABLE_MOVIE, null, args);
	}

	public int deleteMovie(int movieId) {
		return database.delete(DATABASE_TABLE_MOVIE, MOVIE_ID + " = ?", new String[]{String.valueOf(movieId)});
	}

	public ArrayList<Movie> getAllTvShow() {
		ArrayList<Movie> arrayList = new ArrayList<>();
		Cursor cursor = database.query(DATABASE_TABLE_TV_SHOW, null,
				null,
				null,
				null,
				null,
				TITLE + " ASC",
				null
		);
		cursor.moveToFirst();
		Movie movie;
		if (cursor.getCount() > 0) {
			do {
				movie = new Movie();
				movie.setId(cursor.getInt(cursor.getColumnIndexOrThrow(SHOW_ID)));
				movie.setPosterPath(cursor.getString(cursor.getColumnIndexOrThrow(POSTER_PATH)));
				movie.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(TITLE)));
				movie.setGenres(cursor.getString(cursor.getColumnIndexOrThrow(GENRES)));
				movie.setDateRelease(cursor.getString(cursor.getColumnIndexOrThrow(FIRST_AIR_DATE)));
				movie.setUserScore(cursor.getString(cursor.getColumnIndexOrThrow(USER_SCORE)));
				movie.setOverview(cursor.getString(cursor.getColumnIndexOrThrow(OVERVIEW)));

				arrayList.add(movie);
				cursor.moveToNext();
			} while (!cursor.isAfterLast());
		}
		cursor.close();
		return arrayList;
	}

	public int getTvShow(int movieId) {
		Cursor cursor = database.query(DATABASE_TABLE_TV_SHOW, null,
				SHOW_ID + " = ?",
				new String[]{String.valueOf(movieId)},
				null,
				null,
				null,
				null
		);
		int result = cursor.getCount();
		cursor.close();
		return result;
	}

	public long insertTvShow(Movie movie) {
		ContentValues args = new ContentValues();
		args.put(SHOW_ID, movie.getId());
		args.put(POSTER_PATH, movie.getPosterPath());
		args.put(TITLE, movie.getTitle());
		args.put(GENRES, movie.getGenres());
		args.put(FIRST_AIR_DATE, movie.getDateRelease());
		args.put(USER_SCORE, movie.getUserScore());
		args.put(OVERVIEW, movie.getOverview());

		return database.insert(DATABASE_TABLE_TV_SHOW, null, args);
	}

	public int deleteTvShow(int movieId) {
		return database.delete(DATABASE_TABLE_TV_SHOW, SHOW_ID + " = ?", new String[]{String.valueOf(movieId)});
	}
}
