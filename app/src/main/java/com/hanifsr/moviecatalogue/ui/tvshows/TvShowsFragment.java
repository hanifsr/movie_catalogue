package com.hanifsr.moviecatalogue.ui.tvshows;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.hanifsr.moviecatalogue.MovieDetail;
import com.hanifsr.moviecatalogue.R;
import com.hanifsr.moviecatalogue.adapter.MovieAdapter;
import com.hanifsr.moviecatalogue.interfaces.OnMovieItemClickCallback;
import com.hanifsr.moviecatalogue.model.Movie;

import java.util.ArrayList;

public class TvShowsFragment extends Fragment {

	private RecyclerView recyclerView;
	private MovieAdapter movieAdapter;
	private ProgressBar progressBar;
	private SearchView searchView;

	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_tv_shows, container, false);
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		recyclerView = view.findViewById(R.id.rv_tvshows);
		progressBar = view.findViewById(R.id.progress_bar);
		searchView = view.findViewById(R.id.sv_tv_shows);
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		if (getActivity() != null) {
			showLoading(true);

			final TvShowsViewModel tvShowsViewModel = ViewModelProviders.of(this).get(TvShowsViewModel.class);
			if (tvShowsViewModel.getSearchQuery() != null) {
				tvShowsViewModel.setQueriedTvShows(tvShowsViewModel.getSearchQuery());
			} else {
				tvShowsViewModel.setTvShows();
			}

			movieAdapter = new MovieAdapter();
			movieAdapter.notifyDataSetChanged();

			searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
				@Override
				public boolean onQueryTextSubmit(String query) {
					tvShowsViewModel.setQueriedTvShows(query);
					tvShowsViewModel.setSearchQuery(query);
					return true;
				}

				@Override
				public boolean onQueryTextChange(String newText) {
					if (newText.isEmpty()) {
						tvShowsViewModel.setTvShows();
						tvShowsViewModel.setSearchQuery(null);
					}
					return true;
				}
			});

			showRecyclerList();

			tvShowsViewModel.getTvShows().observe(this, new Observer<ArrayList<Movie>>() {
				@Override
				public void onChanged(ArrayList<Movie> movies) {
					if (movies != null) {
						movieAdapter.setData(movies);
						showLoading(false);
					}
				}
			});
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (data != null) {
			if (requestCode == MovieDetail.REQUEST_DELETE && resultCode == MovieDetail.RESULT_DELETE) {
				String title = data.getStringExtra(MovieDetail.EXTRA_TITLE);
				showSnackbarMessage(getString(R.string.delete_message_success, title));
			}
		}
	}

	private void showRecyclerList() {
		recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
		recyclerView.setHasFixedSize(true);
		recyclerView.setAdapter(movieAdapter);

		movieAdapter.setOnMovieItemClickCallback(new OnMovieItemClickCallback() {
			@Override
			public void onMovieItemClicked(Movie movie, int position) {
				showSelectedMovie(movie, position);
			}
		});
	}

	private void showSelectedMovie(Movie movie, int position) {
		Intent intent = new Intent(this.getActivity(), MovieDetail.class);
		intent.putExtra(MovieDetail.EXTRA_MOVIE, movie.getId());
		intent.putExtra(MovieDetail.EXTRA_POSITION, position);
		intent.putExtra(MovieDetail.EXTRA_INDEX, 1);
		startActivityForResult(intent, MovieDetail.REQUEST_DELETE);
	}

	private void showLoading(Boolean state) {
		if (state) {
			progressBar.setVisibility(View.VISIBLE);
		} else {
			progressBar.setVisibility(View.GONE);
		}
	}

	private void showSnackbarMessage(String message) {
		Snackbar.make(recyclerView, message, Snackbar.LENGTH_SHORT).show();
	}
}
