package com.hanifsr.moviecatalogue.ui.movies;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.hanifsr.moviecatalogue.data.source.MovieCatalogueRepository;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

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
	}
}