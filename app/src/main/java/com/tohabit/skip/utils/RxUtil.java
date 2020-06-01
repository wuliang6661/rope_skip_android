package com.tohabit.skip.utils;

import android.util.Log;

import com.tohabit.skip.model.http.PgResponseBody;
import com.tohabit.skip.model.http.exception.ApiException;
import com.tohabit.skip.model.http.response.ResultSet;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.ResponseBody;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.exceptions.Exceptions;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by sundongdong on 2017/2/24.
 */
public class RxUtil {
    private static final String TAG = "RxUtil";

    public static String fileName;

    public static String getFileName() {
        return fileName;
    }

    //下载文件时要传入文件名
    public static void setFileName(String name) {
        fileName = name;
    }

    /**
     * 统一线程处理
     *
     * @param <T>
     * @return
     */
    public static <T> Observable.Transformer<T, T> rxSchedulerHelper() {    //compose简化线程
        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> observable) {
                return observable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    /**
     * 统一返回结果处理
     *
     * @param <T>
     * @return
     */
    public static <T> Observable.Transformer<ResultSet<T>, T> handleResult() {   //compose判断结果
        return new Observable.Transformer<ResultSet<T>, T>() {
            @Override
            public Observable<T> call(Observable<ResultSet<T>> httpResponseObservable) {
                return httpResponseObservable.flatMap(new Func1<ResultSet<T>, Observable<T>>() {
                    @Override
                    public Observable<T> call(ResultSet<T> response) {
                        if (response == null) {
                            Log.i(TAG, "response is null");
                            return Observable.error(new ApiException(-1, "response is null"));
                        }
                        Log.i(TAG, "response.message is: " + response.msg);
                        Log.i(TAG, "response.code is: " + response.code);
                        if (response.code == 0||response.code==200) {
                            return createData(response.data);
                        } else {
                            Log.i(TAG, "login over time. response.code in is" + response.code);
//                            //表示需要重新登录
//                            if (Constants.RESULT_CODE_SESSION_OVERTIME == response.code
//                                    || Constants.RESULT_CODE_SESSION_IS_EMPTY == response.code
//                                    || Constants.RESULT_CODE_USER_DISABLE == response.code) {
//                                App.getInstance().userInfoMode = null;
//                                App.getInstance().stopPush();
//                                TRouter.go(RouterConstants.LOGIN, new DataExtra(RouterConstants.HEAD_DATA, LoginActivity.FLAG_SPLASH_TAG).build(), Intent.FLAG_ACTIVITY_CLEAR_TASK);
//
////                                App.getInstance().getCurActivity().runOnUiThread(new Runnable() {
////                                    @Override
////                                    public void run() {
////                                        DiLogUtil.create(App.getInstance().getCurActivity()
////                                                , R.string.remind
////                                                , R.string.your_accout_is_login_in_another_place
////                                                , R.string.exit_login
////                                                , R.string.cancel
////                                                , new MaterialDiLog.SingleButtonCallback() {
////                                                    @Override
////                                                    public void onClick(@NonNull MaterialDiLog diLog, @NonNull DiLogAction which) {
////                                                        TRouter.go(RouterConstants.LOGIN, new DataExtra(RouterConstants.HEAD_DATA, LoginActivity.FLAG_LOGIN_TAG).build(), Intent.FLAG_ACTIVITY_CLEAR_TASK);
////                                                    }
////                                                }).show();
////                                    }
////                                });
//
//                            }
//                            Log.i(TAG, "login over time. response.code out is" + response.code);
//                            if (response.data instanceof String) {
//                                if (!TextUtils.isEmpty((String) response.data)) {
//                                    return Observable.error(new ApiException(response.code, response.msg, (String) response.data));
//                                } else {
//                                    return Observable.error(new ApiException(response.code, response.msg));
//                                }
//                            } else {
                                return Observable.error(new ApiException(response.code, response.msg));
//                            }
                        }
                    }
                });
            }
        };
    }

    public static void checkResultValid(){

    }

    /**
     * 统一返回结果处理:下载文件
     *
     * @param
     * @return 已下载文件的存储路径url
     */
    public static Observable.Transformer<ResponseBody, File> handleFileResult() {   //compose判断结果
        return new Observable.Transformer<ResponseBody, File>() {
            @Override
            public Observable<File> call(Observable<ResponseBody> httpResponseObservable) {
                return httpResponseObservable.flatMap(new Func1<ResponseBody, Observable<File>>() {
                    @Override
                    public Observable<File> call(ResponseBody response) {
                        File downFile = null;
                        downFile = AppDirUtil.getDownloadFile(fileName);
                        FileUtils.createNewFile(downFile);
                        FileOutputStream fos = null;

                        try {
                            InputStream is = response.byteStream();
                            fos = new FileOutputStream(downFile);
                            BufferedInputStream bis = new BufferedInputStream(is);
                            byte[] buffer = new byte[1024];
                            int len;
                            while ((len = bis.read(buffer)) != -1) {
                                fos.write(buffer, 0, len);
                                fos.flush();
                            }
                            fos.flush();
                            fos.close();
                            bis.close();
                            is.close();
                            PgResponseBody.setDownload(false);
                            return createData(downFile);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                            return Observable.error(new ApiException("下载文件出错"));
                        } catch (IOException e) {
                            e.printStackTrace();
                            if (null != fos) {
                                try {
                                    fos.close();
                                    fos = null;
                                } catch (IOException e1) {
                                    e1.printStackTrace();
                                }
                            }
                            return Observable.error(new ApiException("下载文件出错"));
                        }


                    }
                });
            }
        };
    }

    /**
     * 生成Observable
     *
     * @param <T>
     * @return
     */
    public static <T> Observable<T> createData(final T t) {
        return Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(Subscriber<? super T> subscriber) {
                try {
                    subscriber.onNext(t);
                    subscriber.onCompleted();
                } catch (Exception e) {
                    try {
                        Log.d(TAG, "createData-->onError");
                        subscriber.onError(e);
                    } catch (Throwable e2) {
                        Log.d(TAG, "Error occurred attempting to subscribe [" + e.getMessage() +
                                "] and then again while trying to pass to onError." + e2.toString());
                        Exceptions.throwIfFatal(e2);
                    }
                }
            }
        });
    }
}
