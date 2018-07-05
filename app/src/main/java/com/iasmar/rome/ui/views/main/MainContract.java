package com.iasmar.rome.ui.views.main;


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
 * @since 1.0
 */
interface MainContract {

    //*********************************** The main view ***********************************//


    /**
     * The profile view  must  implement this interface.
     */
    interface View extends IBaseView<Presenter> {

    }


    //*********************************** The main presenter ***********************************//

    /**
     * The profile presenter  must  implement this interface.
     *
     * @param <V> the profile view.
     */
    interface Presenter<V extends View> extends IBasePresenter<V> {


    }
}
