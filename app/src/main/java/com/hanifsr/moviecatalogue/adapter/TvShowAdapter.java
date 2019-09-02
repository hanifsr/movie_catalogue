package com.hanifsr.moviecatalogue.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.hanifsr.moviecatalogue.R;
import com.hanifsr.moviecatalogue.model.TvShow;

import java.util.ArrayList;

public class TvShowAdapter extends RecyclerView.Adapter<TvShowAdapter.TvShowViewHolder> {

	private ArrayList<TvShow> tvShowArrayList;
	private OnTvShowItemClickCallback onTvShowItemClickCallback;

	public void setOnTvShowItemClickCallback(OnTvShowItemClickCallback onTvShowItemClickCallback) {
		this.onTvShowItemClickCallback = onTvShowItemClickCallback;
	}

	public TvShowAdapter(ArrayList<TvShow> tvShowArrayList) {
		this.tvShowArrayList = tvShowArrayList;
	}

	@NonNull
	@Override
	public TvShowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tvshow, parent, false);
		return new TvShowViewHolder(view);
	}

	@Override
	public void onBindViewHolder(@NonNull final TvShowViewHolder holder, int position) {
		TvShow tvShow = tvShowArrayList.get(position);

		Glide.with(holder.itemView.getContext())
				.load(tvShow.getTvshowPoster())
				.into(holder.ivPoster);

		holder.tvTitle.setText(tvShow.getTvshowTitle());
		holder.tvGenre.setText(tvShow.getTvshowGenres());

		holder.itemView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onTvShowItemClickCallback.onTvShowItemClicked(tvShowArrayList.get(holder.getAdapterPosition()));
			}
		});
	}

	@Override
	public int getItemCount() {
		return tvShowArrayList.size();
	}

	public class TvShowViewHolder extends RecyclerView.ViewHolder {

		ImageView ivPoster;
		TextView tvTitle, tvGenre;

		public TvShowViewHolder(@NonNull View itemView) {
			super(itemView);

			ivPoster = itemView.findViewById(R.id.iv_tvshow_poster);
			tvTitle = itemView.findViewById(R.id.tv_tvshow_title);
			tvGenre = itemView.findViewById(R.id.tv_tvshow_genre);
		}
	}

	public interface OnTvShowItemClickCallback {
		void onTvShowItemClicked(TvShow tvShow);
	}
}
