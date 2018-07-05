package com.iasmar.rome.data.sources.remote;


import android.support.annotation.NonNull;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import io.reactivex.Flowable;
import com.iasmar.rome.data.modules.BaseModule;
import com.iasmar.rome.util.schedulers.BaseSchedulerProvider;

import static com.iasmar.rome.util.ObjectHelper.requireNonNull;


/**
 * Created by Asmar on 01/7/2018.
 * <p>
 * The base of the data source that will communicate with the server.
 *
 * @param <T> {@link BaseModule] child.
 * @author Asmar
 * @version 1
 * @see BaseSchedulerProvider
 * @see BaseModule
 * @since 0.1.0
 */

public class BaseRemoteDataSource<T extends BaseModule> {

    // the scheduler to perform subscription actions on
    private BaseSchedulerProvider schedulerProvider;

    /**
     * Prevent direct instantiation.
     * <p>
     * Initialize Scheduler provider.
     *
     * @param schedulerProvider The  inject of scheduler provider.
     */
    public BaseRemoteDataSource(
            @NonNull BaseSchedulerProvider schedulerProvider) {
        this.schedulerProvider = requireNonNull(schedulerProvider,
                "scheduleProvider cannot be null");
    }

    protected BaseRemoteDataSource() {
    }


    /**
     * Gets data from the server.
     *
     * @param classOfT    the class of T
     * @param endPointApi The attempt API.
     * @return The List of {@link T} module from the given json text or null.
     */
    protected Flowable<List<T>> getListData(Class<T[]> classOfT, String endPointApi) {
        return Flowable
                .fromCallable(() -> getList(classOfT, new CustomHttp().get(endPointApi)))
                .subscribeOn(schedulerProvider.io());
    }

    /**
     * Gets list of data from the given json text.
     *
     * @param classOfT   the class of T.
     * @param jsonText the string from which the object is to be deserialize.
     * @return The List of {@link T} module from the given json text or null.
     */
    private List<T> getList(Class<T[]> classOfT, String jsonText) {
        classOfT = requireNonNull(classOfT, "type cannot be null");
        if (jsonText != null) {
            final T[] jsonToObject = new Gson().fromJson(jsonText, classOfT);
            return Arrays.asList(jsonToObject);

        }
        return new ArrayList<>();
    }


}
