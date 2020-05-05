package com.habit.star.model.http.exception;

import android.text.TextUtils;

import com.habit.star.R;
import com.habit.star.app.App;

import rx.functions.Action1;

/**
 * Created by sundongdong on 2017/2/24.
 * RxJava 处理错误基类，封装了消息提示
 */
public abstract class ActionError implements Action1<Throwable> {
    String defaultMsg;
    final String TAG = "ActionError";

    public ActionError() {
        defaultMsg = App.getStringResource(R.string.processing_failed);
    }

    public ActionError(String defaultMsg) {
        this.defaultMsg = defaultMsg;
    }

    @Override
    public void call(Throwable throwable) {
        if (throwable != null) {
//            Alog.logError(TAG, throwable);
            throwable.printStackTrace();
        }
        if (throwable instanceof ApiException) {
            if (!TextUtils.isEmpty(((ApiException) throwable).data)) {
                if (!TextUtils.isEmpty(((ApiException) throwable).message)) {
                    onError(((ApiException) throwable).message, ((ApiException) throwable).code, ((ApiException) throwable).data);
                } else {
                    onError(defaultMsg, ((ApiException) throwable).code, ((ApiException) throwable).data);
                }
                return;
            }

            if (!TextUtils.isEmpty(((ApiException) throwable).message)) {
                onError(((ApiException) throwable).message, ((ApiException) throwable).code);
            } else {
                onError(defaultMsg, ((ApiException) throwable).code);
            }
        } else {
            onError(defaultMsg, -1);
        }
    }

    public void onError(String msg) {

    }

    public abstract void onError(String msg, int code);

    public void onError(String msg, int code, String data) {

    }
}
