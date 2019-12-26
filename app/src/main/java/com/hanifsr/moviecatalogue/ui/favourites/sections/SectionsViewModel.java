package com.hanifsr.moviecatalogue.ui.favourites.sections;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import androidx.paging.PagedList;

import com.hanifsr.moviecatalogue.data.source.MovieCatalogueRepository;
import com.hanifsr.moviecatalogue.data.source.local.entity.FavouriteMovieEntity;
import com.hanifsr.moviecatalogue.data.source.local.entity.FavouriteTvShowEntity;

public class SectionsViewModel extends ViewModel {

	private static final String TAG = "GGWP";

	private MovieCatalogueRepository movieCatalogueRepository;

	private MutableLiveData<Boolean> deleted = new MutableLiveData<>();
	private LiveData<PagedList<FavouriteMovieEntity>> favouriteMovies = Transformations.switchMap(deleted, input -> movieCatalogueRepository.getFavouriteMovies());
	private LiveData<PagedList<FavouriteTvShowEntity>> favouriteTvShows = Transformations.switchMap(deleted, input -> movieCatalogueRepository.getFavouriteTvShows());

	public SectionsViewModel(MovieCatalogueRepository movieCatalogueRepository) {
		this.movieCatalogueRepository = movieCatalogueRepository;
	}

	void setDeleted() {
		deleted.postValue(true);
	}

	LiveData<PagedList<FavouriteMovieEntity>> getFavouriteMovies() {
		if (favouriteMovies.getValue() == null) {
			deleted.postValue(false);
		}

		return favouriteMovies;
	}

	LiveData<PagedList<FavouriteTvShowEntity>> getFavouriteTvShows() {
		if (favouriteTvShows.getValue() == null) {
			deleted.postValue(false);
		}

		return favouriteTvShows;
	}
}
