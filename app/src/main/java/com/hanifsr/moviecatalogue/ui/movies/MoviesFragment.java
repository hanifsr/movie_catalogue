package com.hanifsr.moviecatalogue.ui.movies;

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
import com.hanifsr.moviecatalogue.R;
import com.hanifsr.moviecatalogue.data.source.remote.response.Movie;
import com.hanifsr.moviecatalogue.factory.ViewModelFactory;
import com.hanifsr.moviecatalogue.ui.adapter.MovieAdapter;
import com.hanifsr.moviecatalogue.ui.adapter.OnMovieItemClickCallback;
import com.hanifsr.moviecatalogue.ui.detail.MovieDetailActivity;

import java.util.List;
import java.util.Locale;

public class MoviesFragment extends Fragment {

	private RecyclerView recyclerView;
	private MovieAdapter movieAdapter;
	private ProgressBar progressBar;
	private SearchView searchView;

	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_movies, container, false);
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		recyclerView = view.findViewById(R.id.rv_movies);
		progressBar = view.findViewById(R.id.progress_bar);
		searchView = view.findViewById(R.id.sv_movies);
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		if (getActivity() != null) {
			showLoading(true);

			final MoviesViewModel moviesViewModel = obtainViewModel(this);

			movieAdapter = new MovieAdapter();

			searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
				@Override
				public boolean onQueryTextSubmit(String query) {
					moviesViewModel.setSearchQuery(query);
					return true;
				}

				@Override
				public boolean onQueryTextChange(String newText) {
					if (newText.isEmpty() && moviesViewModel.isReadyToDelete()) {
						moviesViewModel.setSearchQuery(null);
					}
					return true;
				}
			});

			showRecyclerList();

			String language = Locale.getDefault().getISO3Language().substring(0, 2) + "-" + Locale.getDefault().getISO3Country().substring(0, 2);
			moviesViewModel.getMovies(language).observe(this, new Observer<List<Movie>>() {
				@Override
				public void onChanged(List<Movie> movies) {
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
			if (requestCode == MovieDetailActivity.REQUEST_DELETE && resultCode == MovieDetailActivity.RESULT_DELETE) {
				String title = data.getStringExtra(MovieDetailActivity.EXTRA_TITLE);
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
		Intent intent = new Intent(this.getActivity(), MovieDetailActivity.class);
		intent.putExtra(MovieDetailActivity.EXTRA_ID, movie.getId());
		intent.putExtra(MovieDetailActivity.EXTRA_POSITION, position);
		intent.putExtra(MovieDetailActivity.EXTRA_INDEX, 0);
		startActivityForResult(intent, MovieDetailActivity.REQUEST_DELETE);
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

	private static MoviesViewModel obtainViewModel(Fragment fragment) {
		ViewModelFactory factory = ViewModelFactory.getInstance(fragment.getActivity().getApplication());
		return ViewModelProviders.of(fragment, factory).get(MoviesViewModel.class);
	}
}
