package com.hanifsr.moviecatalogue;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreferenceCompat;

import java.util.Locale;

public class SettingsActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings_activity);

		getSupportFragmentManager()
				.beginTransaction()
				.replace(R.id.settings, new SettingsFragment())
				.commit();

		ActionBar actionBar = getSupportActionBar();
		if (actionBar != null) {
			actionBar.setDisplayHomeAsUpEnabled(true);
		}
	}

	public static class SettingsFragment extends PreferenceFragmentCompat {

		private ReminderReceiver reminderReceiver;

		@Override
		public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
			setPreferencesFromResource(R.xml.root_preferences, rootKey);

			reminderReceiver = new ReminderReceiver();

			Preference languagePreference = findPreference("language");
			final SwitchPreferenceCompat dailyPreference = findPreference("daily");
			final SwitchPreferenceCompat releaseTodayPreference = findPreference("release_today");

			Intent languageIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
			if (languagePreference != null) {
				languagePreference.setIntent(languageIntent);
				languagePreference.setSummary(Locale.getDefault().getDisplayName());
			}

			if (dailyPreference != null) {
				dailyPreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
					@Override
					public boolean onPreferenceClick(Preference preference) {
						if (dailyPreference.isChecked()) {
							reminderReceiver.setReminder(getContext(), ReminderReceiver.TYPE_DAILY);
//							reminderReceiver.setDailyReminder(getContext(), ReminderReceiver.TYPE_DAILY);
//							Toast.makeText(getContext(), "Daily reminder is On", Toast.LENGTH_SHORT).show();
							return true;
						} else {
							reminderReceiver.cancelReminder(getContext(), ReminderReceiver.TYPE_DAILY);
//							Toast.makeText(getContext(), "Daily reminder is Off", Toast.LENGTH_SHORT).show();
							return false;
						}
					}
				});
			}

			if (releaseTodayPreference != null) {
				releaseTodayPreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
					@Override
					public boolean onPreferenceClick(Preference preference) {
						if (releaseTodayPreference.isChecked()) {
							reminderReceiver.setReminder(getContext(), ReminderReceiver.TYPE_RELEASE_TODAY);
//							Toast.makeText(getContext(), "Release today reminder On", Toast.LENGTH_SHORT).show();
							return true;
						} else {
							reminderReceiver.cancelReminder(getContext(), ReminderReceiver.TYPE_RELEASE_TODAY);
//							Toast.makeText(getContext(), "Release today reminder Off", Toast.LENGTH_SHORT).show();
							return false;
						}
					}
				});
			}
		}
	}
}