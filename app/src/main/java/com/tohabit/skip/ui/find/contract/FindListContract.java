package com.tohabit.skip.ui.find.contract;


import com.tohabit.skip.base.BasePresenter;
import com.tohabit.skip.base.BaseView;
import com.tohabit.skip.ui.find.bean.FindModel;

import java.util.List;


/**
 * @date:  2020-02-26 23:17
 * @ClassName: FindListContract.java
 * @Description:
 * @author: sundongdong
 * @version V1.0
 */
public interface FindListContract {
    interface View extends BaseView {

        /**
         * 设置数据
         * @param data
         */
        void setList(List<FindModel> data);

    }

    interface Presenter extends BasePresenter<View> {
        /**
         * 获取数据
         */
        void getList(String type);
    }

}