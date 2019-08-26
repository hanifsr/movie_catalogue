package com.hanifsr.moviecatalogue;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class MovieAdapter extends BaseAdapter {

	private Context context;
	private ArrayList<Movie> movies;

	public MovieAdapter(Context context) {
		this.context = context;
		movies = new ArrayList<>();
	}

	public void setMovies(ArrayList<Movie> movies) {
		this.movies = movies;
	}

	@Override
	public int getCount() {
		return movies.size();
	}

	@Override
	public Object getItem(int position) {
		return movies.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View view, ViewGroup viewGroup) {
		if (view == null) {
			view = LayoutInflater.from(context).inflate(R.layout.item_movie, viewGroup, false);
		}

		ViewHolder viewHolder = new ViewHolder(view);
		Movie movie = (Movie) getItem(position);
		viewHolder.bind(movie);

		return view;
	}

	private class ViewHolder {

		private TextView tvTitle, tvGenres;
		private ImageView ivPoster;

		ViewHolder(View view) {
			tvTitle = view.findViewById(R.id.tv_title);
			tvGenres = view.findViewById(R.id.tv_genre);
			ivPoster = view.findViewById(R.id.iv_poster);
		}

		void bind(Movie movie) {
			tvTitle.setText(movie.getTitle());
			tvGenres.setText(movie.getGenres());
			// ivPoster.setImageResource(movie.getPoster());
			Glide.with(context).load(movie.getPoster()).into(ivPoster);
		}
	}
}