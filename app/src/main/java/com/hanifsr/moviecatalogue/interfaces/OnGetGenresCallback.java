package com.hanifsr.moviecatalogue.interfaces;

import com.hanifsr.moviecatalogue.model.Genre;

import java.util.ArrayList;

public interface OnGetGenresCallback {

	void onSuccess(ArrayList<Genre> genres);

	void onError(Throwable error);
}
