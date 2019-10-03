package com.hanifsr.moviecatalogue;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.hanifsr.moviecatalogue.model.Movie;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Locale;

import cz.msebera.android.httpclient.Header;

public class DetailViewModel extends ViewModel {

	private static final String API_KEY = BuildConfig.TMDB_API_KEY;

	private MutableLiveData<Movie> movieMutableLiveData = new MutableLiveData<>();

	public void setDetails(int movieId, final int index) {
		final Movie movie = new Movie();

		String language;
		if (Locale.getDefault().toString().equals("in_ID")) {
			language = "id-ID";
		} else {
			language = "en-US";
		}

		String type = "";
		if (index == 0) {
			type = "movie";
		} else if (index == 1) {
			type = "tv";
		}

		String url = "https://api.themoviedb.org/3/" + type + "/" + movieId + "?api_key=" + API_KEY + "&language=" + language;

		AsyncHttpClient client = new AsyncHttpClient();
		client.get(url, new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
				try {
					String result = new String(responseBody);
					JSONObject responseObject = new JSONObject(result);

					movie.setId(responseObject.getInt("id"));

					if (index == 0) {
						movie.setTitle(responseObject.getString("title"));
						movie.setDateRelease(responseObject.getString("release_date"));
					} else if (index == 1) {
						movie.setTitle(responseObject.getString("name"));
						movie.setDateRelease(responseObject.getString("first_air_date"));
					}

					movie.setPosterPath("https://image.tmdb.org/t/p/w500/" + responseObject.getString("poster_path"));

					JSONArray genreArray = responseObject.getJSONArray("genres");
					StringBuilder genres = new StringBuilder();
					for (int i = 0; i < genreArray.length(); i++) {
						JSONObject genreObject = genreArray.getJSONObject(i);
						if (i == 0) {
							genres.append(genreObject.getString("name"));
						} else {
							genres.append(", ").append(genreObject.getString("name"));
						}
					}
					movie.setGenres(genres.toString());
					movie.setUserScore(responseObject.getString("vote_average"));
					movie.setOverview(responseObject.getString("overview"));

					movieMutableLiveData.postValue(movie);
				} catch (Exception e) {
					Log.d("DETAIL", "onSuccessException: " + e.getMessage());
				}
			}

			@Override
			public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
				Log.d("DETAIL", "onFailure: " + error.getMessage());
			}
		});
	}

	LiveData<Movie> getDetails() {
		return movieMutableLiveData;
	}
}
