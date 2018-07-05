package com.iasmar.rome.data.repositories.engineers;

import java.util.List;

import io.reactivex.Flowable;
import com.iasmar.rome.data.modules.Engineer;
import com.iasmar.rome.data.modules.Schedule;
import com.iasmar.rome.data.repositories.BaseDataSource;


/**
 * Created by Asmar on 01/7/2018.
 * <p>
 * Main entry point for accessing engineers data.
 *
 * @author Asmar
 * @version 1
 * @see Engineer
 * @see BaseDataSource
 * @since 0.1.0
 */
public interface EngineersDataSource extends BaseDataSource {

    /**
     * Gets engineers.
     *
     * @return the List of {@link Engineer} module.
     */
    Flowable<List<Engineer>> getEngineers();



}