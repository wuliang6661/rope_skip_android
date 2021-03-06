package com.tohabit.skip.ui.mine.contract;


import com.tohabit.skip.base.BasePresenter;
import com.tohabit.skip.base.BaseView;
import com.tohabit.skip.pojo.po.FeedBackBO;

import java.util.List;

/**
 * @version V1.0
 * @date: 2020-02-16 12:19
 * @ClassName: FeedBackContract.java
 * @Description:
 * @author: sundongdong
 */
public interface FeedBackContract {
    interface View extends BaseView {

        void getFeedBack(List<FeedBackBO> feedBackBOS);

        void addSouress();

    }

    interface Presenter extends BasePresenter<View> {

    }

}