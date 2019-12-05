package com.hanifsr.moviecatalogue.data.source.remote;

import com.hanifsr.moviecatalogue.data.source.remote.response.Movie;

public interface OnGetDetailCallback {
	void onGetDetailSuccess(Movie movie);

	void onGetDetailError(Throwable error);
}
