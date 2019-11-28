package com.hanifsr.moviecatalogue.data.source.remote;

import com.hanifsr.moviecatalogue.data.source.remote.response.Movie;

public interface OnGetDetailCallback {

	void onSuccess(Movie movie);

	void onError(Throwable error);
}
