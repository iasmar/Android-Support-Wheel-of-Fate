package com.iasmar.rome.data.repositories.main;

import android.support.annotation.Nullable;

import com.iasmar.rome.data.modules.Main;
import com.iasmar.rome.data.repositories.BaseRepository;



/**
 * Created by Asmar on 01/7/2018.
 * <p>
 * Concrete implementation to load main from the data sources.
 *
 * @author Asmar
 * @version 1
 * @see Main
 * @see MainDataSource
 * @since 0.1.0
 */
public class MainRepository extends BaseRepository<Main, MainDataSource> implements MainDataSource {




    // The instance of MainRepository.
    @Nullable
    private static MainRepository INSTANCE = null;

    /**
     * Prevent direct instantiation.
     *
     */
    private MainRepository( ) {
    }

    /**
     * Returns the single instance of this class, creating it if necessary.
     *
     * @return The {@link MainRepository} instance.
     */
    public static synchronized MainRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MainRepository();
        }
        return INSTANCE;
    }

    /**
     * Used to force {@link #getInstance()} to create a new instance
     * next time it's called.
     */
    public static void destroyInstance() {
        INSTANCE = null;
    }




    /**
     * Force update data in the next call by making the cache dirty true.
     * @param cacheIsDirty true to force update.
     */
    public void setCacheDirty(boolean cacheIsDirty) {
    }


}