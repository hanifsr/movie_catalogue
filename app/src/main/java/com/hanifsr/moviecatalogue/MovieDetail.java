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

		ivPoster = findViewById(R.id.iv_poster_detail);
		tvTitle = findViewById(R.id.tv_title_detail);
		tvGenres = findViewById(R.id.tv_genres_detail);
		tvDateRelease = findViewById(R.id.tv_date_release_detail);
		tvRating = findViewById(R.id.tv_rating_detail);
		tvRuntime = findViewById(R.id.tv_runtime_detail);
		tvOverview = findViewById(R.id.tv_overview_detail);

		Movie movie = getIntent().getParcelableExtra(EXTRA_MOVIE);

		// ivPoster.setImageResource(movie.getPoster());
		Glide.with(this).load(movie.getPoster()).into(ivPoster);
		tvTitle.setText(movie.getTitle());
		tvGenres.setText(movie.getGenres());
		tvDateRelease.setText(movie.getDateRelease());
		tvRating.setText(movie.getRating());
		tvRuntime.setText(movie.getRuntime());
		tvOverview.setText(movie.getOverview());

		setActionBarTitle(movie.getTitle());
	}

	private void setActionBarTitle(String title) {
		if (getSupportActionBar() != null) {
			getSupportActionBar().setTitle(title);
			getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}
}
