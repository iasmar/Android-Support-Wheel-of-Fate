package com.iasmar.rome;


import android.content.Context;
import android.support.annotation.NonNull;


import com.iasmar.rome.data.repositories.engineers.EngineersRepository;
import com.iasmar.rome.data.repositories.main.MainRepository;
import com.iasmar.rome.data.sources.remote.engineers.EngineersRemoteDataSource;
import com.iasmar.rome.util.schedulers.BaseSchedulerProvider;
import com.iasmar.rome.util.schedulers.SchedulerProvider;

import static com.iasmar.rome.util.ObjectHelper.requireNonNull;


/**
 * Created by Asmar on 01/7/2018.
 * <p>
 * Enables injection of production implementations for
 *  repositories at compile time.
 *
 * @author Asmar
 * @since 0.1.0
 */

public class Injection {

    public static EngineersRepository provideEngineersRepository(@NonNull Context context) {
        context = requireNonNull(context, "context cannot be null");

     return    EngineersRepository.getInstance(EngineersRemoteDataSource.getInstance(provideSchedulerProvider()));

    }

    public static MainRepository provideMainRepository(@NonNull Context context) {
        context = requireNonNull(context, "context cannot be null");
        return MainRepository.getInstance();

    }
    public static BaseSchedulerProvider provideSchedulerProvider() {
        return SchedulerProvider.getInstance();
    }
}
