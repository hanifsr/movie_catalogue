package com.hanifsr.moviecatalogue.interfaces;

import com.hanifsr.moviecatalogue.model.Movie;

public interface OnGetDetailCallback {

	void onSuccess(Movie movie);

	void onError(Throwable error);
}
