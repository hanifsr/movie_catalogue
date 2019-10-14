package com.hanifsr.moviecatalogue.interfaces;

import com.hanifsr.moviecatalogue.model.Movie;

import java.util.ArrayList;

public interface OnGetMoviesCallback {

	void onSuccess(ArrayList<Movie> movies);

	void onError(Throwable error);
}
