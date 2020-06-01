package com.tohabit.skip.ui.login.presenter;

import android.content.Context;
import android.graphics.Bitmap;

import com.tohabit.skip.R;
import com.tohabit.skip.api.HttpResultSubscriber;
import com.tohabit.skip.api.HttpServerImpl;
import com.tohabit.skip.app.App;
import com.tohabit.skip.base.RxPresenter;
import com.tohabit.skip.model.http.RetrofitHelper;
import com.tohabit.skip.model.http.exception.ActionError;
import com.tohabit.skip.pojo.po.UserBO;
import com.tohabit.skip.ui.login.contract.SplashContract;
import com.tohabit.skip.utils.AppDirUtil;
import com.tohabit.skip.utils.RxUtil;

import javax.inject.Inject;

import id.zelory.compressor.Compressor;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action1;

/**
 * 创建日期：2018/6/2 9:21
 *
 * @author sundongdong
 * @version 1.0
 * @since 文件名称： SplashPresenter.java
 * 类说明：启动页Presenter
 */
public class SplashPresenter extends RxPresenter<SplashContract.View> implements SplashContract.Presenter {
    private final String TAG = "SplashPresenter";
    RetrofitHelper mRetrofitHelper;

    @Inject
    public SplashPresenter(RetrofitHelper retrofitHelper) {
        mRetrofitHelper = retrofitHelper;
    }

    @Override
    public void initComponent(final Context mContext) {
        Subscription subscription = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                //初始化图片压缩器
                App.getInstance().imageCompressor = new Compressor(mContext)
                        .setMaxWidth(768)
                        .setMaxHeight(1280)
                        .setQuality(90)
                        .setCompressFormat(Bitmap.CompressFormat.JPEG)
                        .setDestinationDirectoryPath(AppDirUtil.getImageFilesDir().getAbsolutePath());

                subscriber.onNext("");
                subscriber.onCompleted();
            }
        }).compose(RxUtil.<String>rxSchedulerHelper()).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                mView.setInitComponentSuccess();
            }
        }, new ActionError(App.getStringResource(R.string.error_init_failed)) {
            @Override
            public void onError(String msg, int code) {
                mView.setInitComponentFailed();
            }
        });
        addSubscrebe(subscription);
    }


    @Override
    public void getUserInfo() {
        HttpServerImpl.tokenLogin().subscribe(new HttpResultSubscriber<UserBO>() {
            @Override
            public void onSuccess(UserBO s) {
                if (mView != null) {
                    mView.getUserInfo(s);
                }
            }

            @Override
            public void onFiled(String message) {
                if (mView != null) {
                    mView.getUserInfoError();
                }
            }
        });
    }
}