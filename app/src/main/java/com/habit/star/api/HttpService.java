package com.habit.star.api;

import com.habit.star.pojo.BaseResult;
import com.habit.star.pojo.po.UserBO;

import java.util.Map;

import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by wuliang on 2017/3/9.
 * <p>
 * 此处存放后台服务器的所有接口数据
 */

public interface HttpService {

    String URL = "http://47.116.77.29:8080/rope_skipping_webservice/";

    /**
     * 密码登录接口
     */
    @POST("app/user/passwordLogin")
    Observable<BaseResult<UserBO>> passwordLogin(@Body Map<String, Object> params);

    /**
     * 注册接口
     */
    @POST("app/user/register")
    Observable<BaseResult<String>> regiest(@Body Map<String, Object> params);

    /**
     * 发送验证码
     */
    @POST("app/user/sendCode")
    Observable<BaseResult<String>> sendCode(@Body Map<String, Object> params);

    /**
     * 验证手机号接口
     */
    @POST("app/user/verifyPhone")
    Observable<BaseResult<String>> verifyPhone(@Body Map<String, Object> params);

    /**
     * 忘记密码
     */
    @POST("app/user/forgetPassword")
    Observable<BaseResult<String>> forgetPassword(@Body Map<String, Object> params);

    /**
     * 自动token登录
     */
    @POST("app/user/tokenLogin")
    Observable<BaseResult<UserBO>> tokenLogin();


    /**
     * 修改用户名称
     */
    @POST("app/user/updateNickName")
    Observable<BaseResult<String>> updateNickName(@Body Map<String, Object> params);


}
