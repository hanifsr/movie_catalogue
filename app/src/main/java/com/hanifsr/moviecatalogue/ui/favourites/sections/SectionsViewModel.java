package com.hanifsr.moviecatalogue.ui.favourites.sections;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.hanifsr.moviecatalogue.model.Movie;

import java.util.ArrayList;

public class SectionsViewModel extends ViewModel {

	private MutableLiveData<ArrayList<Movie>> movieList = new MutableLiveData<>();

	void setMovie(ArrayList<Movie> movies) {
		movieList.postValue(movies);
	}

	LiveData<ArrayList<Movie>> getMovies() {
		return movieList;
	}
}
