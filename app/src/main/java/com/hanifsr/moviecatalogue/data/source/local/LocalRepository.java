package com.hanifsr.moviecatalogue.data.source.local;

import androidx.lifecycle.LiveData;

import com.hanifsr.moviecatalogue.data.source.local.entity.FavouriteMovieEntity;
import com.hanifsr.moviecatalogue.data.source.local.entity.FavouriteTvShowEntity;
import com.hanifsr.moviecatalogue.data.source.local.room.MovieCatalogueDao;

import java.util.List;

public class LocalRepository {

	private final MovieCatalogueDao movieCatalogueDao;

	private static LocalRepository INSTANCE;

	private LocalRepository(MovieCatalogueDao movieCatalogueDao) {
		this.movieCatalogueDao = movieCatalogueDao;
	}

	public static LocalRepository getInstance(MovieCatalogueDao movieCatalogueDao) {
		if (INSTANCE == null) {
			INSTANCE = new LocalRepository(movieCatalogueDao);
		}

		return INSTANCE;
	}

	public void insertFavouriteMovie(FavouriteMovieEntity favouriteMovieEntity) {
		movieCatalogueDao.insertFavouriteMovie(favouriteMovieEntity);
	}

	public void insertFavouriteTvShow(FavouriteTvShowEntity favouriteTvShowEntity) {
		movieCatalogueDao.insertFavouriteTvShow(favouriteTvShowEntity);
	}

	public void deleteFavouriteMovie(FavouriteMovieEntity favouriteMovieEntity) {
		movieCatalogueDao.deleteFavouriteMovie(favouriteMovieEntity);
	}

	public void deleteFavouriteTvShow(FavouriteTvShowEntity favouriteTvShowEntity) {
		movieCatalogueDao.deleteFavouriteTvShow(favouriteTvShowEntity);
	}

	public LiveData<List<FavouriteMovieEntity>> getFavouriteMovies() {
		return movieCatalogueDao.getFavouriteMovies();
	}

	public LiveData<List<FavouriteTvShowEntity>> getFavouriteTvShows() {
		return movieCatalogueDao.getFavouriteTvShows();
	}

	public LiveData<FavouriteMovieEntity> getFavouriteMovie(int id) {
		return movieCatalogueDao.getFavouriteMovie(id);
	}

	public LiveData<FavouriteTvShowEntity> getFavouriteTvShow(int id) {
		return movieCatalogueDao.getFavouriteTvShow(id);
	}
}
