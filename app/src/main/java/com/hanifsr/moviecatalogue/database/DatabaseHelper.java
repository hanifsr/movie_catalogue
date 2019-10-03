package com.hanifsr.moviecatalogue.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

	private static String DATABASE_NAME = "dbmoviecatalogue";

	private static final int DATABASE_VERSION = 1;

	private static final String SQL_CREATE_TABLE_MOVIE = String.format("CREATE TABLE %s"
					+ " (%s INTEGER PRIMARY KEY,"
					+ " %s TEXT NOT NULL,"
					+ " %s TEXT NOT NULL,"
					+ " %s TEXT NOT NULL,"
					+ " %s TEXT NOT NULL,"
					+ " %s TEXT NOT NULL,"
					+ " %s TEXT NOT NULL)",
			DatabaseContract.TABLE_NAME_MOVIE,
			DatabaseContract.MovieColumns.MOVIE_ID,
			DatabaseContract.MovieColumns.POSTER_PATH,
			DatabaseContract.MovieColumns.TITLE,
			DatabaseContract.MovieColumns.GENRES,
			DatabaseContract.MovieColumns.DATE_RELEASE,
			DatabaseContract.MovieColumns.USER_SCORE,
			DatabaseContract.MovieColumns.OVERVIEW
	);

	private static final String SQL_CREATE_TABLE_TV_SHOW = String.format("CREATE TABLE %s"
					+ " (%s INTEGER PRIMARY KEY,"
					+ " %s TEXT NOT NULL,"
					+ " %s TEXT NOT NULL,"
					+ " %s TEXT NOT NULL,"
					+ " %s TEXT NOT NULL,"
					+ " %s TEXT NOT NULL,"
					+ " %s TEXT NOT NULL)",
			DatabaseContract.TABLE_NAME_TV_SHOW,
			DatabaseContract.TvShowColumns.SHOW_ID,
			DatabaseContract.TvShowColumns.POSTER_PATH,
			DatabaseContract.TvShowColumns.TITLE,
			DatabaseContract.TvShowColumns.GENRES,
			DatabaseContract.TvShowColumns.FIRST_AIR_DATE,
			DatabaseContract.TvShowColumns.USER_SCORE,
			DatabaseContract.TvShowColumns.OVERVIEW
	);

	DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(SQL_CREATE_TABLE_MOVIE);
		db.execSQL(SQL_CREATE_TABLE_TV_SHOW);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.TABLE_NAME_MOVIE);
		db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.TABLE_NAME_TV_SHOW);
		onCreate(db);
	}
}
