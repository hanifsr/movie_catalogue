package com.hanifsr.moviecatalogue.ui.tvshows;

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

public class TvShowsViewModelTest {

	private TvShowsViewModel tvShowsViewModel;
	private MovieCatalogueRepository movieCatalogueRepository = mock(MovieCatalogueRepository.class);

	@Rule
	public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

	@Before
	public void setUp() {
		tvShowsViewModel = new TvShowsViewModel(movieCatalogueRepository);
	}

	@Test
	public void getTvShows() {
		String language = "en-GB";
		List<Movie> dummyTvShows = DataDummy.generateDummyTvShows();

		MutableLiveData<List<Movie>> tvShows = new MutableLiveData<>();
		tvShows.postValue(dummyTvShows);

		when(movieCatalogueRepository.getTvShows(language)).thenReturn(tvShows);

		Observer<List<Movie>> observer = mock(Observer.class);

		tvShowsViewModel.getTvShows(language).observeForever(observer);

		verify(observer).onChanged(dummyTvShows);
	}
}