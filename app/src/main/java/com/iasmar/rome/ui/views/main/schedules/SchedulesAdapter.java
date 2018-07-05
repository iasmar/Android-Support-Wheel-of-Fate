package com.iasmar.rome.ui.views.main.schedules;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.iasmar.rome.R;
import com.iasmar.rome.data.modules.Schedule;
import com.iasmar.rome.data.modules.ModulesHolder;
import com.iasmar.rome.ui.views.custom.recyclerview.BaseAdapter;

import static com.iasmar.rome.util.ObjectHelper.requireNonNull;


/**
 * Created by Asmar on 01/7/2018.
 * <p>
 * Display a list  of {@link Schedule}s.
 *
 * @author Asmar
 * @version 1
 * @see BaseAdapter
 * @see SchedulesAdapterViewHolder
 * @see ModulesHolder
 * @see Schedule
 * @since 1.0
 */
class SchedulesAdapter extends BaseAdapter<SchedulesAdapterViewHolder, ModulesHolder<Schedule>> {


    /**
     * The constructor purpose is to
     * <p>
     * replace the current list of schedules to the new one.
     *
     * @param schedules the list of schedules.
     * @see ModulesHolder
     * @see Schedule
     * @see SchedulesContract.View
     */
    SchedulesAdapter(ModulesHolder<Schedule> schedules ) {
        replaceData(schedules);

    }

    /**
     * Called when RecyclerView needs a new {@link SchedulesAdapterViewHolder}.
     * an item.
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     * @return A new {@link SchedulesAdapterViewHolder} that holds a View.
     */
    @NonNull
    @Override
    public SchedulesAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        parent = requireNonNull(parent, "parent cannot be null");
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new SchedulesAdapterViewHolder(inflater.inflate(R.layout.row_schedule, parent, false));
    }

    /**
     * Called by RecyclerView to display the data at the specified position.
     *
     * @param holder   The @link SchedulesAdapterViewHolder} that holds a View, which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull SchedulesAdapterViewHolder holder, int position) {
        holder = requireNonNull(holder, "holder cannot be null");
        super.onBindViewHolder(holder, position);
         Schedule schedule = getData().getItemByIndex(holder.getAdapterPosition());
         schedule = requireNonNull(schedule, "schedule cannot be null");

        holder.setDay(schedule.getDay());
        holder.setDayShift(schedule.getShiftEngineers().get(0).getName());
        holder.setNightShift(schedule.getShiftEngineers().get(1).getName());

    }


}
/////////