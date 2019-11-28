package com.hanifsr.moviecatalogue.widgets;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Binder;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;
import com.hanifsr.moviecatalogue.R;
import com.hanifsr.moviecatalogue.utils.MappingHelper;
import com.hanifsr.moviecatalogue.data.source.remote.response.Movie;

import java.util.ArrayList;

import static com.hanifsr.moviecatalogue.data.source.local.DatabaseContract.MovieColumns.MOVIE_CONTENT_URI;
import static com.hanifsr.moviecatalogue.data.source.local.DatabaseContract.TvShowColumns.TV_SHOW_CONTENT_URI;

public class StackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

	private final ArrayList<Movie> widgetItems = new ArrayList<>();
	private final Context context;

	public StackRemoteViewsFactory(Context context) {
		this.context = context;
	}

	@Override
	public void onCreate() {

	}

	@Override
	public void onDataSetChanged() {
		final long identityToken = Binder.clearCallingIdentity();

		widgetItems.clear();

		for (int i = 0; i <= 1; i++) {
			Cursor cursor;
			if (i == 0) {
				cursor = context.getContentResolver().query(MOVIE_CONTENT_URI, null, null, null, null);
			} else {
				cursor = context.getContentResolver().query(TV_SHOW_CONTENT_URI, null, null, null, null);
			}

			if (cursor != null) {
				widgetItems.addAll(MappingHelper.mapCursorToArrayList(cursor, i));
				cursor.close();
			}
		}

		Binder.restoreCallingIdentity(identityToken);
	}

	@Override
	public void onDestroy() {

	}

	@Override
	public int getCount() {
		return widgetItems.size();
	}

	@Override
	public RemoteViews getViewAt(int position) {
		String IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500/";

		RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.widget_item);
		try {
			Bitmap bitmap = Glide.with(context)
					.asBitmap()
					.load(IMAGE_BASE_URL + widgetItems.get(position).getPosterPath())
					.submit()
					.get();
			rv.setImageViewBitmap(R.id.image_view, bitmap);
		} catch (Exception e) {
			e.printStackTrace();
		}

		Bundle extras = new Bundle();
		extras.putString(FavouritesWidget.EXTRA_ITEM, widgetItems.get(position).getTitle());

		Intent fillIntent = new Intent();
		fillIntent.putExtras(extras);

		rv.setOnClickFillInIntent(R.id.image_view, fillIntent);
		return rv;
	}

	@Override
	public RemoteViews getLoadingView() {
		return null;
	}

	@Override
	public int getViewTypeCount() {
		return 1;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}
}
