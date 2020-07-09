package com.tohabit.skip.api.rx;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.blankj.utilcode.util.ToastUtils;
import com.tohabit.skip.api.DialogCallException;
import com.tohabit.skip.app.App;
import com.tohabit.skip.app.RouterConstants;
import com.tohabit.skip.pojo.BaseResult;
import com.tohabit.skip.ui.login.activity.LoginActivity;
import com.tohabit.skip.utils.AppManager;

import java.util.Timer;
import java.util.TimerTask;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 作者 by wuliang 时间 16/11/24.
 */

public class RxResultHelper {
    private static final String TAG = "RxResultHelper";

    public static <T> Observable.Transformer<BaseResult<T>, T> httpRusult() {
        return apiResponseObservable -> apiResponseObservable.flatMap(
                (Func1<BaseResult<T>, Observable<T>>) mDYResponse -> {
                    Log.d(TAG, "call() called with: mDYResponse = [" + mDYResponse + "]");
                    if (mDYResponse.surcess()) {
                        return createData(mDYResponse.getData());
                    } else if (mDYResponse.getCode() == 421) {   //重新登录
                        App.spUtils.remove("token");
                        App.token = null;
                        Activity activity = AppManager.getAppManager().curremtActivity();
                        Intent intent = new Intent(activity, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.putExtra(RouterConstants.ARG_MODE, LoginActivity.FLAG_LOGIN_TAG);
                        showToast(mDYResponse.getMsg());
                        AppManager.getAppManager().finishAllActivity();
                        activity.startActivity(intent);
                        return createData(null);
                    } else if (mDYResponse.getCode() == 398) {  //可拨打电话的弹窗
                        return Observable.error(new DialogCallException(mDYResponse.getMsg()));
                    } else if (mDYResponse.getCode() == 401) {
                        App.spUtils.remove("token");
                        App.token = null;
                        Activity activity = AppManager.getAppManager().curremtActivity();
                        Intent intent = new Intent(activity, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.putExtra(RouterConstants.ARG_MODE, LoginActivity.FLAG_LOGIN_TAG);
                        showToast(mDYResponse.getMsg());
                        AppManager.getAppManager().finishAllActivity();
                        activity.startActivity(intent);
                        return createData(null);
                    } else {
                        return Observable.error(new RuntimeException(mDYResponse.getMsg()));
                    }
                }
        ).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }


    private static <T> Observable<T> createData(final T t) {
        return Observable.create(subscriber -> {
            try {
                subscriber.onNext(t);
                subscriber.onCompleted();
            } catch (Exception e) {
                subscriber.onError(e);
            }
        });
    }

    private static boolean isToast = false;

    private static  synchronized void showToast(String msg){
        if(!isToast){
            isToast = true;
            ToastUtils.showShort(msg);
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    isToast = false;
                }
            },2000);
        }
    }

}
