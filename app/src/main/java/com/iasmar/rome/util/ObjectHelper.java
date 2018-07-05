package com.iasmar.rome.util;

import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;

import static com.google.common.base.Preconditions.checkNotNull;


/**
 * Created by Asmar on 01/7/2018.
 * <p>
 * This Class provides utility methods containing the backport of Java 7's Objects utility class.
 * <p>Named as such to avoid clash with java.util.Objects.
 *
 * @author Asmar
 * @since 1.0
 */
public final class ObjectHelper {


    /**
     * Prevent instances of this class.
     */
    @VisibleForTesting
     ObjectHelper() {
        throw new IllegalStateException("No instances!");
    }


    /**
     * Ensures that an object reference passed as a parameter to the calling method is not null.
     *
     * @param reference    an object reference
     * @param errorMessage the exception message to use if the check fails; will be converted to a
     *                     string using {@link String#valueOf(Object)}
     * @return the non-null reference that was validated
     */
    public static <T> T requireNonNull(T reference, @Nullable String errorMessage) {
        return checkNotNull(reference, errorMessage);
    }



}
