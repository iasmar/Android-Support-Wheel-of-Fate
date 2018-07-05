package com.iasmar.rome.configuration;

import com.iasmar.rome.data.sources.remote.BaseRemoteDataSource;

import static com.iasmar.rome.configuration.NetworkConfig.BASE_URL;


/**
 * Created by Asmar on 01/7/2018.
 * <p>
 * This is the APIs end point that remote data source will use in order to get the attempt API.
 * <p>
 * The base URL is controlled by gradle and could be changed from gradle.properties.
 *
 * @author Asmar
 * @version 1
 * @see BaseRemoteDataSource
 * @since 0.1.0
 */
public final class ApiEndPoint {

        public static final String END_POINT_API_ENGINEERS = BASE_URL
            + "engineers";
}