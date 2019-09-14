package com.hanifsr.moviecatalogue;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.hanifsr.moviecatalogue.adapter.SectionsPagerAdapter;

public class MainActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		/*
		BottomNavigationView bottomNavigationView = findViewById(R.id.nav_view);
		// Passing each menu ID as a set of Ids because each
		// menu should be considered as top level destinations.
		AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
				R.id.navigation_movies, R.id.navigation_tvshows)
				.build();
		NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
		NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
		NavigationUI.setupWithNavController(bottomNavigationView, navController);
		*/
		Toolbar toolbar = findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());

		ViewPager viewPager = findViewById(R.id.view_pager);
		viewPager.setAdapter(sectionsPagerAdapter);

		TabLayout tabs = findViewById(R.id.tabs);
		tabs.setupWithViewPager(viewPager);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main_menu, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.action_language_setting) {
			Intent intent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
			startActivity(intent);
		}
		return super.onOptionsItemSelected(item);
	}
}