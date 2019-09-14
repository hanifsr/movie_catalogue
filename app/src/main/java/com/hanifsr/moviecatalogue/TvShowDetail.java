package com.hanifsr.moviecatalogue;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.hanifsr.moviecatalogue.model.TvShow;

public class TvShowDetail extends AppCompatActivity {

	public static final String EXTRA_TV_SHOW = "extra_tv_show";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tv_show_detail);

		ImageView ivPoster = findViewById(R.id.iv_tvshow_poster_detail);
		TextView tvTitle = findViewById(R.id.tv_tvshow_title_detail);
		TextView tvGenres = findViewById(R.id.tv_tvshow_genres_detail);
		TextView tvRating = findViewById(R.id.tv_tvshow_rating_detail);
		TextView tvRuntime = findViewById(R.id.tv_tvshow_runtime_detail);
		TextView tvOverview = findViewById(R.id.tv_tvshow_overview_detail);

		TvShow tvShow = getIntent().getParcelableExtra(EXTRA_TV_SHOW);

		Glide.with(this).load(tvShow.getTvshowPoster()).into(ivPoster);
		tvTitle.setText(tvShow.getTvshowTitle());
		tvGenres.setText(tvShow.getTvshowGenres());
		tvRating.setText(tvShow.getTvshowRating());
		tvRuntime.setText(tvShow.getTvshowRuntime());
		tvOverview.setText(tvShow.getTvshowOverview());

		setActionBarTitle(tvShow.getTvshowTitle());
	}

	private void setActionBarTitle(String title) {
		if (getSupportActionBar() != null) {
			getSupportActionBar().setTitle(title);
			getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}
}
