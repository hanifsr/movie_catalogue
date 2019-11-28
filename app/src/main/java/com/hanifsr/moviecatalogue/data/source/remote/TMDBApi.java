package com.hanifsr.moviecatalogue.data.source.remote;

import com.hanifsr.moviecatalogue.data.source.remote.response.GenreResponse;
import com.hanifsr.moviecatalogue.data.source.remote.response.Movie;
import com.hanifsr.moviecatalogue.data.source.remote.response.MovieResponse;

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

	@GET("discover/movie")
	Call<MovieResponse> getReleaseTodayMovies(
			@Query("api_key") String apiKey,
			@Query("release_date.gte") String releaseDateGTE,
			@Query("release_date.lte") String releaseDateLTE
	);

	@GET("search/movie")
	Call<MovieResponse> getQueriedMovies(
			@Query("api_key") String apiKey,
			@Query("language") String language,
			@Query("query") String query
	);

	@GET("search/tv")
	Call<MovieResponse> getQueriedTvShows(
			@Query("api_key") String apiKey,
			@Query("language") String language,
			@Query("query") String query
	);
}
