package com.hanifsr.moviecatalogue.ui.tvshows;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TvShowsViewModel extends ViewModel {

	private MutableLiveData<String> text;

	public TvShowsViewModel() {
		text = new MutableLiveData<>();
		text.setValue("This is tv shows fragment");
	}

	public LiveData<String> getText() {
		return text;
	}
}
