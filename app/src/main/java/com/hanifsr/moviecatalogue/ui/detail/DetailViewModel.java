package com.hanifsr.moviecatalogue.ui.detail;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.hanifsr.moviecatalogue.data.source.MovieCatalogueRepository;
import com.hanifsr.moviecatalogue.data.source.remote.response.Movie;

import java.util.Locale;

public class DetailViewModel extends ViewModel {

	private static final String TAG = "GGWP";
	private MovieCatalogueRepository movieCatalogueRepository;

	private LiveData<Movie> movie;

	public DetailViewModel(MovieCatalogueRepository movieCatalogueRepository) {
		this.movieCatalogueRepository = movieCatalogueRepository;
	}

	// TODO: Fix this method
	LiveData<Movie> getDetails(int movieId, int index) {
		/*
		 * This codes should be the right implementation of viewmodel
		 * but the Unit Test failed
		 */
		String language = Locale.getDefault().getISO3Language().substring(0, 2) + "-" + Locale.getDefault().getISO3Country().substring(0, 2);
		if (movie == null) {
			if (index == 0) {
				movie = movieCatalogueRepository.getMovieDetail(movieId, language);
			} else if (index == 1) {
				movie = movieCatalogueRepository.getTvShowDetail(movieId, language);
			}
		}

		/*
		 * This codes should be the wrong implementation of viewmodel
		 * but the Unit Test succeed
		 */
		/*if (index == 0) {
			movie = movieCatalogueRepository.getMovieDetail(movieId, "en-GB");
		} else if (index == 1) {
			movie = movieCatalogueRepository.getTvShowDetail(movieId, "en-GB");
		}*/

		return movie;
	}
}
