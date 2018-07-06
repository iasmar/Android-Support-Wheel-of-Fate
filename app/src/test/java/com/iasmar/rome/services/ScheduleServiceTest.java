package com.iasmar.rome.services;


import com.iasmar.rome.configuration.Constant;
import com.iasmar.rome.data.modules.Engineer;
import com.iasmar.rome.data.modules.Schedule;
import com.iasmar.rome.exception.ScheduleException;
import com.iasmar.rome.util.GeneralUtils;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.iasmar.rome.configuration.Constant.ENGINEER_OFF_DAYS;
import static com.iasmar.rome.configuration.Constant.MAX_SHIFTS_PER_DAY;
import static com.iasmar.rome.configuration.Constant.SCHEDULE_PERIOD_DAYS;
import static org.junit.Assert.*;

public class ScheduleServiceTest {


    @Mock
    private ScheduleService mockScheduleService;


    /**
     * Called before each test start.
     * setup categories repository(.
     */
    @Before
    public void setupCategoriesRepository() {
        // Mockito has a very convenient way to inject mocks by using the @Mock annotation. To
        // inject the mocks in the test the initMocks method needs to be called.
        MockitoAnnotations.initMocks(this);
    }



    @Test
    public void verifyInstantWith_null() {
        newInstantOFScheduleService(null);
        ScheduleService scheduleService = newInstantOFScheduleService(null);
        List<Schedule> schedules = scheduleService.getSchedules();
        assertNull(schedules);
    }

    @Test
    public void verifyInstantWith_emptyList() {
        newInstantOFScheduleService(null);
        ScheduleService scheduleService = newInstantOFScheduleService(provideEmptyListOfEngineers());
        List<Schedule> schedules = scheduleService.getSchedules();
        assertNull(schedules);
    }


    @Test
    public void verifyGenerateShifts_valid() {
        List<Engineer> engineers = provideListOfEngineers();
        ScheduleService scheduleService = newInstantOFScheduleService(engineers);
        Schedule schedule = scheduleService.generateShifts(0);
        assertNotNull(schedule);
    }

    @Test(expected = ScheduleException.class)
    public void verifyGenerateShifts_null() {
        ScheduleService scheduleService = newInstantOFScheduleService(null);
        scheduleService.generateShifts(0);
    }

    @Test(expected = ScheduleException.class)
    public void verifyGenerateShifts_empty() {
        ScheduleService scheduleService = newInstantOFScheduleService(provideEmptyListOfEngineers());
        scheduleService.generateShifts(0);
    }

    //TODO more testing engineers resting functionality.

    @Test
    public void verifyEngineersResting() {
        List<Engineer> engineers = provideListOfEngineers();
        ScheduleService scheduleService = newInstantOFScheduleService(engineers);
        scheduleService.getSchedules();
        assertTrue(scheduleService.engineerResting.size() == MAX_SHIFTS_PER_DAY * ENGINEER_OFF_DAYS);
    }

    @Test
    public void verifyEngineerCompletedMaxShifts() {
        List<Engineer> engineers = provideListOfEngineers();
        ScheduleService scheduleService = newInstantOFScheduleService(engineers);
        scheduleService.getSchedules();
        assertTrue(scheduleService.removedEngineers.size() == scheduleService.copyOfEngineers.size());
    }


    @Test
    public void verifyGetRandomEngineer_valid() {
        List<Engineer> engineers = provideListOfEngineers();
        ScheduleService scheduleService = newInstantOFScheduleService(engineers);
        Engineer engineer = scheduleService.getRandomEngineer();
        assertNotNull(engineer);
    }


    @Test
    public void verifyValidEngineer_null() {
        Engineer validateEngineer = mockScheduleService.getRandomEngineer();
        assertNull(validateEngineer);
    }

    @Test(expected = ScheduleException.class)
    public void verifyGetRandomEngineer_emptyEngineers() {
        List<Engineer> engineers = provideEmptyListOfEngineers();
        ScheduleService scheduleService = newInstantOFScheduleService(engineers);
        scheduleService.getRandomEngineer();
    }

    @Test(expected = ScheduleException.class)
    public void verifyGetRandomEngineer_null() {
        ScheduleService scheduleService = newInstantOFScheduleService(null);
        scheduleService.getRandomEngineer();
    }


    static ScheduleService newInstantOFScheduleService(List<Engineer> engineers) {
        return new ScheduleService(engineers);

    }

    public static List<Engineer> provideListOfEngineers() {
        int id;
        Map<String, Engineer> engineers = new LinkedHashMap<>();
        for (int i = 0; i < SCHEDULE_PERIOD_DAYS; i++) {
            do {
                id = GeneralUtils.getRandomNumber(2147483645);

            } while (engineers.containsKey("" + id));

            engineers.put("" + id, generateEngineer("" + id, "" + id, null));

        }
        return new ArrayList<>(engineers.values());
    }
//    static List<Engineer> provideListOfEngineers() {
//
//        List<Engineer> engineers = new ArrayList<>();
//        Engineer engineer = generateEngineer("0", "Bogdan", null);
//        engineers.add(engineer);
//        engineer = generateEngineer("1", "Nic", null);
//        engineers.add(engineer);
//        engineer = generateEngineer("2", "Tung", null);
//        engineers.add(engineer);
//        engineer = generateEngineer("3", "Gautam", null);
//        engineers.add(engineer);
//        engineer = generateEngineer("4", "Bala", null);
//        engineers.add(engineer);
//        engineer = generateEngineer("5", "Nazih", null);
//        engineers.add(engineer);
//        engineer = generateEngineer("6", "Huteri", null);
//        engineers.add(engineer);
//        engineer = generateEngineer("7", "Aldy", null);
//        engineers.add(engineer);
//        engineer = generateEngineer("8", "Ankur", null);
//        engineers.add(engineer);
//        engineer = generateEngineer("9", "Chinh", null);
//        engineers.add(engineer);
//
//        return engineers;
//    }


    private List<Engineer> provideEmptyListOfEngineers() {
        return new ArrayList<>();
    }


    private static Engineer generateEngineer(String id, String name, String profilePic) {
        return new Engineer(id, name, profilePic);
    }

}