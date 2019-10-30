package com.hanifsr.consumerapp.sections;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.hanifsr.consumerapp.R;
import com.hanifsr.consumerapp.adapter.MovieAdapter;
import com.hanifsr.consumerapp.interfaces.OnMovieItemClickCallback;
import com.hanifsr.consumerapp.model.Movie;

import java.util.ArrayList;

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
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
							 @Nullable Bundle savedInstanceState) {
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
		sectionsViewModel.setMovie(index);

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

		movieAdapter.setOnMovieItemClickCallback(new OnMovieItemClickCallback() {
			@Override
			public void onMovieItemClicked(Movie movie, int position) {
				showSelectedMovie(movie, index, position);
			}
		});
	}

	private void showSelectedMovie(Movie movie, int index, int position) {
		Toast.makeText(getContext(), movie.getTitle(), Toast.LENGTH_SHORT).show();
	}

	private void showLoading(Boolean state) {
		if (state) {
			progressBar.setVisibility(View.VISIBLE);
		} else {
			progressBar.setVisibility(View.GONE);
		}
	}
}
