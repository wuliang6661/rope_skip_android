package com.habit.star.ui.mine.contract;


import com.habit.star.base.BasePresenter;
import com.habit.star.base.BaseView;
import com.habit.star.pojo.po.FamilyUserBO;

import java.util.List;

/**
 * 创建日期：2018/7/2 16:49
 * @author dongdong
 * @version 1.0
 * @since
 * 文件名称： FamilyMemberContract.java
 * 类说明：
 */
public interface FamilyMemberContract {
    interface View extends BaseView {

        void getAllUserBO(List<FamilyUserBO> list);


    }

    interface Presenter extends BasePresenter<View> {

    }

}