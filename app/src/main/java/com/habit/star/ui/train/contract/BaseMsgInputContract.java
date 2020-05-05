package com.habit.star.ui.train.contract;


import com.habit.star.base.BasePresenter;
import com.habit.star.base.BaseView;
import com.habit.star.ui.train.bean.TranRecordModel;

import java.util.List;


/**
 * @date:  2020-02-11 12:22
 * @ClassName: BaseMsgInputContract.java
 * @Description:
 * @author: sundongdong
 * @version V1.0
 */
public interface BaseMsgInputContract {
    interface View extends BaseView {
    }

    interface Presenter extends BasePresenter<View> {
    }

}