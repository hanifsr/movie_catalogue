package com.hanifsr.moviecatalogue.ui.detail;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;
import com.hanifsr.moviecatalogue.R;
import com.hanifsr.moviecatalogue.data.source.remote.response.Movie;
import com.hanifsr.moviecatalogue.factory.ViewModelFactory;
import com.hanifsr.moviecatalogue.widgets.FavouritesWidget;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MovieDetailActivity extends AppCompatActivity implements View.OnClickListener {

	private static final String TAG = "GGWP";

	public static final String EXTRA_ID = "extra_movie";
	public static final String EXTRA_POSITION = "extra_position";
	public static final String EXTRA_INDEX = "extra_index";
	public static final String EXTRA_TITLE = "extra_title";

	private String IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w780/";

	public static final int REQUEST_DELETE = 200;
	public static final int RESULT_DELETE = 201;

	private ProgressBar progressBar;
	private ImageView ivPoster;
	private ImageView ivBackdrop;
	private TextView tvTitle;
	private TextView tvGenres;
	private TextView tvReleaseDate;
	private RatingBar ratingBar;
	private TextView tvOverview;
	private Button btnFavourite;

	private int position;
	private int index;

	private Movie movie;
	private DetailViewModel detailViewModel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_movie_detail);

		bind();
		showLoading(true);

		int movieId = getIntent().getIntExtra(EXTRA_ID, 0);
		position = getIntent().getIntExtra(EXTRA_POSITION, 0);
		index = getIntent().getIntExtra(EXTRA_INDEX, 0);

		detailViewModel = obtainViewModel(this);

		String language = Locale.getDefault().getISO3Language().substring(0, 2) + "-" + Locale.getDefault().getISO3Country().substring(0, 2);
		detailViewModel.getDetails(movieId, index, language).observe(this, movie -> {
			String releaseDate = dateFormat(movie.getReleaseDate());

			Glide.with(MovieDetailActivity.this).load(IMAGE_BASE_URL + movie.getBackdropPath()).into(ivBackdrop);
			Glide.with(MovieDetailActivity.this).load(IMAGE_BASE_URL + movie.getPosterPath()).into(ivPoster);
			tvTitle.setText(movie.getTitle());
			tvGenres.setText(movie.getGenresHelper());
			tvReleaseDate.setText(releaseDate);
			ratingBar.setRating(Float.parseFloat(movie.getUserScore()) / 2);
			if (movie.getOverview().isEmpty()) {
				tvOverview.setText(R.string.no_overview);
			} else {
				tvOverview.setText(movie.getOverview());
			}

			setActionBarTitle(movie.getTitle());

			MovieDetailActivity.this.movie = movie;
			showLoading(false);
		});

		if (index == 0) {
			detailViewModel.getFavouriteMovie(movieId).observe(this, favouriteMovieEntity -> {
				if (favouriteMovieEntity != null) {
					detailViewModel.setFavourite(true);
					btnFavourite.setText(R.string.remove_from_favourite);
				} else {
					detailViewModel.setFavourite(false);
					btnFavourite.setText(R.string.add_to_favourite);
				}
			});
		} else if (index == 1) {
			detailViewModel.getFavouriteTvShow(movieId).observe(this, favouriteTvShowEntity -> {
				if (favouriteTvShowEntity != null) {
					detailViewModel.setFavourite(true);
					btnFavourite.setText(R.string.remove_from_favourite);
				} else {
					detailViewModel.setFavourite(false);
					btnFavourite.setText(R.string.add_to_favourite);
				}
			});
		}
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.btn_favourite) {
			Intent intent = new Intent();
			intent.putExtra(EXTRA_POSITION, position);
			intent.putExtra(EXTRA_TITLE, movie.getTitle());

			if (detailViewModel.isFavourite()) {
				int affectedRows = detailViewModel.deleteFromFavourite(movie, index);

				if (affectedRows > 0) {
					setResult(RESULT_DELETE, intent);
					updateWidget();
					finish();
				} else {
					showSnackbarMessage(getString(R.string.delete_message_failed, movie.getTitle()), v);
				}
			} else {
				long rowId = detailViewModel.insertToFavourite(movie, index);

				if (rowId > 0) {
					updateWidget();
					showSnackbarMessage(getString(R.string.insert_message_success, movie.getTitle()), v);
				} else {
					showSnackbarMessage(getString(R.string.insert_message_failed, movie.getTitle()), v);
				}
			}
		}
	}

	private void bind() {
		progressBar = findViewById(R.id.progress_bar_detail);
		ivPoster = findViewById(R.id.iv_movie_poster_detail);
		ivBackdrop = findViewById(R.id.iv_movie_backdrop);
		tvTitle = findViewById(R.id.tv_movie_title_detail);
		tvGenres = findViewById(R.id.tv_movie_genres_detail);
		tvReleaseDate = findViewById(R.id.tv_movie_release_date_detail);
		ratingBar = findViewById(R.id.rating_bar_movie);
		tvOverview = findViewById(R.id.tv_movie_overview_detail);
		btnFavourite = findViewById(R.id.btn_favourite);

		btnFavourite.setOnClickListener(this);
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

	private static DetailViewModel obtainViewModel(AppCompatActivity activity) {
		ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());
		return ViewModelProviders.of(activity, factory).get(DetailViewModel.class);
	}
}
