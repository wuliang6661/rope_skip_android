package com.tohabit.skip.ui.train.contract;


import com.tohabit.skip.base.BasePresenter;
import com.tohabit.skip.base.BaseView;


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