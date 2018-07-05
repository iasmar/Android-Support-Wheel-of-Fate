package com.iasmar.rome.ui.views.main;

import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;

import com.iasmar.rome.R;
import com.iasmar.rome.ui.views.base.BaseActivity;

import static com.iasmar.rome.util.ObjectHelper.requireNonNull;


/**
 * Created by Asmar on 01/7/2018.
 * <p>
 * Initialize all the UI components to the {@link MainActivity} and control the UI components.
 *
 * @author Asmar
 * @version 1
 * @see MainActivity
 * @since 1.0
 */
class MainViewHolder {

    // The tool bar.
    Toolbar toolbar;

    // The app bar.
    AppBarLayout appBar;

    // The base activity.
    private BaseActivity activity;


    /**
     * The constructor purpose is to initialize all the UI components.
     *
     * @param activity the view that will be used to get the UI components.
     */
    MainViewHolder(BaseActivity activity) {
        activity = requireNonNull(activity, "activity cannot be null");
        this.activity = activity;
        appBar = activity.findViewById(R.id.appBar);
        toolbar = activity.findViewById(R.id.view_toolbar);

    }

}
