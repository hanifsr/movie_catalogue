package com.hanifsr.moviecatalogue.ui.favourites.sections;

import androidx.test.espresso.IdlingRegistry;
import androidx.test.rule.ActivityTestRule;

import com.hanifsr.moviecatalogue.R;
import com.hanifsr.moviecatalogue.testing.SingleFragmentActivity;
import com.hanifsr.moviecatalogue.utils.EspressoIdlingResource;
import com.hanifsr.moviecatalogue.utils.RecyclerViewItemCountAssertion;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

public class SectionsFragmentTest {

	@Rule
	public ActivityTestRule<SingleFragmentActivity> activityTestRule = new ActivityTestRule<>(SingleFragmentActivity.class);
	private SectionsFragment sectionsFragment = new SectionsFragment();

	@Before
	public void setUp() {
		IdlingRegistry.getInstance().register(EspressoIdlingResource.getEspressoIdlingResource());
	}

	@After
	public void tearDown() {
		IdlingRegistry.getInstance().unregister(EspressoIdlingResource.getEspressoIdlingResource());
	}

	@Test
	public void loadFavouriteMovies() {
		activityTestRule.getActivity().setFragment(SectionsFragment.newInstance(0));
		onView(withId(R.id.rv_favourites)).check(new RecyclerViewItemCountAssertion(0));
	}

	@Test
	public void loadFavouriteTvShows() {
		activityTestRule.getActivity().setFragment(SectionsFragment.newInstance(1));
		onView(withId(R.id.rv_favourites)).check(new RecyclerViewItemCountAssertion(0));
	}
}