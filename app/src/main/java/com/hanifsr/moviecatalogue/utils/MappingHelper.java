package com.hanifsr.moviecatalogue.utils;

import android.database.Cursor;

import com.hanifsr.moviecatalogue.data.source.local.entity.FavouriteMovieEntity;
import com.hanifsr.moviecatalogue.data.source.local.entity.FavouriteTvShowEntity;
import com.hanifsr.moviecatalogue.data.source.remote.response.Movie;

import java.util.ArrayList;
import java.util.List;

import static com.hanifsr.moviecatalogue.data.source.local.DatabaseContract.GENRES;
import static com.hanifsr.moviecatalogue.data.source.local.DatabaseContract.MovieColumns.MOVIE_ID;
import static com.hanifsr.moviecatalogue.data.source.local.DatabaseContract.MovieColumns.RELEASE_DATE;
import static com.hanifsr.moviecatalogue.data.source.local.DatabaseContract.OVERVIEW;
import static com.hanifsr.moviecatalogue.data.source.local.DatabaseContract.POSTER_PATH;
import static com.hanifsr.moviecatalogue.data.source.local.DatabaseContract.TITLE;
import static com.hanifsr.moviecatalogue.data.source.local.DatabaseContract.TvShowColumns.FIRST_AIR_DATE;
import static com.hanifsr.moviecatalogue.data.source.local.DatabaseContract.TvShowColumns.TV_SHOW_ID;
import static com.hanifsr.moviecatalogue.data.source.local.DatabaseContract.USER_SCORE;

public class MappingHelper {

	public static List<Movie> mapCursorToArrayList(Cursor cursor, int index) {
		List<Movie> movies = new ArrayList<>();
		Movie movie;

		while (cursor.moveToNext()) {
			movie = new Movie();

			if (index == 0) {
				movie.setId(cursor.getInt(cursor.getColumnIndexOrThrow(MOVIE_ID)));
				movie.setReleaseDate(cursor.getString(cursor.getColumnIndexOrThrow(RELEASE_DATE)));
			} else if (index == 1) {
				movie.setId(cursor.getInt(cursor.getColumnIndexOrThrow(TV_SHOW_ID)));
				movie.setReleaseDate(cursor.getString(cursor.getColumnIndexOrThrow(FIRST_AIR_DATE)));
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

	public static List<Movie> mapListFavouriteMovieEntityToListMovie(List<FavouriteMovieEntity> favouriteMovieEntities) {
		List<Movie> movies = new ArrayList<>();

		for (FavouriteMovieEntity favouriteMovieEntity : favouriteMovieEntities) {
			movies.add(new Movie(favouriteMovieEntity.getId(),
					favouriteMovieEntity.getPosterPath(),
					favouriteMovieEntity.getBackdropPath(),
					favouriteMovieEntity.getTitle(),
					favouriteMovieEntity.getGenre(),
					favouriteMovieEntity.getReleaseDate(),
					favouriteMovieEntity.getUserScore(),
					favouriteMovieEntity.getOverview()));
		}

		return movies;
	}

	public static List<Movie> mapListFavouriteTvShowEntityToListMovie(List<FavouriteTvShowEntity> favouriteTvShowEntities) {
		List<Movie> tvShows = new ArrayList<>();

		for (FavouriteTvShowEntity favouriteTvShowEntity : favouriteTvShowEntities) {
			tvShows.add(new Movie(favouriteTvShowEntity.getId(),
					favouriteTvShowEntity.getPosterPath(),
					favouriteTvShowEntity.getBackdropPath(),
					favouriteTvShowEntity.getTitle(),
					favouriteTvShowEntity.getGenre(),
					favouriteTvShowEntity.getFirstAirDate(),
					favouriteTvShowEntity.getUserScore(),
					favouriteTvShowEntity.getOverview()));
		}

		return tvShows;
	}
}
