package com.hanifsr.consumerapp.interfaces;

import com.hanifsr.consumerapp.model.Movie;

public interface OnMovieItemClickCallback {
	void onMovieItemClicked(Movie movie, int position);
}
