package com.habit.star.ui.mine.contract;


import com.habit.star.base.BasePresenter;
import com.habit.star.base.BaseView;
import com.habit.star.pojo.po.MessageBO;
import com.habit.star.ui.mine.bean.MessageModel;
import com.habit.star.ui.train.bean.ImprovePlanModel;

import java.util.List;


/**
 * @date:  2020-04-23 22:16
 * @ClassName: MessageListContract.java
 * @Description:
 * @author: sundongdong
 * @version V1.0
 */
public interface MessageListContract {
    interface View extends BaseView {

        /**
         * 设置数据
         * @param data
         */
        void setList(List<MessageBO> data);

    }

    interface Presenter extends BasePresenter<View> {
        /**
         * 获取数据
         */
        void getList(String type);
    }

}