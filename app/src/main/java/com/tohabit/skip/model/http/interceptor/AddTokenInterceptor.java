/**
 * @Copyright: HangZhou hzhikbt System Technology Co., Ltd. All Right Reserved.
 * @address: http://ic_pyq.hzhikbt.com
 * @date: 2018/1/3 15:25
 * @Description: 本内容仅限于杭州海康威视数字技术系统公司内部使用，禁止转发。
 */

package com.tohabit.skip.model.http.interceptor;

import android.text.TextUtils;
import android.util.Log;

import com.tohabit.skip.app.App;
import com.tohabit.skip.app.Constants;
import com.tohabit.skip.utils.PrefUtils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 创建日期：2018/4/10 10:04
 *
 * @author sundongdong
 * @version 1.0
 * @since 文件名称： AddCookiesInterceptor.java
 * 类说明：
 */
public class AddTokenInterceptor implements Interceptor {
    private final String TAG = "okhttp";

    @Override
    public Response intercept(Chain chain) throws IOException {
        if (chain != null && chain.request() != null) {
            Request.Builder builder = chain.request().newBuilder();
            if (builder.build() != null) {
                String token = PrefUtils.getPrefString(App.getInstance().context,Constants.PREF_KEY_TOKEN,"");
                if (!TextUtils.isEmpty(token)){
                    builder.addHeader("Cookie", "token="+token);
                }
                Log.d(TAG, "intercept: "+token);

//                for (String cookie : RetrofitHelper.cookies) {

//                    Alog.i(TAG, "AddTokenInterceptor token is: " + cookie);
//                    Alog.i(TAG, "token size: " + RetrofitHelper.cookies.size());
//                    if (cookie != null) {
//                        builder.addHeader("Authorization", cookie);
////                        Alog.i(TAG, "addHeader,Authorization: " + cookie);
//                    }
//                }
                return chain.proceed(builder.build());
            }
        }
        return null;
    }

}


