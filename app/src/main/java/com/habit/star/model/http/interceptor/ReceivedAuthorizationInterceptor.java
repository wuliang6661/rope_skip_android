/**
 * @Copyright: HangZhou hzhikbt System Technology Co., Ltd. All Right Reserved.
 * @address: http://ic_pyq.hzhikbt.com
 * @date: 2018/1/3 15:25
 * @Description: 本内容仅限于杭州海康威视数字技术系统公司内部使用，禁止转发。
 */

package com.habit.star.model.http.interceptor;

import com.habit.star.model.http.RetrofitHelper;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * 创建日期：2018/4/10 10:01
 *
 * @author sundongdong
 * @version 1.0
 * @since 文件名称： ReceivedCookiesInterceptor.java
 * 类说明： 接收网络请求的Token（目前放在header中的Authorization key值）
 */
public class ReceivedAuthorizationInterceptor implements Interceptor {
    private final String TAG = "okhttp";

    @Override
    public Response intercept(Chain chain) throws IOException {
        if (chain != null && chain.request() != null) {
            Response originalResponse = chain.proceed(chain.request());
            if (!originalResponse.headers("Authorization").isEmpty()) {
                if (RetrofitHelper.cookies != null) {
                    for (String header : originalResponse.headers("Authorization")) {
                        if (header != null && originalResponse.networkResponse() != null && originalResponse
                                .networkResponse().toString() != null && originalResponse.networkResponse()
                                .toString().contains(RetrofitHelper.HOST_URL)) {
                            RetrofitHelper.cookies.clear();
                            String headerTemp = header.substring(0, header.indexOf(";"));
                            RetrofitHelper.cookies.add(headerTemp);
//                            Alog.i(TAG, "tokens.add:" + headerTemp);
                        }
                    }
                }
            }
            return originalResponse;
        }
        return null;
    }

}


