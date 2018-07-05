package com.iasmar.rome.data.sources.remote.engineers;


import android.support.annotation.NonNull;

import java.util.List;

import io.reactivex.Flowable;
import com.iasmar.rome.data.modules.Engineer;
import com.iasmar.rome.data.repositories.engineers.EngineersDataSource;
import com.iasmar.rome.data.repositories.engineers.RemoteEngineersDataSource;
import com.iasmar.rome.data.sources.remote.BaseRemoteDataSource;
import com.iasmar.rome.util.schedulers.BaseSchedulerProvider;

import static com.iasmar.rome.configuration.ApiEndPoint.END_POINT_API_ENGINEERS;


/**
 * Created by Asmar on 01/7/2018.
 * <p>
 * Implementation of the data source that will communicate with the server.
 *
 * @author Asmar
 * @author Asmar
 * @version 1
 * @see BaseRemoteDataSource
 * @see Engineer
 * @see EngineersDataSource
 * @see RemoteEngineersDataSource
 * @see BaseSchedulerProvider
 * @since 0.1.0
 */
public class EngineersRemoteDataSource extends BaseRemoteDataSource<Engineer> implements RemoteEngineersDataSource {

    // The instance of EngineersRemoteDataSource.
    private static EngineersRemoteDataSource INSTANCE;


    /**
     * Prevent direct instantiation.
     *
     * @param schedulerProvider The  inject of scheduler provider.
     */
    private EngineersRemoteDataSource(@NonNull BaseSchedulerProvider schedulerProvider) {
        super(schedulerProvider);
    }

    /**
     * Returns the single instance of this class, creating it if necessary.
     *
     * @param schedulerProvider The inject of scheduler provider.
     * @return the {@link EngineersRemoteDataSource} instance
     */
    public static synchronized EngineersRemoteDataSource getInstance(@NonNull BaseSchedulerProvider schedulerProvider) {
        if (INSTANCE == null) {
            INSTANCE = new EngineersRemoteDataSource(schedulerProvider);
        }
        return INSTANCE;
    }

    /**
     * TODO use this
     * Used to force {@link #getInstance(BaseSchedulerProvider)} to create a new instance
     * next time it's called.
     */
    public static void destroyInstance() {
        INSTANCE = null;
    }

    /**
     * TODO delete this
     * Not required because the {@link } handles the logic of refreshing the
     * // help from all the available data sources.
     */
    @Override
    public void setCacheDirty(boolean cacheIsDirty) {
    }

    /**
     * Gets engineers from the server.
     *
     * @return the List of {@link Engineer} module from server.
     */
    @Override
    public Flowable<List<Engineer>> getEngineers() {
        return getListData(Engineer[].class, END_POINT_API_ENGINEERS);
    }


}
