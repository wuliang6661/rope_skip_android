/**
 * @Copyright: HangZhou hzhikbt System Technology Co., Ltd. All Right Reserved.
 * @address: http://ic_pyq.hzhikbt.com
 * @date: 2018/1/3 15:25
 * @Description: 本内容仅限于杭州海康威视数字技术系统公司内部使用，禁止转发。
 */

package com.habit.star.model.http.interceptor;

import com.habit.star.model.http.PgResponseBody;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * 创建日期：2018/4/10 10:01
 *
 * @author sundongdong
 * @version 1.0
 * @since 文件名称： ProgressInterceptor.java
 * 类说明： 下载文件时进度提醒的拦截器
 */
public class ProgressInterceptor implements Interceptor {
    private final String TAG = "ProgressInterceptor";

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());
//        Alog.e(TAG,"originalResponse.body().contentLength=="+originalResponse.body().contentLength());
        return originalResponse.newBuilder()
                .body(new PgResponseBody(originalResponse.body()))
                .build();
    }

}


