
package com.iasmar.rome;


import android.content.Context;
import android.support.annotation.NonNull;

import com.iasmar.rome.data.repositories.engineers.EngineersRepository;
import com.iasmar.rome.data.repositories.engineers.FakeEngineersRemoteDataSource;
import com.iasmar.rome.data.repositories.main.MainRepository;
import com.iasmar.rome.util.schedulers.BaseSchedulerProvider;
import com.iasmar.rome.util.schedulers.SchedulerProvider;

import static com.iasmar.rome.util.ObjectHelper.requireNonNull;


/**
 * Created by Asmar on 01/7/2018.
 * <p>
 * Enables injection of mock implementations for
 * data sources at compile time. This is useful for testing, since it allows us to use
 * a fake instance of the class to isolate the dependencies and run a test hermetically.
 *
 * @author Asmar
 * @since 0.1.0
 */


public class Injection {


    public static MainRepository provideMainRepository(@NonNull Context context) {
        context =  requireNonNull(context, "context cannot be null");
        return  MainRepository.getInstance();
    }
    public static EngineersRepository provideEngineersRepository(@NonNull Context context) {
        context =  requireNonNull(context, "context cannot be null");
        return EngineersRepository.getInstance(FakeEngineersRemoteDataSource.getInstance());
    }

    public static BaseSchedulerProvider provideSchedulerProvider() {
        return SchedulerProvider.getInstance();
    }

}
