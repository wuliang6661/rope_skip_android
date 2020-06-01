package com.tohabit.skip.ui.mine.contract;


import com.tohabit.skip.base.BasePresenter;
import com.tohabit.skip.base.BaseView;
import com.tohabit.skip.pojo.po.FamilyUserDetailsBO;

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