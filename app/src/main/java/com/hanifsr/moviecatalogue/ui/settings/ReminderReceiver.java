package com.hanifsr.moviecatalogue.ui.settings;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.hanifsr.moviecatalogue.R;
import com.hanifsr.moviecatalogue.data.model.NotificationItem;
import com.hanifsr.moviecatalogue.data.source.MovieCatalogueRepository;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ReminderReceiver extends BroadcastReceiver {

	private static final String TAG = "GGWP";
	private MovieCatalogueRepository movieCatalogueRepository;

	public static final String TYPE_DAILY = "Daily Reminder";
	public static final String TYPE_RELEASE_TODAY = "Release Today Reminder";
	private final int ID_DAILY = 100;
	private final int ID_RELEASE_TODAY = 101;

	public static final String EXTRA_DATE = "date";
	public static final String EXTRA_TYPE = "type";

	private int idNotification = 0;
	private final ArrayList<NotificationItem> stackNotif = new ArrayList<>();
	private final static String GROUP_KEY = "group_key";
	private static final int MAX_NOTIFICATION = 2;

	@Override
	public void onReceive(final Context context, Intent intent) {
		stackNotif.clear();
		idNotification = 0;

		String type = intent.getStringExtra(EXTRA_TYPE);

		final String title = type.equalsIgnoreCase(TYPE_DAILY) ? TYPE_DAILY : TYPE_RELEASE_TODAY;
		final int notifId = type.equalsIgnoreCase(TYPE_DAILY) ? ID_DAILY : ID_RELEASE_TODAY;

		if (type.equalsIgnoreCase(TYPE_DAILY)) {
			String message = "Don't forget to check Movie Catalogue :)";
			showReminderNotification(context, title, message, notifId);
		} else if (type.equalsIgnoreCase(TYPE_RELEASE_TODAY)) {
			String todayDate = intent.getStringExtra(EXTRA_DATE);
//			movieCatalogueRepository = Injection.provideRepository();
			/*RemoteRepository remoteRepository = RemoteRepository.getInstance();
			remoteRepository.getReleaseTodayMovies(todayDate, new OnGetMoviesCallback() {
				@Override
				public void onSuccess(ArrayList<Movie> movies) {
					String message;
					for (Movie movie : movies) {
						message = movie.getTitle() + " is released today";
						stackNotif.add(new NotificationItem(idNotification, message));
						idNotification++;
						showReminderNotification(context, title, message, notifId);
					}
				}

				@Override
				public void onError(Throwable error) {
					Log.d(TAG, "onError: ");
				}
			});*/
		}
	}

	private void showReminderNotification(Context context, String title, String message, int notifId) {
		String CHANNEL_ID = "channel_1";
		String CHANNEL_NAME = "Reminder channel";

		NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
		NotificationCompat.Builder builder;
		if (idNotification < MAX_NOTIFICATION) {
			builder = new NotificationCompat.Builder(context, CHANNEL_ID)
					.setSmallIcon(R.drawable.ic_notifications_black_24dp)
					.setContentTitle(title)
					.setContentText(message)
					.setColor(ContextCompat.getColor(context, android.R.color.transparent))
					.setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
					.setSound(alarmSound);
		} else {
			NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle()
					.setBigContentTitle("Today released movies");

			for (int i = 0; i < stackNotif.size(); i++) {
				inboxStyle.addLine(stackNotif.get(i).getMessage());
			}

			builder = new NotificationCompat.Builder(context, CHANNEL_ID)
					.setSmallIcon(R.drawable.ic_notifications_black_24dp)
					.setContentTitle(title)
					.setContentText(idNotification + " movies released today")
					.setGroup(GROUP_KEY)
					.setGroupSummary(true)
					.setStyle(inboxStyle);
		}

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
					CHANNEL_NAME,
					NotificationManager.IMPORTANCE_DEFAULT);

			channel.enableVibration(true);
			channel.setVibrationPattern(new long[]{1000, 1000, 1000, 1000, 1000});

			builder.setChannelId(CHANNEL_ID);

			if (notificationManager != null) {
				notificationManager.createNotificationChannel(channel);
			}
		}

		Notification notification = builder.build();

		if (notificationManager != null) {
			notificationManager.notify(notifId, notification);
		}
	}

	public void setReminder(Context context, String type) {
		int reminderId = type.equalsIgnoreCase(TYPE_DAILY) ? ID_DAILY : ID_RELEASE_TODAY;

		String time = "";
		if (type.equalsIgnoreCase(TYPE_DAILY)) {
			time = "07:00";
		} else if (type.equalsIgnoreCase(TYPE_RELEASE_TODAY)) {
			time = "08:00";
		}

		final String TIME_FORMAT = "HH:mm";
		if (isDateInvalid(time, TIME_FORMAT)) {
			return;
		}

		String todayDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

		AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		Intent intent = new Intent(context, ReminderReceiver.class);
		intent.putExtra(EXTRA_TYPE, type);
		intent.putExtra(EXTRA_DATE, todayDate);

		String[] timeArray = time.split(":");

		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeArray[0]));
		calendar.set(Calendar.MINUTE, Integer.parseInt(timeArray[1]));
		calendar.set(Calendar.SECOND, 0);

		PendingIntent pendingIntent = PendingIntent.getBroadcast(context, reminderId, intent, 0);

		if (alarmManager != null) {
			alarmManager.setInexactRepeating(AlarmManager.RTC, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
		}

		if (type.equalsIgnoreCase(TYPE_DAILY)) {
			Toast.makeText(context, "Daily reminder is On", Toast.LENGTH_SHORT).show();
		} else if (type.equalsIgnoreCase(TYPE_RELEASE_TODAY)) {
			Toast.makeText(context, "Release today reminder is On", Toast.LENGTH_SHORT).show();
		}
	}

	public void cancelReminder(Context context, String type) {
		AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		Intent intent = new Intent(context, ReminderReceiver.class);
		int requestCode = type.equalsIgnoreCase(TYPE_DAILY) ? ID_DAILY : ID_RELEASE_TODAY;

		PendingIntent pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, 0);
		pendingIntent.cancel();

		if (alarmManager != null) {
			alarmManager.cancel(pendingIntent);
		}

		if (type.equalsIgnoreCase(TYPE_DAILY)) {
			Toast.makeText(context, "Daily reminder is Off", Toast.LENGTH_SHORT).show();
		} else if (type.equalsIgnoreCase(TYPE_RELEASE_TODAY)) {
			Toast.makeText(context, "Release today reminder Off", Toast.LENGTH_SHORT).show();
		}
	}

	public boolean isDateInvalid(String date, String format) {
		try {
			DateFormat dateFormat = new SimpleDateFormat(format, Locale.getDefault());
			dateFormat.setLenient(false);
			dateFormat.parse(date);
			return false;
		} catch (ParseException e) {
			return true;
		}
	}
}
