package com.habit.star.presenter;

import com.habit.star.base.RxPresenter;
import com.habit.star.presenter.contract.MainContract;

import javax.inject.Inject;

/**
 * 创建日期：7/4/2018 10:11 AM
 *
 * @author dongdong
 * @version 1.0
 * @since
 * 文件名称： MainPresenter.java
 * 类说明：
 */
public class MainPresenter extends RxPresenter<MainContract.View> implements MainContract.Presenter {
    @Inject
    public MainPresenter() {
    }
}