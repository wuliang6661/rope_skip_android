package com.habit.star.utils;

import android.net.Uri;

import com.habit.star.R;
import com.habit.star.app.App;
import com.habit.star.model.http.RetrofitHelper;
import com.habit.star.model.http.exception.ApiException;
import com.habit.star.model.http.response.ResultSet;
import com.habit.star.ui.mine.bean.FileBean;

import java.io.File;

import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * 创建日期：7/4/2018 10:11 AM
 *
 * @author dongdong
 * @version 1.0
 * @since
 * 文件名称： FiltPresenter.java
 * 类说明：
 */
public class FiltPresenter{
//    /**
//     * 上传图片文件带有默认阈值
//     *
//     * @param fileUri
//     * @param successAction
//     * @param errorAction
//     * @return
//     */
//    public static Subscription uploadImageFile(final Uri fileUri, Action1<String> successAction, Action1<Throwable> errorAction) {
//        Subscription subscription;
//        final File originalfile = FileUtil.from(App.getInstance(), fileUri);
//        if (originalfile == null) {
//            subscription = Observable.just("").flatMap(new Func1<String, Observable<String>>() {
//                @Override
//                public Observable<String> call(String bean) {
//                    return Observable.error(new ApiException(-1, App.getStringResource(R.string.error_upload_file), ""));
//                }
//            }).compose(RxUtil.<String>rxSchedulerHelper()).subscribe(successAction, errorAction);
//            return subscription;
//        }
//
//        if (originalfile.length() > 524288) {
//            subscription = App.getInstance().imageCompressor
//                    .compressToFileAsObservable(originalfile)
//                    .flatMap(new Func1<File, Observable<ResultSet<String>>>() {
//                        @Override
//                        public Observable<ResultSet<String>> call(File file) {
//                            return RetrofitHelper.getInstance().upLoadImage(file);
//                        }
//                    })
//                    .compose(RxUtil.<ResultSet<String>>rxSchedulerHelper())
//                    .compose(RxUtil.<String>handleResult())
//                    .subscribe(successAction, errorAction);
//        } else {
//            subscription = RetrofitHelper.getInstance().upLoadImage(originalfile)
//                    .compose(RxUtil.<ResultSet<String>>rxSchedulerHelper())
//                    .compose(RxUtil.<String>handleResult())
//                    .subscribe(successAction, errorAction);
//        }
//        return subscription;
//    }

//    /**
//     * 上传图片文件带有默认阈值
//     *
//     * @param fileUri
//     * @param successAction
//     * @param errorAction
//     * @return
//     */
//    public static Subscription upLoadImage(final Uri fileUri, Action1<FileBean> successAction, Action1<Throwable> errorAction) {
//        Subscription subscription;
//        if (fileUri == null) {
//            return null;
//        }
//        final File originalfile = FileUtil.from(App.getInstance(), fileUri);
//        if (originalfile == null || App.getInstance().imageCompressor == null) {
//            subscription = Observable.just(new FileBean()).flatMap(new Func1<FileBean, Observable<ResultSet<FileBean>>>() {
//                @Override
//                public Observable<ResultSet<FileBean>> call(FileBean bean) {
//                    return Observable.error(new ApiException(-1, App.getStringResource(R.string.error_upload_file), ""));
//                }
//            }).compose(RxUtil.<ResultSet<FileBean>>rxSchedulerHelper())
//                    .compose(RxUtil.<FileBean>handleResult()).subscribe(successAction, errorAction);
//            return subscription;
//        }
//        if (originalfile.length() > 524288) {
//            subscription = App.getInstance().imageCompressor
//                    .compressToFileAsObservable(originalfile)
//                    .flatMap(new Func1<File, Observable<ResultSet<FileBean>>>() {
//                        @Override
//                        public Observable<ResultSet<FileBean>> call(File file) {
//                            if (file == null) {
//                                return null;
//                            }
//                            return RetrofitHelper.getInstance().upBasePic(file);
//                        }
//                    })
//                    .compose(RxUtil.<ResultSet<FileBean>>rxSchedulerHelper())
//                    .compose(RxUtil.<FileBean>handleResult())
//                    .subscribe(successAction, errorAction);
//        } else {
//            subscription = RetrofitHelper.getInstance().upBasePic(originalfile)
//                    .compose(RxUtil.<ResultSet<FileBean>>rxSchedulerHelper())
//                    .compose(RxUtil.<FileBean>handleResult())
//                    .subscribe(successAction, errorAction);
//        }
//        return subscription;
//    }
}