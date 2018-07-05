package com.iasmar.rome.data.sources.remote;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.iasmar.rome.configuration.NetworkConfig;
import com.iasmar.rome.exception.ApiError;

import java.io.IOException;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;


import static com.iasmar.rome.util.GeneralUtils.getFirstItem;
import static com.iasmar.rome.util.ObjectHelper.requireNonNull;

/**
 * Created by Asmar on 01/7/2018.
 * <p>
 * This class is responsible to make the connection with the server and make the retry and parse and convert responses.
 *
 * @author Asmar
 * @author Asmar
 * @version 1
 * @see NetworkConfig
 * @see BaseRemoteDataSource
 * @see CustomInterceptor
 * @since 0.1.0
 */
class CustomHttp {
    private OkHttpClient client;

    /**
     * Prevent direct instantiation.
     *
     * Initialise and config the OkHttpClient.
     */
    CustomHttp() {
        ConnectionPool connectionPool = new ConnectionPool();
        client = new OkHttpClient.Builder()
                .addInterceptor(new CustomInterceptor())
                .readTimeout(NetworkConfig.READ_TIMEOUT, NetworkConfig.TIME_UNIT)
                .writeTimeout(NetworkConfig.WRITE_TIMEOUT, NetworkConfig.TIME_UNIT)
                .connectTimeout(NetworkConfig.CONNECT_TIMEOUT, NetworkConfig.TIME_UNIT)
                .connectionPool(connectionPool)
                .retryOnConnectionFailure(NetworkConfig.RETRY_ON_CONNECTION_FAILURE)
                .build();
    }


    /**
     *
     * @param url the URL target of the request.
     * @return  The response as a JSON text.
     * @throws IOException some errors might accrue such as {@link OutOfMemoryError},request could not be executed due to cancellation, a connectivity
     * @throws RuntimeException when the response did not success.
     * @see ApiError
     */
   protected String get(String url) throws IOException {
        Request request = createGetRequest(url);
        Response  response = execute(request);

            response = requireNonNull(response, "response cannot be null");
            if (response.isSuccessful()) {
                ResponseBody responseBody = response.body();
                String responseBodyString = getAsString(responseBody);

                JsonObject jsonObject = getAsJsonObject(responseBodyString);
                JsonArray jsonArray = getAsJsonArray(jsonObject);
                return getAsString(jsonArray);

            } else {
                throw new ApiError(response.code());
            }
    }

    /**
     * Create request with the type of GET and build it.
     *
     * @param url the URL target of the request.
     * @return The request that is ready to get excused.
     */
    private Request createGetRequest(String url) {
        return new Request.Builder()
                .get()
                .url(url)
                .build();
    }

    /**
     * Make a new call and execute the request.
     *
     * @param request the request that will be executed.
     * @return The response of the request.
     * @throws IOException if the request could not be executed due to cancellation, a connectivity
     * problem or timeout. Because networks can fail during an exchange, it is possible that the
     * remote server accepted the request before the failure.
     * @throws IllegalStateException when the call has already been executed.
     */
    private Response execute(Request request) throws IOException {
        return client.newCall(request).execute();
    }

    /**
     * Parse the string and convert it into a json object.
     * @param json The json text.
     *
     * @return JsonObject from the given string.
     */
    private JsonObject getAsJsonObject(String json) {
        json = requireNonNull(json, "json cannot be null");
        return new JsonParser().parse(json).getAsJsonObject();
    }

    /**
     * Convenience method to get the specified member as a JsonArray.
     * @param jsonObject The json object that will be used to get the first member name from and converted to json array.
     * @return the first member name from and converted to json array.
     */
    private JsonArray getAsJsonArray(JsonObject jsonObject) {
        jsonObject = requireNonNull(jsonObject, "jsonObject cannot be null");
        String memberName = getFirstItem(jsonObject.keySet());
        memberName = requireNonNull(memberName, "memberName cannot be null");

        return jsonObject.getAsJsonArray(memberName);
    }

    /**
     * Returns a String representation of this element.
     *
     * @param jsonArray The json array that will be converted to a string.
     * @return The string of the json array.
     */
    private String getAsString(JsonArray jsonArray) {
        jsonArray = requireNonNull(jsonArray, "jsonArray cannot be null");

        return jsonArray.toString();
    }

    /**
     * Convert response body to string.
     *
     * <p>This method loads entire response body into memory. If the response body is very large this
     * may trigger an {@link OutOfMemoryError}. Prefer to stream the response body if this is a
     * possibility for your response.
     *
     *
     * @param responseBody the response body that will be converted to a string.
     * @return The string of the response body.
     * @throws IOException  some errors might accrue such as {@link OutOfMemoryError}.
     */
    private String getAsString(ResponseBody responseBody) throws IOException {
        responseBody = requireNonNull(responseBody, "responseBody cannot be null");
        return responseBody.string();
    }
}