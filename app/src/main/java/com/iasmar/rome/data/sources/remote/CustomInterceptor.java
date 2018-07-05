package com.iasmar.rome.data.sources.remote;


import android.support.annotation.NonNull;
import android.util.Log;

import com.iasmar.rome.configuration.NetworkConfig;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;


/**
 * Created by Asmar on 01/7/2018.
 * <p>
 * The aim of this class is to
 * Observes, modifies, and potentially short-circuits requests going out and the corresponding
 * responses coming back in. Typically interceptors add, remove, or transform headers on the request
 * or response.
 *
 * @author Asmar
 * @author Asmar
 * @version 1
 * @see NetworkConfig
 * @see BaseRemoteDataSource
 * @see CustomInterceptor
 * @since 0.1.0
 */

class CustomInterceptor implements Interceptor {

   private static final String TAG = CustomInterceptor.class.getSimpleName();

    /**
     * Intercept requests so that we can make custom retry with certain rules.
     * @param chain the chain.
     * @return the response of the request.
     * @throws IOException if something went wrong with the request.
     */
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request = chain.request();

      long t1 = System.nanoTime();
        Log.d(TAG,String.format("Sending request %s on %s%n%s",
        request.url(), chain.connection(), request.headers()));
//     try the request
        Response response = chain.proceed(request);

        int tryCount = 1;

        while (!response.isSuccessful() && tryCount < NetworkConfig.MAX_RETRY) {

            tryCount++;

            chain =  chain.withConnectTimeout(NetworkConfig.CONNECT_TIMEOUT * NetworkConfig.TIME_OUT_BY_MULTI, NetworkConfig.TIME_UNIT);
            chain =  chain.withReadTimeout(NetworkConfig.READ_TIMEOUT * NetworkConfig.TIME_OUT_BY_MULTI, NetworkConfig.TIME_UNIT);
            chain =  chain.withWriteTimeout(NetworkConfig.WRITE_TIMEOUT *  NetworkConfig.TIME_OUT_BY_MULTI, NetworkConfig.TIME_UNIT);

            // retry the request
            response = chain.proceed(request);
        }
        long t2 = System.nanoTime();
        Log.d(TAG,String.format("Received response for %s in %.1fms%n%s",
        response.request().url(), (t2 - t1) / 1e6d, response.headers()));

        // otherwise just pass the original response on
        return response;

    }

}