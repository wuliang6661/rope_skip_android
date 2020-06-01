package com.tohabit.skip.ui.mine.contract;


import com.tohabit.skip.base.BasePresenter;
import com.tohabit.skip.base.BaseView;
import com.tohabit.skip.pojo.po.FamilyUserBO;

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