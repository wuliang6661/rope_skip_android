/**
 * @Copyright: HangZhou hzhikbt System Technology Co., Ltd. All Right Reserved.
 * @address: http://ic_pyq.hzhikbt.com
 * @date: 2018/1/3 15:25
 * @Description: 本内容仅限于杭州海康威视数字技术系统公司内部使用，禁止转发。
 */

package com.habit.star.model.http.interceptor;

import com.habit.star.utils.SystemUtil;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 创建日期：2018/4/10 10:11
 *
 * @author sundongdong
 * @version 1.0
 * @since 文件名称： CacheInterceptor.java
 * 类说明：
 */
public class CacheInterceptor implements Interceptor {
    private final String TAG = "CacheInterceptor";

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        if (!SystemUtil.isNetworkConnected()) {
            request = request.newBuilder()
                    .cacheControl(CacheControl.FORCE_CACHE)
                    .build();
        }
        Response response = chain.proceed(request);
        if (SystemUtil.isNetworkConnected()) {
            int maxAge = 0;
            // 有网络时, 不缓存, 最大保存时长为0
            response.newBuilder()
                    .header("Cache-Control", "public, max-age=" + maxAge)
                    .removeHeader("Pragma")
                    .build();
        } else {
            // 无网络时，设置超时为4周
            int maxStale = 60 * 60 * 24 * 28;
            response.newBuilder()
                    .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                    .removeHeader("Pragma")
                    .build();
        }
        return response;
    }

}


