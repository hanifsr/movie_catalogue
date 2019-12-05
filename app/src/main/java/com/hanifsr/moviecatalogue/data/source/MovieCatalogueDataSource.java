package com.hanifsr.moviecatalogue.data.source;

import androidx.lifecycle.LiveData;

import com.hanifsr.moviecatalogue.data.source.remote.response.Movie;

import java.util.ArrayList;

public interface MovieCatalogueDataSource {

	LiveData<ArrayList<Movie>> getMovies(String language);

	LiveData<ArrayList<Movie>> getTvShows(String language);

	LiveData<Movie> getMovieDetail(int movieId, String language);

	LiveData<Movie> getTvShowDetail(int tvShowId, String language);

	LiveData<ArrayList<Movie>> getReleaseTodayMovies(String todayDate);

	LiveData<ArrayList<Movie>> getQueriedMovies(String language, String query);

	LiveData<ArrayList<Movie>> getQueriedTvShows(String language, String query);
}
