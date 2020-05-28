package com.habit.star.ui.train.presenter;

import com.habit.star.api.HttpResultSubscriber;
import com.habit.star.api.HttpServerImpl;
import com.habit.star.base.RxPresenter;
import com.habit.star.model.http.RetrofitHelper;
import com.habit.star.pojo.po.BeatsBO;
import com.habit.star.pojo.po.MusicBO;
import com.habit.star.ui.train.contract.RopeSkipSettingContract;

import java.util.List;

import javax.inject.Inject;


/**
 * @version V1.0
 * @date: 2020-02-12 15:43
 * @ClassName: RoseSkipSettingPresenter.java
 * @Description:
 * @author: sundongdong
 */
public class RoseSkipSettingPresenter extends RxPresenter<RopeSkipSettingContract.View> implements RopeSkipSettingContract.Presenter {

    RetrofitHelper mRetrofitHelper;

    @Inject
    public RoseSkipSettingPresenter(RetrofitHelper retrofitHelper) {
        mRetrofitHelper = retrofitHelper;
    }


    /**
     * 查询所有节拍
     */
    public void getBeats() {
        HttpServerImpl.getBeats().subscribe(new HttpResultSubscriber<List<BeatsBO>>() {
            @Override
            public void onSuccess(List<BeatsBO> s) {
                if(mView != null){
                    mView.getBeats(s);
                }
            }

            @Override
            public void onFiled(String message) {
                if (mView != null) {
                    mView.showError(message);
                }
            }
        });
    }


    /**
     * 查询所有系统音乐
     */
    public void getMusicList() {
        HttpServerImpl.getMusicList().subscribe(new HttpResultSubscriber<List<MusicBO>>() {
            @Override
            public void onSuccess(List<MusicBO> s) {
                if(mView != null){
                    mView.getMusics(s);
                }
            }

            @Override
            public void onFiled(String message) {
                if (mView != null) {
                    mView.showError(message);
                }
            }
        });
    }

}