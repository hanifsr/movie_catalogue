package com.hanifsr.moviecatalogue.ui.favourites.sections;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.hanifsr.moviecatalogue.model.Movie;

import java.util.ArrayList;

import static com.hanifsr.moviecatalogue.database.MovieHelper.INSTANCE;

public class SectionsViewModel extends ViewModel {

	private MutableLiveData<ArrayList<Movie>> movieList = new MutableLiveData<>();

	LiveData<ArrayList<Movie>> getMovies() {
		return movieList;
	}

	void setMovie(int index) {
		ArrayList<Movie> movies = new ArrayList<>();
		if (index == 0) {
			movies = INSTANCE.getAllMovie();
		} else if (index == 1) {
			movies = INSTANCE.getAllTvShow();
		}
		movieList.postValue(movies);
	}
}
