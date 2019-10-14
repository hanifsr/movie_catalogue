package com.hanifsr.moviecatalogue.interfaces;

import com.hanifsr.moviecatalogue.model.Movie;

public interface OnMovieItemClickCallback {
	void onMovieItemClicked(Movie movie, int position);
}
