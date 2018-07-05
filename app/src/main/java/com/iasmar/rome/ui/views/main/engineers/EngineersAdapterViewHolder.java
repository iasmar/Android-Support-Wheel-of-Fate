package com.iasmar.rome.ui.views.main.engineers;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.iasmar.rome.R;

import static com.iasmar.rome.util.ViewUtils.setText;

/**
 * Created by Asmar on 01/7/2018.
 * <p>
 * Initialize all the UI components to the {@link EngineersAdapter} and control the UI components.
 *
 * @author Asmar
 * @version 1
 * @see EngineersAdapter
 * @since 1.0
 */
class EngineersAdapterViewHolder extends RecyclerView.ViewHolder {

    //The  name of the engineers.
    private final TextView rowNameTxv;

    /**
     * The constructor purpose is to
     * <p>
     * Initialize UI components.
     *
     * @param view The item view in the recycle view.
     */
    EngineersAdapterViewHolder(View view) {
        super(view);

        rowNameTxv = view.findViewById(R.id.row_engineer_name_txv);
    }


    /**
     * Set the name of the engineers.
     *
     * @param name The name of the engineers.
     */
    public void setName(String name) {
        setText(rowNameTxv,name);

    }
}