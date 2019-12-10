package com.hanifsr.moviecatalogue.ui.movies;

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

public class MoviesFragmentTest {

	@Rule
	public ActivityTestRule<SingleFragmentActivity> activityTestRule = new ActivityTestRule<>(SingleFragmentActivity.class);
	private MoviesFragment moviesFragment = new MoviesFragment();

	@Before
	public void setUp() {
		IdlingRegistry.getInstance().register(EspressoIdlingResource.getEspressoIdlingResource());
		activityTestRule.getActivity().setFragment(moviesFragment);
	}

	@After
	public void tearDown() {
		IdlingRegistry.getInstance().unregister(EspressoIdlingResource.getEspressoIdlingResource());
	}

	@Test
	public void loadMovies() {
		onView(withId(R.id.rv_movies)).check(new RecyclerViewItemCountAssertion(20));
	}
}