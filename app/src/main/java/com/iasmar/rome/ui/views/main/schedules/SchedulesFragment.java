package com.iasmar.rome.ui.views.main.schedules;

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
import com.iasmar.rome.data.modules.Schedule;
import com.iasmar.rome.data.modules.ModulesHolder;
import com.iasmar.rome.ui.views.base.BaseFragment;
import com.iasmar.rome.ui.views.base.IBasePresenter;
import com.iasmar.rome.ui.views.custom.recyclerview.RecyclerViewHelper;
import com.iasmar.rome.ui.views.main.MainActivity;
import com.iasmar.rome.util.ObjectHelper;
import com.iasmar.rome.util.ViewUtils;

import static com.iasmar.rome.util.ObjectHelper.requireNonNull;


/**
 * Created by Asmar on 01/7/2018.
 * <p>
 * Display a list  of {@link Schedule}s. User can choose to view all or active schedules.
 *
 * @author Asmar
 * @version 1
 * @see BaseFragment
 * @see ModulesHolder
 * @see Schedule
 * @see Injection
 * @see SchedulesPresenter
 * @see SchedulesContract.View
 * @see SchedulesViewHolder
 * @since 1.0
 */
public class SchedulesFragment extends BaseFragment implements SchedulesContract.View, SwipeRefreshLayout.OnRefreshListener {


    //*********************************** Public static fields ***********************************//

    //Unique tag name for the fragment, to later retrieve the.
    public static final String FRAGMENT_TAG = "SchedulesFragment";
    private static final String TAG = SchedulesFragment.class.getSimpleName();

    /**
     * Get new instance of schedules fragment.
     *
     * @return new instance of schedules fragment.
     */
    public static SchedulesFragment newInstance() {
        return new SchedulesFragment();
    }

    //*********************************** private static fields ***********************************//
    //TODO refactoring
    public static final String forceUpdateKey = "forceUpdate";


    //*********************************** private fields ***********************************//
    // The context.
    private Context context;
    // The presenter.
    private SchedulesContract.Presenter presenter;
    //    forceUpdate force update means get the remote server.
    private boolean forceUpdate = true;

    // The view holder.
    private SchedulesViewHolder viewHolder;

    // schedules holder.
    private ModulesHolder<Schedule> schedulesHolder = new ModulesHolder<>();

    //The recycler view helper.
    private RecyclerViewHelper<SchedulesAdapterViewHolder, ModulesHolder<Schedule>> recyclerViewHelper;

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
        presenter.loadSchedules(forceUpdate);

    }

    /**
     * Called when the Fragment is no longer resumed.
     */
    @Override
    public void onPause() {
        super.onPause();
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
        // Set up schedules view.
        setupSchedulesView(view);
        // Set up loading view.
        setupLoadingView();
        // Set up recycler view.
        setupRecyclerView();


    }

    /**
     * Get the current presenter.
     *
     * @return The current presenter.
     */
    @Override
    protected IBasePresenter getPresenter() {
        return new SchedulesPresenter(
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
        return R.layout.fragment_schedules;
    }


    //*********************************** presenter implementations ***********************************//


    /**
     * Called when the presenter is successfully created this method will be called.
     * Set the presenter.
     *
     * @param presenter the ProfilePresenter
     */
    @Override
    public void setPresenter(SchedulesContract.Presenter presenter) {
        this.presenter = requireNonNull(presenter, "presenter cannot be null");
    }

    /**
     * Show loading or hide loading to the user.
     *
     * @param active enable or disable the loading view_indicator.
     */
    @Override
    public void setLoading(boolean active) {
        if (active) {
            resetFragmentView();
            viewHolder.headerLl.setVisibility(View.GONE);
            viewHolder.viewMessageLl.setVisibility(View.GONE);

        }
        viewHolder.swipeRefreshPost(active);

    }


    /**
     * Called when there are some schedules found.
     *
     * @param schedules the list of schedules.
     * @see ModulesHolder
     * @see Schedule
     */
    @Override
    public void showSchedules(ModulesHolder<Schedule> schedules) {
        boolean animation = true;
        if (schedulesHolder.getSize() > 0) {
            animation = false;
        }
        this.schedulesHolder = schedules;
        recyclerViewHelper.replaceData(schedulesHolder, animation);
        viewHolder.headerLl.setVisibility(View.VISIBLE);
        viewHolder.viewMessageLl.setVisibility(View.GONE);

    }

    /**
     * Called if there is no schedules.
     */
    @Override
    public void showNoSchedules() {
        resetFragmentView();
        viewHolder.viewMessageLl.setVisibility(View.VISIBLE);
        viewHolder.setMessage(R.string.no_data_found);
        //TODO replace with micro debugger.
        Log.i(TAG, "showNoSchedules " + ViewUtils.getString(getTheContext(), R.string.no_data_found));


    }

    /**
     * reset the fragment view means reset fragment as new.
     */
    protected void resetFragmentView() {
        schedulesHolder.clear();
        recyclerViewHelper.replaceData(schedulesHolder, false);
        viewHolder.headerLl.setVisibility(View.GONE);
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
        viewHolder.headerLl.setVisibility(View.GONE);
        viewHolder.viewMessageLl.setVisibility(View.VISIBLE);
        viewHolder.setMessage(error);
        ViewUtils.makeToast(getTheContext(),error, Toast.LENGTH_LONG);

        //TODO replace with micro debugger.
        Log.e(TAG, "showError " + ViewUtils.getString(getTheContext(), error));
    }


    //*********************************** other implementations ***********************************//


    /**
     * Called when the user Swipe down.
     * Refresh all schedules with force update.
     *
     * @see SwipeRefreshLayout.OnRefreshListener
     **/
    @Override
    public void onRefresh() {
        presenter.loadSchedules(true);
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
     * Setup schedules view holder.
     *
     * @param view the current view.
     * @see SchedulesViewHolder
     */
    private void setupSchedulesView(View view) {
        // Set up schedules view.
        viewHolder = new SchedulesViewHolder(view);
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
        SchedulesAdapter schedulesAdapter = new SchedulesAdapter(schedulesHolder);
        recyclerViewHelper = viewHolder.setupRecyclerView(schedulesAdapter);
    }


}
