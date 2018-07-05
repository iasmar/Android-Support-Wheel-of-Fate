package com.iasmar.rome.services;



import com.iasmar.rome.data.modules.Engineer;
import com.iasmar.rome.data.modules.Schedule;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;


import static org.junit.Assert.*;
@RunWith(Parameterized.class)
public class ScheduleServiceTestRepeat {

    @Parameterized.Parameters
    public static Object[][] data() {
        return new Object[100][0];
    }

    public ScheduleServiceTestRepeat() {
    }



    @Test
    public void verifyGetSchedules_rule1() {
        List<Engineer> engineers = ScheduleServiceTest.provideListOfEngineers();
        ScheduleService scheduleService = ScheduleServiceTest.newInstantOFScheduleService(engineers);
        List<Schedule> schedules = scheduleService.getSchedules();
        assertTrue(schedules.size() > 0);
        System.out.println("run");

    }

}