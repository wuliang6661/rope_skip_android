package com.tohabit.skip.base;

/**
 * Created by sundongdong on 2017/2/24.
 * Presenter基类
 */
public interface BasePresenter<T extends BaseView> {

    void attachView(T view);

    void detachView();
}