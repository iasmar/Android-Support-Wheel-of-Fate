package com.iasmar.rome.data.modules;

/**
 * Created by Asmar on 01/7/2018.
 * <p>
 * This is the base module that other modules (From APIS and local storage) will implement.
 * <p>
 * The main purpose of this Module is to provide main methods that each module must have.
 *
 * @param <T> Sort type.
 * @author Asmar
 * @version 1
 * @since 0.1.0
 */
public interface BaseModule<T> {

    /**
     * The unique id.
     *
     * @return The unique id.
     */
    String getId();

    /**
     * The hash of the module.
     *
     * @return The hash of the module.
     */
    String getHash();


    /**
     * Custom check will be implement in each module.
     *
     * @return true if the current module empty.
     */
    boolean isEmpty();


    /**
     * Sort by.
     *
     * @return sort type.
     */
    T getSortBy();
}