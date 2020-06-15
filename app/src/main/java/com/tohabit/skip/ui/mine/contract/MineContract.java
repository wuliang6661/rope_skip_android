package com.tohabit.skip.ui.mine.contract;

import com.tohabit.skip.base.BasePresenter;
import com.tohabit.skip.base.BaseView;
import com.tohabit.skip.pojo.po.DeviceBO;
import com.tohabit.skip.pojo.po.DeviceLinkBO;
import com.tohabit.skip.pojo.po.UserBO;

import java.util.List;

/**
 * 创建日期：2018/6/1 9:20
 *
 * @author dongdong
 * @version 1.0
 * @since 文件名称： MineContract.java
 * 类说明：
 */
public interface MineContract {
    interface View extends BaseView {
//        void getUserInfo(UserInfoMode userInfoMode);

        void getUserInfo(UserBO userBO);

        void getLinkDevice(List<DeviceBO> deviceBO);

        void getDeviceData(DeviceLinkBO linkBO);
    }

    interface Presenter extends BasePresenter<View> {

//        void getUserInfo();
    }

}