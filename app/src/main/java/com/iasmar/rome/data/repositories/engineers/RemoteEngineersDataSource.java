package com.iasmar.rome.data.repositories.engineers;

import com.iasmar.rome.data.modules.Engineer;
import com.iasmar.rome.data.repositories.BaseDataSource;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by Asmar on 01/7/2018.
 * <p>
 * Remote entry point for accessing engineers data.
 *
 * @author Asmar
 * @version 1
 * @see Engineer
 * @see BaseDataSource
 * @see EngineersDataSource
 * @since 0.1.0
 */
public interface RemoteEngineersDataSource extends EngineersDataSource {

}