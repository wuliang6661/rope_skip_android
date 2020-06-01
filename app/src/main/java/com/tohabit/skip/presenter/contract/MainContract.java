package com.tohabit.skip.presenter.contract;


import android.bluetooth.BluetoothDevice;

import com.tohabit.skip.base.BasePresenter;
import com.tohabit.skip.base.BaseView;

/**
 * 创建日期：2018/7/2 16:49
 * @author dongdong
 * @version 1.0
 * @since
 * 文件名称： MainContract.java
 * 类说明：
 */
public interface MainContract {
    interface View extends BaseView {


        void getBlueDevice(BluetoothDevice device);
    }

    interface Presenter extends BasePresenter<View> {

    }

}