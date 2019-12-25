package com.hanifsr.moviecatalogue.di;

import android.app.Application;

import com.hanifsr.moviecatalogue.data.source.MovieCatalogueRepository;
import com.hanifsr.moviecatalogue.data.source.local.LocalRepository;
import com.hanifsr.moviecatalogue.data.source.local.room.MovieCatalogueDatabase;
import com.hanifsr.moviecatalogue.data.source.remote.RemoteRepository;

public class Injection {

	public static MovieCatalogueRepository provideRepository(Application application) {
		MovieCatalogueDatabase database = MovieCatalogueDatabase.getInstance(application);

		LocalRepository localRepository = LocalRepository.getInstance(database.movieCatalogueDao());
		RemoteRepository remoteRepository = RemoteRepository.getInstance();
		return MovieCatalogueRepository.getInstance(localRepository, remoteRepository);
	}
}
