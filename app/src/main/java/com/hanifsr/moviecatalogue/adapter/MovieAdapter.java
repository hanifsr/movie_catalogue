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
import com.hanifsr.moviecatalogue.model.Movie;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ListViewHolder> {

	private ArrayList<Movie> movieArrayList;
	private OnItemClickCallback onItemClickCallback;

	public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
		this.onItemClickCallback = onItemClickCallback;
	}

	public MovieAdapter(ArrayList<Movie> movieArrayList) {
		this.movieArrayList = movieArrayList;
	}

	@NonNull
	@Override
	public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);
		return new ListViewHolder(view);
	}

	@Override
	public void onBindViewHolder(@NonNull final ListViewHolder holder, int position) {
		Movie movie = movieArrayList.get(position);

		Glide.with(holder.itemView.getContext())
				.load(movie.getPoster())
				.into(holder.ivPoster);

		holder.tvTitle.setText(movie.getTitle());
		holder.tvGenre.setText(movie.getGenres());

		holder.itemView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onItemClickCallback.onItemClicked(movieArrayList.get(holder.getAdapterPosition()));
			}
		});
	}

	@Override
	public int getItemCount() {
		return movieArrayList.size();
	}

	public class ListViewHolder extends RecyclerView.ViewHolder {

		ImageView ivPoster;
		TextView tvTitle, tvGenre;

		public ListViewHolder(@NonNull View itemView) {
			super(itemView);

			ivPoster = itemView.findViewById(R.id.iv_poster);
			tvTitle = itemView.findViewById(R.id.tv_title);
			tvGenre = itemView.findViewById(R.id.tv_genre);
		}
	}

	public interface OnItemClickCallback {
		void onItemClicked(Movie movie);
	}
}