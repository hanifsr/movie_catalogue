package com.hanifsr.moviecatalogue.data.source.remote;

import com.hanifsr.moviecatalogue.data.source.remote.response.Movie;

import java.util.ArrayList;

public interface OnGetMoviesCallback {
	void onGetMoviesSuccess(ArrayList<Movie> movies);

	void onGetMoviesError(Throwable error);
}
