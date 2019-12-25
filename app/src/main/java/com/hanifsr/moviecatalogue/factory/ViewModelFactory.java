package com.hanifsr.moviecatalogue.factory;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.hanifsr.moviecatalogue.data.source.MovieCatalogueRepository;
import com.hanifsr.moviecatalogue.di.Injection;
import com.hanifsr.moviecatalogue.ui.detail.DetailViewModel;
import com.hanifsr.moviecatalogue.ui.favourites.sections.SectionsViewModel;
import com.hanifsr.moviecatalogue.ui.movies.MoviesViewModel;
import com.hanifsr.moviecatalogue.ui.tvshows.TvShowsViewModel;

public class ViewModelFactory extends ViewModelProvider.NewInstanceFactory {

	private static volatile ViewModelFactory INSTANCE;
	private final MovieCatalogueRepository movieCatalogueRepository;

	private ViewModelFactory(MovieCatalogueRepository movieCatalogueRepository) {
		this.movieCatalogueRepository = movieCatalogueRepository;
	}

	public static ViewModelFactory getInstance(Application application) {
		if (INSTANCE == null) {
			synchronized (ViewModelFactory.class) {
				if (INSTANCE == null) {
					INSTANCE = new ViewModelFactory(Injection.provideRepository(application));
				}
			}
		}

		return INSTANCE;
	}

	@NonNull
	@Override
	public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
		if (modelClass.isAssignableFrom(MoviesViewModel.class)) {
			return (T) new MoviesViewModel(movieCatalogueRepository);
		} else if (modelClass.isAssignableFrom(TvShowsViewModel.class)) {
			return (T) new TvShowsViewModel(movieCatalogueRepository);
		} else if (modelClass.isAssignableFrom(DetailViewModel.class)) {
			return (T) new DetailViewModel(movieCatalogueRepository);
		} else if (modelClass.isAssignableFrom(SectionsViewModel.class)) {
			return (T) new SectionsViewModel(movieCatalogueRepository);
		}

		throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
	}
}
