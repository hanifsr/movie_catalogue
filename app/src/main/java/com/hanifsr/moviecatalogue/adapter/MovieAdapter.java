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

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

	private ArrayList<Movie> movieArrayList;
	private OnMovieItemClickCallback onMovieItemClickCallback;

	public void setOnMovieItemClickCallback(OnMovieItemClickCallback onMovieItemClickCallback) {
		this.onMovieItemClickCallback = onMovieItemClickCallback;
	}

	public MovieAdapter(ArrayList<Movie> movieArrayList) {
		this.movieArrayList = movieArrayList;
	}

	@NonNull
	@Override
	public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);
		return new MovieViewHolder(view);
	}

	@Override
	public void onBindViewHolder(@NonNull final MovieViewHolder holder, int position) {
		Movie movie = movieArrayList.get(position);

		Glide.with(holder.itemView.getContext())
				.load(movie.getMoviePoster())
				.into(holder.ivPoster);

		holder.tvTitle.setText(movie.getMovieTitle());
		holder.tvGenre.setText(movie.getMovieGenres());

		holder.itemView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onMovieItemClickCallback.onMovieItemClicked(movieArrayList.get(holder.getAdapterPosition()));
			}
		});
	}

	@Override
	public int getItemCount() {
		return movieArrayList.size();
	}

	public class MovieViewHolder extends RecyclerView.ViewHolder {

		ImageView ivPoster;
		TextView tvTitle, tvGenre;

		public MovieViewHolder(@NonNull View itemView) {
			super(itemView);

			ivPoster = itemView.findViewById(R.id.iv_movie_poster);
			tvTitle = itemView.findViewById(R.id.tv_movie_title);
			tvGenre = itemView.findViewById(R.id.tv_movie_genre);
		}
	}

	public interface OnMovieItemClickCallback {
		void onMovieItemClicked(Movie movie);
	}
}