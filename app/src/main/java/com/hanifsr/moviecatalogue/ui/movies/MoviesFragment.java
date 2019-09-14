package com.hanifsr.moviecatalogue.ui.movies;

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

import com.hanifsr.moviecatalogue.MovieDetail;
import com.hanifsr.moviecatalogue.adapter.MovieAdapter;
import com.hanifsr.moviecatalogue.R;
import com.hanifsr.moviecatalogue.model.Movie;

import java.util.ArrayList;

public class MoviesFragment extends Fragment {

	private static final String ARG_SECTION_NUMBER = "section_number";

	public static MoviesFragment newInstance(int index) {
		MoviesFragment moviesFragment = new MoviesFragment();
		Bundle bundle = new Bundle();
		bundle.putInt(ARG_SECTION_NUMBER, index);
		moviesFragment.setArguments(bundle);
		return moviesFragment;
	}

	private RecyclerView recyclerView;
	private TypedArray dataPoster;
	private String[] dataTitle, dataGenres, dataDateRelease, dataRating, dataRuntime, dataOverview;
	private ArrayList<Movie> movies;

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

		recyclerView = view.findViewById(R.id.rv_movies);
		recyclerView.setHasFixedSize(true);

		prepare(index);
		addItem(index);
		showRecyclerList();
	}

	private void prepare(int index) {
		if (index == 0) {
			dataPoster = getResources().obtainTypedArray(R.array.data_movie_poster);
			dataTitle = getResources().getStringArray(R.array.data_movie_title);
			dataGenres = getResources().getStringArray(R.array.data_movie_genres);
			dataDateRelease = getResources().getStringArray(R.array.data_movie_date_release);
			dataRating = getResources().getStringArray(R.array.data_movie_rating);
			dataRuntime = getResources().getStringArray(R.array.data_movie_runtime);
			dataOverview = getResources().getStringArray(R.array.data_movie_overview);
		} else if (index == 1) {
			dataPoster = getResources().obtainTypedArray(R.array.data_tv_poster);
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
				movie.setMoviePoster(dataPoster.getResourceId(i, -1));
				movie.setMovieTitle(dataTitle[i]);
				movie.setMovieGenres(dataGenres[i]);
				movie.setMovieDateRelease(dataDateRelease[i]);
				movie.setMovieRating(dataRating[i]);
				movie.setMovieRuntime(dataRuntime[i]);
				movie.setMovieOverview(dataOverview[i]);
				movies.add(movie);
			}
		} else if (index == 1) {
			for (int i = 0; i < dataTitle.length; i++) {
				Movie movie = new Movie();
				movie.setMoviePoster(dataPoster.getResourceId(i, -1));
				movie.setMovieTitle(dataTitle[i]);
				movie.setMovieGenres(dataGenres[i]);
				movie.setMovieRating(dataRating[i]);
				movie.setMovieRuntime(dataRuntime[i]);
				movie.setMovieOverview(dataOverview[i]);
				movies.add(movie);
			}
		}
	}

	private void showRecyclerList() {
		recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
		MovieAdapter movieAdapter = new MovieAdapter(movies);
		recyclerView.setAdapter(movieAdapter);

		movieAdapter.setOnMovieItemClickCallback(new MovieAdapter.OnMovieItemClickCallback() {
			@Override
			public void onMovieItemClicked(Movie movie) {
				showSelectedMovie(movie);
			}
		});
	}

	private void showSelectedMovie(Movie movie) {
		Intent intent = new Intent(this.getActivity(), MovieDetail.class);
		intent.putExtra(MovieDetail.EXTRA_MOVIE, movie);
		startActivity(intent);
	}
}
