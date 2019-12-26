package com.hanifsr.moviecatalogue.ui.adapter;

import com.hanifsr.moviecatalogue.data.source.local.entity.FavouriteTvShowEntity;

public interface OnFavouriteTvShowItemClickCallback {
	void onFavouriteTvShowItemClicked(FavouriteTvShowEntity favouriteTvShowEntity, int position);
}
