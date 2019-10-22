package com.hanifsr.moviecatalogue;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;
import com.hanifsr.moviecatalogue.model.Movie;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static com.hanifsr.moviecatalogue.database.DatabaseContract.GENRES;
import static com.hanifsr.moviecatalogue.database.DatabaseContract.MovieColumns.DATE_RELEASE;
import static com.hanifsr.moviecatalogue.database.DatabaseContract.MovieColumns.MOVIE_CONTENT_URI;
import static com.hanifsr.moviecatalogue.database.DatabaseContract.MovieColumns.MOVIE_ID;
import static com.hanifsr.moviecatalogue.database.DatabaseContract.OVERVIEW;
import static com.hanifsr.moviecatalogue.database.DatabaseContract.POSTER_PATH;
import static com.hanifsr.moviecatalogue.database.DatabaseContract.TITLE;
import static com.hanifsr.moviecatalogue.database.DatabaseContract.TvShowColumns.FIRST_AIR_DATE;
import static com.hanifsr.moviecatalogue.database.DatabaseContract.TvShowColumns.TV_SHOW_CONTENT_URI;
import static com.hanifsr.moviecatalogue.database.DatabaseContract.TvShowColumns.TV_SHOW_ID;
import static com.hanifsr.moviecatalogue.database.DatabaseContract.USER_SCORE;

public class MovieDetail extends AppCompatActivity implements View.OnClickListener {

	public static final String EXTRA_MOVIE = "extra_movie";
	public static final String EXTRA_POSITION = "extra_position";
	public static final String EXTRA_INDEX = "extra_index";
	public static final String EXTRA_TITLE = "extra_title";

	private String IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w780/";

	public static final int REQUEST_DELETE = 200;
	public static final int RESULT_DELETE = 201;

	private ProgressBar progressBar;
	private Button btnFavourite;
	private RatingBar ratingBar;

	private int position;
	private int index;
	private int isFavourite;
	private Uri uriWithId;

	private Movie movie;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_movie_detail);

		progressBar = findViewById(R.id.progress_bar_detail);
		btnFavourite = findViewById(R.id.btn_favourite);
		ratingBar = findViewById(R.id.rating_bar_movie);
		showLoading(true);

		final ImageView ivPoster = findViewById(R.id.iv_movie_poster_detail);
		final ImageView ivBackdrop = findViewById(R.id.iv_movie_backdrop);
		final TextView tvTitle = findViewById(R.id.tv_movie_title_detail);
		final TextView tvGenres = findViewById(R.id.tv_movie_genres_detail);
		final TextView tvDateRelease = findViewById(R.id.tv_movie_date_release_detail);
		final TextView tvOverview = findViewById(R.id.tv_movie_overview_detail);

		btnFavourite.setOnClickListener(this);

		int movieId = getIntent().getIntExtra(EXTRA_MOVIE, 0);
		position = getIntent().getIntExtra(EXTRA_POSITION, 0);
		index = getIntent().getIntExtra(EXTRA_INDEX, 0);


		if (index == 0) {
			uriWithId = Uri.parse(MOVIE_CONTENT_URI + "/" + movieId);
		} else if (index == 1) {
			uriWithId = Uri.parse(TV_SHOW_CONTENT_URI + "/" + movieId);
		}

		Cursor cursor = getContentResolver().query(uriWithId, null, null, null, null);
		if (cursor != null) {
			isFavourite = cursor.getCount();
			cursor.close();
		}

		DetailViewModel detailViewModel = ViewModelProviders.of(this).get(DetailViewModel.class);
		detailViewModel.setDetails(movieId, index);
		detailViewModel.getDetails().observe(this, new Observer<Movie>() {
			@Override
			public void onChanged(Movie movie) {
				String dateRelease = dateFormat(movie.getDateRelease());

				Glide.with(MovieDetail.this).load(IMAGE_BASE_URL + movie.getBackdropPath()).into(ivBackdrop);
				Glide.with(MovieDetail.this).load(IMAGE_BASE_URL + movie.getPosterPath()).into(ivPoster);
				tvTitle.setText(movie.getTitle());
				tvGenres.setText(movie.getGenresHelper());
				tvDateRelease.setText(dateRelease);
				ratingBar.setRating(Float.parseFloat(movie.getUserScore()) / 2);

				if (movie.getOverview().isEmpty()) {
					tvOverview.setText(R.string.no_overview);
				} else {
					tvOverview.setText(movie.getOverview());
				}

				setActionBarTitle(movie.getTitle());

				MovieDetail.this.movie = movie;
				showLoading(false);
			}
		});

		if (isFavourite == 1) {
			btnFavourite.setText(R.string.remove_from_favourite);
		} else {
			btnFavourite.setText(R.string.add_to_favourite);
		}
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.btn_favourite) {
			Intent intent = new Intent();
			intent.putExtra(EXTRA_POSITION, position);
			intent.putExtra(EXTRA_TITLE, movie.getTitle());

			if (isFavourite == 1) {
				int result = getContentResolver().delete(uriWithId, null, null);

				if (result > 0) {
					btnFavourite.setText(R.string.add_to_favourite);
					setResult(RESULT_DELETE, intent);
					updateWidget();
					finish();
				} else {
					showSnackbarMessage(getString(R.string.delete_message_failed, movie.getTitle()), v);
				}
			} else {
				long result = 0;
				ContentValues values = new ContentValues();
				values.put(POSTER_PATH, movie.getPosterPath());
				values.put(TITLE, movie.getTitle());
				values.put(GENRES, movie.getGenresHelper());
				values.put(USER_SCORE, movie.getUserScore());
				values.put(OVERVIEW, movie.getOverview());

				Uri uriResult = null;
				if (index == 0) {
					values.put(MOVIE_ID, movie.getId());
					values.put(DATE_RELEASE, movie.getDateRelease());
					uriResult = getContentResolver().insert(MOVIE_CONTENT_URI, values);
				} else if (index == 1) {
					values.put(TV_SHOW_ID, movie.getId());
					values.put(FIRST_AIR_DATE, movie.getDateRelease());
					uriResult = getContentResolver().insert(TV_SHOW_CONTENT_URI, values);
				}

				if (uriResult != null) {
					Cursor cursorResult = getContentResolver().query(uriResult, null, null, null, null);
					if (cursorResult != null) {
						result = cursorResult.getCount();
						cursorResult.close();
					}
				}

				if (result > 0) {
					isFavourite = 1;
					btnFavourite.setText(R.string.remove_from_favourite);
					updateWidget();
					showSnackbarMessage(getString(R.string.insert_message_success, movie.getTitle()), v);
				} else {
					showSnackbarMessage(getString(R.string.insert_message_failed, movie.getTitle()), v);
				}
			}
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
			ratingBar.setVisibility(View.GONE);
			btnFavourite.setVisibility(View.GONE);
		} else {
			progressBar.setVisibility(View.GONE);
			ratingBar.setVisibility(View.VISIBLE);
			btnFavourite.setVisibility(View.VISIBLE);
		}
	}

	private String dateFormat(String unformattedDate) {
		String formattedDate = null;
		try {
			Date date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(unformattedDate);
			formattedDate = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault()).format(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return formattedDate;
	}

	private void showSnackbarMessage(String message, View view) {
		Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show();
	}

	private void updateWidget() {
		AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
		ComponentName movieWidget = new ComponentName(this, FavouritesWidget.class);
		int[] appWidgetIds = appWidgetManager.getAppWidgetIds(movieWidget);
		appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.stack_view);
	}
}
