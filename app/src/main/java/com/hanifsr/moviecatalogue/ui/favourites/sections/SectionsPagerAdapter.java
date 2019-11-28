package com.hanifsr.moviecatalogue.ui.favourites.sections;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.hanifsr.moviecatalogue.R;

public class SectionsPagerAdapter extends FragmentPagerAdapter {

	@StringRes
	private static final int[] TAB_TITLES = new int[]{R.string.movies, R.string.tv_shows};
	private final Context context;

	public SectionsPagerAdapter(Context context, FragmentManager fm) {
		super(fm);
		this.context = context;
	}

	@NonNull
	@Override
	public Fragment getItem(int position) {
		return SectionsFragment.newInstance(position);
	}

	@Nullable
	@Override
	public CharSequence getPageTitle(int position) {
		return context.getResources().getString(TAB_TITLES[position]);
	}

	@Override
	public int getCount() {
		return TAB_TITLES.length;
	}
}
