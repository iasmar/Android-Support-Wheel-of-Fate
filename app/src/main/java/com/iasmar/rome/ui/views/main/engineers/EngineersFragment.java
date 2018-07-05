package com.iasmar.rome.ui.views.main.engineers;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.annotation.VisibleForTesting;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.iasmar.rome.Injection;
import com.iasmar.rome.R;
import com.iasmar.rome.data.modules.Engineer;
import com.iasmar.rome.data.modules.ModulesHolder;
import com.iasmar.rome.ui.views.base.BaseFragment;
import com.iasmar.rome.ui.views.base.IBasePresenter;
import com.iasmar.rome.ui.views.custom.recyclerview.RecyclerViewHelper;
import com.iasmar.rome.ui.views.main.MainActivity;
import com.iasmar.rome.ui.views.main.schedules.SchedulesFragment;
import com.iasmar.rome.util.ObjectHelper;
import com.iasmar.rome.util.ViewUtils;


import static com.iasmar.rome.util.ObjectHelper.requireNonNull;


/**
 * Created by Asmar on 01/7/2018.
 * <p>
 * Display a list  of {@link Engineer}s. User can choose to view all or active engineers.
 *
 * @author Asmar
 * @version 1
 * @see BaseFragment
 * @see ModulesHolder
 * @see Engineer
 * @see Injection
 * @see EngineersPresenter
 * @see EngineersContract.View
 * @see EngineersViewHolder
 * @since 1.0
 */
public class EngineersFragment extends BaseFragment implements EngineersContract.View, SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {


    //*********************************** Public static fields ***********************************//

    //Unique tag name for the fragment, to later retrieve the.
    public static final String FRAGMENT_TAG = "EngineersFragment";
    private static final String TAG = EngineersFragment.class.getSimpleName();

    /**
     * Get new instance of engineers fragment.
     *
     * @return new instance of engineers fragment.
     */
    public static EngineersFragment newInstance() {
        return new EngineersFragment();
    }

    //*********************************** private static fields ***********************************//
    //TODO refactoring
    public static final String forceUpdateKey = "forceUpdate";


    //*********************************** private fields ***********************************//
    // The context.
    private Context context;
    // The presenter.
    private EngineersContract.Presenter presenter;
    //    forceUpdate force update means get the remote server.
    private boolean forceUpdate = true;

    // The view holder.
    private EngineersViewHolder viewHolder;

    // engineers holder.
    private ModulesHolder<Engineer> engineersHolder = new ModulesHolder<>();

    //The recycler view helper.
    private RecyclerViewHelper<EngineersAdapterViewHolder, ModulesHolder<Engineer>> recyclerViewHelper;

    //*********************************** Constructors ***********************************//


    //*********************************** Override super methods ***********************************//

    /**
     * Save current View's state.
     *
     * @param outState A mapping from String keys to various {@link Parcelable} values.
     */
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(forceUpdateKey, false);
    }


    /**
     * Called when the fragment is visible to the user and actively running.
     * This is generally.
     */
    @Override
    public void onResume() {
        super.onResume();
        // Network reload will be forced on first load.
        presenter.loadEngineers(forceUpdate);

    }

    /**
     * Called when the Fragment is no longer resumed.
     */
    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.frg_engineers_generate_but:
                ((MainActivity) ObjectHelper.requireNonNull(getTheContext(),"context cannot be null")).proceedItem(SchedulesFragment.FRAGMENT_TAG);
                break;

        }
    }
    //*********************************** Override base methods ***********************************//


    /**
     * Called immediately after {@link #onViewCreated(View, Bundle)}.
     *
     * @param view               The current View.
     * @param savedInstanceState A mapping from String keys to various {@link Parcelable} values.
     */
    @Override
    protected void onViewReady(View view, @Nullable Bundle savedInstanceState) {
        super.onViewReady(view, savedInstanceState);
        if (savedInstanceState != null) {
            forceUpdate = savedInstanceState.getBoolean(forceUpdateKey);
        }

    }

    /**
     * The presenter won't update the view unless it's active.
     *
     * @return true if the fragment is currently added to its activity.
     */
    @VisibleForTesting
    @Override
    public boolean isActive() {
        return isAdded();
    }

    /**
     * Initialize the view after {@link #onViewCreated(View, Bundle)} being called.
     *
     * @param view The current view.
     */
    @Override
    protected void intiView(View view) {
        // Set up engineers view.
        setupEngineersView(view);
        // Set up loading view.
        setupLoadingView();
        // Set up recycler view.
        setupRecyclerView();
        // Set up generate the next shift button.
        setupGenerateBut();



    }


    /**
     * Get the current presenter.
     *
     * @return The current presenter.
     */
    @Override
    protected IBasePresenter getPresenter() {
        return new EngineersPresenter(
                Injection.provideEngineersRepository(getTheContext()),
                this,
                Injection.provideSchedulerProvider());
    }

    /**
     * Get the current content view res layout id.
     *
     * @return The current content view res layout id.
     */
    @Override
    protected int getContentView() {
        return R.layout.fragment_engineers;
    }


    //*********************************** presenter implementations ***********************************//


    /**
     * Called when the presenter is successfully created this method will be called.
     * Set the presenter.
     *
     * @param presenter the ProfilePresenter
     */
    @Override
    public void setPresenter(EngineersContract.Presenter presenter) {
        this.presenter = requireNonNull(presenter, "presenter cannot be null");
    }

    /**
     * Show loading or hide loading to the user.
     *
     * @param active enable or disable the loading view_indicator.
     */
    @Override
    public void setLoading(boolean active) {
        if(active){
            resetFragmentView();
            viewHolder.generateBut.setVisibility(View.GONE);
            viewHolder.viewMessageLl.setVisibility(View.GONE);
        }

        viewHolder.swipeRefreshPost(active);

    }

    /**
     * Called when there are some engineers found.
     *
     * @param engineers the list of engineers.
     * @see ModulesHolder
     * @see Engineer
     */
    @Override
    public void showEngineers(ModulesHolder<Engineer> engineers) {
        boolean animation = true;
        if (engineersHolder.getSize() > 0) {
            animation = false;
        }
        this.engineersHolder = engineers;
        recyclerViewHelper.replaceData(engineersHolder, animation);
        viewHolder.generateBut.setVisibility(View.VISIBLE);
        viewHolder.viewMessageLl.setVisibility(View.GONE);

    }

    /**
     * Called if there is no engineers.
     */
    @Override
    public void showNoEngineers() {
        resetFragmentView();
        viewHolder.viewMessageLl.setVisibility(View.VISIBLE);
        viewHolder.setMessage(R.string.no_data_found);
        ViewUtils.makeToast(getTheContext(),R.string.no_data_found, Toast.LENGTH_LONG);
        //TODO replace with micro debugger.
        Log.i(TAG, "showNoEngineers " + ViewUtils.getString(getTheContext(), R.string.no_data_found));

    }
    /**
     * reset the fragment view means reset fragment as new.
     */
    protected void resetFragmentView(){
        engineersHolder.clear();
        recyclerViewHelper.replaceData(engineersHolder, false);
        viewHolder.generateBut.setVisibility(View.GONE);
        viewHolder.viewMessageLl.setVisibility(View.GONE);

    }

    /**
     * If any kind of error appears while loading.
     *
     * @param error String res id.
     */
    @Override
    public void showError(@StringRes int error) {
        resetFragmentView();
        viewHolder.viewMessageLl.setVisibility(View.VISIBLE);
        viewHolder.setMessage(error);
        ViewUtils.makeToast(getTheContext(),error, Toast.LENGTH_LONG);
        viewHolder.generateBut.setVisibility(View.VISIBLE);

        //TODO replace with micro debugger.
        Log.e(TAG, "showError " + ViewUtils.getString(getTheContext(), error));
    }



    //*********************************** other implementations ***********************************//


    /**
     * Called when the user Swipe down.
     * Refresh all engineers with force update.
     *
     * @see SwipeRefreshLayout.OnRefreshListener
     **/
    @Override
    public void onRefresh() {
        presenter.loadEngineers(true);
    }


    //*********************************** Private methods ***********************************//
    /**
     * Get the context.
     *
     * @return context.
     */
    private Context getTheContext() {
        if (context == null) {
            context = getContext();
        }

        return context;
    }


    /**
     * Setup engineers view holder.
     *
     * @param view the current view.
     * @see EngineersViewHolder
     */
    private void setupEngineersView(View view) {
        // Set up engineers view.
        viewHolder = new EngineersViewHolder(view);
    }


    /**
     * Set up progress indicator.
     * Set the scrolling view in the {@link SwipeRefreshLayout}.
     * Set the refresh listener in the  SwipeRefreshLayout.
     **/
    private void setupLoadingView() {
        viewHolder.setupLoadingView();
        // Set the refresh listener in the custom SwipeRefreshLayout.
        viewHolder.swipeRefresh.setOnRefreshListener(this);
    }



    /**
     * Set up RecyclerView.
     **/
    private void setupRecyclerView() {
        EngineersAdapter engineersAdapter = new EngineersAdapter(engineersHolder);
        recyclerViewHelper = viewHolder.setupRecyclerView(engineersAdapter);
    }

    /**
     * Set up generate the next shift button.
     */
    private void setupGenerateBut() {
        viewHolder.generateBut.setOnClickListener(this);
    }



}
