package com.habit.star.ui.mine.contract;


import com.habit.star.base.BasePresenter;
import com.habit.star.base.BaseView;
import com.habit.star.pojo.po.UserBO;

/**
 * @date:  2020-02-16 12:19
 * @ClassName: FeedBackContract.java
 * @Description:
 * @author: sundongdong
 * @version V1.0
 */
public interface PersonalDataContract {
    interface View extends BaseView {

        void getUserInfo(UserBO userBO);
    }

    interface Presenter extends BasePresenter<View> {

    }

}