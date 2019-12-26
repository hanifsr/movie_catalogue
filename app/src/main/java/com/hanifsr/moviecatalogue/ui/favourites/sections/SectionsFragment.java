package com.hanifsr.moviecatalogue.ui.favourites.sections;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.hanifsr.moviecatalogue.R;
import com.hanifsr.moviecatalogue.data.source.local.entity.FavouriteMovieEntity;
import com.hanifsr.moviecatalogue.data.source.local.entity.FavouriteTvShowEntity;
import com.hanifsr.moviecatalogue.factory.ViewModelFactory;
import com.hanifsr.moviecatalogue.ui.adapter.FavouriteMoviePagedAdapter;
import com.hanifsr.moviecatalogue.ui.adapter.FavouriteTvShowPagedAdapter;
import com.hanifsr.moviecatalogue.ui.detail.MovieDetailActivity;

public class SectionsFragment extends Fragment {

	private static final String ARG_SECTION_NUMBER = "section_number";

	private RecyclerView recyclerView;
	private FavouriteMoviePagedAdapter favouriteMoviePagedAdapter;
	private FavouriteTvShowPagedAdapter favouriteTvShowPagedAdapter;
	private SectionsViewModel sectionsViewModel;
	private ProgressBar progressBar;

	static SectionsFragment newInstance(int index) {
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
	public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		progressBar = view.findViewById(R.id.progress_bar);
		recyclerView = view.findViewById(R.id.rv_favourites);
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		if (getActivity() != null) {
			showLoading(true);

			int index = 0;
			if (getArguments() != null) {
				index = getArguments().getInt(ARG_SECTION_NUMBER);
			}

			sectionsViewModel = obtainViewModel(this);

			if (index == 0) {
				favouriteMoviePagedAdapter = new FavouriteMoviePagedAdapter();
				sectionsViewModel.getFavouriteMovies().observe(this, favouriteMovieEntities -> {
					if (favouriteMovieEntities != null) {
						favouriteMoviePagedAdapter.submitList(favouriteMovieEntities);
						favouriteMoviePagedAdapter.notifyDataSetChanged();
						showLoading(false);
					}
				});
			} else if (index == 1) {
				favouriteTvShowPagedAdapter = new FavouriteTvShowPagedAdapter();
				sectionsViewModel.getFavouriteTvShows().observe(this, favouriteTvShowEntities -> {
					if (favouriteTvShowEntities != null) {
						favouriteTvShowPagedAdapter.submitList(favouriteTvShowEntities);
						favouriteTvShowPagedAdapter.notifyDataSetChanged();
						showLoading(false);
					}
				});
			}

			showRecyclerList(index);
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (data != null) {
			if (requestCode == MovieDetailActivity.REQUEST_DELETE && resultCode == MovieDetailActivity.RESULT_DELETE) {
				int position = data.getIntExtra(MovieDetailActivity.EXTRA_POSITION, 0);
				String title = data.getStringExtra(MovieDetailActivity.EXTRA_TITLE);

				sectionsViewModel.setDeleted();

				showSnackbarMessage(getString(R.string.delete_message_success, title));
			}
		}
	}

	private void showRecyclerList(final int index) {
		recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
		recyclerView.setHasFixedSize(true);
		recyclerView.setContentDescription(index == 0 ? getString(R.string.movies) : getString(R.string.tv_shows));
		recyclerView.setAdapter(index == 0 ? favouriteMoviePagedAdapter : favouriteTvShowPagedAdapter);

		if (index == 0) {
			favouriteMoviePagedAdapter.setOnFavouriteMovieItemClickCallback(this::showSelectedFavouriteMovie);
		} else if (index == 1) {
			favouriteTvShowPagedAdapter.setOnFavouriteTvShowItemClickCallback(this::showSelectedFavouriteTvShow);
		}
	}

	private void showSelectedFavouriteMovie(FavouriteMovieEntity favouriteMovieEntity, int position) {
		Intent intent = new Intent(this.getActivity(), MovieDetailActivity.class);
		intent.putExtra(MovieDetailActivity.EXTRA_ID, favouriteMovieEntity.getId());
		intent.putExtra(MovieDetailActivity.EXTRA_POSITION, position);
		intent.putExtra(MovieDetailActivity.EXTRA_INDEX, 0);
		startActivityForResult(intent, MovieDetailActivity.REQUEST_DELETE);
	}

	private void showSelectedFavouriteTvShow(FavouriteTvShowEntity favouriteTvShowEntity, int position) {
		Intent intent = new Intent(this.getActivity(), MovieDetailActivity.class);
		intent.putExtra(MovieDetailActivity.EXTRA_ID, favouriteTvShowEntity.getId());
		intent.putExtra(MovieDetailActivity.EXTRA_POSITION, position);
		intent.putExtra(MovieDetailActivity.EXTRA_INDEX, 1);
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

	private static SectionsViewModel obtainViewModel(Fragment fragment) {
		ViewModelFactory factory = ViewModelFactory.getInstance(fragment.getActivity().getApplication());
		return ViewModelProviders.of(fragment, factory).get(SectionsViewModel.class);
	}
}
