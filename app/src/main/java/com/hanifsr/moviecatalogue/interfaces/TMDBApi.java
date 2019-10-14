package com.hanifsr.moviecatalogue.interfaces;

import com.hanifsr.moviecatalogue.model.GenreResponse;
import com.hanifsr.moviecatalogue.model.Movie;
import com.hanifsr.moviecatalogue.model.MovieResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TMDBApi {

	@GET("movie/popular")
	Call<MovieResponse> getPopularMovies(
			@Query("api_key") String apiKey,
			@Query("language") String language,
			@Query("page") int page
	);

	@GET("tv/popular")
	Call<MovieResponse> getPopularTvShows(
			@Query("api_key") String apiKey,
			@Query("language") String language,
			@Query("page") int page
	);

	@GET("genre/movie/list")
	Call<GenreResponse> getMovieGenres(
			@Query("api_key") String apiKey,
			@Query("language") String language
	);

	@GET("genre/tv/list")
	Call<GenreResponse> getTvShowGenres(
			@Query("api_key") String apiKey,
			@Query("language") String language
	);

	@GET("movie/{movie_id}")
	Call<Movie> getMovieDetail(
			@Path("movie_id") int id,
			@Query("api_key") String apiKey,
			@Query("language") String language
	);

	@GET("tv/{tv_id}")
	Call<Movie> getTvShowDetail(
			@Path("tv_id") int id,
			@Query("api_key") String apiKey,
			@Query("language") String language
	);
}
