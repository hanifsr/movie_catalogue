package com.hanifsr.moviecatalogue.ui.favourites.sections;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.paging.PagedList;

import com.hanifsr.moviecatalogue.data.source.MovieCatalogueRepository;
import com.hanifsr.moviecatalogue.data.source.local.entity.FavouriteMovieEntity;
import com.hanifsr.moviecatalogue.data.source.local.entity.FavouriteTvShowEntity;
import com.hanifsr.moviecatalogue.utils.DataDummy;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class SectionsViewModelTest {

	private SectionsViewModel sectionsViewModel;
	private MovieCatalogueRepository movieCatalogueRepository = mock(MovieCatalogueRepository.class);

	@Rule
	public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

	@Before
	public void setUp() {
		sectionsViewModel = new SectionsViewModel(movieCatalogueRepository);
	}

	@Test
	public void getFavouriteMovies() {
		MutableLiveData<PagedList<FavouriteMovieEntity>> favouriteMovies = new MutableLiveData<>();
		PagedList<FavouriteMovieEntity> pagedList = mock(PagedList.class);
		favouriteMovies.postValue(pagedList);

		when(movieCatalogueRepository.getFavouriteMovies()).thenReturn(favouriteMovies);

		Observer<PagedList<FavouriteMovieEntity>> observer = mock(Observer.class);

		sectionsViewModel.getFavouriteMovies().observeForever(observer);

		verify(observer).onChanged(pagedList);
	}

	@Test
	public void getFavouriteTvShows() {
		MutableLiveData<PagedList<FavouriteTvShowEntity>> favouriteTvShows = new MutableLiveData<>();
		PagedList<FavouriteTvShowEntity> pagedList = mock(PagedList.class);
		favouriteTvShows.postValue(pagedList);

		when(movieCatalogueRepository.getFavouriteTvShows()).thenReturn(favouriteTvShows);

		Observer<PagedList<FavouriteTvShowEntity>> observer = mock(Observer.class);

		sectionsViewModel.getFavouriteTvShows().observeForever(observer);

		verify(observer).onChanged(pagedList);
	}
}