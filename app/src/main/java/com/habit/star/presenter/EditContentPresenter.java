package com.habit.star.presenter;


import com.habit.star.base.RxPresenter;
import com.habit.star.presenter.contract.EditContentContract;

import javax.inject.Inject;

/**
 * 创建日期：2018/6/23 10:25
 * @author sundongdong
 * @version 1.0
 * @since
 * 文件名称： EditContentPresenter.java
 * 类说明：编辑内容Presenter
 */
public class EditContentPresenter extends RxPresenter<EditContentContract.View> implements EditContentContract.Presenter {
    @Inject
    public EditContentPresenter() {}

}