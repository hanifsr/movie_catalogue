package com.hanifsr.moviecatalogue.ui.movies;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MoviesViewModel extends ViewModel {

	private MutableLiveData<String> text;

	public MoviesViewModel() {
		text = new MutableLiveData<>();
		text.setValue("This is movies fragment");
	}

	public LiveData<String> getText() {
		return text;
	}
}
