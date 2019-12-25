package com.hanifsr.moviecatalogue.ui.movies;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.hanifsr.moviecatalogue.data.source.MovieCatalogueRepository;
import com.hanifsr.moviecatalogue.data.source.remote.response.Movie;
import com.hanifsr.moviecatalogue.utils.DataDummy;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MoviesViewModelTest {

	private MoviesViewModel moviesViewModel;
	private MovieCatalogueRepository movieCatalogueRepository = mock(MovieCatalogueRepository.class);

	@Rule
	public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

	@Before
	public void setUp() {
		moviesViewModel = new MoviesViewModel(movieCatalogueRepository);
	}

	@Test
	public void getMovies() {
		String language = "en-GB";
		List<Movie> dummyMovies = DataDummy.generateDummyMovies();

		MutableLiveData<List<Movie>> movies = new MutableLiveData<>();
		movies.postValue(dummyMovies);

		when(movieCatalogueRepository.getMovies(language)).thenReturn(movies);

		Observer<List<Movie>> observer = mock(Observer.class);

		moviesViewModel.getMovies(language).observeForever(observer);

		verify(observer).onChanged(dummyMovies);
	}
}