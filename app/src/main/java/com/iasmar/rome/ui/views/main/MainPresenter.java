package com.iasmar.rome.ui.views.main;

import android.support.annotation.NonNull;

import com.google.common.base.Optional;

import io.reactivex.disposables.Disposable;
import com.iasmar.rome.data.repositories.main.MainRepository;
import com.iasmar.rome.ui.views.base.BasePresenter;
import com.iasmar.rome.util.schedulers.BaseSchedulerProvider;
import com.iasmar.rome.util.testing.EspressoIdlingResource;


public class MainPresenter<V extends MainContract.View> extends BasePresenter<V>
        implements MainContract.Presenter<V> {
    // The main repository.
    @NonNull
    private MainRepository mainRepository;

    // The main view.
    @NonNull
    private final MainContract.View mainView;

    // The scheduler provider.
    @NonNull
    private BaseSchedulerProvider schedulerProvider;


    /**
     * The constructor purpose is to
     * <p>
     * Initialize the inject main repository,
     * main view and the inject scheduler provider.
     * Initialize the composite disposable for espresso testing library.
     * Set the presenter back to the view.
     *
     * @param mainRepository The inject of main repository.
     * @param mainView       The main view.
     * @param schedulerProvider The  inject of scheduler provider.
     * @see MainRepository
     * @see MainContract.View
     * @see BaseSchedulerProvider
     */
    MainPresenter(@NonNull MainRepository mainRepository,
                  @NonNull MainContract.View mainView,
                  @NonNull BaseSchedulerProvider schedulerProvider) {
        super(mainRepository, schedulerProvider);
        onAttach(mainView);
        this.mainView = mainView;
        this.schedulerProvider = getSchedulerProvider();
        this.mainRepository = (MainRepository) getRepository();

        mainView.setPresenter(this);

    }

    /**
     * This method should be called on onResume().
     */
    @Override
    public void subscribe() {
        super.subscribe();
    }

    /**
     * Called when the fragment is no longer attached to its activity.  This
     * is called after onDestroy().
     */
    @Override
    public void onDetach() {
        super.onDetach();
        MainRepository.destroyInstance();
    }







    /**
     * Show loading or hide loading to the user.
     *
     * @param active true to show or false to hide.
     * @see MainContract.View
     */
    private void setLoading(boolean active) {
        mainView.setLoading(active);
    }


    /**
     * Force update data in the next call by making the cache dirty true.
     * @param cacheIsDirty true to force update.
     * @see MainRepository
     */
    private void setCacheDirty(boolean cacheIsDirty) {
        mainRepository.setCacheDirty(cacheIsDirty);
    }

    /**
     * The network request might be handled in a different thread so make sure Espresso knows
     * that the app is busy until the response is handled.
     * App is busy until further notice.
     */
    private void espressoIncrement() {
        EspressoIdlingResource.increment();
    }

    /**
     * Free the App.
     * Set app as idle.
     */
    private void espressoDecrement() {
        if (!EspressoIdlingResource.getIdlingResource().isIdleNow()) {
            EspressoIdlingResource.decrement();
        }
    }




   

    
}
