package com.iasmar.rome.ui.views.base;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.CallSuper;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.iasmar.rome.util.ViewUtils;
import com.iasmar.rome.util.testing.EspressoIdlingResource;

import static com.iasmar.rome.configuration.Constant.CUSTOM_INVALID_INT;


/**
 * Created by Asmar on 01/7/2018.
 * <p>
 * Base class for all the other activities.
 *
 * @author Asmar
 * @version 1
 * @since 1.0
 */
public abstract class BaseActivity extends AppCompatActivity {
//TODO back button issue
//TODO product details minimize app issue

    /**
     * This method called once  the {@link AppCompatActivity} created.
     * Called to do initial creation of a activity.
     *
     * @param savedInstanceState A mapping from String keys to various {@link Parcelable} values.
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int contentView = getContentView();
        // used by activities that don`t need setContentView such as SplashActivity.
        if (contentView != CUSTOM_INVALID_INT) {
            setContentView(contentView);
        }
        onViewReady(savedInstanceState, getIntent());

    }

    /**
     * This method called after the {@link AppCompatActivity#setContentView(int)}.
     * To be used by child activities
     *
     * @param savedInstanceState A mapping from String keys to various {@link Parcelable} values.
     * @param intent             intent that started this activity.
     */
    @CallSuper
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        //To be used by child activities
    }

    /**
     * Destroy all fragments and loaders.
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    //TODO add comments
    @VisibleForTesting
    public IdlingResource getCountingIdlingResource() {
        return EspressoIdlingResource.getIdlingResource();
    }


    /**
     * Get the attempt content view.
     * To be used by child activities
     *
     * @return layoutResID Resource ID to be inflated.
     */
    @LayoutRes
    protected abstract int getContentView();


    /**
     * Replace the current view to the new one.
     *
     * @param fragment The fragment.
     * @param frameId  The frame id.
     *                 The {@code fragment} is added to the container view with id {@code frameId}. The operation is
     *                 performed by the {@code fragmentManager}.
     * @param tag      Optional tag name for the fragment, to later retrieve the
     * @param bundle             the arguments supplied when the fragment was instantiated.
     * @return whether it successfully replaced or not.
     */
    public boolean replaceFragment(BaseFragment fragment, int frameId , @Nullable String tag, @Nullable Bundle bundle) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        ViewUtils.replaceFragment(fragmentManager, fragment, frameId,tag,bundle);
        //TODO add the logic for returning true or false.
        return true;
    }


}