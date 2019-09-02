package com.hanifsr.moviecatalogue;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hanifsr.moviecatalogue.model.Movie;

public class MovieDetail extends AppCompatActivity {

	public static final String EXTRA_MOVIE = "extra_movie";
	private ImageView ivPoster;
	private TextView tvTitle, tvGenres, tvDateRelease, tvRating, tvRuntime, tvOverview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_movie_detail);

		ivPoster = findViewById(R.id.iv_movie_poster_detail);
		tvTitle = findViewById(R.id.tv_movie_title_detail);
		tvGenres = findViewById(R.id.tv_movie_genres_detail);
		tvDateRelease = findViewById(R.id.tv_movie_date_release_detail);
		tvRating = findViewById(R.id.tv_movie_rating_detail);
		tvRuntime = findViewById(R.id.tv_movie_runtime_detail);
		tvOverview = findViewById(R.id.tv_movie_overview_detail);

		Movie movie = getIntent().getParcelableExtra(EXTRA_MOVIE);

		// ivPoster.setImageResource(movie.getMoviePoster());
		Glide.with(this).load(movie.getMoviePoster()).into(ivPoster);
		tvTitle.setText(movie.getMovieTitle());
		tvGenres.setText(movie.getMovieGenres());
		tvDateRelease.setText(movie.getMovieDateRelease());
		tvRating.setText(movie.getMovieRating());
		tvRuntime.setText(movie.getMovieRuntime());
		tvOverview.setText(movie.getMovieOverview());

		setActionBarTitle(movie.getMovieTitle());
	}

	private void setActionBarTitle(String title) {
		if (getSupportActionBar() != null) {
			getSupportActionBar().setTitle(title);
			getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}
}
