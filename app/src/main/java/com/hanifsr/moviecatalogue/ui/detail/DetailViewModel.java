package com.hanifsr.moviecatalogue.ui.detail;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.hanifsr.moviecatalogue.data.source.MovieCatalogueRepository;
import com.hanifsr.moviecatalogue.data.source.remote.response.Movie;

public class DetailViewModel extends ViewModel {

	private static final String TAG = "GGWP";
	private MovieCatalogueRepository movieCatalogueRepository;

	private LiveData<Movie> movie;

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
}
