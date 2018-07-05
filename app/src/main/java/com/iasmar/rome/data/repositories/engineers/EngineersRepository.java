package com.iasmar.rome.data.repositories.engineers;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Flowable;

import com.iasmar.rome.data.modules.Engineer;
import com.iasmar.rome.data.modules.Schedule;
import com.iasmar.rome.data.repositories.BaseRepository;
import com.iasmar.rome.exception.ScheduleException;
import com.iasmar.rome.services.ScheduleService;
import com.iasmar.rome.util.GeneralUtils;
import com.iasmar.rome.util.ObjectHelper;

import static com.iasmar.rome.util.ObjectHelper.requireNonNull;


/**
 * Created by Asmar on 01/7/2018.
 * <p>
 * Concrete implementation to load engineers from the data sources.
 * <p>
 * This implements a synchronisation for data
 * obtained from the server and cache data.
 *
 * @author Asmar
 * @version 1
 * @see Engineer
 * @see EngineersDataSource
 * @since 0.1.0
 */
public class EngineersRepository extends BaseRepository<Engineer, EngineersDataSource> implements RemoteEngineersDataSource {

    // The remote data source that will be based from the presenter.
    @NonNull
    private final RemoteEngineersDataSource remoteDataSource;


    /**
     * This variable has package local visibility so it can be accessed from tests.
     */
    @VisibleForTesting
    @Nullable
    LinkedHashMap<String, Engineer> cachedEngineers;

    /**
     * Marks the cache as invalid, to force an update the next time data is requested.
     * This variable has package local visibility so it can be accessed from tests.
     */
    @VisibleForTesting
    boolean cacheIsDirty = true;

    // The instance of EngineersRepository.
    @Nullable
    private static EngineersRepository INSTANCE = null;

    /**
     * Prevent direct instantiation.
     *
     * @param remoteDataSource The remote data source.
     */
    private EngineersRepository(@NonNull RemoteEngineersDataSource remoteDataSource
    ) {
        this.remoteDataSource = requireNonNull(remoteDataSource, "remoteDataSource cannot be null");

    }

    /**
     * Returns the single instance of this class, creating it if necessary.
     *
     * @param engineersRemoteDataSource The remote data source.
     * @return The {@link EngineersRepository} instance.
     */
    public static synchronized EngineersRepository getInstance(@NonNull RemoteEngineersDataSource engineersRemoteDataSource
    ) {
        if (INSTANCE == null) {
            INSTANCE = new EngineersRepository(engineersRemoteDataSource);
        }
        return INSTANCE;
    }

    /**
     * Used to force {@link #getInstance(RemoteEngineersDataSource)} to create a new instance
     * next time it's called.
     */
    public static void destroyInstance() {
        INSTANCE = null;
    }

    /**
     * Gets data from cache or remote data source.
     *
     * @return the List of {@link Engineer} module from cache or remote.
     */
    public Flowable<List<Engineer>> getEngineers() {

        // Respond immediately with cache if available and not dirty.
        if (cachedEngineers != null && !cacheIsDirty) {
            return Flowable.fromIterable(cachedEngineers.values()).toList().toFlowable();
        } else if (cachedEngineers == null) {
            cachedEngineers = new LinkedHashMap<>();
        }
        // Get the data from remote and then save it into the cache.
        return getRemoteData();
    }


    /**
     * Gets schedules for engineers.
     *
     * @return the schedules for engineers or empty.
     * @see ScheduleService
     */
    public Flowable<List<Schedule>> getSchedules() {
        if (cachedEngineers != null && cachedEngineers.size() > 0) {
            List<Engineer> engineers = new ArrayList<>(cachedEngineers.values());
            ScheduleService scheduleService = new ScheduleService(engineers);
            List<Schedule> schedules;
            try {
                schedules = scheduleService.getSchedules();

            } catch (ScheduleException e) {
                return Flowable.error(e);

            }
            return Flowable.fromIterable(schedules).distinct().toList().toFlowable();
        }
        return Flowable.empty();

    }

    /**
     * Query the network if available and sort them, then store in the cache.
     *
     * @return the List of {@link Engineer} module from network.
     */
    private Flowable<List<Engineer>> getRemoteData() {
        return remoteDataSource
                .getEngineers()
                .flatMap(data -> Flowable.fromIterable(data)
                        .doOnNext(engineer -> {
                            ObjectHelper.requireNonNull(cachedEngineers, "cachedEngineers cannot be null").put(engineer.getId(), engineer);
                        })
                        .distinct()
                        .sorted(EngineersRepository.this::sortItems)
                        .toList().toFlowable().doOnComplete(() -> cacheIsDirty = false));


    }

    /**
     * Force update data in the next call by making the cache dirty true.
     *
     * @param cacheIsDirty true to force update.
     */
    public void setCacheDirty(boolean cacheIsDirty) {
        this.cacheIsDirty = cacheIsDirty;
    }


    /**
     * Compares two {@code long} values numerically.
     *
     * @param o1 the first module to compare.
     * @param o2 the second module to compare.
     * @return the value of the comparison.
     */
    private int sortItems(Engineer o1, Engineer o2) {
        return -GeneralUtils.compare(o1.getSortBy(), o2.getSortBy());
    }

}