package com.iasmar.rome.services;



import com.iasmar.rome.configuration.Constant;
import com.iasmar.rome.data.modules.Engineer;
import com.iasmar.rome.data.modules.Schedule;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


import static com.iasmar.rome.configuration.Constant.ENGINEER_OFF_DAYS;
import static com.iasmar.rome.services.ScheduleServiceTest.newInstantOFScheduleService;
import static com.iasmar.rome.services.ScheduleServiceTest.provideListOfEngineers;
import static org.junit.Assert.*;
@RunWith(Parameterized.class)
public class ScheduleServiceTestRepeat {

    @Parameterized.Parameters
    public static Object[][] data() {
        return new Object[1000][0];
    }

    public ScheduleServiceTestRepeat() {
    }


    @Test
    public void verifyGetSchedules_valid() {
        List<Engineer> engineers = provideListOfEngineers();
        ScheduleService scheduleService = newInstantOFScheduleService(engineers);
        List<Schedule> schedules = scheduleService.getSchedules();
        assertTrue(schedules.size() > 0);
    }

    @Test
    public void verifyGetSchedules_valid_maxShiftPerEng() {
        List<Engineer> engineers = provideListOfEngineers();
        ScheduleService scheduleService = newInstantOFScheduleService(engineers);
        List<Schedule> schedules = scheduleService.getSchedules();
        List<Engineer> allShiftEngineers = new ArrayList<>();

        for (Schedule schedule : schedules) {
            List<Engineer> shiftEngineers = schedule.getShiftEngineers();
            allShiftEngineers.addAll(shiftEngineers);
        }

        for (Engineer engineer : allShiftEngineers) {

            int frequency = Collections.frequency(allShiftEngineers, engineer);
            if (frequency != Constant.MAX_SHIFTS_PER_ENGINEER) {
                fail();
            }
        }
    }

    @Test
    public void verifyGetSchedules_valid_engOneShiftPerDay() {
        List<Engineer> engineers = provideListOfEngineers();
        ScheduleService scheduleService = newInstantOFScheduleService(engineers);
        List<Schedule> schedules = scheduleService.getSchedules();

        for (Schedule schedule : schedules) {
            List<Engineer> shiftEngineers = schedule.getShiftEngineers();
            for (Engineer engineer : shiftEngineers) {

                int frequency = Collections.frequency(shiftEngineers, engineer);
                if (frequency > 1) {
                    fail();
                }
            }
        }
    }


    @Test
    public void verifyGetSchedules_valid_restingDay() {
        List<Engineer> engineers = provideListOfEngineers();
        ScheduleService scheduleService = newInstantOFScheduleService(engineers);
        List<Schedule> schedules = scheduleService.getSchedules();

        for (int i = 0; i < schedules.size(); i++) {
            List<Engineer> shiftEngineers = schedules.get(i).getShiftEngineers();
            for (Engineer engineer : shiftEngineers) {
                for (int j = 1; j <= ENGINEER_OFF_DAYS; j++) {
                    if (i + j < schedules.size()) {
                        Schedule nextShift = schedules.get(i + j);
                        List<Engineer> nextShiftEngineers = nextShift.getShiftEngineers();

                        for (Engineer nextEngineer : nextShiftEngineers) {
                            if (engineer.getId().equals(nextEngineer.getId())) {
                                fail();
                            }
                        }
                    }


                }


            }
        }
    }


}