package com.hanifsr.moviecatalogue.widgets;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.hanifsr.moviecatalogue.R;

public class FavouritesWidget extends AppWidgetProvider {

	private static final String TOAST_ACTION = "com.hanifsr.moviecatalogue.TOAST_ACTION";
	public static final String EXTRA_ITEM = "com.hanifsr.moviecatalogue.EXTRA_ITEM";

	static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
								int appWidgetId) {

		Intent intent = new Intent(context, StackWidgetService.class);
		intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
		intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));

		RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.favourites_widget);
		views.setRemoteAdapter(R.id.stack_view, intent);
		views.setEmptyView(R.id.stack_view, R.id.empty_view);

		Intent toastIntent = new Intent(context, FavouritesWidget.class);
		toastIntent.setAction(FavouritesWidget.TOAST_ACTION);
		toastIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
		intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));

		PendingIntent toastPendingIntent = PendingIntent.getBroadcast(context, 0, toastIntent, PendingIntent.FLAG_UPDATE_CURRENT);
		views.setPendingIntentTemplate(R.id.stack_view, toastPendingIntent);

		appWidgetManager.updateAppWidget(appWidgetId, views);
	}

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
		// There may be multiple widgets active, so update all of them
		for (int appWidgetId : appWidgetIds) {
			updateAppWidget(context, appWidgetManager, appWidgetId);
		}
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		super.onReceive(context, intent);
		if (intent.getAction() != null) {
			if (intent.getAction().equals(TOAST_ACTION)) {
				String title = intent.getStringExtra(EXTRA_ITEM);
				Toast.makeText(context, title, Toast.LENGTH_SHORT).show();
			}
		}
	}
}

