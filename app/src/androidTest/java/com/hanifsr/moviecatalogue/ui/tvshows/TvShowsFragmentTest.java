package com.hanifsr.moviecatalogue.ui.tvshows;

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

public class TvShowsFragmentTest {

	@Rule
	public ActivityTestRule<SingleFragmentActivity> activityTestRule = new ActivityTestRule<>(SingleFragmentActivity.class);
	private TvShowsFragment tvShowsFragment = new TvShowsFragment();

	@Before
	public void setUp() {
		IdlingRegistry.getInstance().register(EspressoIdlingResource.getEspressoIdlingResource());
		activityTestRule.getActivity().setFragment(tvShowsFragment);
	}

	@After
	public void tearDown() {
		IdlingRegistry.getInstance().unregister(EspressoIdlingResource.getEspressoIdlingResource());
	}

	@Test
	public void loadTvShows() {
		onView(withId(R.id.rv_tvshows)).check(new RecyclerViewItemCountAssertion(20));
	}
}