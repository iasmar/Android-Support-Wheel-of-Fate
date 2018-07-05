package com.iasmar.rome.app;

import android.app.Application;
import android.content.res.Configuration;


/**
 * Created by Asmar on 01/7/2018.
 * <p>
 * Base class for maintaining global application state.
 *
 * @author Asmar
 * @version 1
 * @since 0.1.0
 */
public class RomeApplication extends Application {


    /**
     * Called when the application is starting, before any other application objects have been created.
     * Required initialization logic here.
     */
    @Override
    public void onCreate() {
        super.onCreate();

    }


    /**
     * Called by the system when the device configuration changes while the component is running.
     *
     * @param newConfig The new device configuration.
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }


    /**
     * This is called when the overall system is running low on memory,
     * and would like actively running processes to tighten their belts.
     */
    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }
}