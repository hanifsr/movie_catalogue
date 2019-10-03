package com.hanifsr.moviecatalogue.ui.favourites.sections;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.hanifsr.moviecatalogue.MovieDetail;
import com.hanifsr.moviecatalogue.R;
import com.hanifsr.moviecatalogue.adapter.MovieAdapter;
import com.hanifsr.moviecatalogue.model.Movie;

import java.util.ArrayList;

import static com.hanifsr.moviecatalogue.database.MovieHelper.INSTANCE;

public class SectionsFragment extends Fragment {

	private static final String ARG_SECTION_NUMBER = "section_number";

	private RecyclerView recyclerView;
	private MovieAdapter movieAdapter;
	private ProgressBar progressBar;

	public static SectionsFragment newInstance(int index) {
		SectionsFragment sectionsFragment = new SectionsFragment();
		Bundle bundle = new Bundle();
		bundle.putInt(ARG_SECTION_NUMBER, index);
		sectionsFragment.setArguments(bundle);
		return sectionsFragment;
	}

	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_sections, container, false);
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		int index = 0;
		if (getArguments() != null) {
			index = getArguments().getInt(ARG_SECTION_NUMBER);
		}

		progressBar = view.findViewById(R.id.progress_bar);
		showLoading(true);

		movieAdapter = new MovieAdapter();
		movieAdapter.notifyDataSetChanged();

		recyclerView = view.findViewById(R.id.rv_favourites);
		recyclerView.setHasFixedSize(true);

		showRecyclerList(index);

		SectionsViewModel sectionsViewModel = ViewModelProviders.of(this).get(SectionsViewModel.class);
		if (index == 0) {
			sectionsViewModel.setMovie(INSTANCE.getAllMovie());
		} else if (index == 1) {
			sectionsViewModel.setMovie(INSTANCE.getAllTvShow());
		}

		sectionsViewModel.getMovies().observe(this, new Observer<ArrayList<Movie>>() {
			@Override
			public void onChanged(ArrayList<Movie> movies) {
				if (movies != null) {
					movieAdapter.setData(movies);
					showLoading(false);
				}
			}
		});
	}

	private void showRecyclerList(final int index) {
		recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
		recyclerView.setAdapter(movieAdapter);

		movieAdapter.setOnMovieItemClickCallback(new MovieAdapter.OnMovieItemClickCallback() {
			@Override
			public void onMovieItemClicked(Movie movie, int position) {
				showSelectedMovie(movie, index, position);
			}
		});
	}

	private void showSelectedMovie(Movie movie, int index, int position) {
		Intent intent = new Intent(this.getActivity(), MovieDetail.class);
		intent.putExtra(MovieDetail.EXTRA_MOVIE, movie.getId());
		intent.putExtra(MovieDetail.EXTRA_POSITION, position);
		intent.putExtra(MovieDetail.EXTRA_INDEX, index);
		intent.putExtra(MovieDetail.EXTRA_FAVOURITE, 1);
		startActivityForResult(intent, MovieDetail.REQUEST_DELETE);
//		startActivity(intent);
	}

	private void showLoading(Boolean state) {
		if (state) {
			progressBar.setVisibility(View.VISIBLE);
		} else {
			progressBar.setVisibility(View.GONE);
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (data != null) {
			if (requestCode == MovieDetail.REQUEST_DELETE && resultCode == MovieDetail.RESULT_DELETE) {
				int position = data.getIntExtra(MovieDetail.EXTRA_POSITION, 0);
				String title = data.getStringExtra(MovieDetail.EXTRA_TITLE);
				movieAdapter.removeItem(position);

				showSnackbarMessage(getString(R.string.delete_message_success, title));
			}
		}
	}

	private void showSnackbarMessage(String message) {
		Snackbar.make(recyclerView, message, Snackbar.LENGTH_SHORT).show();
	}
}
