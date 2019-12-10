package com.hanifsr.moviecatalogue.ui;

import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.rule.ActivityTestRule;

import com.hanifsr.moviecatalogue.R;
import com.hanifsr.moviecatalogue.ui.main.MainActivity;
import com.hanifsr.moviecatalogue.utils.EspressoIdlingResource;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

public class MovieCatalogueTest {

	@Rule
	public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);

	@Before
	public void setUp() {
		IdlingRegistry.getInstance().register(EspressoIdlingResource.getEspressoIdlingResource());
	}

	@After
	public void tearDown() {
		IdlingRegistry.getInstance().unregister(EspressoIdlingResource.getEspressoIdlingResource());
	}

	@Test
	public void toMovieDetailActivityTest() {
		onView(withId(R.id.rv_movies)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

		onView(withId(R.id.tv_movie_title_detail)).check(matches(withText("Frozen II")));
	}

	@Test
	public void toTvShowDetailActivityTest() {
		onView(withId(R.id.navigation_tv_shows)).perform(click());

		onView(withId(R.id.rv_tvshows)).check(matches(isDisplayed()));

		onView(withId(R.id.rv_tvshows)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

		onView(withId(R.id.tv_movie_title_detail)).check(matches(withText("The Mandalorian")));
	}
}
