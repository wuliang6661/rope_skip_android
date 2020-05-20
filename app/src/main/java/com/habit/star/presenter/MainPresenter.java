package com.habit.star.presenter;

import com.habit.star.app.App;
import com.habit.star.app.Constants;
import com.habit.star.base.RxPresenter;
import com.habit.star.presenter.contract.MainContract;
import com.habit.star.utils.StringUtils;
import com.habit.star.utils.blue.bleutils.BlueUtils;
import com.inuker.bluetooth.library.search.SearchResult;

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
        String MAC = App.spUtils.getString(Constants.MAC);
        if (StringUtils.isEmpty(MAC)) {
            return;
        }
        BlueUtils blueUtils = BlueUtils.getInstance();
        if (blueUtils.isConnect()) {
            return;
        }
        blueUtils.setListener(new BlueUtils.onBlueListener() {
            @Override
            public void onConnect(boolean isConnect) {

            }

            @Override
            public void searchStart() {

            }

            @Override
            public void searchStop() {

            }

            @Override
            public void searchMacs(SearchResult result) {

            }
        });
        blueUtils.connectMac(MAC);
    }

}