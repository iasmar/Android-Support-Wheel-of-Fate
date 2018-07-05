package com.iasmar.rome.exception;

/**
 * Created by Asmar on 01/7/2018.
 * <p>
 * Custom ScheduleException class that will thrown if something went wrong while crating the schedule.
 *
 * @author Asmar
 * @version 1
 * @since 0.1.0
 */
public class ScheduleException extends RuntimeException {

    // General exception
    public static final String SCHEDULE_GENERAL_EXCEPTION = "An error occurred while generating the schedule.";
    // Invalid exception
    public static final String SCHEDULE_INVALID_EXCEPTION = "Base on the given setting we can not create the schedule.";


    public ScheduleException(String message) {
        super(message);
    }

    public ScheduleException(Throwable cause) {
        super(cause);
    }

    public ScheduleException(String message, Throwable throwable) {
        super(message, throwable);
    }


}