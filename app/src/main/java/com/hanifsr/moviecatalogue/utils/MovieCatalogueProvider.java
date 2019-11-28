package com.hanifsr.moviecatalogue.utils;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import com.hanifsr.moviecatalogue.data.source.local.MovieHelper;

import static com.hanifsr.moviecatalogue.data.source.local.DatabaseContract.AUTHORITY;
import static com.hanifsr.moviecatalogue.data.source.local.DatabaseContract.MovieColumns.MOVIE_CONTENT_URI;
import static com.hanifsr.moviecatalogue.data.source.local.DatabaseContract.TABLE_NAME_MOVIE;
import static com.hanifsr.moviecatalogue.data.source.local.DatabaseContract.TABLE_NAME_TV_SHOW;
import static com.hanifsr.moviecatalogue.data.source.local.DatabaseContract.TvShowColumns.TV_SHOW_CONTENT_URI;

public class MovieCatalogueProvider extends ContentProvider {

	private static final int MOVIE = 1;
	private static final int TV_SHOW = 2;
	private static final int MOVIE_ID = 3;
	private static final int TV_SHOW_ID = 4;
	private MovieHelper movieHelper;

	private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

	static {
		uriMatcher.addURI(AUTHORITY, TABLE_NAME_MOVIE, MOVIE);
		uriMatcher.addURI(AUTHORITY, TABLE_NAME_TV_SHOW, TV_SHOW);

		uriMatcher.addURI(AUTHORITY, TABLE_NAME_MOVIE + "/#", MOVIE_ID);
		uriMatcher.addURI(AUTHORITY, TABLE_NAME_TV_SHOW + "/#", TV_SHOW_ID);
	}

	public MovieCatalogueProvider() {
	}

	@Override
	public boolean onCreate() {
		movieHelper = MovieHelper.getInstance(getContext());
		movieHelper.open();
		return true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
						String[] selectionArgs, String sortOrder) {
		Cursor cursor = null;
		switch (uriMatcher.match(uri)) {
			case MOVIE:
				cursor = movieHelper.queryAllMovie();
				break;
			case TV_SHOW:
				cursor = movieHelper.queryAllTvShow();
				break;
			case MOVIE_ID:
				if (uri.getLastPathSegment() != null) {
					cursor = movieHelper.queryMovieById(Integer.parseInt(uri.getLastPathSegment()));
				}
				break;
			case TV_SHOW_ID:
				if (uri.getLastPathSegment() != null) {
					cursor = movieHelper.queryTvShowById(Integer.parseInt(uri.getLastPathSegment()));
				}
				break;
			default:
				cursor = null;
				break;
		}

		return cursor;
	}

	@Override
	public String getType(Uri uri) {
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		long added;
		switch (uriMatcher.match(uri)) {
			case MOVIE:
				added = movieHelper.insertMovie(values);
				getContext().getContentResolver().notifyChange(MOVIE_CONTENT_URI, null);
				return Uri.parse(MOVIE_CONTENT_URI + "/" + added);
			case TV_SHOW:
				added = movieHelper.insertTvShow(values);
				getContext().getContentResolver().notifyChange(TV_SHOW_CONTENT_URI, null);
				return Uri.parse(TV_SHOW_CONTENT_URI + "/" + added);
			default:
				added = 0;
				getContext().getContentResolver().notifyChange(MOVIE_CONTENT_URI, null);
				return Uri.parse(MOVIE_CONTENT_URI + "/" + added);
		}
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
					  String[] selectionArgs) {
		return 0;
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		int deleted;
		switch (uriMatcher.match(uri)) {
			case MOVIE_ID:
				deleted = movieHelper.deleteMovieById(Integer.parseInt(uri.getLastPathSegment()));
				getContext().getContentResolver().notifyChange(MOVIE_CONTENT_URI, null);
				break;
			case TV_SHOW_ID:
				deleted = movieHelper.deleteTvShowById(Integer.parseInt(uri.getLastPathSegment()));
				getContext().getContentResolver().notifyChange(TV_SHOW_CONTENT_URI, null);
				break;
			default:
				deleted = 0;
				getContext().getContentResolver().notifyChange(MOVIE_CONTENT_URI, null);
				break;
		}

		return deleted;
	}
}
