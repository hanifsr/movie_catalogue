package com.hanifsr.moviecatalogue.ui.detail;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.hanifsr.moviecatalogue.data.source.MovieCatalogueRepository;
import com.hanifsr.moviecatalogue.data.source.remote.response.Movie;
import com.hanifsr.moviecatalogue.utils.DataDummy;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class DetailViewModelTest {

	private DetailViewModel detailViewModel;
	private MovieCatalogueRepository movieCatalogueRepository = mock(MovieCatalogueRepository.class);
	private Movie dummyMovie = DataDummy.generateDummyMovies().get(0);
	private Movie dummyTvShow = DataDummy.generateDummyTvShows().get(0);
	private String language = "en-GB";

	@Rule
	public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

	@Before
	public void setUp() {
		detailViewModel = new DetailViewModel(movieCatalogueRepository);
	}

	@Test
	public void getMovieDetails() {
		MutableLiveData<Movie> movie = new MutableLiveData<>();
		movie.postValue(dummyMovie);

		when(movieCatalogueRepository.getMovieDetail(dummyMovie.getId(), language)).thenReturn(movie);

		Observer<Movie> observer = mock(Observer.class);

		detailViewModel.getDetails(dummyMovie.getId(), 0, language).observeForever(observer);

		verify(observer).onChanged(dummyMovie);
	}

	@Test
	public void getTvShowDetails() {
		MutableLiveData<Movie> tvShow = new MutableLiveData<>();
		tvShow.postValue(dummyTvShow);

		when(movieCatalogueRepository.getTvShowDetail(dummyTvShow.getId(), language)).thenReturn(tvShow);

		Observer<Movie> observer = mock(Observer.class);

		detailViewModel.getDetails(dummyTvShow.getId(), 1, language).observeForever(observer);

		verify(observer).onChanged(dummyTvShow);
	}
}