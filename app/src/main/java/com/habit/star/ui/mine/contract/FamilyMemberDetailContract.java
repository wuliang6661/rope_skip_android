package com.habit.star.ui.mine.contract;


import com.habit.star.base.BasePresenter;
import com.habit.star.base.BaseView;
import com.habit.star.pojo.po.FamilyUserBO;
import com.habit.star.pojo.po.FamilyUserDetailsBO;

import java.util.List;

/**
 * 创建日期：2018/7/2 16:49
 * @author dongdong
 * @version 1.0
 * @since
 * 文件名称： FamilyMemberDetailContract.java
 * 类说明：
 */
public interface FamilyMemberDetailContract {
    interface View extends BaseView {

        void getUserDetails(FamilyUserDetailsBO detailsBO);

    }

    interface Presenter extends BasePresenter<View> {

    }

}