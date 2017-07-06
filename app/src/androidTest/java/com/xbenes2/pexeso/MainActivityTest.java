package com.xbenes2.pexeso;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withTagValue;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityTest {
    private static int SAFE_TIMEOUT_MARGIN = 1000;

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void test_grid_view_is_displayed() {
        onView(withId(R.id.imagesGrid)).check(matches(isDisplayed()));
    }

    @Test
    public void test_first_item_is_unknown_at_the_beginning() {
        onData(anything()).inAdapterView(withId(R.id.imagesGrid))
                .atPosition(0).check(matches(withTagValue(is((Object) R.drawable.letter_unknown))));

    }

    @Test
    public void test_clicking_item_opens_it() {
        onData(anything()).inAdapterView(withId(R.id.imagesGrid))
                .atPosition(0)
                .perform(click());

        onData(anything()).inAdapterView(withId(R.id.imagesGrid))
                .atPosition(0).check(matches(withTagValue(is(not((Object) R.drawable.letter_unknown)))));
    }


    @Test
    public void test_clicking_two_items_makes_them_closed_and_unknown_or_over_after_some_timeout() throws InterruptedException {
        onData(anything()).inAdapterView(withId(R.id.imagesGrid))
                .atPosition(0)
                .perform(click());

        onData(anything()).inAdapterView(withId(R.id.imagesGrid))
                .atPosition(1)
                .perform(click());

        Thread.sleep(MainActivity.HIDE_TIMEOUT + SAFE_TIMEOUT_MARGIN);

        onData(anything()).inAdapterView(withId(R.id.imagesGrid))
                .atPosition(0).check(matches(anyOf(withTagValue(is((Object) R.drawable.letter_unknown)), withTagValue(is((Object) R.drawable.letter_over)))));
        onData(anything()).inAdapterView(withId(R.id.imagesGrid))
                .atPosition(0).check(matches(anyOf(withTagValue(is((Object) R.drawable.letter_unknown)), withTagValue(is((Object) R.drawable.letter_over)))));
    }
}
