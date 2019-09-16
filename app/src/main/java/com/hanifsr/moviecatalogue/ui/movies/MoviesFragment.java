package com.hanifsr.moviecatalogue.ui.movies;

import android.content.Intent;
import android.os.Bundle;
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

import com.hanifsr.moviecatalogue.MovieDetail;
import com.hanifsr.moviecatalogue.R;
import com.hanifsr.moviecatalogue.adapter.MovieAdapter;
import com.hanifsr.moviecatalogue.model.Movie;

import java.util.ArrayList;

public class MoviesFragment extends Fragment {

	private static final String ARG_SECTION_NUMBER = "section_number";

	private RecyclerView recyclerView;
	private String[] dataTitle, dataGenres, dataDateRelease, dataRating, dataRuntime, dataOverview;
	private ArrayList<Movie> movies;
	private MovieAdapter movieAdapter;
	private ProgressBar progressBar;

	public static MoviesFragment newInstance(int index) {
		MoviesFragment moviesFragment = new MoviesFragment();
		Bundle bundle = new Bundle();
		bundle.putInt(ARG_SECTION_NUMBER, index);
		moviesFragment.setArguments(bundle);
		return moviesFragment;
	}

	public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_movies, container, false);
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		int index = 0;
		if (getArguments() != null) {
			index = getArguments().getInt(ARG_SECTION_NUMBER);
		}

		progressBar = view.findViewById(R.id.progress_bar);

		MoviesViewModel moviesViewModel = ViewModelProviders.of(this).get(MoviesViewModel.class);
		moviesViewModel.setMovie(index);
		showLoading(true);

		moviesViewModel.getMovies().observe(this, new Observer<ArrayList<Movie>>() {
			@Override
			public void onChanged(ArrayList<Movie> movies) {
				if (movies != null) {
					movieAdapter.setData(movies);
					showLoading(false);
				}
			}
		});

		movieAdapter = new MovieAdapter();
		movieAdapter.notifyDataSetChanged();

		recyclerView = view.findViewById(R.id.rv_movies);
//		recyclerView.setHasFixedSize(true);

		// prepare(index);
		// addItem(index);
		showRecyclerList(index);
	}

	private void prepare(int index) {
		if (index == 0) {
//			dataPoster = getResources().obtainTypedArray(R.array.data_movie_poster);
			dataTitle = getResources().getStringArray(R.array.data_movie_title);
			dataGenres = getResources().getStringArray(R.array.data_movie_genres);
			dataDateRelease = getResources().getStringArray(R.array.data_movie_date_release);
			dataRating = getResources().getStringArray(R.array.data_movie_rating);
			dataRuntime = getResources().getStringArray(R.array.data_movie_runtime);
			dataOverview = getResources().getStringArray(R.array.data_movie_overview);
		} else if (index == 1) {
//			dataPoster = getResources().obtainTypedArray(R.array.data_tv_poster);
			dataTitle = getResources().getStringArray(R.array.data_tv_title);
			dataGenres = getResources().getStringArray(R.array.data_tv_genres);
			dataRating = getResources().getStringArray(R.array.data_tv_rating);
			dataRuntime = getResources().getStringArray(R.array.data_tv_runtime);
			dataOverview = getResources().getStringArray(R.array.data_tv_overview);
		}
	}

	private void addItem(int index) {
		movies = new ArrayList<>();

		if (index == 0) {
			for (int i = 0; i < dataTitle.length; i++) {
				Movie movie = new Movie();
				// movie.setPosterPath(dataPoster.getResourceId(i, -1));
				movie.setTitle(dataTitle[i]);
				movie.setGenres(dataGenres[i]);
				movie.setDateRelease(dataDateRelease[i]);
				movie.setUserScore(dataRating[i]);
				movie.setRuntime(dataRuntime[i]);
				movie.setOverview(dataOverview[i]);
				movies.add(movie);
			}
		} else if (index == 1) {
			for (int i = 0; i < dataTitle.length; i++) {
				Movie movie = new Movie();
				// movie.setPosterPath(dataPoster.getResourceId(i, -1));
				movie.setTitle(dataTitle[i]);
				movie.setGenres(dataGenres[i]);
				movie.setUserScore(dataRating[i]);
				movie.setRuntime(dataRuntime[i]);
				movie.setOverview(dataOverview[i]);
				movies.add(movie);
			}
		}
	}

	private void showRecyclerList(final int index) {
		recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
//		MovieAdapter movieAdapter = new MovieAdapter(movies);
		recyclerView.setAdapter(movieAdapter);

		movieAdapter.setOnMovieItemClickCallback(new MovieAdapter.OnMovieItemClickCallback() {
			@Override
			public void onMovieItemClicked(Movie movie) {
				showSelectedMovie(movie, index);
			}
		});
	}

	private void showSelectedMovie(Movie movie, int index) {
		Intent intent = new Intent(this.getActivity(), MovieDetail.class);
		intent.putExtra(MovieDetail.EXTRA_MOVIE, movie);
		intent.putExtra(MovieDetail.EXTRA_INDEX, index);
		startActivity(intent);
	}

	private void showLoading(Boolean state) {
		if (state) {
			progressBar.setVisibility(View.VISIBLE);
		} else {
			progressBar.setVisibility(View.GONE);
		}
	}
}
