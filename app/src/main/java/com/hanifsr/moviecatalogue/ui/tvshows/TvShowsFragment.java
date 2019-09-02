package com.hanifsr.moviecatalogue.ui.tvshows;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hanifsr.moviecatalogue.R;
import com.hanifsr.moviecatalogue.TvShowDetail;
import com.hanifsr.moviecatalogue.adapter.TvShowAdapter;
import com.hanifsr.moviecatalogue.model.TvShow;

import java.util.ArrayList;

public class TvShowsFragment extends Fragment {

	private RecyclerView recyclerView;
	private TypedArray dataPoster;
	private String[] dataTitle, dataGenres, dataRating, dataRuntime, dataOverview;
	private ArrayList<TvShow> tvShows;

	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_tv_shows, container, false);

		recyclerView = view.findViewById(R.id.rv_tvshows);
		recyclerView.setHasFixedSize(true);

		prepare();
		addItem();
		showRecyclerList();

		return view;
	}

	private void prepare() {
		dataPoster = getResources().obtainTypedArray(R.array.data_tv_poster);
		dataTitle = getResources().getStringArray(R.array.data_tv_title);
		dataGenres = getResources().getStringArray(R.array.data_tv_genres);
		dataRating = getResources().getStringArray(R.array.data_tv_rating);
		dataRuntime = getResources().getStringArray(R.array.data_tv_runtime);
		dataOverview = getResources().getStringArray(R.array.data_tv_overview);
	}

	private void addItem() {
		tvShows = new ArrayList<>();

		for (int i = 0; i < dataTitle.length; i++) {
			TvShow tvShow = new TvShow();
			tvShow.setTvshowPoster(dataPoster.getResourceId(i, -1));
			tvShow.setTvshowTitle(dataTitle[i]);
			tvShow.setTvshowGenres(dataGenres[i]);
			tvShow.setTvshowRating(dataRating[i]);
			tvShow.setTvshowRuntime(dataRuntime[i]);
			tvShow.setTvshowOverview(dataOverview[i]);
			tvShows.add(tvShow);
		}
	}

	private void showRecyclerList() {
		recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
		TvShowAdapter tvShowAdapter = new TvShowAdapter(tvShows);
		recyclerView.setAdapter(tvShowAdapter);

		tvShowAdapter.setOnTvShowItemClickCallback(new TvShowAdapter.OnTvShowItemClickCallback() {
			@Override
			public void onTvShowItemClicked(TvShow tvShow) {
				showSelectedMovie(tvShow);
			}
		});
	}

	private void showSelectedMovie(TvShow tvShow) {
		Intent intent = new Intent(this.getActivity(), TvShowDetail.class);
		intent.putExtra(TvShowDetail.EXTRA_TV_SHOW, tvShow);
		startActivity(intent);
	}
}
