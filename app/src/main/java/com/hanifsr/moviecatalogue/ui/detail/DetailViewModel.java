package com.hanifsr.moviecatalogue.ui.detail;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.hanifsr.moviecatalogue.data.source.MovieCatalogueRepository;
import com.hanifsr.moviecatalogue.data.source.local.entity.FavouriteMovieEntity;
import com.hanifsr.moviecatalogue.data.source.local.entity.FavouriteTvShowEntity;
import com.hanifsr.moviecatalogue.data.source.remote.response.Movie;

public class DetailViewModel extends ViewModel {

	private static final String TAG = "GGWP";

	private MovieCatalogueRepository movieCatalogueRepository;
	private boolean favourite;

	private LiveData<Movie> movie;
	private LiveData<FavouriteMovieEntity> favouriteMovie;
	private LiveData<FavouriteTvShowEntity> favouriteTvShow;

	public DetailViewModel(MovieCatalogueRepository movieCatalogueRepository) {
		this.movieCatalogueRepository = movieCatalogueRepository;
	}

	LiveData<Movie> getDetails(int movieId, int index, String language) {
		if (movie == null) {
			if (index == 0) {
				movie = movieCatalogueRepository.getMovieDetail(movieId, language);
			} else if (index == 1) {
				movie = movieCatalogueRepository.getTvShowDetail(movieId, language);
			}
		}

		return movie;
	}

	LiveData<FavouriteMovieEntity> getFavouriteMovie(int id) {
		favouriteMovie = movieCatalogueRepository.getFavouriteMovie(id);

		return favouriteMovie;
	}

	LiveData<FavouriteTvShowEntity> getFavouriteTvShow(int id) {
		favouriteTvShow = movieCatalogueRepository.getFavouriteTvShow(id);

		return favouriteTvShow;
	}

	boolean isFavourite() {
		Log.d(TAG, "isFavourite: " + favourite);
		return favourite;
	}

	void setFavourite(boolean favourite) {
		this.favourite = favourite;
		Log.d(TAG, "setFavourite: " + this.favourite);
	}

	long insertToFavourite(Movie movie, int index) {
		if (index == 0) {
			FavouriteMovieEntity favouriteMovieEntity = new FavouriteMovieEntity(movie.getId(),
					movie.getPosterPath(),
					movie.getBackdropPath(),
					movie.getTitle(),
					movie.getGenresHelper(),
					movie.getReleaseDate(),
					movie.getUserScore(),
					movie.getOverview());

			movieCatalogueRepository.insertFavouriteMovie(favouriteMovieEntity);
			favourite = true;
		} else if (index == 1) {
			FavouriteTvShowEntity favouriteTvShowEntity = new FavouriteTvShowEntity(movie.getId(),
					movie.getPosterPath(),
					movie.getBackdropPath(),
					movie.getTitle(),
					movie.getGenresHelper(),
					movie.getReleaseDate(),
					movie.getUserScore(),
					movie.getOverview());

			movieCatalogueRepository.insertFavouriteTvShow(favouriteTvShowEntity);
			favourite = true;
		}

		return favourite ? movie.getId() : 0;
	}

	int deleteFromFavourite(Movie movie, int index) {
		if (index == 0) {
			FavouriteMovieEntity favouriteMovieEntity = new FavouriteMovieEntity(movie.getId(),
					movie.getPosterPath(),
					movie.getBackdropPath(),
					movie.getTitle(),
					movie.getGenresHelper(),
					movie.getReleaseDate(),
					movie.getUserScore(),
					movie.getOverview());

			movieCatalogueRepository.deleteFavouriteMovie(favouriteMovieEntity);
			favourite = false;
		} else if (index == 1) {
			FavouriteTvShowEntity favouriteTvShowEntity = new FavouriteTvShowEntity(movie.getId(),
					movie.getPosterPath(),
					movie.getBackdropPath(),
					movie.getTitle(),
					movie.getGenresHelper(),
					movie.getReleaseDate(),
					movie.getUserScore(),
					movie.getOverview());

			movieCatalogueRepository.deleteFavouriteTvShow(favouriteTvShowEntity);
			favourite = false;
		}

		return favourite ? 0 : movie.getId();
	}
}
