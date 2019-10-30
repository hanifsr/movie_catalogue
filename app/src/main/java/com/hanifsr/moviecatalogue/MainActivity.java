package com.hanifsr.moviecatalogue;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.hanifsr.moviecatalogue.database.MovieHelper;

public class MainActivity extends AppCompatActivity {

	private MovieHelper movieHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		BottomNavigationView navView = findViewById(R.id.nav_view);
		AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
				R.id.navigation_movies, R.id.navigation_tv_shows, R.id.navigation_favourites)
				.build();
		NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
		NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
		NavigationUI.setupWithNavController(navView, navController);

		if (getSupportActionBar() != null) {
			getSupportActionBar().setElevation(0);
		}

		movieHelper = MovieHelper.getInstance(getApplicationContext());
		movieHelper.open();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main_menu, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.action_setting) {
			Intent settingsIntent = new Intent(this, SettingsActivity.class);
			startActivity(settingsIntent);
		}
		return super.onOptionsItemSelected(item);
		/*switch (item.getItemId()) {
			case R.id.action_language_setting:
				Intent languageIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
				startActivity(languageIntent);
				return true;
			case R.id.action_setting:
				Intent settingsIntent = new Intent(this, SettingsActivity.class);
				startActivity(settingsIntent);
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}*/
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		movieHelper.close();
	}
}