package com.hanifsr.moviecatalogue.data.source.local.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.hanifsr.moviecatalogue.data.source.local.entity.FavouriteMovieEntity;
import com.hanifsr.moviecatalogue.data.source.local.entity.FavouriteTvShowEntity;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {FavouriteMovieEntity.class, FavouriteTvShowEntity.class},
		version = 1,
		exportSchema = false)
public abstract class MovieCatalogueDatabase extends RoomDatabase {

	public abstract MovieCatalogueDao movieCatalogueDao();

	private static volatile MovieCatalogueDatabase INSTANCE;
	private static final int NUMBER_OF_THREADS = 4;
	public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

	public static MovieCatalogueDatabase getInstance(Context context) {
		if (INSTANCE == null) {
			synchronized (MovieCatalogueDatabase.class) {
				if (INSTANCE == null) {
					INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
							MovieCatalogueDatabase.class, "movie_catalogue_database")
							.build();
				}
			}
		}

		return INSTANCE;
	}
}
