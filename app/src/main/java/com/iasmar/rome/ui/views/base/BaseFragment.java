package com.iasmar.rome.ui.views.base;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.iasmar.rome.util.ObjectHelper;

import java.util.Objects;

/**
 * Created by Asmar on 01/7/2018.
 * <p>
 * Base class for all the other fragments.
 *
 * @author Asmar
 * @version 1
 * @since 1.0
 */
public abstract class BaseFragment extends Fragment {

    // The base presenter interface
    private IBasePresenter presenter;

    /**
     * Called to do initial creation of a fragment.
     *
     * @param savedInstanceState If the fragment is being re-created from
     *                           a previous saved state, this is the state.
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        // Create the presenter
        presenter = getPresenter();

    }


    /**
     * Called to have the fragment instantiate its user interface view.
     *
     * @param inflater           The LayoutInflater object that can be used to inflate
     *                           any views in the fragment,
     * @param container          If non-null, this is the parent view that the fragment's
     *                           UI should be attached to.  The fragment should not add the view itself,
     *                           but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     *                           from a previous saved state as given here.
     * @return the View for the fragment's UI,
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(getContentView(), container, false);
    }


    /**
     * Called immediately after {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}.
     *
     * @param view               The View returned by {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}.
     * @param savedInstanceState If non-null, this fragment is being re-constructed.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        intiView(view);

        onViewReady(view, savedInstanceState);

    }

    /**
     * Called when the fragment is visible to the user and actively running.
     * This is generally.
     * Subscribe to the presenter.
     */
    @Override
    public void onResume() {
        super.onResume();
        if (presenter != null) {
            this.presenter.subscribe();

        }
    }

    /**
     * Called when the Fragment is no longer resumed.
     * Unsubscribe to the presenter.
     */
    @Override
    public void onPause() {
        super.onPause();
        if (presenter != null) {
            this.presenter.unsubscribe();
        }
    }


    /**
     * Get the attempt presenter.
     * To be used by child fragments.
     *
     * @return The presenter
     */
    protected abstract IBasePresenter getPresenter();

    /**
     * Get the attempt content view.
     * To be used by child fragments.
     *
     * @return layoutResID Resource ID to be inflated.
     */
    protected abstract int getContentView();



    /**
     * This method called after the {@link Fragment#onViewCreated(View, Bundle)}.
     * To be used by child fragments.
     *
     * @param view               The View returned by {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}.
     * @param savedInstanceState A mapping from String keys to various {@link Parcelable} values.
     */
    protected void onViewReady(View view, @Nullable Bundle savedInstanceState) {
        // To bw used by child fragments.
    }

    /**
     * This method called after the {@link Fragment#onViewCreated(View, Bundle)}.
     * To be used by child fragments to initialize the necessary view.
     *
     * @param view The View returned by {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}.
     */
    protected abstract void intiView(View view);

    /**
     * reset the fragment view means reset fragment as new.
     */
    protected abstract void resetFragmentView();


}
