package com.hanifsr.moviecatalogue.ui.detail;

import android.content.Context;
import android.content.Intent;

import androidx.test.espresso.IdlingRegistry;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.hanifsr.moviecatalogue.R;
import com.hanifsr.moviecatalogue.data.source.remote.response.Movie;
import com.hanifsr.moviecatalogue.utils.DataDummy;
import com.hanifsr.moviecatalogue.utils.EspressoIdlingResource;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

public class MovieDetailActivityTest {

	private Movie dummyMovie = DataDummy.generateDummyMovies().get(0);
	private Movie dummyTvShow = DataDummy.generateDummyTvShows().get(0);

	@Rule
	public ActivityTestRule<MovieDetailActivity> activityTestRule = new ActivityTestRule<>(MovieDetailActivity.class, true, false);

	@Before
	public void setUp() {
		IdlingRegistry.getInstance().register(EspressoIdlingResource.getEspressoIdlingResource());
	}

	@After
	public void tearDown() {
		IdlingRegistry.getInstance().unregister(EspressoIdlingResource.getEspressoIdlingResource());
	}

	@Test
	public void loadMovieDetail() {
		activityTestRule.launchActivity(createIntentWithExtras(dummyMovie.getId(), 0));
		onView(withId(R.id.tv_movie_title_detail)).check(matches(withText(dummyMovie.getTitle())));
	}

	@Test
	public void loadTvShowDetail() {
		activityTestRule.launchActivity(createIntentWithExtras(dummyTvShow.getId(), 1));
		onView(withId(R.id.tv_movie_title_detail)).check(matches(withText(dummyTvShow.getTitle())));
	}

	private Intent createIntentWithExtras(int id, int index) {
		Context targetContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
		Intent intent = new Intent(targetContext, MovieDetailActivity.class);
		intent.putExtra(MovieDetailActivity.EXTRA_ID, id);
		intent.putExtra(MovieDetailActivity.EXTRA_POSITION, 0);
		intent.putExtra(MovieDetailActivity.EXTRA_INDEX, index);

		return intent;
	}
}