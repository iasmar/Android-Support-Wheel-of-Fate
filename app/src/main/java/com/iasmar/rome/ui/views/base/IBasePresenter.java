package com.iasmar.rome.ui.views.base;


/**
 * Created by Asmar on 01/7/2018.
 * <p>
 * Every presenter in the app must either implement this interface or extend BasePresenter
 * indicating the MvpView type that wants to be attached with.
 *
 * @param <V> The view.
 * @author Asmar
 * @version 1
 * @since 1.0
 */
public interface IBasePresenter<V extends IBaseView> {

    // This  should be called on onResume().
    void subscribe();

    // This method should be called on onPause().
    void unsubscribe();

    /**
     * Called from the constructor.
     *
     * @param view The view.
     */
    void onAttach(V view);

    //  Called when the fragment is no longer attached to its activity.  This is called after onDestroy().
    void onDetach();

    /**
     * Show loading error to the user.
     *
     * @param error the throwable error.
     */
    void handleError(Throwable error);

}
