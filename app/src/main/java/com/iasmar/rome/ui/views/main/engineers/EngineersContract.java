package com.iasmar.rome.ui.views.main.engineers;

import com.iasmar.rome.data.modules.Engineer;
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
 * @see Engineer
 * @since 1.0
 */
interface EngineersContract {

    //*********************************** The engineers view ***********************************//


    /**
     * The engineers view  must  implement this interface.
     */
    interface View extends IBaseView<Presenter> {
        /**
         * Display engineers on the UI.
         */
        void showEngineers(ModulesHolder<Engineer> engineers);
        /**
         * Display no engineers on the UI.
         */
        void showNoEngineers();


    }


    //*********************************** The engineers presenter ***********************************//

    /**
     * The engineers presenter  must  implement this interface.
     *
     * @param <V> the engineers view.
     */
    interface Presenter<V extends View> extends IBasePresenter<V> {

        /**
         * load engineers.
         *
         * @param forceUpdate true to force update from the server false load from cash or DB,
         *                    if there is a problem with th connection will return from cash or DB.
         */
        void loadEngineers(boolean forceUpdate);


    }
}
