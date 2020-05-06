package com.habit.star.api.rx;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.habit.star.api.DialogCallException;
import com.habit.star.app.App;
import com.habit.star.pojo.BaseResult;
import com.habit.star.ui.activity.MainActivity;
import com.habit.star.utils.AppManager;
import com.habit.star.utils.ToastUtil;

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
                        if (activity instanceof MainActivity) {
                            return Observable.error(new RuntimeException(mDYResponse.getMsg()));
                        }
                        Intent intent = new Intent(activity, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        ToastUtil.show("登录过期，请重新登录");
                        AppManager.getAppManager().finishAllActivity();
                        activity.startActivity(intent);
                        return Observable.error(new RuntimeException("登录过期，请重新登录"));
                    } else if (mDYResponse.getCode() == 398) {  //可拨打电话的弹窗
                        return Observable.error(new DialogCallException(mDYResponse.getMsg()));
                    } else if (mDYResponse.getCode() == 401) {
                        App.spUtils.remove("token");
                        App.token = null;
                        Activity activity = AppManager.getAppManager().curremtActivity();
                        if (activity instanceof MainActivity) {
                            return Observable.error(new RuntimeException(mDYResponse.getMsg()));
                        }
                        Intent intent = new Intent(activity, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        ToastUtil.show("登录过期，请重新登录");
                        AppManager.getAppManager().finishAllActivity();
                        activity.startActivity(intent);
                        return Observable.error(new RuntimeException("登录过期，请重新登录"));
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
}
