package com.hanifsr.moviecatalogue.ui.tvshows;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hanifsr.moviecatalogue.R;

public class TvShowsFragment extends Fragment {

	private TvShowsViewModel tvShowsViewModel;

	public static TvShowsFragment newInstance() {
		return new TvShowsFragment();
	}

	@Override
	public View onCreateView(@NonNull LayoutInflater inflater,
							 ViewGroup container, Bundle savedInstanceState) {
		tvShowsViewModel = ViewModelProviders.of(this).get(TvShowsViewModel.class);
		View view = inflater.inflate(R.layout.fragment_tv_shows, container, false);
		final TextView textView = view.findViewById(R.id.tv_tvshows);
		tvShowsViewModel.getText().observe(this, new Observer<String>() {
			@Override
			public void onChanged(String s) {
				textView.setText(s);
			}
		});
		return view;
	}
}
