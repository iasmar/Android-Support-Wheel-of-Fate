package com.iasmar.rome.ui.views.base;


import android.support.annotation.StringRes;
import android.support.annotation.VisibleForTesting;

/**
 * Created by Asmar on 01/7/2018.
 * <p>
 * Every view in the app must either implement this interface.
 *
 * @param <T> The presenter.
 * @author Asmar
 * @version 1
 * @since 1.0
 */
public interface IBaseView<T> {


    /**
     * This  should be called on the constructor in the presenter class.
     *
     * @param presenter The presenter.
     */
    void setPresenter(T presenter);

    /**
     * Set loading on the UI.
     *
     * @param active true to show the loading false to hide.
     */
    void setLoading(boolean active);

    /**
     * Display error on the UI.
     *
     * @param error String Res id (error message)
     */
    void showError(@StringRes int error);


    /**
     * is the fragment active.
     *
     * @return true if the fragment is currently active.
     */
    @VisibleForTesting
    boolean isActive();
}
