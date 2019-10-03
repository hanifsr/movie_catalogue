package com.hanifsr.moviecatalogue.database;

import android.provider.BaseColumns;

class DatabaseContract {

	static String TABLE_NAME_MOVIE = "movie";
	static String TABLE_NAME_TV_SHOW = "tv_show";

	static final class MovieColumns implements BaseColumns {

		static String MOVIE_ID = "movie_id";
		static String POSTER_PATH = "poster_path";
		static String TITLE = "title";
		static String GENRES = "genres";
		static String DATE_RELEASE = "date_release";
		static String USER_SCORE = "user_score";
		static String OVERVIEW = "overview";
	}

	static final class TvShowColumns implements BaseColumns {

		static String SHOW_ID = "show_id";
		static String POSTER_PATH = "poster_path";
		static String TITLE = "title";
		static String GENRES = "genres";
		static String FIRST_AIR_DATE = "first_air_date";
		static String USER_SCORE = "user_score";
		static String OVERVIEW = "overview";
	}
}
