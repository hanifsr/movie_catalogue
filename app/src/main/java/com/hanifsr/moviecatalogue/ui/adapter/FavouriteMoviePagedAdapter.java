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
import com.hanifsr.moviecatalogue.data.source.local.entity.FavouriteMovieEntity;

public class FavouriteMoviePagedAdapter extends PagedListAdapter<FavouriteMovieEntity, FavouriteMoviePagedAdapter.FavouriteMovieViewHolder> {

	private OnFavouriteMovieItemClickCallback onFavouriteMovieItemClickCallback;
	private static DiffUtil.ItemCallback<FavouriteMovieEntity> DIFF_CALLBACK =
			new DiffUtil.ItemCallback<FavouriteMovieEntity>() {
				@Override
				public boolean areItemsTheSame(@NonNull FavouriteMovieEntity oldItem, @NonNull FavouriteMovieEntity newItem) {
					return oldItem.getId() == newItem.getId();
				}

				@SuppressLint("DiffUtilEquals")
				@Override
				public boolean areContentsTheSame(@NonNull FavouriteMovieEntity oldItem, @NonNull FavouriteMovieEntity newItem) {
					return oldItem.equals(newItem);
				}
			};

	public FavouriteMoviePagedAdapter() {
		super(DIFF_CALLBACK);
	}

	public void setOnFavouriteMovieItemClickCallback(OnFavouriteMovieItemClickCallback onFavouriteMovieItemClickCallback) {
		this.onFavouriteMovieItemClickCallback = onFavouriteMovieItemClickCallback;
	}

	@NonNull
	@Override
	public FavouriteMovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);
		return new FavouriteMovieViewHolder(view);
	}

	@Override
	public void onBindViewHolder(@NonNull FavouriteMovieViewHolder holder, int position) {
		FavouriteMovieEntity favouriteMovieEntity = getItem(position);
		if (favouriteMovieEntity != null) {
			String IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500/";
			Glide.with(holder.itemView.getContext())
					.load(IMAGE_BASE_URL + favouriteMovieEntity.getPosterPath())
					.into(holder.ivPoster);
			holder.tvTitle.setText(favouriteMovieEntity.getTitle());
			holder.tvGenre.setText(favouriteMovieEntity.getGenre());
			if (favouriteMovieEntity.getReleaseDate() == null) {
				holder.tvReleaseDate.setText("0");
			} else {
				holder.tvReleaseDate.setText(favouriteMovieEntity.getReleaseDate().split("-")[0]);
			}
			holder.tvUserScore.setText(favouriteMovieEntity.getUserScore());

			holder.itemView.setOnClickListener(v -> {
				onFavouriteMovieItemClickCallback.onFavouriteMovieItemClicked(getItem(holder.getAdapterPosition()), holder.getAdapterPosition());
			});
		}
	}

	class FavouriteMovieViewHolder extends RecyclerView.ViewHolder {

		ImageView ivPoster;
		TextView tvTitle, tvGenre, tvReleaseDate, tvUserScore;

		FavouriteMovieViewHolder(@NonNull View itemView) {
			super(itemView);

			ivPoster = itemView.findViewById(R.id.iv_movie_poster);
			tvTitle = itemView.findViewById(R.id.tv_movie_title);
			tvGenre = itemView.findViewById(R.id.tv_movie_genre);
			tvReleaseDate = itemView.findViewById(R.id.tv_movie_release_date);
			tvUserScore = itemView.findViewById(R.id.tv_movie_user_score);
		}
	}
}
