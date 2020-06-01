package com.tohabit.skip.presenter;

import android.bluetooth.BluetoothDevice;

import com.tohabit.skip.base.RxPresenter;
import com.tohabit.skip.presenter.contract.MainContract;
import com.tohabit.skip.utils.blue.OnSearchListenter;
import com.tohabit.skip.utils.blue.btutil.BlueDeviceUtils;

import javax.inject.Inject;

/**
 * 创建日期：7/4/2018 10:11 AM
 *
 * @author dongdong
 * @version 1.0
 * @since 文件名称： MainPresenter.java
 * 类说明：
 */
public class MainPresenter extends RxPresenter<MainContract.View> implements MainContract.Presenter {
    @Inject
    public MainPresenter() {
    }

    /**
     * 打开蓝牙成功之后，默认连接上次连接过的蓝牙
     */
    public void connectBlue() {
//        String MAC = App.spUtils.getString(Constants.MAC);
//        if (StringUtils.isEmpty(MAC)) {
//            return;
//        }
        BlueDeviceUtils blueDeviceUtils = BlueDeviceUtils.getInstance();
        blueDeviceUtils.setListener(new OnSearchListenter() {

            @Override
            public void searchStart() {

            }

            @Override
            public void searchDevices(BluetoothDevice device) {
                if(mView != null){
                    mView.getBlueDevice(device);
                }
            }

            @Override
            public void searchStop() {

            }
        });
        blueDeviceUtils.startScanBluth();
    }

}