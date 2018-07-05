package com.iasmar.rome.configuration;

import java.util.concurrent.TimeUnit;

import com.iasmar.rome.BuildConfig;

/**
 * Created by Asmar on 01/7/2018.
 * <p>
 * This is the network config class will provide all configurations that will be used in CustomHttp and CustomInterceptor.
 * The base URL is controlled by gradle and could be changed from gradle.properties.
 *
 * @author Asmar
 * @version 1
 * @see com.iasmar.rome.data.sources.remote.CustomHttp
 * @see com.iasmar.rome.data.sources.remote.CustomInterceptor
 * @since 0.1.0
 */
public final class NetworkConfig {

    // The base URL is controlled by gradle and could be changed from gradle.properties.
    static final String BASE_URL = BuildConfig.BASE_URL;

    // Time unit for the timeout configurations.
    public static final TimeUnit TIME_UNIT = TimeUnit.MILLISECONDS;

    // The max allow retries.
    public static final int MAX_RETRY = 3;

    // increase the time out by multiply by.
    public static final int TIME_OUT_BY_MULTI = 2;

    // Timeout configurations.
    public static final int READ_TIMEOUT = 10000;
    public static final int WRITE_TIMEOUT = 10000;
    public static final int CONNECT_TIMEOUT = 10000;

    /**
     * Configure this client to retry or not when a connectivity problem is encountered. By default, this client silently recovers from the following problems:
     * <p>
     * Unreachable IP addresses. If the URL's host has multiple IP addresses, failure to reach any individual IP address doesn't fail the overall request. This can increase availability of multi-homed services.
     * Stale pooled connections. The ConnectionPool reuses sockets to decrease request latency, but these connections will occasionally time out.
     * Unreachable proxy servers. A ProxySelector can be used to attempt multiple proxy servers in sequence, eventually falling back to a direct connection.
     * <p>
     * Problems:
     * there might be other problems appear that we want to retry.
     * such as:
     * failed SSL handshake
     * <p>
     * Solution:
     * Set this to false to avoid retrying requests when doing so is destructive.
     * In this case the calling application should do its own recovery of connectivity failures.
     * By implement our own retry. by setting #MAX_RETRY.
     */

    public static final boolean RETRY_ON_CONNECTION_FAILURE = false;

}
