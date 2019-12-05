package com.hanifsr.moviecatalogue.di;

import com.hanifsr.moviecatalogue.data.source.MovieCatalogueRepository;
import com.hanifsr.moviecatalogue.data.source.remote.RemoteRepository;

public class Injection {

	public static MovieCatalogueRepository provideRepository() {
		RemoteRepository remoteRepository = RemoteRepository.getInstance();
		return MovieCatalogueRepository.getInstance(remoteRepository);
	}
}
