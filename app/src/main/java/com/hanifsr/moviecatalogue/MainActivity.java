package com.hanifsr.moviecatalogue;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.res.TypedArray;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.hanifsr.moviecatalogue.adapter.MovieAdapter;
import com.hanifsr.moviecatalogue.model.Movie;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		BottomNavigationView bottomNavigationView = findViewById(R.id.nav_view);
		// Passing each menu ID as a set of Ids because each
		// menu should be considered as top level destinations.
		AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
				R.id.navigation_movies, R.id.navigation_tvshows)
				.build();
		NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
		NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
		NavigationUI.setupWithNavController(bottomNavigationView, navController);
	}
}