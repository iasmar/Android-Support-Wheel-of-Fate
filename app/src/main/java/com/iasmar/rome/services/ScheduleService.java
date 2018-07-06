package com.iasmar.rome.services;

import android.support.annotation.VisibleForTesting;

import com.iasmar.rome.data.modules.Engineer;
import com.iasmar.rome.data.modules.Schedule;
import com.iasmar.rome.exception.ScheduleException;
import com.iasmar.rome.util.GeneralUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import static com.iasmar.rome.configuration.Constant.MAX_SHIFTS_PER_ENGINEER;
import static com.iasmar.rome.configuration.Constant.MAX_SHIFTS_PER_DAY;
import static com.iasmar.rome.configuration.Constant.SCHEDULE_PERIOD_DAYS;
import static com.iasmar.rome.configuration.Constant.ENGINEER_OFF_DAYS;
import static com.iasmar.rome.exception.ScheduleException.SCHEDULE_GENERAL_EXCEPTION;
import static com.iasmar.rome.exception.ScheduleException.SCHEDULE_INVALID_EXCEPTION;

/**
 * Created by Asmar on 01/7/2018.
 * <p>
 * Schedule service where the business logic for creating schedule for engineers.
 *
 * @author Asmar
 * @version 1
 * @see Engineer
 * @see Schedule
 * @see ScheduleException
 * @since 0.1.0
 */
public class ScheduleService {


    // The list of engineers.
    private List<Engineer> engineers;

    // A copy of engineers list.
    @VisibleForTesting
    HashMap<String, Engineer> copyOfEngineers = new LinkedHashMap<>();

    // engineers with how many shifts they have
    HashMap<Integer, List<Engineer>> shifts = new LinkedHashMap<>();

    // Track of total shifts for each engineer.
    private HashMap<String, Integer> engineerTotalShifts = new LinkedHashMap<>();

    // Rule 2: An engineer can do at most one shift in a day.
    // MAX_SHIFTS_PER_DAY
    // Rule 3: An engineer cannot have more than one shift on any consecutive days
    // ENGINEER_OFF_DAYS
    @VisibleForTesting
    List<Engineer> engineerResting = new ArrayList<>();

    // Rule 4: Each engineer should have completed 2 shifts of support in any 2 week period.
    // MAX_SHIFTS_PER_ENGINEER

    // Invalid engineers who are not eligible to inter the pool.
    @VisibleForTesting
    List<String> removedEngineers = new ArrayList<>();
    private int takenShits;


    /**
     * Initialization of the list of engineers and make a copy of that list.
     *
     * @param engineers The list of engineers.
     */
    public ScheduleService(List<Engineer> engineers) {
        if (engineers != null) {
            this.engineers = engineers;
            for (Engineer engineer : engineers) {
                copyOfEngineers.put(engineer.getId(), engineer);
                engineerTotalShifts.put(engineer.getId(), 0);
            }
            shifts.put(0, new ArrayList(engineers));

        }


    }


    /**
     * Get a list of schedules.
     *
     * @return a list of schedules.
     * @throws ScheduleException if there is an engineer did not take MAX_SHIFTS_PER_ENGINEER.
     */
    public List<Schedule> getSchedules() {
        if (engineers == null || engineers.size() == 0) {
            return null;
        }
        List<Schedule> schedules = new ArrayList<>();

        // loop through all days to create shifts for each day.
        for (int i = 0; i < SCHEDULE_PERIOD_DAYS; i++) {
            // create shifts for the current day.
            Schedule schedule = generateShifts(i);
            // add the  generated schedule into schedules.
            schedules.add(schedule);

        }


        // for rule 4 making sure that no engineer left with less than MAX_SHIFTS_PER_ENGINEER shifts.
        if (removedEngineers.size() != copyOfEngineers.size()) {
            throw new ScheduleException(SCHEDULE_GENERAL_EXCEPTION);
        }
        return schedules;
    }

    /**
     * Generate shifts for the current day
     * Apply rule 1: There are only two support shifts per day, a day shift and a night shift.
     * Apply rule 2: Each engineer should have completed shifts in the given period.
     *
     * @param currentDay the current day.
     * @return shifts for the current day.
     */
    @VisibleForTesting
    Schedule generateShifts(int currentDay) {
        List<Engineer> shiftEngineers = new ArrayList<>();

        // Rule 1: There are only two support shifts per day, a day shift and a night shift.
        for (int j = 0; j < MAX_SHIFTS_PER_DAY; j++) {

            Engineer pickedEngineer = getRandomEngineer();
            int engTotalShifts = engineerTotalShifts.get(pickedEngineer.getId());
            List<Engineer> engineersWithShifts = shifts.get(engTotalShifts);

            if (engineersWithShifts != null) {

                engineersWithShifts.remove(pickedEngineer);
                shifts.put(engTotalShifts, engineersWithShifts);
            }

            int engNewTotalShifts = engTotalShifts + 1;
            engineerTotalShifts.put(pickedEngineer.getId(), engNewTotalShifts);

             engineersWithShifts = shifts.get(engNewTotalShifts);
            if (engineersWithShifts == null) {
                engineersWithShifts = new ArrayList<>();
            }

            engineersWithShifts.add(pickedEngineer);
            shifts.put(engNewTotalShifts, engineersWithShifts);


            shiftEngineers.add(pickedEngineer);
            takenShits++;

            // remove engineer so that the engineer will not enter the pool.
            this.engineers.remove(pickedEngineer);
            //  Rule 2: no more that one shift per day
            //  Rule 3: engineer off days.
            engineerResting.add(pickedEngineer);


            // Rule 4: Each engineer should have completed shifts in the given period.
            engineerCompletedMaxShifts(pickedEngineer);


            // Rule 2: new day so add back engineers who had been removed from the pool to avoid one engineer having more than one shift.
            // Rule 3: new day add back engineers and the off days.
            engineersResting(currentDay, j);
        }


        return new Schedule(GeneralUtils.toString(currentDay), currentDay, shiftEngineers);
    }

    /**
     * Add back the removed engineers after the off days.
     * Rule 3: An engineer cannot have more than one shift on any ENGINEER_OFF_DAYS days.
     * Rule 2: new day so add back engineers who had been removed from the pool to avoid one engineer having more than one shift.
     *
     * @param currentDay   the current day.
     * @param currentShift the current shift in the current day.
     */
    private void engineersResting(int currentDay, int currentShift) {

        if ((currentDay > ENGINEER_OFF_DAYS && currentShift == MAX_SHIFTS_PER_DAY - 1) || engineerResting.size() == MAX_SHIFTS_PER_DAY * (ENGINEER_OFF_DAYS + 1)) {
            for (int j = 0; j < MAX_SHIFTS_PER_DAY; j++) {
                if (engineerResting.size() > 0) {
                    Engineer engineer = engineerResting.get(0);
                    if (!engineers.contains(engineer) && !removedEngineers.contains(engineer.getId())) {
                        engineers.add(engineer);

                    }

                    engineerResting.remove(0);
                }
            }

        }
    }


    /**
     * Rule 4: Each engineer should have completed MAX_SHIFTS_PER_ENGINEER shifts of support in any SCHEDULE_PERIOD_DAYS week period.
     *
     * @param engineer the given engineer.
     */
    private void engineerCompletedMaxShifts(Engineer engineer) {
        int engTotalShifts = engineerTotalShifts.get(engineer.getId());
        // Rule: Each engineer should have completed shifts in the given period.
        if (engTotalShifts == MAX_SHIFTS_PER_ENGINEER) {
            removedEngineers.add(engineer.getId());
        }
    }

    /**
     * Get a random engineer.
     *
     * @return A random engineer.
     * @throws ScheduleException if engineers size is less that 1.
     */
    @VisibleForTesting
    Engineer getRandomEngineer() {
        if (engineers != null) {
            if (engineers.size() > 0) {

                List<Engineer> possibleCandidates = new ArrayList<>();
                int totalShifts = SCHEDULE_PERIOD_DAYS * MAX_SHIFTS_PER_DAY;

                // Making sure that after takenShits >= totalShifts / MAX_SHIFTS_PER_DAY, find each engineer
                // who dose not have any shift assigned already and then randomly select one.
                if (takenShits >= totalShifts / MAX_SHIFTS_PER_DAY * ENGINEER_OFF_DAYS) {
                    // find each engineer who dose not have 1 to MAX_SHIFTS_PER_DAY shifts assigned already and then randomly select one.
                    for (int i = 0; i < MAX_SHIFTS_PER_DAY; i++) {
                        if (possibleCandidates.size() == 0) {
                            List<Engineer> engineers = shifts.get(i);
                            if (engineers != null && engineers.size() > 0) {
                                possibleCandidates.addAll(engineers);
                            }
                            for (Engineer engineer : this.engineerResting) {
                                possibleCandidates.remove(engineer);

                            }
                        }

                    }

                }
                // if there are no engineers have priority, add all the engineers to the pool.
                if (possibleCandidates.size() == 0) {
                    possibleCandidates.addAll(engineers);
                }

                // randomly get an engineer.
                int randomIndex = GeneralUtils.getRandomNumber(possibleCandidates.size());
                return possibleCandidates.get(randomIndex);
            }
        }
        throw new ScheduleException(SCHEDULE_INVALID_EXCEPTION);
    }


}