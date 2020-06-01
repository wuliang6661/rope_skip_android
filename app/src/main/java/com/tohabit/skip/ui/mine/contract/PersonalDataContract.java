package com.tohabit.skip.ui.mine.contract;


import com.tohabit.skip.base.BasePresenter;
import com.tohabit.skip.base.BaseView;
import com.tohabit.skip.pojo.po.UserBO;

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