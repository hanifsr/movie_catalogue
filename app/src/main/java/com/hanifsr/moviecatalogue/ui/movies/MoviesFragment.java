package com.hanifsr.moviecatalogue.ui.movies;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.hanifsr.moviecatalogue.MovieDetail;
import com.hanifsr.moviecatalogue.adapter.MovieAdapter;
import com.hanifsr.moviecatalogue.R;
import com.hanifsr.moviecatalogue.model.Movie;

import java.util.ArrayList;

public class MoviesFragment extends Fragment {

	private RecyclerView recyclerView;
	private TypedArray dataPoster;
	private String[] dataTitle, dataGenres, dataDateRelease, dataRating, dataRuntime, dataOverview;
	private ArrayList<Movie> movies;

	public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_movies, container, false);

		recyclerView = view.findViewById(R.id.rv_movies);
		recyclerView.setHasFixedSize(true);

		prepare();
		addItem();
		showRecyclerList();

		return view;
	}

	private void prepare() {
		dataPoster = getResources().obtainTypedArray(R.array.data_movie_poster);
		dataTitle = getResources().getStringArray(R.array.data_movie_title);
		dataGenres = getResources().getStringArray(R.array.data_movie_genres);
		dataDateRelease = getResources().getStringArray(R.array.data_movie_date_release);
		dataRating = getResources().getStringArray(R.array.data_movie_rating);
		dataRuntime = getResources().getStringArray(R.array.data_movie_runtime);
		dataOverview = getResources().getStringArray(R.array.data_movie_overview);
	}

	private void addItem() {
		movies = new ArrayList<>();

		for (int i = 0; i < dataTitle.length; i++) {
			Movie movie = new Movie();
			movie.setPoster(dataPoster.getResourceId(i, -1));
			movie.setTitle(dataTitle[i]);
			movie.setGenres(dataGenres[i]);
			movie.setDateRelease(dataDateRelease[i]);
			movie.setRating(dataRating[i]);
			movie.setRuntime(dataRuntime[i]);
			movie.setOverview(dataOverview[i]);
			movies.add(movie);
		}
	}

	private void showRecyclerList() {
		recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
		MovieAdapter movieAdapter = new MovieAdapter(movies);
		recyclerView.setAdapter(movieAdapter);

		movieAdapter.setOnItemClickCallback(new MovieAdapter.OnItemClickCallback() {
			@Override
			public void onItemClicked(Movie movie) {
				showSelectedMovie(movie);
			}
		});
	}

	private void showSelectedMovie(Movie movie) {
//		Toast.makeText(this.getActivity(), movie.getPoster(), Toast.LENGTH_SHORT).show();
		Intent intent = new Intent(this.getActivity(), MovieDetail.class);
		intent.putExtra(MovieDetail.EXTRA_MOVIE, movie);
		startActivity(intent);
	}
}
