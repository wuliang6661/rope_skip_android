package com.tohabit.skip.ui.mine.contract;


import com.tohabit.skip.base.BasePresenter;
import com.tohabit.skip.base.BaseView;
import com.tohabit.skip.pojo.po.MessageBO;

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
        void getList(String type,int pageNum);
    }

}