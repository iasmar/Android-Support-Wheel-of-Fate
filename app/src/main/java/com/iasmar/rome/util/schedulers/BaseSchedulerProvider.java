package com.iasmar.rome.util.schedulers;

import android.support.annotation.NonNull;

import io.reactivex.Scheduler;

/**
 * Created by Asmar on 01/7/2018.
 * <p>
 * Allow providing different types of {@link Scheduler}s.
 *
 * @author Asmar
 * @version 1
 * @see Scheduler
 * @since 1.0
 */
public interface BaseSchedulerProvider {

    /**
     * Returns a default, shared {@link Scheduler} instance intended for computational work.
     * <p>
     * This can be used for event-loops, processing callbacks and other computational work.
     * <p>
     * It is not recommended to perform blocking, IO-bound work on this scheduler. Use {@link #io()} instead.
     * <p>
     *
     * @return a {@link Scheduler} meant for computation-bound work.
     */
    @NonNull
    Scheduler computation();

    /**
     * Returns a default, shared {@link Scheduler} instance intended for IO-bound work.
     * <p>
     * This can be used for asynchronously performing blocking IO.
     *
     * @return a {@link Scheduler} meant for IO-bound work.
     */
    @NonNull
    Scheduler io();

    /**
     * Returns a default, shared {@link Scheduler} instance intended for UI work.
     *
     * @return A {@link Scheduler} which executes actions on the Android main thread.
     */
    @NonNull
    Scheduler ui();
}
