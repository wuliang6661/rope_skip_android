package com.tohabit.skip.ui.mine.contract;


import com.tohabit.skip.base.BasePresenter;
import com.tohabit.skip.base.BaseView;

/**
 * @version V1.0
 * @date: 2020-02-16 12:19
 * @ClassName: AddAddressContract.java
 * @Description:
 * @author: sundongdong
 */
public interface AddAddressContract {
    interface View extends BaseView {

        void saveSouress();

    }

    interface Presenter extends BasePresenter<View> {

    }

}