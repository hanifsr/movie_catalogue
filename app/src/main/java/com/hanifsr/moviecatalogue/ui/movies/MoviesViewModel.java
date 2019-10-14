package com.hanifsr.moviecatalogue.ui.movies;

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

public class MoviesViewModel extends ViewModel {

	private static final String TAG = "GGWP";

	private MovieRepository movieRepository;

	private MutableLiveData<ArrayList<Movie>> movieList = new MutableLiveData<>();
	private SimpleArrayMap<Integer, String> genreList = new SimpleArrayMap<>();

	LiveData<ArrayList<Movie>> getMovies() {
		return movieList;
	}

	void setMovies() {
		movieRepository = MovieRepository.getInstance();

		final String language = Locale.getDefault().toString().equals("in_ID") ? "id-ID" : "en-US";

		movieRepository.getMovieGenres(language, new OnGetGenresCallback() {
			@Override
			public void onSuccess(ArrayList<Genre> genres) {
				for (Genre genre : genres) {
					genreList.put(genre.getId(), genre.getName());
				}
				getMoviesRetrofit(language);
			}

			@Override
			public void onError(Throwable error) {
				Log.d(TAG, error.getMessage());
			}
		});


	}

	private void getMoviesRetrofit(String language) {
		final ArrayList<Movie> movieArrayList = new ArrayList<>();

		movieRepository.getMovies(language, new OnGetMoviesCallback() {
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
					movieArrayList.add(movie);
				}
				movieList.postValue(movieArrayList);
			}

			@Override
			public void onError(Throwable error) {
				Log.d(TAG, error.getMessage());
			}
		});
	}
}
