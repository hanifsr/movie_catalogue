package com.hanifsr.moviecatalogue.model;

import com.hanifsr.moviecatalogue.BuildConfig;
import com.hanifsr.moviecatalogue.interfaces.OnGetDetailCallback;
import com.hanifsr.moviecatalogue.interfaces.OnGetGenresCallback;
import com.hanifsr.moviecatalogue.interfaces.OnGetMoviesCallback;
import com.hanifsr.moviecatalogue.interfaces.TMDBApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieRepository {

	private static final String BASE_URL = "https://api.themoviedb.org/3/";

	private static MovieRepository repository;

	private TMDBApi api;

	private MovieRepository(TMDBApi api) {
		this.api = api;
	}

	public static MovieRepository getInstance() {
		if (repository == null) {
			Retrofit retrofit = new Retrofit.Builder()
					.baseUrl(BASE_URL)
					.addConverterFactory(GsonConverterFactory.create())
					.build();

			repository = new MovieRepository(retrofit.create(TMDBApi.class));
		}

		return repository;
	}

	public void getMovies(String language, final OnGetMoviesCallback callback) {
		api.getPopularMovies(BuildConfig.TMDB_API_KEY, language, 1)
				.enqueue(new Callback<MovieResponse>() {
					@Override
					public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
						if (response.isSuccessful()) {
							MovieResponse movieResponse = response.body();
							if (movieResponse != null && movieResponse.getMovies() != null) {
								callback.onSuccess(movieResponse.getMovies());
							}
						}
					}

					@Override
					public void onFailure(Call<MovieResponse> call, Throwable t) {
						callback.onError(t);
					}
				});
	}

	public void getTvShows(String language, final OnGetMoviesCallback callback) {
		api.getPopularTvShows(BuildConfig.TMDB_API_KEY, language, 1)
				.enqueue(new Callback<MovieResponse>() {
					@Override
					public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
						if (response.isSuccessful()) {
							MovieResponse movieResponse = response.body();
							if (movieResponse != null && movieResponse.getMovies() != null) {
								callback.onSuccess(movieResponse.getMovies());
							}
						}
					}

					@Override
					public void onFailure(Call<MovieResponse> call, Throwable t) {
						callback.onError(t);
					}
				});
	}

	public void getMovieGenres(String language, final OnGetGenresCallback callback) {
		api.getMovieGenres(BuildConfig.TMDB_API_KEY, language)
				.enqueue(new Callback<GenreResponse>() {
					@Override
					public void onResponse(Call<GenreResponse> call, Response<GenreResponse> response) {
						if (response.isSuccessful()) {
							GenreResponse genreResponse = response.body();
							if (genreResponse != null && genreResponse.getGenres() != null) {
								callback.onSuccess(genreResponse.getGenres());
							}
						}
					}

					@Override
					public void onFailure(Call<GenreResponse> call, Throwable t) {
						callback.onError(t);
					}
				});
	}

	public void getTvShowGenres(String language, final OnGetGenresCallback callback) {
		api.getTvShowGenres(BuildConfig.TMDB_API_KEY, language)
				.enqueue(new Callback<GenreResponse>() {
					@Override
					public void onResponse(Call<GenreResponse> call, Response<GenreResponse> response) {
						if (response.isSuccessful()) {
							GenreResponse genreResponse = response.body();
							if (genreResponse != null && genreResponse.getGenres() != null) {
								callback.onSuccess(genreResponse.getGenres());
							}
						}
					}

					@Override
					public void onFailure(Call<GenreResponse> call, Throwable t) {
						callback.onError(t);
					}
				});
	}

	public void getMovieDetail(int movieId, String language, final OnGetDetailCallback callback) {
		api.getMovieDetail(movieId, BuildConfig.TMDB_API_KEY, language)
				.enqueue(new Callback<Movie>() {
					@Override
					public void onResponse(Call<Movie> call, Response<Movie> response) {
						if (response.isSuccessful()) {
							Movie movie = response.body();
							if (movie != null) {
								callback.onSuccess(movie);
							}
						}
					}

					@Override
					public void onFailure(Call<Movie> call, Throwable t) {
						callback.onError(t);
					}
				});
	}

	public void getTvShowDetail(int tvShowId, String language, final OnGetDetailCallback callback) {
		api.getTvShowDetail(tvShowId, BuildConfig.TMDB_API_KEY, language)
				.enqueue(new Callback<Movie>() {
					@Override
					public void onResponse(Call<Movie> call, Response<Movie> response) {
						if (response.isSuccessful()) {
							Movie movie = response.body();
							if (movie != null) {
								callback.onSuccess(movie);
							}
						}
					}

					@Override
					public void onFailure(Call<Movie> call, Throwable t) {
						callback.onError(t);
					}
				});
	}

	public void getReleaseTodayMovies(String todayDate, final OnGetMoviesCallback callback) {
		api.getReleaseTodayMovies(BuildConfig.TMDB_API_KEY, todayDate, todayDate)
				.enqueue(new Callback<MovieResponse>() {
					@Override
					public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
						if (response.isSuccessful()) {
							MovieResponse movieResponse = response.body();
							if (movieResponse != null && movieResponse.getMovies() != null) {
								callback.onSuccess(movieResponse.getMovies());
							}
						}
					}

					@Override
					public void onFailure(Call<MovieResponse> call, Throwable t) {
						callback.onError(t);
					}
				});
	}

	public void getQueriedMovies(String language, String query, final OnGetMoviesCallback callback) {
		api.getQueriedMovies(BuildConfig.TMDB_API_KEY, language, query)
				.enqueue(new Callback<MovieResponse>() {
					@Override
					public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
						if (response.isSuccessful()) {
							MovieResponse movieResponse = response.body();
							if (movieResponse != null && movieResponse.getMovies() != null) {
								callback.onSuccess(movieResponse.getMovies());
							}
						}
					}

					@Override
					public void onFailure(Call<MovieResponse> call, Throwable t) {
						callback.onError(t);
					}
				});
	}

	public void getQueriedTvShows(String language, String query, final OnGetMoviesCallback callback) {
		api.getQueriedTvShows(BuildConfig.TMDB_API_KEY, language, query)
				.enqueue(new Callback<MovieResponse>() {
					@Override
					public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
						if (response.isSuccessful()) {
							MovieResponse movieResponse = response.body();
							if (movieResponse != null && movieResponse.getMovies() != null) {
								callback.onSuccess(movieResponse.getMovies());
							}
						}
					}

					@Override
					public void onFailure(Call<MovieResponse> call, Throwable t) {
						callback.onError(t);
					}
				});
	}
}
