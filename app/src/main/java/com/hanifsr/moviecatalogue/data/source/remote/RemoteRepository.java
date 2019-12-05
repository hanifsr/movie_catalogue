package com.hanifsr.moviecatalogue.data.source.remote;

import com.hanifsr.moviecatalogue.BuildConfig;
import com.hanifsr.moviecatalogue.data.source.remote.response.GenreResponse;
import com.hanifsr.moviecatalogue.data.source.remote.response.Movie;
import com.hanifsr.moviecatalogue.data.source.remote.response.MovieResponse;
import com.hanifsr.moviecatalogue.utils.EspressoIdlingResource;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RemoteRepository {

	private static final String BASE_URL = "https://api.themoviedb.org/3/";
	private static RemoteRepository INSTANCE;
	private TMDBApi api;

	private RemoteRepository(TMDBApi api) {
		this.api = api;
	}

	public static RemoteRepository getInstance() {
		if (INSTANCE == null) {
			Retrofit retrofit = new Retrofit.Builder()
					.baseUrl(BASE_URL)
					.addConverterFactory(GsonConverterFactory.create())
					.build();

			INSTANCE = new RemoteRepository(retrofit.create(TMDBApi.class));
		}

		return INSTANCE;
	}

	public void getMovies(String language, final OnGetMoviesCallback callback) {
		EspressoIdlingResource.increment();
		api.getPopularMovies(BuildConfig.TMDB_API_KEY, language, 1)
				.enqueue(new Callback<MovieResponse>() {
					@Override
					public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
						if (response.isSuccessful()) {
							MovieResponse movieResponse = response.body();
							if (movieResponse != null && movieResponse.getMovies() != null) {
								callback.onGetMoviesSuccess(movieResponse.getMovies());
								EspressoIdlingResource.decrement();
							}
						}
					}

					@Override
					public void onFailure(Call<MovieResponse> call, Throwable t) {
						callback.onGetMoviesError(t);
						EspressoIdlingResource.decrement();
					}
				});
	}

	public void getTvShows(String language, final OnGetMoviesCallback callback) {
		EspressoIdlingResource.increment();
		api.getPopularTvShows(BuildConfig.TMDB_API_KEY, language, 1)
				.enqueue(new Callback<MovieResponse>() {
					@Override
					public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
						if (response.isSuccessful()) {
							MovieResponse movieResponse = response.body();
							if (movieResponse != null && movieResponse.getMovies() != null) {
								callback.onGetMoviesSuccess(movieResponse.getMovies());
								EspressoIdlingResource.decrement();
							}
						}
					}

					@Override
					public void onFailure(Call<MovieResponse> call, Throwable t) {
						callback.onGetMoviesError(t);
						EspressoIdlingResource.decrement();
					}
				});
	}

	public void getMovieGenres(String language, final OnGetGenresCallback callback) {
		EspressoIdlingResource.increment();
		api.getMovieGenres(BuildConfig.TMDB_API_KEY, language)
				.enqueue(new Callback<GenreResponse>() {
					@Override
					public void onResponse(Call<GenreResponse> call, Response<GenreResponse> response) {
						if (response.isSuccessful()) {
							GenreResponse genreResponse = response.body();
							if (genreResponse != null && genreResponse.getGenres() != null) {
								callback.onGetGenresSuccess(genreResponse.getGenres());
								EspressoIdlingResource.decrement();
							}
						}
					}

					@Override
					public void onFailure(Call<GenreResponse> call, Throwable t) {
						callback.onGetGenresError(t);
						EspressoIdlingResource.decrement();
					}
				});
	}

	public void getTvShowGenres(String language, final OnGetGenresCallback callback) {
		EspressoIdlingResource.increment();
		api.getTvShowGenres(BuildConfig.TMDB_API_KEY, language)
				.enqueue(new Callback<GenreResponse>() {
					@Override
					public void onResponse(Call<GenreResponse> call, Response<GenreResponse> response) {
						if (response.isSuccessful()) {
							GenreResponse genreResponse = response.body();
							if (genreResponse != null && genreResponse.getGenres() != null) {
								callback.onGetGenresSuccess(genreResponse.getGenres());
								EspressoIdlingResource.decrement();
							}
						}
					}

					@Override
					public void onFailure(Call<GenreResponse> call, Throwable t) {
						callback.onGetGenresError(t);
						EspressoIdlingResource.decrement();
					}
				});
	}

	public void getMovieDetail(int movieId, String language, final OnGetDetailCallback callback) {
		EspressoIdlingResource.increment();
		api.getMovieDetail(movieId, BuildConfig.TMDB_API_KEY, language)
				.enqueue(new Callback<Movie>() {
					@Override
					public void onResponse(Call<Movie> call, Response<Movie> response) {
						if (response.isSuccessful()) {
							Movie movie = response.body();
							if (movie != null) {
								callback.onGetDetailSuccess(movie);
								EspressoIdlingResource.decrement();
							}
						}
					}

					@Override
					public void onFailure(Call<Movie> call, Throwable t) {
						callback.onGetDetailError(t);
						EspressoIdlingResource.decrement();
					}
				});
	}

	public void getTvShowDetail(int tvShowId, String language, final OnGetDetailCallback callback) {
		EspressoIdlingResource.increment();
		api.getTvShowDetail(tvShowId, BuildConfig.TMDB_API_KEY, language)
				.enqueue(new Callback<Movie>() {
					@Override
					public void onResponse(Call<Movie> call, Response<Movie> response) {
						if (response.isSuccessful()) {
							Movie movie = response.body();
							if (movie != null) {
								callback.onGetDetailSuccess(movie);
								EspressoIdlingResource.decrement();
							}
						}
					}

					@Override
					public void onFailure(Call<Movie> call, Throwable t) {
						callback.onGetDetailError(t);
						EspressoIdlingResource.decrement();
					}
				});
	}

	public void getReleaseTodayMovies(String todayDate, final OnGetMoviesCallback callback) {
		EspressoIdlingResource.increment();
		api.getReleaseTodayMovies(BuildConfig.TMDB_API_KEY, todayDate, todayDate)
				.enqueue(new Callback<MovieResponse>() {
					@Override
					public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
						if (response.isSuccessful()) {
							MovieResponse movieResponse = response.body();
							if (movieResponse != null && movieResponse.getMovies() != null) {
								callback.onGetMoviesSuccess(movieResponse.getMovies());
								EspressoIdlingResource.decrement();
							}
						}
					}

					@Override
					public void onFailure(Call<MovieResponse> call, Throwable t) {
						callback.onGetMoviesError(t);
						EspressoIdlingResource.decrement();
					}
				});
	}

	public void getQueriedMovies(String language, String query, final OnGetMoviesCallback callback) {
		EspressoIdlingResource.increment();
		api.getQueriedMovies(BuildConfig.TMDB_API_KEY, language, query)
				.enqueue(new Callback<MovieResponse>() {
					@Override
					public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
						if (response.isSuccessful()) {
							MovieResponse movieResponse = response.body();
							if (movieResponse != null && movieResponse.getMovies() != null) {
								callback.onGetMoviesSuccess(movieResponse.getMovies());
								EspressoIdlingResource.decrement();
							}
						}
					}

					@Override
					public void onFailure(Call<MovieResponse> call, Throwable t) {
						callback.onGetMoviesError(t);
						EspressoIdlingResource.decrement();
					}
				});
	}

	public void getQueriedTvShows(String language, String query, final OnGetMoviesCallback callback) {
		EspressoIdlingResource.increment();
		api.getQueriedTvShows(BuildConfig.TMDB_API_KEY, language, query)
				.enqueue(new Callback<MovieResponse>() {
					@Override
					public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
						if (response.isSuccessful()) {
							MovieResponse movieResponse = response.body();
							if (movieResponse != null && movieResponse.getMovies() != null) {
								callback.onGetMoviesSuccess(movieResponse.getMovies());
								EspressoIdlingResource.decrement();
							}
						}
					}

					@Override
					public void onFailure(Call<MovieResponse> call, Throwable t) {
						callback.onGetMoviesError(t);
						EspressoIdlingResource.decrement();
					}
				});
	}
}
