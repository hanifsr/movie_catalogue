package com.hanifsr.moviecatalogue.ui.tvshows;

import android.text.TextUtils;
import android.util.Log;

import androidx.collection.SimpleArrayMap;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.hanifsr.moviecatalogue.interfaces.OnGetGenresCallback;
import com.hanifsr.moviecatalogue.interfaces.OnGetMoviesCallback;
import com.hanifsr.moviecatalogue.model.Genre;
import com.hanifsr.moviecatalogue.model.Movie;
import com.hanifsr.moviecatalogue.model.MovieRepository;

import java.util.ArrayList;
import java.util.Locale;

public class TvShowsViewModel extends ViewModel {

	private static final String TAG = "GGWP";

	private MovieRepository movieRepository;
	private String searchQuery;

	private MutableLiveData<ArrayList<Movie>> tvShowList = new MutableLiveData<>();
	private SimpleArrayMap<Integer, String> genreList = new SimpleArrayMap<>();

	String getSearchQuery() {
		return searchQuery;
	}

	void setSearchQuery(String searchQuery) {
		this.searchQuery = searchQuery;
	}

	LiveData<ArrayList<Movie>> getTvShows() {
		return tvShowList;
	}

	void setTvShows() {
		movieRepository = MovieRepository.getInstance();

		final String language = Locale.getDefault().toString().equals("in_ID") ? "id-ID" : "en-US" ;

		movieRepository.getTvShowGenres(language, new OnGetGenresCallback() {
			@Override
			public void onSuccess(ArrayList<Genre> genres) {
				for (Genre genre : genres) {
					genreList.put(genre.getId(), genre.getName());
				}
				getTvShowsRetrofit(language);
			}

			@Override
			public void onError(Throwable error) {
				Log.d(TAG, error.getMessage());
			}
		});
	}

	private void getTvShowsRetrofit(String language) {
		final ArrayList<Movie> tvShowArrayList = new ArrayList<>();

		movieRepository.getTvShows(language, new OnGetMoviesCallback() {
			@Override
			public void onSuccess(ArrayList<Movie> movies) {
				for (Movie movie : movies) {
					ArrayList<String> movieGenres = new ArrayList<>();
					for (Integer genreIds : movie.getGenreIds()) {
						if (genreList.containsKey(genreIds)) {
							movieGenres.add(genreList.get(genreIds));
						}
					}
					movie.setGenresHelper(TextUtils.join(", ", movieGenres));
					tvShowArrayList.add(movie);
				}
				tvShowList.postValue(tvShowArrayList);
			}

			@Override
			public void onError(Throwable error) {
				Log.d(TAG, error.getMessage());
			}
		});
	}

	void setQueriedTvShows(final String query) {
		movieRepository = MovieRepository.getInstance();

		final String language = Locale.getDefault().toString().equals("in_ID") ? "id-ID" : "en-US";

		movieRepository.getTvShowGenres(language, new OnGetGenresCallback() {
			@Override
			public void onSuccess(ArrayList<Genre> genres) {
				for (Genre genre : genres) {
					genreList.put(genre.getId(), genre.getName());
				}
				getQueriedTvShowsRetrofit(language, query);
			}

			@Override
			public void onError(Throwable error) {
				Log.d(TAG, error.getMessage());
			}
		});
	}

	private void getQueriedTvShowsRetrofit(String language, String query) {
		final ArrayList<Movie> tvShowArrayList = new ArrayList<>();

		movieRepository.getQueriedTvShows(language, query, new OnGetMoviesCallback() {
			@Override
			public void onSuccess(ArrayList<Movie> movies) {
				for (Movie movie : movies) {
					ArrayList<String> movieGenres = new ArrayList<>();
					for (Integer genreIds : movie.getGenreIds()) {
						if (genreList.containsKey(genreIds)) {
							movieGenres.add(genreList.get(genreIds));
						}
					}
					movie.setGenresHelper(TextUtils.join(", ", movieGenres));
					tvShowArrayList.add(movie);
				}
				tvShowList.postValue(tvShowArrayList);
			}

			@Override
			public void onError(Throwable error) {
				Log.d(TAG, error.getMessage());
			}
		});
	}
}
