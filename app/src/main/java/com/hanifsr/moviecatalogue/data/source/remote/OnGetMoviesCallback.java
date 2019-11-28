package com.hanifsr.moviecatalogue.data.source.remote;

import com.hanifsr.moviecatalogue.data.source.remote.response.Movie;

import java.util.ArrayList;

public interface OnGetMoviesCallback {

	void onSuccess(ArrayList<Movie> movies);

	void onError(Throwable error);
}
