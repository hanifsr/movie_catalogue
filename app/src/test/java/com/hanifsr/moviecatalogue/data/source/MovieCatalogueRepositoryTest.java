package com.hanifsr.moviecatalogue.data.source;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.PagedList;

import com.hanifsr.moviecatalogue.BuildConfig;
import com.hanifsr.moviecatalogue.data.source.local.LocalRepository;
import com.hanifsr.moviecatalogue.data.source.local.entity.FavouriteMovieEntity;
import com.hanifsr.moviecatalogue.data.source.local.entity.FavouriteTvShowEntity;
import com.hanifsr.moviecatalogue.data.source.remote.OnGetDetailCallback;
import com.hanifsr.moviecatalogue.data.source.remote.OnGetGenresCallback;
import com.hanifsr.moviecatalogue.data.source.remote.OnGetMoviesCallback;
import com.hanifsr.moviecatalogue.data.source.remote.RemoteRepository;
import com.hanifsr.moviecatalogue.data.source.remote.TMDBApi;
import com.hanifsr.moviecatalogue.data.source.remote.response.GenreResponse;
import com.hanifsr.moviecatalogue.data.source.remote.response.Movie;
import com.hanifsr.moviecatalogue.data.source.remote.response.MovieResponse;
import com.hanifsr.moviecatalogue.utils.DataDummy;
import com.hanifsr.moviecatalogue.utils.LiveDataTestUtil;
import com.hanifsr.moviecatalogue.utils.PagedListUtil;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.mockwebserver.MockWebServer;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MovieCatalogueRepositoryTest {

	private LocalRepository localRepository = mock(LocalRepository.class);
	private RemoteRepository remoteRepository = mock(RemoteRepository.class);
	private FakeMovieCatalogueRepository fakeMovieCatalogueRepository = new FakeMovieCatalogueRepository(localRepository, remoteRepository);
	private Movie dummyMovie = DataDummy.generateDummyMovies().get(0);
	private Movie dummyTvShow = DataDummy.generateDummyTvShows().get(0);
	private FavouriteMovieEntity dummyFavouriteMovieEntity = DataDummy.generateDummyFavouriteMovies().get(0);
	private FavouriteTvShowEntity dummyFavouriteTvShowEntity = DataDummy.generateDummyFavouriteTvShows().get(0);
	private MockWebServer mockWebServer = new MockWebServer();
	private TMDBApi api;

	@Rule
	public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

	@Before
	public void setUp() throws Exception {
		mockWebServer.start();
		api = new Retrofit.Builder()
				.baseUrl(mockWebServer.url("https://api.themoviedb.org/3/"))
				.addConverterFactory(GsonConverterFactory.create())
				.build()
				.create(TMDBApi.class);
	}

	@After
	public void tearDown() throws Exception {
		mockWebServer.shutdown();
	}

	@Test
	public void getMovies() throws IOException {
		final Response<GenreResponse> movieGenreResponse = api.getMovieGenres(BuildConfig.TMDB_API_KEY, "en-GB").execute();
		final Response<MovieResponse> movieResponse = api.getPopularMovies(BuildConfig.TMDB_API_KEY, "en-GB", 1).execute();

		assertNotNull(movieGenreResponse.body());
		assertNotNull(movieResponse.body());

		doAnswer(new Answer() {
			@Override
			public Object answer(InvocationOnMock invocation) throws Throwable {
				((OnGetGenresCallback) invocation.getArguments()[1])
						.onGetGenresSuccess(movieGenreResponse.body().getGenres());
				return null;
			}
		}).when(remoteRepository).getMovieGenres(eq("en-GB"), any(OnGetGenresCallback.class));

		doAnswer(new Answer() {
			@Override
			public Object answer(InvocationOnMock invocation) throws Throwable {
				((OnGetMoviesCallback) invocation.getArguments()[1])
						.onGetMoviesSuccess(movieResponse.body().getMovies());
				return null;
			}
		}).when(remoteRepository).getMovies(eq("en-GB"), any(OnGetMoviesCallback.class));

		List<Movie> movies = LiveDataTestUtil.getValue(fakeMovieCatalogueRepository.getMovies("en-GB"));

		verify(remoteRepository, times(1)).getMovieGenres(eq("en-GB"), any(OnGetGenresCallback.class));
		verify(remoteRepository, times(1)).getMovies(eq("en-GB"), any(OnGetMoviesCallback.class));

		assertNotNull(movies);
		assertEquals(20, movies.size());
	}

	@Test
	public void getTvShows() throws IOException {
		final Response<GenreResponse> tvShowGenreResponse = api.getTvShowGenres(BuildConfig.TMDB_API_KEY, "en-GB").execute();
		final Response<MovieResponse> tvShowResponse = api.getPopularTvShows(BuildConfig.TMDB_API_KEY, "en-GB", 1).execute();

		assertNotNull(tvShowGenreResponse.body());
		assertNotNull(tvShowResponse.body());

		doAnswer(new Answer() {
			@Override
			public Object answer(InvocationOnMock invocation) throws Throwable {
				((OnGetGenresCallback) invocation.getArguments()[1])
						.onGetGenresSuccess(tvShowGenreResponse.body().getGenres());
				return null;
			}
		}).when(remoteRepository).getTvShowGenres(eq("en-GB"), any(OnGetGenresCallback.class));

		doAnswer(new Answer() {
			@Override
			public Object answer(InvocationOnMock invocation) throws Throwable {
				((OnGetMoviesCallback) invocation.getArguments()[1])
						.onGetMoviesSuccess(tvShowResponse.body().getMovies());
				return null;
			}
		}).when(remoteRepository).getTvShows(eq("en-GB"), any(OnGetMoviesCallback.class));

		List<Movie> tvShows = LiveDataTestUtil.getValue(fakeMovieCatalogueRepository.getTvShows("en-GB"));

		verify(remoteRepository, times(1)).getTvShowGenres(eq("en-GB"), any(OnGetGenresCallback.class));
		verify(remoteRepository, times(1)).getTvShows(eq("en-GB"), any(OnGetMoviesCallback.class));

		assertNotNull(tvShows);
		assertEquals(20, tvShows.size());
	}

	@Test
	public void getMovieDetail() throws IOException {
		final Response<Movie> movieDetailResponse = api.getMovieDetail(dummyMovie.getId(), BuildConfig.TMDB_API_KEY, "en-GB").execute();

		assertNotNull(movieDetailResponse.body());

		doAnswer(new Answer() {
			@Override
			public Object answer(InvocationOnMock invocation) throws Throwable {
				((OnGetDetailCallback) invocation.getArguments()[2])
						.onGetDetailSuccess(movieDetailResponse.body());
				return null;
			}
		}).when(remoteRepository).getMovieDetail(eq(dummyMovie.getId()), eq("en-GB"), any(OnGetDetailCallback.class));

		Movie movie = LiveDataTestUtil.getValue(fakeMovieCatalogueRepository.getMovieDetail(dummyMovie.getId(), "en-GB"));

		verify(remoteRepository, times(1)).getMovieDetail(eq(dummyMovie.getId()), eq("en-GB"), any(OnGetDetailCallback.class));

		assertNotNull(movie);
		assertEquals(dummyMovie.getTitle(), movie.getTitle());
	}

	@Test
	public void getTvShowDetail() throws IOException {
		final Response<Movie> tvShowDetailResponse = api.getTvShowDetail(dummyTvShow.getId(), BuildConfig.TMDB_API_KEY, "en-GB").execute();

		assertNotNull(tvShowDetailResponse.body());

		doAnswer(new Answer() {
			@Override
			public Object answer(InvocationOnMock invocation) throws Throwable {
				((OnGetDetailCallback) invocation.getArguments()[2])
						.onGetDetailSuccess(tvShowDetailResponse.body());
				return null;
			}
		}).when(remoteRepository).getTvShowDetail(eq(dummyTvShow.getId()), eq("en-GB"), any(OnGetDetailCallback.class));

		Movie tvShow = LiveDataTestUtil.getValue(fakeMovieCatalogueRepository.getTvShowDetail(dummyTvShow.getId(), "en-GB"));

		verify(remoteRepository, times(1)).getTvShowDetail(eq(dummyTvShow.getId()), eq("en-GB"), any(OnGetDetailCallback.class));

		assertNotNull(tvShow);
		assertEquals(dummyTvShow.getTitle(), tvShow.getTitle());
	}

	@Test
	public void getFavouriteMovies() {
		DataSource.Factory<Integer, FavouriteMovieEntity> factory = mock(DataSource.Factory.class);

		when(localRepository.getFavouriteMovies()).thenReturn(factory);

		fakeMovieCatalogueRepository.getFavouriteMovies();
		PagedList<FavouriteMovieEntity> result = PagedListUtil.mockPagedList(DataDummy.generateDummyFavouriteMovies());

		verify(localRepository, times(1)).getFavouriteMovies();

		assertNotNull(result);
		assertEquals(DataDummy.generateDummyFavouriteMovies().size(), result.size());
	}

	@Test
	public void getFavouriteTvShows() {
		DataSource.Factory<Integer, FavouriteTvShowEntity> factory = mock(DataSource.Factory.class);

		when(localRepository.getFavouriteTvShows()).thenReturn(factory);

		fakeMovieCatalogueRepository.getFavouriteTvShows();
		PagedList<FavouriteTvShowEntity> result = PagedListUtil.mockPagedList(DataDummy.generateDummyFavouriteTvShows());

		verify(localRepository, times(1)).getFavouriteTvShows();

		assertNotNull(result);
		assertEquals(DataDummy.generateDummyFavouriteTvShows().size(), result.size());
	}

	@Test
	public void getFavouriteMovie() {
		MutableLiveData<FavouriteMovieEntity> dummyFavouriteMovie = new MutableLiveData<>();
		dummyFavouriteMovie.postValue(dummyFavouriteMovieEntity);

		when(localRepository.getFavouriteMovie(dummyFavouriteMovieEntity.getId())).thenReturn(dummyFavouriteMovie);

		FavouriteMovieEntity result = LiveDataTestUtil.getValue(fakeMovieCatalogueRepository.getFavouriteMovie(dummyFavouriteMovieEntity.getId()));

		verify(localRepository, times(1)).getFavouriteMovie(dummyFavouriteMovieEntity.getId());

		assertNotNull(result);
		assertEquals(dummyFavouriteMovieEntity.getId(), result.getId());
	}

	@Test
	public void getFavouriteTvShow() {
		MutableLiveData<FavouriteTvShowEntity> dummyFavouriteTvShow = new MutableLiveData<>();
		dummyFavouriteTvShow.postValue(dummyFavouriteTvShowEntity);

		when(localRepository.getFavouriteTvShow(dummyFavouriteTvShowEntity.getId())).thenReturn(dummyFavouriteTvShow);

		FavouriteTvShowEntity result = LiveDataTestUtil.getValue(fakeMovieCatalogueRepository.getFavouriteTvShow(dummyFavouriteTvShowEntity.getId()));

		verify(localRepository, times(1)).getFavouriteTvShow(dummyFavouriteTvShowEntity.getId());

		assertNotNull(result);
		assertEquals(dummyFavouriteTvShowEntity.getId(), result.getId());
	}
}