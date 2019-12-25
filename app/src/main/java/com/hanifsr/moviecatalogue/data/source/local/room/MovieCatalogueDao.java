package com.hanifsr.moviecatalogue.data.source.local.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.hanifsr.moviecatalogue.data.source.local.entity.FavouriteMovieEntity;
import com.hanifsr.moviecatalogue.data.source.local.entity.FavouriteTvShowEntity;

import java.util.List;

@Dao
public interface MovieCatalogueDao {

	@Insert
	void insertFavouriteMovie(FavouriteMovieEntity favouriteMovieEntity);

	@Insert
	void insertFavouriteTvShow(FavouriteTvShowEntity favouriteTvShowEntity);

	@Delete
	void deleteFavouriteMovie(FavouriteMovieEntity favouriteMovieEntity);

	@Delete
	void deleteFavouriteTvShow(FavouriteTvShowEntity favouriteTvShowEntity);

	@Query("SELECT * FROM favourite_movie ORDER BY title ASC")
	LiveData<List<FavouriteMovieEntity>> getFavouriteMovies();

	@Query("SELECT * FROM favourite_tv_show ORDER BY title ASC")
	LiveData<List<FavouriteTvShowEntity>> getFavouriteTvShows();

	@Query("SELECT * FROM favourite_movie WHERE id = :id")
	LiveData<FavouriteMovieEntity> getFavouriteMovie(int id);

	@Query("SELECT * FROM favourite_tv_show WHERE id = :id")
	LiveData<FavouriteTvShowEntity> getFavouriteTvShow(int id);
}
