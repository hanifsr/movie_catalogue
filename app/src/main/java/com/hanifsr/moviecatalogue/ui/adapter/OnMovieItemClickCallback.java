package com.hanifsr.moviecatalogue.ui.adapter;

import com.hanifsr.moviecatalogue.data.source.remote.response.Movie;

public interface OnMovieItemClickCallback {
	void onMovieItemClicked(Movie movie, int position);
}
