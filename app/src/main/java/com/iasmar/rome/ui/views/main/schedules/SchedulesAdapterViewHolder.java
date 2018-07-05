package com.iasmar.rome.ui.views.main.schedules;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import static com.iasmar.rome.util.ViewUtils.getString;
import static com.iasmar.rome.util.ViewUtils.setText;

import com.iasmar.rome.R;

/**
 * Created by Asmar on 01/7/2018.
 * <p>
 * Initialize all the UI components to the {@link SchedulesAdapter} and control the UI components.
 *
 * @author Asmar
 * @version 1
 * @see SchedulesAdapter
 * @since 1.0
 */
class SchedulesAdapterViewHolder extends RecyclerView.ViewHolder {



   // The day of the schedule.
    private final TextView dayTxv;

    //Engineer in charge for the day shift.
    private final TextView dayShiftTxv;

    //Engineer in charge for the night shift.
    private final TextView nightShiftTxv;

    /**
     * The constructor purpose is to
     * <p>
     * Initialize UI components.
     *
     * @param view The item view in the recycle view.
     */
    SchedulesAdapterViewHolder(View view) {
        super(view);

         dayTxv = view.findViewById(R.id.row_schedule_day_txv);
         dayShiftTxv = view.findViewById(R.id.row_schedule_day_shift_txv);
         nightShiftTxv = view.findViewById(R.id.row_schedule_night_shift_txv);
    }

    /**
     * Set the day of the schedule.
     *
     * @param day The day of the schedule.
     */
    public void setDay(int day) {
        setText(dayTxv,getString(dayTxv.getContext(),R.string.day)+" "+(day+1));


    }
    /**
     * Set the Engineer in charge for the day shift.
     *
     * @param dayShift The Engineer in charge for the day shift.
     */
    public void setDayShift(String dayShift) {
        setText(dayShiftTxv,dayShift);

    }
    /**
     * Set the Engineer in charge for the night shift.
     *
     * @param nightShift The Engineer in charge for the night shift.
     */
    public void setNightShift(String nightShift) {
        setText(nightShiftTxv,nightShift);

    }
}