package com.iasmar.rome.ui.views.main.engineers;

import android.support.annotation.StringRes;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.iasmar.rome.R;
import com.iasmar.rome.data.modules.Engineer;
import com.iasmar.rome.data.modules.ModulesHolder;
import com.iasmar.rome.ui.views.custom.recyclerview.CustomSwipeRefreshLayout;
import com.iasmar.rome.ui.views.custom.recyclerview.RecyclerViewHelper;
import com.iasmar.rome.util.ViewUtils;

import static com.iasmar.rome.util.ObjectHelper.requireNonNull;


/**
 * Created by Asmar on 01/7/2018.
 * <p>
 * Initialize all the UI components to the {@link EngineersFragment} and control the UI components.
 *
 * @author Asmar
 * @version 1
 * @see EngineersFragment
 * @see CustomSwipeRefreshLayout * @since 1.0
 */
class EngineersViewHolder {
    // Swipe to refresh the data.
    final CustomSwipeRefreshLayout swipeRefresh;
    // The recycler view engineers.
    private final RecyclerView categRecv;

    // Generate next shift.
    final Button generateBut;

    // display a message
    final LinearLayout viewMessageLl;
    private final TextView messageTxv;

    /**
     * The constructor purpose is to initialize all the UI components.
     *
     * @param view the view that will be used to get the UI components.
     */
    EngineersViewHolder(View view) {
        view = requireNonNull(view, "view cannot be null");
        swipeRefresh = view.findViewById(R.id.frg_engineers_swipe_refresh);
        categRecv = view.findViewById(R.id.frg_engineers_recv);
        generateBut = view.findViewById(R.id.frg_engineers_generate_but);
        viewMessageLl = view.findViewById(R.id.frg_engineers_view_message_ll);
        messageTxv = view.findViewById(R.id.view_message_txv);

    }
    /**
     * Display a message to the user.
     * @param message Text to be displayed.
     */
    void setMessage(String message) {
        ViewUtils.setText(messageTxv, message);

    }

    /**
     * Display a message to the user.
     * @param message The resource identifier of the string resource to be displayed.
     */
    void setMessage(@StringRes int message) {
        ViewUtils.setText(messageTxv, message);

    }


    /**
     * Causes the Runnable to be added to the message queue, then set refreshing.
     *
     * @param refreshing Whether or not the view should show refresh progress.
     */
    void swipeRefreshPost(boolean refreshing) {
        swipeRefresh.post(() -> swipeRefresh.setRefreshing(refreshing));

    }

    /**
     * Set up progress indicator.
     * Set the scrolling view in the {@link SwipeRefreshLayout}.
     **/
    void setupLoadingView() {
        // Set up progress indicator
        swipeRefresh.setColorSchemeColors();
        // Set the scrolling view in the custom SwipeRefreshLayout.
        swipeRefresh.setScrollUpChild(categRecv);
    }


    /**
     * Set up RecyclerView.
     *
     * @param engineersAdapter The engineers adapter.
     * @return New recycler view helper.
     */
    RecyclerViewHelper<EngineersAdapterViewHolder, ModulesHolder<Engineer>> setupRecyclerView(EngineersAdapter engineersAdapter) {
        return new RecyclerViewHelper<>(categRecv, engineersAdapter, 1);
    }

}
