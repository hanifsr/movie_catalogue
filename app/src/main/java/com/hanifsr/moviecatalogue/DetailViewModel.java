package com.hanifsr.moviecatalogue;

import android.text.TextUtils;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.hanifsr.moviecatalogue.interfaces.OnGetDetailCallback;
import com.hanifsr.moviecatalogue.model.Genre;
import com.hanifsr.moviecatalogue.model.Movie;
import com.hanifsr.moviecatalogue.model.MovieRepository;

import java.util.ArrayList;
import java.util.Locale;

public class DetailViewModel extends ViewModel {

	private static final String TAG = "GGWP";

	private MovieRepository movieRepository = MovieRepository.getInstance();

	private MutableLiveData<Movie> movieMutableLiveData = new MutableLiveData<>();

	LiveData<Movie> getDetails() {
		return movieMutableLiveData;
	}

	void setDetails(int movieId, int index) {
		String language = Locale.getDefault().toString().equals("in_ID") ? "id-ID" : "en-US";

		if (index == 0) {
			setMovie(movieId, language);
		} else if (index == 1) {
			setTvShow(movieId, language);
		}
	}

	private void setMovie(int movieId, String language) {
		movieRepository.getMovieDetail(movieId, language, new OnGetDetailCallback() {
			@Override
			public void onSuccess(Movie movie) {
				if (movie.getGenresDetail() != null) {
					ArrayList<String> movieGenres = new ArrayList<>();
					for (Genre genre : movie.getGenresDetail()) {
						movieGenres.add(genre.getName());
					}
					movie.setGenresHelper(TextUtils.join(", ", movieGenres));
				}
				movieMutableLiveData.postValue(movie);
			}

			@Override
			public void onError(Throwable error) {
				Log.d(TAG, error.getMessage());
			}
		});
	}

	private void setTvShow(int tvShowId, String language) {
		movieRepository.getTvShowDetail(tvShowId, language, new OnGetDetailCallback() {
			@Override
			public void onSuccess(Movie movie) {
				if (movie.getGenresDetail() != null) {
					ArrayList<String> tvShowGenres = new ArrayList<>();
					for (Genre genre : movie.getGenresDetail()) {
						tvShowGenres.add(genre.getName());
					}
					movie.setGenresHelper(TextUtils.join(", ", tvShowGenres));
				}
				movieMutableLiveData.postValue(movie);
			}

			@Override
			public void onError(Throwable error) {
				Log.d(TAG, error.getMessage());
			}
		});
	}
}
