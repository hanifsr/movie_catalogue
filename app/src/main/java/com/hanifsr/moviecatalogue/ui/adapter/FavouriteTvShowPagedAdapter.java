package com.hanifsr.moviecatalogue.ui.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.hanifsr.moviecatalogue.R;
import com.hanifsr.moviecatalogue.data.source.local.entity.FavouriteTvShowEntity;

public class FavouriteTvShowPagedAdapter extends PagedListAdapter<FavouriteTvShowEntity, FavouriteTvShowPagedAdapter.FavouriteTvShowViewHolder> {

	private OnFavouriteTvShowItemClickCallback onFavouriteTvShowItemClickCallback;
	private static DiffUtil.ItemCallback<FavouriteTvShowEntity> DIFF_CALLBACK =
			new DiffUtil.ItemCallback<FavouriteTvShowEntity>() {
				@Override
				public boolean areItemsTheSame(@NonNull FavouriteTvShowEntity oldItem, @NonNull FavouriteTvShowEntity newItem) {
					return oldItem.getId() == newItem.getId();
				}

				@SuppressLint("DiffUtilEquals")
				@Override
				public boolean areContentsTheSame(@NonNull FavouriteTvShowEntity oldItem, @NonNull FavouriteTvShowEntity newItem) {
					return oldItem.equals(newItem);
				}
			};

	public FavouriteTvShowPagedAdapter() {
		super(DIFF_CALLBACK);
	}

	public void setOnFavouriteTvShowItemClickCallback(OnFavouriteTvShowItemClickCallback onFavouriteTvShowItemClickCallback) {
		this.onFavouriteTvShowItemClickCallback = onFavouriteTvShowItemClickCallback;
	}

	@NonNull
	@Override
	public FavouriteTvShowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);
		return new FavouriteTvShowViewHolder(view);
	}

	@Override
	public void onBindViewHolder(@NonNull FavouriteTvShowViewHolder holder, int position) {
		FavouriteTvShowEntity favouriteTvShowEntity = getItem(position);
		if (favouriteTvShowEntity != null) {
			String IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500/";
			Glide.with(holder.itemView.getContext())
					.load(IMAGE_BASE_URL + favouriteTvShowEntity.getPosterPath())
					.into(holder.ivPoster);
			holder.tvTitle.setText(favouriteTvShowEntity.getTitle());
			holder.tvGenre.setText(favouriteTvShowEntity.getGenre());
			if (favouriteTvShowEntity.getFirstAirDate() == null) {
				holder.tvReleaseDate.setText("0");
			} else {
				holder.tvReleaseDate.setText(favouriteTvShowEntity.getFirstAirDate().split("-")[0]);
			}
			holder.tvUserScore.setText(favouriteTvShowEntity.getUserScore());

			holder.itemView.setOnClickListener(v -> {
				onFavouriteTvShowItemClickCallback.onFavouriteTvShowItemClicked(getItem(holder.getAdapterPosition()), holder.getAdapterPosition());
			});
		}
	}

	class FavouriteTvShowViewHolder extends RecyclerView.ViewHolder {

		ImageView ivPoster;
		TextView tvTitle, tvGenre, tvReleaseDate, tvUserScore;

		FavouriteTvShowViewHolder(@NonNull View itemView) {
			super(itemView);

			ivPoster = itemView.findViewById(R.id.iv_movie_poster);
			tvTitle = itemView.findViewById(R.id.tv_movie_title);
			tvGenre = itemView.findViewById(R.id.tv_movie_genre);
			tvReleaseDate = itemView.findViewById(R.id.tv_movie_release_date);
			tvUserScore = itemView.findViewById(R.id.tv_movie_user_score);
		}
	}
}
