package com.iasmar.rome.util.schedulers;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/**
 * Implementation of the {@link BaseSchedulerProvider} making all {@link Scheduler}s execute
 * synchronously so we can easily run assertions in our tests.
 * <p>
 * To achieve this, we are using the {@link io.reactivex.internal.schedulers.TrampolineScheduler} from the {@link Schedulers} class.
 *
 * @author Asmar
 * @version 1
 * @since 1.0
 */
public class SchedulerProvider implements BaseSchedulerProvider {
    // The instance of SchedulerProvider.
    @Nullable
    private static SchedulerProvider INSTANCE;

    // Prevent direct instantiation.
    private SchedulerProvider() {
    }

    /**
     * Returns the single instance of this class, creating it if necessary.
     *
     * @return the {@link SchedulerProvider} instance.
     */
    public static synchronized SchedulerProvider getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SchedulerProvider();
        }
        return INSTANCE;
    }

    /**
     * Returns a default, shared {@link Scheduler} instance whose {@link io.reactivex.Scheduler.Worker}
     * instances queue work and execute them in a FIFO manner on one of the participating threads.
     *
     * @return a {@link Scheduler} that queues work on the current thread.
     */
    @Override
    @NonNull
    public Scheduler computation() {
        return Schedulers.computation();
    }

    /**
     * Returns a default, shared {@link Scheduler} instance intended for IO-bound work.
     * <p>
     * This can be used for asynchronously performing blocking IO.
     * <p>
     *
     * @return a {@link Scheduler} meant for IO-bound work
     */
    @Override
    @NonNull
    public Scheduler io() {
        return Schedulers.io();
    }

    /**
     * Returns a default, shared {@link Scheduler} instance intended for UI work.
     *
     * @return A {@link Scheduler} which executes actions on the Android main thread.
     */
    @Override
    @NonNull
    public Scheduler ui() {
        return AndroidSchedulers.mainThread();
    }
}
