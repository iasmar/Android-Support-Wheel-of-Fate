package com.iasmar.rome.util;

import android.os.Build;
import android.support.annotation.Nullable;

import com.iasmar.rome.data.modules.Engineer;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import static com.iasmar.rome.util.ObjectHelper.requireNonNull;


/**
 * Created by Asmar on 01/7/2018.
 * <p>
 * This provides methods to help other classes achieve some takes.
 *
 * @author Asmar
 * @since 1.0
 */
//TODO add comments

public class GeneralUtils {
    public static <E> Map<String, E> convertListToMap(Collection<E> sourceList, ListToMapConverterInterface<E> converterInterface) {
        Map<String, E> newMap = new HashMap<String, E>();
        for( E item : sourceList ) {
            newMap.put( converterInterface.getKeyForItem( item ), item );
        }
        return newMap;
    }

    public interface ListToMapConverterInterface<E> {
        String getKeyForItem(E item);
    }
    public static String getHash(@Nullable String input) {
        input = requireNonNull(input, "input cannot be null");
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        digest = requireNonNull(digest, "digest cannot be null");
        byte[] encodedHash = digest.digest(
                getBytesUTF(input));
        return bytesToHex(encodedHash);
    }

    private static byte[] getBytesUTF(@Nullable String input) {
        input = requireNonNull(input, "input cannot be null");
        byte[] result = null;
        try {
            result = input.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;

    }

    private static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder();
        for (byte aHash : hash) {
            String hex = Integer.toHexString(0xff & aHash);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }


    public static <T> T getFirstItem(Set<T> keys) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            return keys.stream().findFirst().orElse(null);

        return keys.iterator().next();
    }

    public static String toString(int i) {
        return Integer.toString(i);
    }

    public static String toString(String[] arr) {
        //TODO refactoring
        StringBuilder stringBuilder = new StringBuilder();
        for (String singleArr : arr) {
            stringBuilder.append(singleArr+",");
        }
        return stringBuilder.toString();
    }
    public static String append(String... str)  {
        StringBuilder stringBuilder = new StringBuilder();
        for (String string : str) {
            stringBuilder.append(string+",");
        }
        return stringBuilder.toString();
    }


    public static boolean isNullOrEmpty(@Nullable String input) {
        return com.google.common.base.Strings.isNullOrEmpty(input);
    }



    public static <T> boolean isNullOrEmpty(List<T> list) {
        return list == null || list.size() == 0;

    }


    /**
     * Compares two {@code long} values numerically.
     *
     * @param x the first {@code long} to compare
     * @param y the second {@code long} to compare
     * @return the value {@code 0} if {@code x == y};
     * a value less than {@code 0} if {@code x < y}; and
     * a value greater than {@code 0} if {@code x > y}
     */
    public static int compare(long x, long y) {
        return Long.compare(x, y);
    }
    public static int compare(String x, String y) {
        return x.compareTo(y);
    }
    public static int compare(boolean b1, boolean b2) {
        return (b1 != b2) ? (b1) ? -1 : 1 : 0;

    }

    public static String replace(@Nullable String input, @Nullable String target, @Nullable String replacement) {
        return input.replace(target, replacement);

    }
    public static int getRandomNumber(int max) {
        Random ran = new Random();
        return ran.nextInt(max);
    }

}
