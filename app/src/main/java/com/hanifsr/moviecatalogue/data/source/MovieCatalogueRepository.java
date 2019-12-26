package com.hanifsr.moviecatalogue.data.source;

import android.text.TextUtils;
import android.util.Log;

import androidx.collection.SimpleArrayMap;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.hanifsr.moviecatalogue.data.source.local.LocalRepository;
import com.hanifsr.moviecatalogue.data.source.local.entity.FavouriteMovieEntity;
import com.hanifsr.moviecatalogue.data.source.local.entity.FavouriteTvShowEntity;
import com.hanifsr.moviecatalogue.data.source.local.room.MovieCatalogueDatabase;
import com.hanifsr.moviecatalogue.data.source.remote.OnGetDetailCallback;
import com.hanifsr.moviecatalogue.data.source.remote.OnGetGenresCallback;
import com.hanifsr.moviecatalogue.data.source.remote.OnGetMoviesCallback;
import com.hanifsr.moviecatalogue.data.source.remote.RemoteRepository;
import com.hanifsr.moviecatalogue.data.source.remote.response.Genre;
import com.hanifsr.moviecatalogue.data.source.remote.response.Movie;

import java.util.ArrayList;
import java.util.List;

public class MovieCatalogueRepository implements MovieCatalogueDataSource {

	private static final String TAG = "GGWP";

	private volatile static MovieCatalogueRepository INSTANCE;

	private final LocalRepository localRepository;
	private final RemoteRepository remoteRepository;

	private MovieCatalogueRepository(LocalRepository localRepository, RemoteRepository remoteRepository) {
		this.localRepository = localRepository;
		this.remoteRepository = remoteRepository;
	}

	public static MovieCatalogueRepository getInstance(LocalRepository localRepository, RemoteRepository remoteData) {
		if (INSTANCE == null) {
			synchronized (MovieCatalogueRepository.class) {
				if (INSTANCE == null) {
					INSTANCE = new MovieCatalogueRepository(localRepository, remoteData);
				}
			}
		}
		return INSTANCE;
	}

	@Override
	public LiveData<List<Movie>> getMovies(final String language) {
		Log.d(TAG, "getMovies: INVOKED");
		final MutableLiveData<List<Movie>> moviesResult = new MutableLiveData<>();
		final SimpleArrayMap<Integer, String> genreList = new SimpleArrayMap<>();

		remoteRepository.getMovieGenres(language, new OnGetGenresCallback() {
			@Override
			public void onGetGenresSuccess(ArrayList<Genre> genres) {
				for (Genre genre : genres) {
					genreList.put(genre.getId(), genre.getName());
				}

				final ArrayList<Movie> movieArrayList = new ArrayList<>();
				remoteRepository.getMovies(language, new OnGetMoviesCallback() {
					@Override
					public void onGetMoviesSuccess(ArrayList<Movie> movies) {
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
						moviesResult.postValue(movieArrayList);
					}

					@Override
					public void onGetMoviesError(Throwable error) {
						Log.d(TAG, "getMovies -> onGetMoviesError: " + error.getMessage());
					}
				});
			}

			@Override
			public void onGetGenresError(Throwable error) {
				Log.d(TAG, "getMovies -> onGetGenresError: " + error.getMessage());
			}
		});

		return moviesResult;
	}

	@Override
	public LiveData<List<Movie>> getTvShows(final String language) {
		Log.d(TAG, "getTvShows: INVOKED");
		final MutableLiveData<List<Movie>> tvShowsResult = new MutableLiveData<>();
		final SimpleArrayMap<Integer, String> genreList = new SimpleArrayMap<>();

		remoteRepository.getTvShowGenres(language, new OnGetGenresCallback() {
			@Override
			public void onGetGenresSuccess(ArrayList<Genre> genres) {
				for (Genre genre : genres) {
					genreList.put(genre.getId(), genre.getName());
				}

				final ArrayList<Movie> tvShowArrayList = new ArrayList<>();
				remoteRepository.getTvShows(language, new OnGetMoviesCallback() {
					@Override
					public void onGetMoviesSuccess(ArrayList<Movie> movies) {
						for (Movie tvShow : movies) {
							ArrayList<String> tvShowGenres = new ArrayList<>();
							for (Integer genreIds : tvShow.getGenreIds()) {
								if (genreList.containsKey(genreIds)) {
									tvShowGenres.add(genreList.get(genreIds));
								}
							}
							tvShow.setGenresHelper(TextUtils.join(", ", tvShowGenres));
							tvShowArrayList.add(tvShow);
						}
						tvShowsResult.postValue(tvShowArrayList);
					}

					@Override
					public void onGetMoviesError(Throwable error) {
						Log.d(TAG, "getTvShows -> onGetMoviesError: " + error.getMessage());
					}
				});
			}

			@Override
			public void onGetGenresError(Throwable error) {
				Log.d(TAG, "getTvShows -> onGetGenresError: " + error.getMessage());
			}
		});

		return tvShowsResult;
	}

	@Override
	public LiveData<Movie> getMovieDetail(int movieId, String language) {
		Log.d(TAG, "getMovieDetail: INVOKED");
		final MutableLiveData<Movie> movieResult = new MutableLiveData<>();

		remoteRepository.getMovieDetail(movieId, language, new OnGetDetailCallback() {
			@Override
			public void onGetDetailSuccess(Movie movie) {
				if (movie.getGenresDetail() != null) {
					ArrayList<String> movieGenres = new ArrayList<>();
					for (Genre genre : movie.getGenresDetail()) {
						movieGenres.add(genre.getName());
					}
					movie.setGenresHelper(TextUtils.join(", ", movieGenres));
				}
				movieResult.postValue(movie);
			}

			@Override
			public void onGetDetailError(Throwable error) {
				Log.d(TAG, "getMovieDetail -> onGetDetailError: " + error.getMessage());
			}
		});

		return movieResult;
	}

	@Override
	public LiveData<Movie> getTvShowDetail(int tvShowId, String language) {
		Log.d(TAG, "getTvShowDetail: INVOKED");
		final MutableLiveData<Movie> tvShowResult = new MutableLiveData<>();

		remoteRepository.getTvShowDetail(tvShowId, language, new OnGetDetailCallback() {
			@Override
			public void onGetDetailSuccess(Movie movie) {
				if (movie.getGenresDetail() != null) {
					ArrayList<String> tvShowGenres = new ArrayList<>();
					for (Genre genre : movie.getGenresDetail()) {
						tvShowGenres.add(genre.getName());
					}
					movie.setGenresHelper(TextUtils.join(", ", tvShowGenres));
				}
				tvShowResult.postValue(movie);
			}

			@Override
			public void onGetDetailError(Throwable error) {
				Log.d(TAG, "getTvShowDetail -> onGetDetailError: " + error.getMessage());
			}
		});

		return tvShowResult;
	}

	@Override
	public LiveData<List<Movie>> getReleaseTodayMovies(String todayDate) {
		Log.d(TAG, "getReleaseTodayMovies: INVOKED");
		final MutableLiveData<List<Movie>> todayMoviesResult = new MutableLiveData<>();

		remoteRepository.getReleaseTodayMovies(todayDate, new OnGetMoviesCallback() {
			@Override
			public void onGetMoviesSuccess(ArrayList<Movie> movies) {
				todayMoviesResult.postValue(movies);
			}

			@Override
			public void onGetMoviesError(Throwable error) {
				Log.d(TAG, "getReleaseTodayMovies -> onGetMoviesError: " + error.getMessage());
			}
		});

		return todayMoviesResult;
	}

	@Override
	public LiveData<List<Movie>> getQueriedMovies(final String language, final String query) {
		Log.d(TAG, "getQueriedMovies: INVOKED");
		final MutableLiveData<List<Movie>> queriedMoviesResult = new MutableLiveData<>();
		final SimpleArrayMap<Integer, String> genreList = new SimpleArrayMap<>();

		remoteRepository.getMovieGenres(language, new OnGetGenresCallback() {
			@Override
			public void onGetGenresSuccess(ArrayList<Genre> genres) {
				for (Genre genre : genres) {
					genreList.put(genre.getId(), genre.getName());
				}

				final ArrayList<Movie> movieArrayList = new ArrayList<>();
				remoteRepository.getQueriedMovies(language, query, new OnGetMoviesCallback() {
					@Override
					public void onGetMoviesSuccess(ArrayList<Movie> movies) {
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
						queriedMoviesResult.postValue(movieArrayList);
					}

					@Override
					public void onGetMoviesError(Throwable error) {
						Log.d(TAG, "getQueriedMovies -> onGetMoviesError: " + error.getMessage());
					}
				});
			}

			@Override
			public void onGetGenresError(Throwable error) {
				Log.d(TAG, "getQueriedMovies -> onGetGenresError: " + error.getMessage());
			}
		});

		return queriedMoviesResult;
	}

	@Override
	public LiveData<List<Movie>> getQueriedTvShows(final String language, final String query) {
		Log.d(TAG, "getQueriedTvShows: INVOKED");
		final MutableLiveData<List<Movie>> queriedTvShowsResult = new MutableLiveData<>();
		final SimpleArrayMap<Integer, String> genreList = new SimpleArrayMap<>();

		remoteRepository.getTvShowGenres(language, new OnGetGenresCallback() {
			@Override
			public void onGetGenresSuccess(ArrayList<Genre> genres) {
				for (Genre genre : genres) {
					genreList.put(genre.getId(), genre.getName());
				}

				final ArrayList<Movie> tvShowArrayList = new ArrayList<>();
				remoteRepository.getQueriedTvShows(language, query, new OnGetMoviesCallback() {
					@Override
					public void onGetMoviesSuccess(ArrayList<Movie> movies) {
						for (Movie tvShow : movies) {
							ArrayList<String> tvShowGenres = new ArrayList<>();
							for (Integer genreIds : tvShow.getGenreIds()) {
								if (genreList.containsKey(genreIds)) {
									tvShowGenres.add(genreList.get(genreIds));
								}
							}
							tvShow.setGenresHelper(TextUtils.join(", ", tvShowGenres));
							tvShowArrayList.add(tvShow);
						}
						queriedTvShowsResult.postValue(tvShowArrayList);
					}

					@Override
					public void onGetMoviesError(Throwable error) {
						Log.d(TAG, "getQueriedTvShows -> onGetMoviesError: " + error.getMessage());
					}
				});
			}

			@Override
			public void onGetGenresError(Throwable error) {
				Log.d(TAG, "getQueriedTvShows -> onGetGenresError: " + error.getMessage());
			}
		});

		return queriedTvShowsResult;
	}

	@Override
	public void insertFavouriteMovie(FavouriteMovieEntity favouriteMovieEntity) {
		Log.d(TAG, "insertFavouriteMovie: INVOKED");
		MovieCatalogueDatabase.databaseWriteExecutor.execute(() -> localRepository.insertFavouriteMovie(favouriteMovieEntity));
	}

	@Override
	public void insertFavouriteTvShow(FavouriteTvShowEntity favouriteTvShowEntity) {
		Log.d(TAG, "insertFavouriteTvShow: INVOKED");
		MovieCatalogueDatabase.databaseWriteExecutor.execute(() -> localRepository.insertFavouriteTvShow(favouriteTvShowEntity));
	}

	@Override
	public void deleteFavouriteMovie(FavouriteMovieEntity favouriteMovieEntity) {
		Log.d(TAG, "deleteFavouriteMovie: INVOKED");
		MovieCatalogueDatabase.databaseWriteExecutor.execute(() -> localRepository.deleteFavouriteMovie(favouriteMovieEntity));
	}

	@Override
	public void deleteFavouriteTvShow(FavouriteTvShowEntity favouriteTvShowEntity) {
		Log.d(TAG, "deleteFavouriteTvShow: INVOKED");
		MovieCatalogueDatabase.databaseWriteExecutor.execute(() -> localRepository.deleteFavouriteTvShow(favouriteTvShowEntity));
	}

	@Override
	public LiveData<PagedList<FavouriteMovieEntity>> getFavouriteMovies() {
		Log.d(TAG, "getFavouriteMovies: INVOKED");
		return new LivePagedListBuilder<>(localRepository.getFavouriteMovies(), 20).build();
	}

	@Override
	public LiveData<PagedList<FavouriteTvShowEntity>> getFavouriteTvShows() {
		Log.d(TAG, "getFavouriteTvShows: INVOKED");
		return new LivePagedListBuilder<>(localRepository.getFavouriteTvShows(), 20).build();
	}

	@Override
	public LiveData<FavouriteMovieEntity> getFavouriteMovie(int id) {
		return localRepository.getFavouriteMovie(id);
	}

	@Override
	public LiveData<FavouriteTvShowEntity> getFavouriteTvShow(int id) {
		Log.d(TAG, "getFavouriteTvShow: INVOKED");
		return localRepository.getFavouriteTvShow(id);
	}
}
