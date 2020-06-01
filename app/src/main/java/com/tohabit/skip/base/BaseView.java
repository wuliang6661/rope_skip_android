package com.tohabit.skip.base;

/**
 * Created by sundongdong on 2017/2/24.
 * View基类
 */
public interface BaseView {

    void showProgress();

    void hideProgress();

    void showError(String msg);

    void showError(int errorCode);

}
