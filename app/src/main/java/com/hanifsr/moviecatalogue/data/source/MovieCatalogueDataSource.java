package com.hanifsr.moviecatalogue.data.source;

import androidx.lifecycle.LiveData;
import androidx.paging.PagedList;

import com.hanifsr.moviecatalogue.data.source.local.entity.FavouriteMovieEntity;
import com.hanifsr.moviecatalogue.data.source.local.entity.FavouriteTvShowEntity;
import com.hanifsr.moviecatalogue.data.source.remote.response.Movie;

import java.util.List;

public interface MovieCatalogueDataSource {

	// RemoteRepository
	LiveData<List<Movie>> getMovies(String language);

	LiveData<List<Movie>> getTvShows(String language);

	LiveData<Movie> getMovieDetail(int movieId, String language);

	LiveData<Movie> getTvShowDetail(int tvShowId, String language);

	LiveData<List<Movie>> getReleaseTodayMovies(String todayDate);

	LiveData<List<Movie>> getQueriedMovies(String language, String query);

	LiveData<List<Movie>> getQueriedTvShows(String language, String query);

	// LocalRepository
	void insertFavouriteMovie(FavouriteMovieEntity favouriteMovieEntity);

	void insertFavouriteTvShow(FavouriteTvShowEntity favouriteTvShowEntity);

	void deleteFavouriteMovie(FavouriteMovieEntity favouriteMovieEntity);

	void deleteFavouriteTvShow(FavouriteTvShowEntity favouriteTvShowEntity);

	LiveData<PagedList<FavouriteMovieEntity>> getFavouriteMovies();

	LiveData<PagedList<FavouriteTvShowEntity>> getFavouriteTvShows();

	LiveData<FavouriteMovieEntity> getFavouriteMovie(int id);

	LiveData<FavouriteTvShowEntity> getFavouriteTvShow(int id);
}
