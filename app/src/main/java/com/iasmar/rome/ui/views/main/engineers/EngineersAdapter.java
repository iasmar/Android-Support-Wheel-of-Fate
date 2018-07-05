package com.iasmar.rome.ui.views.main.engineers;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.iasmar.rome.R;
import com.iasmar.rome.data.modules.Engineer;
import com.iasmar.rome.data.modules.ModulesHolder;
import com.iasmar.rome.ui.views.custom.recyclerview.BaseAdapter;

import static com.iasmar.rome.util.ObjectHelper.requireNonNull;


/**
 * Created by Asmar on 01/7/2018.
 * <p>
 * Display a list  of {@link Engineer}s.
 *
 * @author Asmar
 * @version 1
 * @see BaseAdapter
 * @see EngineersAdapterViewHolder
 * @see ModulesHolder
 * @see Engineer
 * @since 1.0
 */
class EngineersAdapter extends BaseAdapter<EngineersAdapterViewHolder, ModulesHolder<Engineer>> {

    /**
     * The constructor purpose is to
     * <p>
     * replace the current list of engineers to the new one.
     *
     * @param engineers the list of engineers.
     * @see ModulesHolder
     * @see Engineer
     * @see EngineersContract.View
     */
    EngineersAdapter(ModulesHolder<Engineer> engineers) {
        replaceData(engineers);

    }

    /**
     * Called when RecyclerView needs a new {@link EngineersAdapterViewHolder}.
     * an item.
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     * @return A new {@link EngineersAdapterViewHolder} that holds a View.
     */
    @Override
    public EngineersAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        parent = requireNonNull(parent, "parent cannot be null");
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new EngineersAdapterViewHolder(inflater.inflate(R.layout.row_engineer, parent, false));
    }

    /**
     * Called by RecyclerView to display the data at the specified position.
     *
     * @param holder   The {@link EngineersAdapterViewHolder} that holds a View, which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull EngineersAdapterViewHolder holder, int position) {
        holder = requireNonNull(holder, "holder cannot be null");
        super.onBindViewHolder(holder, position);
        final Engineer engineer = getData().getItemByIndex(holder.getAdapterPosition());

        holder.setName(engineer.getName());
    }


}
