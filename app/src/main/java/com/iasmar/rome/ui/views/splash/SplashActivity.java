package com.iasmar.rome.ui.views.splash;

import android.content.Intent;
import android.os.Bundle;

import com.iasmar.rome.ui.views.base.BaseActivity;
import com.iasmar.rome.ui.views.main.MainActivity;

import static com.iasmar.rome.configuration.Constant.CUSTOM_INVALID_INT;


/**
 * Created by Asmar on 01/7/2018.
 * <p>
 * The right Way to create Splash Screen
 * As we all know splash screen is the user’s first experience of your application. It normally used to display some kind of progress before the application setup completely. As per Google material design spec, Splash Screen follows a pattern known called Launch Screen. You can find the specification here.
 * <p>
 * Common Mistake
 * In most of the application developers use splash screen to showcase brand icon or picture for couple of seconds. This is common practice which most of the developers are following. It is not a good idea to use a splash screen that wastes a user’s time. This should be strictly avoided.
 * With the common approach you may also lead the problem of blank white page appears during splash launching.
 * <p>
 * Right Way
 * The right way of implementing a splash screen is a little different. In the new approach specify your splash screen’s background as the activity’s theme background.
 * Also the root cause of blank white page problem is that your layout file is visible only after app has been initialized completely.
 * Do not create a layout file for splash activity. Instead, specify activity’s theme background as splash layout.
 * <p>
 * Notice that we do not have setContentView() for this SplashActivity. View is displaying from the theme and this way it is faster than creating a layout.
 * If you look at the time splash screen displays is exactly the same with the time taken by app to configure itself because of a cold launch (very first launch). If the app is cached, the splash screen will go away almost immediately.
 *
 * @author Asmar
 * @version 1
 * @see BaseActivity
 * @since 1.0
 */
public class SplashActivity extends BaseActivity {

    /**
     * This method called after the setContentView(int) in the base class.
     *
     * @param savedInstanceState A mapping from String keys to various parcelable values.
     * @param intent             intent that started this activity.
     */
    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);
        // Start MainActivity
        startActivity(MainActivity.newInstance(SplashActivity.this));
        // close splash activity
        finish();
    }

    /**
     * Get the attempt content view.
     * To be used by child activities.
     * In the splash screen case we return invalid int since we don`t want to set the content view.
     *
     * @return layoutResID Resource ID to be inflated.
     */
    @Override
    protected int getContentView() {
        return CUSTOM_INVALID_INT;
    }

}
