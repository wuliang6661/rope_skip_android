package com.habit.star.ui.mine.contract;


import com.habit.star.base.BasePresenter;
import com.habit.star.base.BaseView;
import com.habit.star.pojo.po.AddressBO;

import java.util.List;

/**
 * @version V1.0
 * @date: 2020-02-16 12:19
 * @ClassName: FeedBackContract.java
 * @Description:
 * @author: sundongdong
 */
public interface MyAddressListContract {
    interface View extends BaseView {

        void getAddressList(List<AddressBO> list);

        void defaltAddressSourcess();

        void deleteAddressSouress();
    }

    interface Presenter extends BasePresenter<View> {

    }

}