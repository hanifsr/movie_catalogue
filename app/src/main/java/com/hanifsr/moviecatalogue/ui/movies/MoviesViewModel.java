package com.hanifsr.moviecatalogue.ui.movies;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.hanifsr.moviecatalogue.data.source.MovieCatalogueRepository;
import com.hanifsr.moviecatalogue.data.source.remote.response.Movie;

import java.util.List;

public class MoviesViewModel extends ViewModel {

	private static final String TAG = "GGWP";

	private MovieCatalogueRepository movieCatalogueRepository;
	private boolean readyToDelete = false;
	private String language;

	private MutableLiveData<String> searchQuery = new MutableLiveData<>();
	private LiveData<List<Movie>> movies = Transformations.switchMap(searchQuery, new Function<String, LiveData<List<Movie>>>() {
		@Override
		public LiveData<List<Movie>> apply(String input) {
//			Log.d(TAG, "movies.Transformations -> language: " + language + ", searchQuery: " + input);
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

	LiveData<List<Movie>> getMovies(String language) {
		if (movies.getValue() == null) {
			searchQuery.postValue(null);
		}
		this.language = language;

		return movies;
	}
}
