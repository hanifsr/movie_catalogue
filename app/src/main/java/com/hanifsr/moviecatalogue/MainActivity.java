package com.hanifsr.moviecatalogue;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

	private TypedArray dataPoster;
	private String[] dataTitle, dataGenres;
	private MovieAdapter movieAdapter;
	private ArrayList<Movie> movies;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		movieAdapter = new MovieAdapter(this);
		ListView listView = findViewById(R.id.movie_list);
		listView.setAdapter(movieAdapter);

		prepare();
		addItem();

		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Toast.makeText(MainActivity.this, movies.get(position).getTitle(), Toast.LENGTH_SHORT).show();
			}
		});
	}

	private void addItem() {
		movies = new ArrayList<>();

		for (int i = 0; i < dataTitle.length; i++) {
			Movie movie = new Movie();
			movie.setPoster(dataPoster.getResourceId(i, -1));
			movie.setTitle(dataTitle[i]);
			movie.setGenres(dataGenres[i]);
			movies.add(movie);
		}
		movieAdapter.setMovies(movies);
	}

	private void prepare() {
		dataTitle = getResources().getStringArray(R.array.data_title);
		dataGenres = getResources().getStringArray(R.array.data_genres);
		dataPoster = getResources().obtainTypedArray(R.array.data_poster);
	}
}