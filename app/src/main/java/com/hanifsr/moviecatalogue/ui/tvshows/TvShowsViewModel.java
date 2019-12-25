package com.hanifsr.moviecatalogue.ui.tvshows;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.hanifsr.moviecatalogue.data.source.MovieCatalogueRepository;
import com.hanifsr.moviecatalogue.data.source.remote.response.Movie;

import java.util.List;

public class TvShowsViewModel extends ViewModel {

	private static final String TAG = "GGWP";
	private MovieCatalogueRepository movieCatalogueRepository;
	private boolean readyToDelete = false;
	private String language;

	private MutableLiveData<String> searchQuery = new MutableLiveData<>();
	private LiveData<List<Movie>> tvShows = Transformations.switchMap(searchQuery, new Function<String, LiveData<List<Movie>>>() {
		@Override
		public LiveData<List<Movie>> apply(String input) {
//			Log.d(TAG, "tvShows.Transformations -> language: " + language + ", searchQuery: " + input);
			if (input != null) {
				return movieCatalogueRepository.getQueriedTvShows(language, input);
			}
			return movieCatalogueRepository.getTvShows(language);
		}
	});

	public TvShowsViewModel(MovieCatalogueRepository movieCatalogueRepository) {
		this.movieCatalogueRepository = movieCatalogueRepository;
	}

	boolean isReadyToDelete() {
		return readyToDelete;
	}

	void setSearchQuery(String searchQuery) {
		readyToDelete = searchQuery != null;
		this.searchQuery.postValue(searchQuery);
	}

	LiveData<List<Movie>> getTvShows(String language) {
		if (tvShows.getValue() == null) {
			searchQuery.postValue(null);
		}
		this.language = language;

		return tvShows;
	}
}
