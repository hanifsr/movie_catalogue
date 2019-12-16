package com.hanifsr.moviecatalogue.ui.movies;

import android.util.Log;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.hanifsr.moviecatalogue.data.source.MovieCatalogueRepository;
import com.hanifsr.moviecatalogue.data.source.remote.response.Movie;

import java.util.ArrayList;
import java.util.Locale;

public class MoviesViewModel extends ViewModel {

	private static final String TAG = "GGWP";
	private MovieCatalogueRepository movieCatalogueRepository;
	private boolean readyToDelete = false;

	private MutableLiveData<String> searchQuery = new MutableLiveData<>();
	private LiveData<ArrayList<Movie>> movies = Transformations.switchMap(searchQuery, new Function<String, LiveData<ArrayList<Movie>>>() {
		@Override
		public LiveData<ArrayList<Movie>> apply(String input) {
			String language = Locale.getDefault().getISO3Language().substring(0, 2) + "-" + Locale.getDefault().getISO3Country().substring(0, 2);
			Log.d(TAG, "movies.Transformations -> language: " + language + ", searchQuery: " + input);
			if (input != null) {
				return movieCatalogueRepository.getQueriedMovies(language, input);
			}
			return movieCatalogueRepository.getMovies(language);
		}
	});

	public MoviesViewModel(MovieCatalogueRepository movieCatalogueRepository) {
		this.movieCatalogueRepository = movieCatalogueRepository;
	}

	boolean isReadyToDelete() {
		return readyToDelete;
	}

	void setSearchQuery(String searchQuery) {
		readyToDelete = searchQuery != null;
		this.searchQuery.postValue(searchQuery);
	}


	// TODO: Fix this method
	LiveData<ArrayList<Movie>> getMovies() {
		/*
		 * This codes should be the right implementation of viewmodel
		 * but the Unit Test failed
		 */
		if (movies.getValue() == null) {
			searchQuery.postValue(null);
		}
		return movies;

		/*
		 * This codes should be the wrong implementation of viewmodel
		 * but the Unit Test succeed
		 */
//		return movieCatalogueRepository.getMovies("en-GB");
	}
}
