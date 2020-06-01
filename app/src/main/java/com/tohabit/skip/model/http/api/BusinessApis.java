package com.tohabit.skip.model.http.api;

import android.database.Observable;

import com.tohabit.skip.model.http.response.ResultSet;
import com.tohabit.skip.ui.login.bean.UserInfoModel;
import com.tohabit.skip.ui.mine.bean.FileBean;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * 创建日期：2018/7/2 13:53
 *
 * @author sundongdong
 * @version 1.0
 * @since 文件名称： BusinessApis.java
 * 类说明：业务网络请求接口管理类
 */
//@FakeApiData
//@ApiFactory
public interface BusinessApis {

    //上传图片
    @Multipart
    @POST("rope_skipping_webservice/app/common/uploadImage")
    Observable<ResultSet<FileBean>> upLoadImage(@Part("img") RequestBody requestBody, @Part MultipartBody.Part part);




    /*用户模块*/

    /**
     * 生成二维码
     *
     * @return
     */
    @POST("rope_skipping_webservice/app/user/QRCode")
    Observable<ResultSet<String>> QRCode();

    /**
     * 忘记密码
     *
     * @return
     */
    @FormUrlEncoded
    @POST("rope_skipping_webservice/app/user/forgetPassword")
    Observable<ResultSet<String>> forgetPassword(@Field("phone") String phone, @Field("password") String password);

    /**
     * 获取用户信息
     *
     * @return
     */
    @GET("rope_skipping_webservice/app/user/getUserInfo")
    Observable<ResultSet<UserInfoModel>> getUserInfo();

    /**
     * 根据二维码返回的编码获取用户信息
     *
     * @return
     */
    @GET("rope_skipping_webservice/app/user/getUserInfoByCode")
    Observable<ResultSet<String>> getUserInfoByCode(@Query("userCode") String userCode);

    /**
     * 消息推送
     *
     * @return
     */
    @POST("rope_skipping_webservice/app/user/isDayPush")
    Observable<ResultSet<String>> isDayPush();

    /**
     * 用户登录
     *
     * @return
     */
    @FormUrlEncoded
    @POST("rope_skipping_webservice/app/user/passwordLogin")
    Observable<ResultSet<UserInfoModel>> login(@Field("phone") String phone, @Field("password") String password);

    /**
     * 注册账号
     *
     * @return
     */
    @FormUrlEncoded
    @POST("rope_skipping_webservice/app/user/register")
    Observable<ResultSet<String>> register(@Field("code") String code, @Field("isBuy") int isBuy, @Field("password") String password, @Field("phone") String phone);

    /**
     * 发送验证码
     *
     * @return
     */
    @FormUrlEncoded
    @POST("rope_skipping_webservice/app/user/sendCode")
    Observable<ResultSet<String>> sendCode(@Field("phone") String phone, @Field("type") String type);

    /**
     * 自动登录
     *
     * @return
     */
    @FormUrlEncoded
    @POST("rope_skipping_webservice/app/user/tokenLogin")
    Observable<ResultSet<UserInfoModel>> tokenLogin();

    /**
     * 修改用户昵称
     *
     * @return
     */
    @FormUrlEncoded
    @POST("rope_skipping_webservice/app/user/updateNickName")
    Observable<ResultSet<String>> updateNickName(@Field("nickName") String nickName);

    /**
     * 修改密码
     *
     * @return
     */
    @FormUrlEncoded
    @POST("rope_skipping_webservice/app/user/updatePassword")
    Observable<ResultSet<String>> updatePassword(@Field("newPassword") String newPassword, @Field("oldPassword") String oldPassword);

    /**
     * 修改手机号
     *
     * @return
     */
    @FormUrlEncoded
    @POST("rope_skipping_webservice/app/user/updatePhone")
    Observable<ResultSet<String>> updatePhone(@Field("code") String code, @Field("phone") String phone);


    /**
     * 上传用户头像
     *
     * @param requestBody
     * @param part
     * @return
     */
    @Multipart
    @POST("rope_skipping_webservice/app/user/uploadHeadImage")
    Observable<ResultSet<String>> uploadHeadImage(@Part("img") RequestBody requestBody, @Part MultipartBody.Part part);

    /**
     * 验证手机号
     *
     * @return
     */
    @FormUrlEncoded
    @POST("rope_skipping_webservice/app/user/verifyPhone")
    Observable<ResultSet<String>> verifyPhone(@Field("code") String code, @Field("phone") String phone);

    /**
     * 验证身份
     *
     * @return
     */
    @FormUrlEncoded
    @POST("rope_skipping_webservice/app/user/verifyUserInfo")
    Observable<ResultSet<String>> verifyUserInfo(@Field("code") String code, @Field("phone") String phone);


    /////消息中心/////

    /**
     * 获取我的消息
     *
     * @return
     */
    @GET("rope_skipping_webservice/app/my/message/getMessageList")
    Observable<ResultSet<String>> getMessageList(@Query("pageNum") int pageNum, @Query("pageSize") int pageSize);

    /**
     * 获取消息未读条数
     *
     * @return
     */
    @GET("rope_skipping_webservice/app/my/message/getNoReadMessageCount")
    Observable<ResultSet<String>> getNoReadMessageCount();

    /////////我的设备//////////

    /**
     * 删除跳绳设备
     *
     * @return
     */
    @FormUrlEncoded
    @POST("rope_skipping_webservice/app/my/device/delDevice")
    Observable<ResultSet<String>> delDevice(@Field("id") String id);

    /**
     * 删除跳绳设备
     *
     * @return
     */
    @GET("rope_skipping_webservice/app/my/device/delDevice")
    Observable<ResultSet<String>> getDevice(@Query("id") String id);

    /**
     * 删除跳绳设备
     *
     * @return
     */
    @GET("rope_skipping_webservice/app/my/device/getDeviceData")
    Observable<ResultSet<String>> getDeviceData();

    /**
     * 查询当前连接的设备
     *
     * @return
     */
    @GET("rope_skipping_webservice/app/my/device/getLinkDevice")
    Observable<ResultSet<String>> getLinkDevice();

    /**
     * 查询我的设备列表
     *
     * @return
     */
    @GET("rope_skipping_webservice/app/my/device/getDeviceList")
    Observable<ResultSet<String>> getDeviceList(@Query("pageNum") int pageNum, @Query("pageSize") int pageSize);

    /**
     * 保存设备信息
     *
     * @return
     */
    @FormUrlEncoded
    @POST("rope_skipping_webservice/app/user/saveDevice")
    Observable<ResultSet<String>> saveDevice(@Field("electricQuantity") String electricQuantity, @Field("id") String id, @Field("macAddress") String macAddress, @Field("name") String name);


    ////////家庭成员//////

    /**
     * 邀请家庭成员
     *
     * @return
     */
    @FormUrlEncoded
    @POST("rope_skipping_webservice/app/my/family/addFamilyUser")
    Observable<ResultSet<String>> addFamilyUser(@Field("id") String id);

    /**
     * 移除家庭成员
     *
     * @return
     */
    @FormUrlEncoded
    @POST("rope_skipping_webservice/app/my/family/delFamilyUser")
    Observable<ResultSet<String>> delFamilyUser(@Field("id") String id);

    /**
     * 查询家庭成员
     *
     * @return
     */
    @GET("rope_skipping_webservice/app/my/family/getFamilyUser")
    Observable<ResultSet<String>> getFamilyUser(@Query("id") String id);

    /**
     * 查询所有家庭成员
     *
     * @return
     */
    @POST("rope_skipping_webservice/app/my/family/getFamilyUserList")
    Observable<ResultSet<String>> getFamilyUserList();

    ////////////收货地址//////////////

    /**
     * 默认收货地址
     *
     * @return
     */
    @FormUrlEncoded
    @POST("rope_skipping_webservice/app/my/address/defaultAddress")
    Observable<ResultSet<String>> defaultAddress(@Field("id") String id);


    /**
     * 删除收货地址
     *
     * @return
     */
    @FormUrlEncoded
    @POST("rope_skipping_webservice/app/my/address/delAddress")
    Observable<ResultSet<String>> delAddress(@Field("id") String id);

    /**
     * 查询收货地址
     *
     * @return
     */
    @GET("rope_skipping_webservice/app/my/address/getAddress")
    Observable<ResultSet<String>> getAddress(@Query("id") String id);

    /**
     * 查询收货地址
     *
     * @return
     */
    @GET("rope_skipping_webservice/app/my/address/getAddressList")
    Observable<ResultSet<String>> getAddressList(@Query("pageNum") String pageNum, @Query("pageSize") String pageSize);

    /**
     * 查询默认收货地址
     *
     * @return
     */
    @GET("rope_skipping_webservice/app/my/address/getDefaultAddress")
    Observable<ResultSet<String>> getDefaultAddress();

    /**
     * 保存收货地址
     *
     * @return
     */
    @POST("rope_skipping_webservice/app/my/address/saveAddress")
    Observable<ResultSet<String>> saveAddress(@Body RequestBody answer);

    ////////我的收藏////////

    /**
     * 添加收藏
     *
     * @return
     */
    @FormUrlEncoded
    @POST("rope_skipping_webservice/app/my/collect/addCollect")
    Observable<ResultSet<String>> addCollect(@Field("objectId") String objectId, @Field("objectType") String objectType);

    /**
     * 取消收藏
     *
     * @return
     */
    @FormUrlEncoded
    @POST("rope_skipping_webservice/app/my/collect/cancelCollect")
    Observable<ResultSet<String>> cancelCollect(@Field("objectId") String objectId, @Field("objectType") String objectType);


    /**
     * 查询收货地址
     *
     * @return
     */
    @GET("rope_skipping_webservice/app/my/collect/getCollectList")
    Observable<ResultSet<String>> getCollectList(@Query("objectType") String objectType, @Query("pageNum") String pageNum, @Query("pageSize") String pageSize);


    /////////系统设置-常见问题//////



    /**
     * 查询收货地址
     *
     * @return
     */
    @GET("rope_skipping_webservice/app/my/question/getQuestionList")
    Observable<ResultSet<String>> getQuestionList(@Query("pageNum") String pageNum, @Query("pageSize") String pageSize);


    /////////帮助中心-意见反馈//////

    /**
     * 新增用户反馈
     *	"contact": "",
     * 	"content": "",
     * 	"feedbackTypeId": 0,
     * 	"image": ""
     * @return
     */
    @POST("rope_skipping_webservice/app/my/feedback/addFeedback")
    Observable<ResultSet<String>> addFeedback(@Body RequestBody answer);

}
