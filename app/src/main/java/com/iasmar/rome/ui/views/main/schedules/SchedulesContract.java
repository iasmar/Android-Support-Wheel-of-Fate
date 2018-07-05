package com.iasmar.rome.ui.views.main.schedules;


import com.iasmar.rome.data.modules.Schedule;
import com.iasmar.rome.data.modules.ModulesHolder;
import com.iasmar.rome.ui.views.base.IBasePresenter;
import com.iasmar.rome.ui.views.base.IBaseView;

/**
 * Created by Asmar on 01/7/2018.
 * <p>
 * This specifies the contract between the view and the presenter.
 *
 * @author Asmar
 * @version 1
 * @see IBaseView
 * @see IBasePresenter
 * @see ModulesHolder
 * @see Schedule
 * @since 1.0
 */
interface SchedulesContract {

    //*********************************** The schedules view ***********************************//


    /**
     * The schedules view  must  implement this interface.
     */
    interface View extends IBaseView<Presenter> {
        /**
         * Display schedules on the UI.
         */
        void showSchedules(ModulesHolder<Schedule> schedules);
        /**
         * Display no schedules on the UI.
         */
        void showNoSchedules();



    }


    //*********************************** The schedules presenter ***********************************//

    /**
     * The schedules presenter  must  implement this interface.
     *
     * @param <V> the schedules view.
     */
    interface Presenter<V extends View> extends IBasePresenter<V> {

        /**
         * load schedules.
         *
         * @param forceUpdate true to force update from the server false load from cash or DB,
         *                    if there is a problem with th connection will return from cash or DB.
         */
        void loadSchedules(boolean forceUpdate);


    }
}
