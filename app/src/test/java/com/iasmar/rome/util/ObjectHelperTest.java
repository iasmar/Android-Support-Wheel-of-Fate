package com.iasmar.rome.util;
import org.junit.Test;
import static com.iasmar.rome.util.ObjectHelper.requireNonNull;
import static org.junit.Assert.*;

/**
 * Created by Asmar on 01/7/2018.
 * <p>
 * Unit tests for the implementation of {@link ObjectHelper}
 *
 * @author Asmar
 * @version 1
 * @see ObjectHelper
 * @since 1.0
 */
public class ObjectHelperTest {


    /**
     * The class should not allowed any instances.
     */
    @Test
    public void ObjectHelperNoInstances() {
        try {
            new ObjectHelper();
            fail();
        } catch (IllegalStateException expected) {
            assertEquals("No instances!", expected.getMessage());
        }

    }


    /**
     * Require non null non null object.
     */
    @Test
    public void requireNonNull_nonNull() {
        String actual = "";
        String expected = requireNonNull(actual, "string cannot be null");
        assertEquals(expected, actual);

    }

    /**
     * Require non null null object.
     */
    @Test
    public void requireNonNull_null() {
        try {
            requireNonNull(null, "string cannot be null");
            fail();
        } catch (NullPointerException expected) {
            assertEquals("string cannot be null", expected.getMessage());
        }
    }


}