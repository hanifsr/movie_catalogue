package com.hanifsr.moviecatalogue.data.source.local;

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
			DatabaseContract.POSTER_PATH,
			DatabaseContract.TITLE,
			DatabaseContract.GENRES,
			DatabaseContract.MovieColumns.RELEASE_DATE,
			DatabaseContract.USER_SCORE,
			DatabaseContract.OVERVIEW
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
			DatabaseContract.TvShowColumns.TV_SHOW_ID,
			DatabaseContract.POSTER_PATH,
			DatabaseContract.TITLE,
			DatabaseContract.GENRES,
			DatabaseContract.TvShowColumns.FIRST_AIR_DATE,
			DatabaseContract.USER_SCORE,
			DatabaseContract.OVERVIEW
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
