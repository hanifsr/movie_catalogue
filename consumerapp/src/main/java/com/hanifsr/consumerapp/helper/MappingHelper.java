package com.hanifsr.consumerapp.helper;

import android.database.Cursor;

import com.hanifsr.consumerapp.model.Movie;

import java.util.ArrayList;

import static com.hanifsr.consumerapp.database.DatabaseContract.GENRES;
import static com.hanifsr.consumerapp.database.DatabaseContract.MovieColumns.DATE_RELEASE;
import static com.hanifsr.consumerapp.database.DatabaseContract.MovieColumns.MOVIE_ID;
import static com.hanifsr.consumerapp.database.DatabaseContract.OVERVIEW;
import static com.hanifsr.consumerapp.database.DatabaseContract.POSTER_PATH;
import static com.hanifsr.consumerapp.database.DatabaseContract.TITLE;
import static com.hanifsr.consumerapp.database.DatabaseContract.TvShowColumns.FIRST_AIR_DATE;
import static com.hanifsr.consumerapp.database.DatabaseContract.TvShowColumns.TV_SHOW_ID;
import static com.hanifsr.consumerapp.database.DatabaseContract.USER_SCORE;

public class MappingHelper {

	public static ArrayList<Movie> mapCursorToArrayList(Cursor cursor, int index) {
		ArrayList<Movie> movies = new ArrayList<>();
		Movie movie;

		while (cursor.moveToNext()) {
			movie = new Movie();

			if (index == 0) {
				movie.setId(cursor.getInt(cursor.getColumnIndexOrThrow(MOVIE_ID)));
				movie.setDateRelease(cursor.getString(cursor.getColumnIndexOrThrow(DATE_RELEASE)));
			} else if (index == 1) {
				movie.setId(cursor.getInt(cursor.getColumnIndexOrThrow(TV_SHOW_ID)));
				movie.setDateRelease(cursor.getString(cursor.getColumnIndexOrThrow(FIRST_AIR_DATE)));
			}
			movie.setPosterPath(cursor.getString(cursor.getColumnIndexOrThrow(POSTER_PATH)));
			movie.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(TITLE)));
			movie.setGenresHelper(cursor.getString(cursor.getColumnIndexOrThrow(GENRES)));
			movie.setUserScore(cursor.getString(cursor.getColumnIndexOrThrow(USER_SCORE)));
			movie.setOverview(cursor.getString(cursor.getColumnIndexOrThrow(OVERVIEW)));

			movies.add(movie);
		}

		return movies;
	}
}
