package com.hanifsr.moviecatalogue.ui.favourites.sections;

import android.app.Application;
import android.content.Context;
import android.database.Cursor;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.hanifsr.moviecatalogue.utils.MappingHelper;
import com.hanifsr.moviecatalogue.data.source.remote.response.Movie;

import java.util.ArrayList;

import static com.hanifsr.moviecatalogue.data.source.local.DatabaseContract.MovieColumns.MOVIE_CONTENT_URI;
import static com.hanifsr.moviecatalogue.data.source.local.DatabaseContract.TvShowColumns.TV_SHOW_CONTENT_URI;

public class SectionsViewModel extends AndroidViewModel {

	private MutableLiveData<ArrayList<Movie>> movieList = new MutableLiveData<>();
	private Context context;

	public SectionsViewModel(@NonNull Application application) {
		super(application);
		context = application.getApplicationContext();
	}

	LiveData<ArrayList<Movie>> getMovies() {
		return movieList;
	}

	void setMovie(int index) {
		ArrayList<Movie> movies = new ArrayList<>();
		Cursor cursor = null;
		if (index == 0) {
			cursor = context.getContentResolver().query(MOVIE_CONTENT_URI, null, null, null, null);
		} else if (index == 1) {
			cursor = context.getContentResolver().query(TV_SHOW_CONTENT_URI, null, null, null, null);
		}

		if (cursor != null) {
			movies = MappingHelper.mapCursorToArrayList(cursor, index);
		}

		movieList.postValue(movies);
	}
}
