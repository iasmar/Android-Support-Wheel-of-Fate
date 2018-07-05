
package com.iasmar.rome.data.repositories.engineers;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;


import com.iasmar.rome.data.modules.Engineer;
import com.iasmar.rome.data.sources.remote.BaseRemoteDataSource;
import com.iasmar.rome.util.schedulers.BaseSchedulerProvider;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;


import io.reactivex.Flowable;

/**
 * Created by Asmar on 01/7/2018.
 * <p>
 * Implementation of a remote data source with static access to the data for easy testing.
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
public class FakeEngineersRemoteDataSource extends BaseRemoteDataSource<Engineer> implements RemoteEngineersDataSource {

    // The instance of CartBadgeRepository.
    private static FakeEngineersRemoteDataSource INSTANCE;

    private static final int SERVICE_LATENCY_IN_MILLIS = 5000;

    private final static Map<String, Engineer> CATEGORIES_SERVICE_DATA;

    static {
        CATEGORIES_SERVICE_DATA = new LinkedHashMap<>(10);
        addEngineer("0", "Bogdan", null);
        addEngineer("1", "Nic", null);
        addEngineer("2", "Tung", null);
        addEngineer("3", "Gautam", null);
        addEngineer("4", "Bala", null);
        addEngineer("5", "Nazih", null);
        addEngineer("6", "Huteri", null);
        addEngineer("7", "Aldy", null);
        addEngineer("8", "Ankur", null);
        addEngineer("9", "Chinh", null);
    }

    /**
     * Prevent direct instantiation.
     *
     */
    private FakeEngineersRemoteDataSource( ) {

    }

    /**
     * Returns the single instance of this class, creating it if necessary.
     *
     * @return the {@link FakeEngineersRemoteDataSource} instance
     */
    public static synchronized FakeEngineersRemoteDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new FakeEngineersRemoteDataSource();
        }
        return INSTANCE;
    }

    /**
     * TODO use this
     * Used to force {@link #getInstance()} to create a new instance
     * next time it's called.
     */
    public static void destroyInstance() {
        INSTANCE = null;
    }


    /**
     * Gets engineers from the server.
     *
     * @return the List of {@link Engineer} module from server.
     */
    @Override
    public Flowable<List<Engineer>> getEngineers() {
        return Flowable
                .fromIterable(CATEGORIES_SERVICE_DATA.values())
                .delay(SERVICE_LATENCY_IN_MILLIS, TimeUnit.MILLISECONDS)
                .toList()
                .toFlowable();
    }

    private static void addEngineer(@NonNull String id, @NonNull String name, String profilePic) {
        Engineer engineer = generateEngineer(id, name, profilePic);
        CATEGORIES_SERVICE_DATA.put(engineer.getId(), engineer);
    }

    private static Engineer generateEngineer(String id, String name, String profilePic) {
        return new Engineer(id, name, profilePic);
    }

    /**
     * TODO delete this
     * Not required because the {@link } handles the logic of refreshing the
     * help from all the available data sources.
     */
    @Override
    public void setCacheDirty(boolean cacheIsDirty) {

    }
}
