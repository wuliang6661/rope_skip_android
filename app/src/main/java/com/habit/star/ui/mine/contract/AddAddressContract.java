package com.habit.star.ui.mine.contract;


import com.habit.star.base.BasePresenter;
import com.habit.star.base.BaseView;

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