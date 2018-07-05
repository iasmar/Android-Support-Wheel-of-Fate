package com.iasmar.rome.data.modules;

import android.support.annotation.NonNull;

import com.iasmar.rome.util.GeneralUtils;

import java.util.List;

import static com.iasmar.rome.util.GeneralUtils.isNullOrEmpty;


/**
 * Created by Asmar on 01/7/2018.
 * <p>
 * Immutable model class for a schedule.
 * <p>
 * The main purpose of this Module is to hold the engineer data.
 *
 * @author Asmar
 * @version 1
 * @see BaseModule
 * @since 0.1.0
 */
public  class Schedule implements BaseModule<String> {

    //The unique id of the schedule.
    private final String scheduleId;

    //The day of the schedule.
    private final int day;

    //Engineers in charge for the shifts in a single day.
    private final List<Engineer> shiftEngineers;


    /**
     * Use this constructor to create a new Schedule.
     *
     * @param scheduleId     The unique id of the schedule.
     * @param day            The day of the schedule.
     * @param shiftEngineers Engineers in charge for the shifts in a single day.
     */
    public Schedule(@NonNull String scheduleId, int day, @NonNull List<Engineer> shiftEngineers) {
        this.scheduleId = scheduleId;
        this.day = day;
        this.shiftEngineers = shiftEngineers;
    }

    /**
     * Get the unique id of the schedule.
     *
     * @return The unique id of the schedule.
     */
    @Override
    public String getId() {
        return scheduleId;
    }

    /**
     * Get the day of the schedule.
     *
     * @return The day of the schedule.
     */
    public int getDay() {
        return day;
    }


    /**
     * Engineers in charge for the shifts in a single day.
     *
     * @return Engineers in charge for the shifts in a single day.
     */
    public List<Engineer> getShiftEngineers() {
        return shiftEngineers;
    }


    /**
     * Get hash of the schedule.
     *
     * @return The hash of the schedule.
     */
    @Override
    public String getHash() {
        return GeneralUtils.getHash(toString());
    }

    /**
     * Is the passed object equals to this object.
     *
     * @param obj The reference object with which to compare.
     * @return {@code true} if this object is the same as the obj
     * argument {@code false} otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        String obj1Hash = ((BaseModule) obj).getHash();
        String obj2Hash = this.getHash();

        return obj1Hash != null && obj1Hash.equals(obj2Hash);

    }

    /**
     * Is this object empty (has no data).
     *
     * @return {@code true} if this object is empty
     * {@code false} otherwise.
     */
    @Override
    public boolean isEmpty() {
        return isNullOrEmpty(getId())
                && isNullOrEmpty(getShiftEngineers());
    }

    /**
     * Get The default sort by.
     *
     * @return The default sort by.
     */
    @Override
    public String getSortBy() {
        return getId();
    }

    /**
     * Get the string representation of the object.
     *
     * @return A string representation of the object.
     */
    @Override
    public String toString() {
        return "scheduleId: " + getId() +
                "day: " + getDay() +
                "ShiftEngineers: " + printShiftEngineers();
    }

    private String printShiftEngineers() {
        String result = null;
        for (Engineer engineer : shiftEngineers) {
            result = GeneralUtils.append(engineer.toString());
        }
        return result;
    }


}
