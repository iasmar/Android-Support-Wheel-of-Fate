package com.iasmar.rome.ui.main.schedules;

import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.iasmar.rome.R;
import com.iasmar.rome.TestUtils;
import com.iasmar.rome.data.repositories.engineers.EngineersRepository;
import com.iasmar.rome.data.repositories.engineers.FakeEngineersRemoteDataSource;
import com.iasmar.rome.ui.views.main.MainActivity;


import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static com.iasmar.rome.R.id.view_toolbar;

/**
 * Created by Asmar on 01/7/2018.
 * <p>
 * Tests for the add schedules screen.
 *
 * @author Asmar
 * @version 1
 * @since 0.1.0
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class SchedulesScreenTest {


    /**
     * {@link IntentsTestRule} is an {@link ActivityTestRule} which inits and releases Espresso
     * Intents before and after each test run.
     * <p>
     * <p>
     * Rules are interceptors which are executed for each test method and are important building
     * blocks of Junit tests.
     */
    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule =
            new ActivityTestRule<>(MainActivity.class, false, false);


    @Test
    public void toolbarTitle() {
        EngineersRepository.destroyInstance();
        FakeEngineersRemoteDataSource.getInstance().getEngineers();
        launchMainActivity();
        onView(withId(R.id.frg_engineers_generate_but)).perform(click());

        // Check that the toolbar shows the correct title
        onView(withId(view_toolbar)).check(matches(TestUtils.withToolbarTitle(R.string.schedule)));

    }
// TODO Rotation tool bar

    private void launchMainActivity() {
        Intent intent = new Intent(InstrumentationRegistry.getInstrumentation()
                .getTargetContext(), MainActivity.class);

        mActivityTestRule.launchActivity(intent);

    }


}
