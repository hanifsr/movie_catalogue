package com.hanifsr.moviecatalogue;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hanifsr.moviecatalogue.model.TvShow;

public class TvShowDetail extends AppCompatActivity {

	public static final String EXTRA_TV_SHOW = "extra_tv_show";
	private ImageView ivPoster;
	private TextView tvTitle, tvGenres, tvRating, tvRuntime, tvOverview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tv_show_detail);

		ivPoster = findViewById(R.id.iv_tvshow_poster_detail);
		tvTitle = findViewById(R.id.tv_tvshow_title_detail);
		tvGenres = findViewById(R.id.tv_tvshow_genres_detail);
		tvRating = findViewById(R.id.tv_tvshow_rating_detail);
		tvRuntime = findViewById(R.id.tv_tvshow_runtime_detail);
		tvOverview = findViewById(R.id.tv_tvshow_overview_detail);

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
