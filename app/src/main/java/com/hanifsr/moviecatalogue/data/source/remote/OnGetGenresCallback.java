package com.hanifsr.moviecatalogue.data.source.remote;

import com.hanifsr.moviecatalogue.data.source.remote.response.Genre;

import java.util.ArrayList;

public interface OnGetGenresCallback {
	void onGetGenresSuccess(ArrayList<Genre> genres);

	void onGetGenresError(Throwable error);
}
