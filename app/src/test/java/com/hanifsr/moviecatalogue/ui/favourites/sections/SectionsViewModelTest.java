package com.hanifsr.moviecatalogue.ui.favourites.sections;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

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
		List<FavouriteMovieEntity> favouriteMovieEntities = DataDummy.generateDummyFavouriteMovies();

		MutableLiveData<List<FavouriteMovieEntity>> favouriteMovies = new MutableLiveData<>();
		favouriteMovies.postValue(favouriteMovieEntities);

		when(movieCatalogueRepository.getFavouriteMovies()).thenReturn(favouriteMovies);

		Observer<List<FavouriteMovieEntity>> observer = mock(Observer.class);

		sectionsViewModel.getFavouriteMovies().observeForever(observer);

		verify(observer).onChanged(favouriteMovieEntities);
	}

	@Test
	public void getFavouriteTvShows() {
		List<FavouriteTvShowEntity> favouriteTvShowEntities = DataDummy.generateDummyFavouriteTvShows();

		MutableLiveData<List<FavouriteTvShowEntity>> favouriteTvShows = new MutableLiveData<>();
		favouriteTvShows.postValue(favouriteTvShowEntities);

		when(movieCatalogueRepository.getFavouriteTvShows()).thenReturn(favouriteTvShows);

		Observer<List<FavouriteTvShowEntity>> observer = mock(Observer.class);

		sectionsViewModel.getFavouriteTvShows().observeForever(observer);

		verify(observer).onChanged(favouriteTvShowEntities);
	}
}