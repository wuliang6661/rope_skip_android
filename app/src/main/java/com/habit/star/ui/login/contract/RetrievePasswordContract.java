package com.habit.star.ui.login.contract;


import com.habit.star.base.BasePresenter;
import com.habit.star.base.BaseView;

/**
 * @date:  2020-02-16 12:05
 * @ClassName: RetrievePasswordContract.java
 * @Description:
 * @author: sundongdong
 * @version V1.0
 */
public interface RetrievePasswordContract {
    interface View extends BaseView {

        /**
         * 获取验证码成功
         *
         * @param str
         */
        void getYZMSuccess();

        /**
         * 验证手机号正确
         */
        void verifyPhoneSuress();

        /**
         * 修改成功
         */
        void forwordPasswordSuress();

    }

    interface Presenter extends BasePresenter<View> {

    }

}