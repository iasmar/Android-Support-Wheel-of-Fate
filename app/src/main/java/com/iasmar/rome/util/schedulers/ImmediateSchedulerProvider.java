package com.iasmar.rome.util.schedulers;

import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

/**
 * To be used for testing only.
 * Implementation of the {@link BaseSchedulerProvider} making all {@link Scheduler}s execute
 * synchronously so we can easily run assertions in our tests.
 * <p>
 * To achieve this, we are using the {@link io.reactivex.internal.schedulers.TrampolineScheduler} from the {@link Schedulers} class.
 * @author Asmar
 * @version 1
 * @since 1.0
 */
public class ImmediateSchedulerProvider implements BaseSchedulerProvider {
    /**
     * To be used for testing only.
     * Returns a default, shared {@link Scheduler} instance whose {@link io.reactivex.Scheduler.Worker}
     * instances queue work and execute them in a FIFO manner on one of the participating threads.
     *
     * @return a {@link Scheduler} that queues work on the current thread.
     */
    @VisibleForTesting
    @NonNull
    @Override
    public Scheduler computation() {
        return Schedulers.trampoline();
    }

    /**
     * To be used for testing only.
     * Returns a default, shared {@link Scheduler} instance whose {@link io.reactivex.Scheduler.Worker}
     * instances queue work and execute them in a FIFO manner on one of the participating threads.
     *
     * @return a {@link Scheduler} that queues work on the current thread.
     */
    @VisibleForTesting
    @NonNull
    @Override
    public Scheduler io() {
        return Schedulers.trampoline();
    }

    /**
     * To be used for testing only.
     * Returns a default, shared {@link Scheduler} instance whose {@link io.reactivex.Scheduler.Worker}
     * instances queue work and execute them in a FIFO manner on one of the participating threads.
     *
     * @return a {@link Scheduler} that queues work on the current thread.
     */
    @VisibleForTesting
    @NonNull
    @Override
    public Scheduler ui() {
        return Schedulers.trampoline();
    }
}
