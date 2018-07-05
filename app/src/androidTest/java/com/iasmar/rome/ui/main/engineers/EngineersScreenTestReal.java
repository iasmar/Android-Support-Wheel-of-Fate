package com.iasmar.rome.ui.main.engineers;

import android.support.test.InstrumentationRegistry;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.iasmar.rome.Injection;
import com.iasmar.rome.data.repositories.engineers.EngineersDataSource;
import com.iasmar.rome.ui.views.main.MainActivity;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import android.support.test.espresso.IdlingRegistry;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ListView;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.After;
import org.junit.Before;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.google.common.base.Preconditions.checkArgument;
import static org.hamcrest.Matchers.allOf;

/**
 * Tests for the Engineers screen, the main screen which contains a list of all Engineers.
 */
/**
 * Created by Asmar on 01/7/2018.
 * <p>
 * Tests for the add engineers screen.
 *
 * @author Asmar
 * @version 1
 * @since 0.1.0
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class EngineersScreenTestReal {
    /**
     * {@link ActivityTestRule} is a JUnit {@link Rule @Rule} to launch your activity under test.
     * <p>
     * Rules are interceptors which are executed for each test method and are important building
     * blocks of Junit tests.
     */
    @Rule
    public ActivityTestRule<MainActivity> mMainActivityTestRule =
            new ActivityTestRule<MainActivity>(MainActivity.class) {

                /**
                 * To avoid a long list of Engineers and the need to scroll through the list to find a
                 * engineer, we call {@link EngineersDataSource ()} before each test.
                 */
                @Override
                protected void beforeActivityLaunched() {
                    super.beforeActivityLaunched();
                    // Doing this in @Before generates a race condition.
                    Injection.provideEngineersRepository(InstrumentationRegistry.getTargetContext());
                }
            };

    /**
     * Prepare your test fixture for this test. In this case we register an IdlingResources with
     * Espresso. IdlingResource resource is a great way to tell Espresso when your app is in an
     * idle state. This helps Espresso to synchronize your test actions, which makes tests significantly
     * more reliable.
     */
    @Before
    public void setUp() throws Exception {
        IdlingRegistry.getInstance().register(
                mMainActivityTestRule.getActivity().getCountingIdlingResource());
    }

    /**
     * Unregister your Idling Resource so it can be garbage collected and does not leak any memory.
     */
    @After
    public void tearDown() throws Exception {
        IdlingRegistry.getInstance().unregister(
                mMainActivityTestRule.getActivity().getCountingIdlingResource());
    }

    /**
     * A custom {@link Matcher} which matches an item in a {@link ListView} by its text.
     * <p>
     * View constraints:
     * <ul>
     * <li>View must be a child of a {@link ListView}
     * <ul>
     *
     * @param itemText the text to match
     * @return Matcher that matches text in the given view
     */
    private Matcher<View> withItemText(final String itemText) {
        checkArgument(!TextUtils.isEmpty(itemText), "itemText cannot be null or empty");
        return new TypeSafeMatcher<View>() {
            @Override
            public boolean matchesSafely(View item) {
                return allOf(
                        isDescendantOfA(isAssignableFrom(RecyclerView.class)),
                        withText(itemText)).matches(item);
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("is isDescendantOfA LV with text " + itemText);
            }
        };
    }
    

    @Test
    public void showAllEngineers() {

// TODO use the data in the mock to avoid failing the test when data is changed

        //Verify that all our Engineers are shown
        onView(withItemText("Bogdan")).check(matches(isDisplayed()));
        onView(withItemText("Nic")).check(matches(isDisplayed()));
        onView(withItemText("Tung")).check(matches(isDisplayed()));
        onView(withItemText("Gautam")).check(matches(isDisplayed()));
        onView(withItemText("Bala")).check(matches(isDisplayed()));
        onView(withItemText("Nazih")).check(matches(isDisplayed()));
        onView(withItemText("Huteri")).check(matches(isDisplayed()));
        onView(withItemText("Aldy")).check(matches(isDisplayed()));
        onView(withItemText("Ankur")).check(matches(isDisplayed()));
        onView(withItemText("Chinh")).check(matches(isDisplayed()));

    }


}
