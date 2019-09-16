package com.hanifsr.moviecatalogue;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.hanifsr.moviecatalogue.model.Movie;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MovieDetail extends AppCompatActivity {

	public static final String EXTRA_MOVIE = "extra_movie";
	public static final String EXTRA_INDEX = "extra_index";

	private ProgressBar progressBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_movie_detail);

		progressBar = findViewById(R.id.progress_bar_detail);
		showLoading(true);

		ImageView ivPoster = findViewById(R.id.iv_movie_poster_detail);
		TextView tvTitle = findViewById(R.id.tv_movie_title_detail);
		TextView tvGenres = findViewById(R.id.tv_movie_genres_detail);
		TextView tvDateRelease = findViewById(R.id.tv_movie_date_release_detail);
		TextView tvRating = findViewById(R.id.tv_movie_rating_detail);
//		TextView tvRuntime = findViewById(R.id.tv_movie_runtime_detail);
		TextView tvOverview = findViewById(R.id.tv_movie_overview_detail);

		Movie movie = getIntent().getParcelableExtra(EXTRA_MOVIE);
		int index = getIntent().getIntExtra(EXTRA_INDEX, 0);

		if (index == 1) {
			TextView tvDateReleaseText = findViewById(R.id.movie_date_release_text);
			tvDateReleaseText.setText(R.string.first_air_date);
		}

		String dateRelease = movie.getDateRelease();
		String formatedDateRelease = null;
		try {
			Date date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(dateRelease);
			formatedDateRelease = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault()).format(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		Glide.with(this).load(movie.getPosterPath()).into(ivPoster);
		tvTitle.setText(movie.getTitle());
		tvGenres.setText(movie.getGenres());
		tvDateRelease.setText(formatedDateRelease);
		tvRating.setText(movie.getUserScore());
//		tvRuntime.setText(movie.getRuntime());

		if (movie.getOverview().isEmpty()) {
			tvOverview.setText(R.string.no_overview);
		} else {
			tvOverview.setText(movie.getOverview());
		}

		setActionBarTitle(movie.getTitle());

		if (getIntent().hasExtra(EXTRA_MOVIE)) {
			showLoading(false);
		}
	}

	private void setActionBarTitle(String title) {
		if (getSupportActionBar() != null) {
			getSupportActionBar().setTitle(title);
			getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	private void showLoading(Boolean state) {
		if (state) {
			progressBar.setVisibility(View.VISIBLE);
		} else {
			progressBar.setVisibility(View.GONE);
		}
	}
}
