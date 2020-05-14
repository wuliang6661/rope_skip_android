package com.habit.star.ui.login.contract;


import com.habit.star.base.BasePresenter;
import com.habit.star.base.BaseView;

/**
 * @version V1.0
 * @date: 2020-02-16 12:05
 * @ClassName: RetrievePasswordContract.java
 * @Description:
 * @author: sundongdong
 */
public interface ModifyTelephoneContract {
    interface View extends BaseView {


        void getYZMSuccess();

        void YzSouress();

        void updateSourcess();
    }

    interface Presenter extends BasePresenter<View> {

    }

}