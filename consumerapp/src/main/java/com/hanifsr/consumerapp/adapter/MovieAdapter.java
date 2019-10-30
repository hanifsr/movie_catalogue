package com.hanifsr.consumerapp.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.hanifsr.consumerapp.R;
import com.hanifsr.consumerapp.interfaces.OnMovieItemClickCallback;
import com.hanifsr.consumerapp.model.Movie;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

	private final ArrayList<Movie> movieArrayList = new ArrayList<>();
	private OnMovieItemClickCallback onMovieItemClickCallback;

	public void setData(ArrayList<Movie> movies) {
		movieArrayList.clear();
		movieArrayList.addAll(movies);
		notifyDataSetChanged();
	}

	public void removeItem(int position) {
		movieArrayList.remove(position);
		notifyItemRemoved(position);
		notifyItemRangeChanged(position, movieArrayList.size());
	}

	public void setOnMovieItemClickCallback(OnMovieItemClickCallback onMovieItemClickCallback) {
		this.onMovieItemClickCallback = onMovieItemClickCallback;
	}

	@NonNull
	@Override
	public MovieAdapter.MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);
		return new MovieViewHolder(view);
	}

	@Override
	public void onBindViewHolder(@NonNull final MovieAdapter.MovieViewHolder holder, int position) {
		Movie movie = movieArrayList.get(position);

		String IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500/";
		Glide.with(holder.itemView.getContext())
				.load(IMAGE_BASE_URL + movie.getPosterPath())
				.into(holder.ivPoster);

		holder.tvDateRelease.setText(movie.getDateRelease().split("-")[0]);
		holder.tvTitle.setText(movie.getTitle());
		holder.tvGenre.setText(movie.getGenresHelper());
		holder.tvRating.setText(movie.getUserScore());

		holder.itemView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onMovieItemClickCallback.onMovieItemClicked(movieArrayList.get(holder.getAdapterPosition()), holder.getAdapterPosition());
			}
		});
	}

	@Override
	public int getItemCount() {
		return movieArrayList.size();
	}

	class MovieViewHolder extends RecyclerView.ViewHolder {

		ImageView ivPoster;
		TextView tvDateRelease, tvTitle, tvGenre, tvRating;

		MovieViewHolder(@NonNull View itemView) {
			super(itemView);

			ivPoster = itemView.findViewById(R.id.iv_movie_poster);
			tvDateRelease = itemView.findViewById(R.id.tv_movie_date_release);
			tvTitle = itemView.findViewById(R.id.tv_movie_title);
			tvGenre = itemView.findViewById(R.id.tv_movie_genre);
			tvRating = itemView.findViewById(R.id.tv_movie_rating);
		}
	}
}
