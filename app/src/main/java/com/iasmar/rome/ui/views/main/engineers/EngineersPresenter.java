package com.iasmar.rome.ui.views.main.engineers;

import android.support.annotation.NonNull;

import com.iasmar.rome.data.modules.Engineer;
import com.iasmar.rome.data.modules.ModulesHolder;
import com.iasmar.rome.data.repositories.engineers.EngineersDataSource;
import com.iasmar.rome.data.repositories.engineers.EngineersRepository;
import com.iasmar.rome.ui.views.base.BasePresenter;
import com.iasmar.rome.util.schedulers.BaseSchedulerProvider;
import com.iasmar.rome.util.testing.EspressoIdlingResource;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;


/**
 * Created by Asmar on 01/7/2018.
 * <p>
 * Listens to user actions from the UI ({@link EngineersFragment}), retrieves the data and updates the
 * UI as required. *
 *
 * @param <V> The current view.
 * @author Asmar
 * @version 1
 * @see EngineersFragment
 * @see EngineersContract.View
 * @see EngineersContract.Presenter
 * @see BaseSchedulerProvider
 * @see EngineersDataSource
 * @see EngineersRepository
 * @see Engineer
 * @see CompositeDisposable
 * @since 1.0
 */

public class EngineersPresenter<V extends EngineersContract.View> extends BasePresenter<V>
        implements EngineersContract.Presenter<V> {
    // The engineers repository.
    @NonNull
    private EngineersRepository engineersRepository;

    // The engineers view.
    @NonNull
    private final EngineersContract.View engineersView;

    // The scheduler provider.
    @NonNull
    private BaseSchedulerProvider schedulerProvider;


    // Is it still loading.
    private boolean isStillLoading;

    /**1
     * The constructor purpose is to
     * <p>
     * Initialize the inject engineers repository,
     * engineers view and the inject scheduler provider.
     * Initialize the composite disposable for espresso testing library.
     * Set the presenter back to the view.
     *
     * @param engineersRepository The inject of engineers repository.
     * @param engineerView         The engineer view.
     * @param schedulerProvider    The  inject of scheduler provider.
     * @see EngineersRepository
     * @see EngineersContract.View
     * @see BaseSchedulerProvider
     */
    EngineersPresenter(@NonNull EngineersRepository engineersRepository,
                        @NonNull EngineersContract.View engineerView,
                        @NonNull BaseSchedulerProvider schedulerProvider) {
        super(engineersRepository, schedulerProvider);
        onAttach(engineerView);
        this.engineersView = engineerView;
        this.schedulerProvider = getSchedulerProvider();
        this.engineersRepository = (EngineersRepository) getRepository();

        engineersView.setPresenter(this);

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
        EngineersRepository.destroyInstance();
    }

    /**
     * Load all engineers and filter them and update the UI.
     *
     * @param forceUpdate Pass in true to refresh the data in the {@link EngineersDataSource}.
     * @see EngineersRepository
     * @see Engineer
     * @see BaseSchedulerProvider
     */
    @Override
    public void loadEngineers(final boolean forceUpdate) {

        if(isStillLoading){
            return;
        }
        isStillLoading = true;

        // Show loading to the user.
        setLoading(true);
        // Use force update to clear the cache
        setCacheDirty(forceUpdate);

        // App is busy until further notice.
        espressoIncrement();

        // Clear all disposables.
        clearDisposables();

        //Create the Disposable.
        // show loading error to the user.
        Disposable disposable = engineersRepository
                // Get the data from the repository.
                .getEngineers()
                .flatMap(Flowable::fromIterable)
                .toList()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .doFinally(() -> {
                    espressoDecrement();
                    isStillLoading = false;
                    // hide loading to the user.
                    setLoading(false);

                })
                .subscribe(
                        // onNext
                        //valid engineers.
                        this::processEngineers,
                        // onError
                        // hide loading to the user.
                        this::handleError);
        // Add disposable
        addDisposable(disposable);
    }

    /**
     * Show loading or hide loading from the UI.
     *
     * @param active true to show or false to hide.
     * @see EngineersContract.View
     */
    private void setLoading(boolean active) {
        engineersView.setLoading(active);
    }


    /**
     * Force update data in the next call by making the cache dirty true.
     * @param cacheIsDirty true to force update.
     * @see EngineersRepository
     */
    private void setCacheDirty(boolean cacheIsDirty) {
        engineersRepository.setCacheDirty(cacheIsDirty);
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


    /**
     * Handel valid engineers.
     *
     * @param engineers list of the engineers.
     * @see Engineer
     * @see EngineersContract.View
     */
    private void processEngineers(@NonNull List<Engineer> engineers) {
        if (!engineers.isEmpty()) {
            ModulesHolder<Engineer> engineersHolder = new ModulesHolder<>();
            for (Engineer engineer : engineers) {
                if (!engineer.isEmpty()) engineersHolder.addItem(engineer);
            }
            // Show engineers
            if (!engineersHolder.isEmpty()) {
                engineersView.showEngineers(engineersHolder);
            } else {
                // Show a message indicating there are no engineers.
                processEmptyEngineers();
            }
        } else {
            // Show a message indicating there are no engineers.
            processEmptyEngineers();
        }

    }


    /**
     * Handel empty engineers.
     *
     * @see EngineersContract.View
     */
    private void processEmptyEngineers() {
        engineersView.showNoEngineers();
    }

}
