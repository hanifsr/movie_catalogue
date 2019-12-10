package com.hanifsr.moviecatalogue.data.source.local;

import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseContract {

	public static String TABLE_NAME_MOVIE = "movie";
	public static String TABLE_NAME_TV_SHOW = "tv_show";
	public static final String AUTHORITY = "com.hanifsr.moviecatalogue";
	private static final String SCHEME = "content";

	public static String POSTER_PATH = "poster_path";
	public static String TITLE = "title";
	public static String GENRES = "genres";
	public static String USER_SCORE = "user_score";
	public static String OVERVIEW = "overview";

	public static final class MovieColumns implements BaseColumns {

		public static String MOVIE_ID = "movie_id";
		public static String RELEASE_DATE = "release_date";


		public static final Uri MOVIE_CONTENT_URI = new Uri.Builder().scheme(SCHEME)
				.authority(AUTHORITY)
				.appendPath(TABLE_NAME_MOVIE)
				.build();
	}

	public static final class TvShowColumns implements BaseColumns {

		public static String TV_SHOW_ID = "tv_show_id";
		public static String FIRST_AIR_DATE = "first_air_date";

		public static final Uri TV_SHOW_CONTENT_URI = new Uri.Builder().scheme(SCHEME)
				.authority(AUTHORITY)
				.appendPath(TABLE_NAME_TV_SHOW)
				.build();
	}
}
