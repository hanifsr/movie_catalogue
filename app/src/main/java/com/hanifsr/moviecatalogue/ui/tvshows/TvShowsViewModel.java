package com.hanifsr.moviecatalogue.ui.tvshows;

import android.util.Log;

import androidx.collection.SimpleArrayMap;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.hanifsr.moviecatalogue.BuildConfig;
import com.hanifsr.moviecatalogue.model.Movie;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;

public class TvShowsViewModel extends ViewModel {

	private static final String API_KEY = BuildConfig.TMDB_API_KEY;

	private MutableLiveData<ArrayList<Movie>> movieList = new MutableLiveData<>();
	private SimpleArrayMap<Integer, String> genreList = new SimpleArrayMap<>();

	void setMovie() {
		AsyncHttpClient client = new AsyncHttpClient();
		final ArrayList<Movie> listItems = new ArrayList<>();

		String language;
		if (Locale.getDefault().toString().equals("in_ID")) {
			language = "id-ID";
		} else {
			language = "en-US";
		}

		String genreUrl = "https://api.themoviedb.org/3/genre/tv/list?api_key=" + API_KEY + "&language=" + language;
		String url = "https://api.themoviedb.org/3/tv/popular?api_key=" + API_KEY + "&language=" + language + "&page=1";

		client.get(genreUrl, new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
				try {
					String result = new String(responseBody);
					JSONObject responseObject = new JSONObject(result);
					JSONArray list = responseObject.getJSONArray("genres");

					for (int i = 0; i < list.length(); i++) {
						JSONObject genreObject = list.getJSONObject(i);
						genreList.put(genreObject.getInt("id"), genreObject.getString("name"));
					}
				} catch (Exception e) {
					Log.d("ExceptionGenre", e.getMessage());
				}
			}

			@Override
			public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
				Log.d("onFailureGenre", error.getMessage());
			}
		});

		client.get(url, new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
				try {
					String result = new String(responseBody);
					JSONObject responseObject = new JSONObject(result);
					JSONArray list = responseObject.getJSONArray("results");

					for (int i = 0; i < list.length(); i++) {
						JSONObject movieObject = list.getJSONObject(i);
						listItems.add(addItems(movieObject));
					}

					movieList.postValue(listItems);
				} catch (Exception e) {
					Log.d("ExceptionMovie", e.getMessage());
				}
			}

			@Override
			public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
				Log.d("onFailureMovie", error.getMessage());
			}
		});
	}

	LiveData<ArrayList<Movie>> getMovies() {
		return movieList;
	}

	private Movie addItems(JSONObject jsonObject) {
		Movie movie = new Movie();

		try {
			movie.setId(jsonObject.getInt("id"));

			String posterPath = "https://image.tmdb.org/t/p/w500/" + jsonObject.getString("poster_path");
			movie.setPosterPath(posterPath);

			movie.setTitle(jsonObject.getString("name"));
			movie.setDateRelease(jsonObject.getString("first_air_date"));

			movie.setUserScore(jsonObject.getString("vote_average"));
			movie.setOverview(jsonObject.getString("overview"));

			StringBuilder movieGenre = new StringBuilder();
			JSONArray genreIds = jsonObject.getJSONArray("genre_ids");
			for (int j = 0; j < genreIds.length(); j++) {
				if (genreList.containsKey(genreIds.getInt(j))) {
					if (j == 0) {
						movieGenre.append(genreList.get(genreIds.getInt(j)));
					} else {
						movieGenre.append(", ").append(genreList.get(genreIds.getInt(j)));
					}
				}
			}
			movie.setGenres(movieGenre.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return movie;
	}
}
