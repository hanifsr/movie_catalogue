package com.hanifsr.moviecatalogue.ui.adapter;

import com.hanifsr.moviecatalogue.data.source.local.entity.FavouriteMovieEntity;

public interface OnFavouriteMovieItemClickCallback {
	void onFavouriteMovieItemClicked(FavouriteMovieEntity favouriteMovieEntity, int position);
}
