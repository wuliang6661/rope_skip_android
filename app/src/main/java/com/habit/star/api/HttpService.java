package com.habit.star.api;

import com.habit.star.pojo.BaseResult;
import com.habit.star.pojo.po.AddressBO;
import com.habit.star.pojo.po.DeviceBO;
import com.habit.star.pojo.po.DeviceLinkBO;
import com.habit.star.pojo.po.FamilyUserBO;
import com.habit.star.pojo.po.FamilyUserDetailsBO;
import com.habit.star.pojo.po.FeedBackBO;
import com.habit.star.pojo.po.FenLeiBO;
import com.habit.star.pojo.po.HuodongBO;
import com.habit.star.pojo.po.MessageBO;
import com.habit.star.pojo.po.QuestionBO;
import com.habit.star.pojo.po.ShouCangBO;
import com.habit.star.pojo.po.UserBO;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
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
     * 查询我的设备列表
     */
    @GET("app/my/device/getDeviceList")
    Observable<BaseResult<List<DeviceBO>>> getDeviceList();

    /**
     * 删除跳绳设备
     */
    @POST("app/my/device/delDevice")
    Observable<BaseResult<String>> delDevice(@Body Map<String, Object> params);

    /**
     * 增加设备信息
     */
    @POST("app/my/device/saveDevice")
    Observable<BaseResult<String>> saveDevices(@Body Map<String, Object> params);

    /**
     * 查询设备数量
     */
    @GET("app/my/device/getDeviceData")
    Observable<BaseResult<DeviceLinkBO>> getDeviceData();

    /**
     * 查询当前连接的设备
     */
    @GET("app/my/device/getLinkDevice")
    Observable<BaseResult<DeviceBO>> getLinkDevice();

    /**
     * 获取用户信息
     */
    @GET("app/user/getUserInfo")
    Observable<BaseResult<UserBO>> getUserInfo();

    /**
     * 查询收货地址
     */
    @GET("app/my/address/getAddress")
    Observable<BaseResult<AddressBO>> getAddress(@Query("id") String id);

    /**
     * 查询收货地址列表
     */
    @GET("app/my/address/getAddressList")
    Observable<BaseResult<List<AddressBO>>> getAddressList(@Query("pageNum") String pageNum, @Query("pageSize") String pageSize);

    /**
     * 查询省市区json数据
     */
    @GET("app/my/address/getAreaInfo")
    Observable<BaseResult<String>> getAreaInfo();

    /**
     * 查询默认地址
     */
    @GET("app/my/address/getDefaultAddress")
    Observable<BaseResult<AddressBO>> getDefaultAddress();

    /**
     * 保存收货地址
     */
    @POST("app/my/address/saveAddress")
    Observable<BaseResult<String>> saveAddress(@Body Map<String, Object> params);

    /**
     * 设置默认收货地址
     */
    @POST("app/my/address/defaultAddress")
    Observable<BaseResult<String>> defaultAddress(@Body Map<String, Object> params);

    /**
     * 删除收货地址
     */
    @POST("app/my/address/delAddress")
    Observable<BaseResult<String>> delAddress(@Body Map<String, Object> params);

    /**
     * 获取所有家庭成员
     */
    @GET("app/my/family/getFamilyUserList")
    Observable<BaseResult<List<FamilyUserBO>>> getFamilyUserList();

    /**
     * 邀请家庭成员
     */
    @POST("app/my/family/addFamilyUser")
    Observable<BaseResult<String>> addFamilyUser(@Body Map<String, Object> params);

    /**
     * 查询家庭成员
     */
    @GET("app/my/family/getFamilyUser")
    Observable<BaseResult<FamilyUserDetailsBO>> getFamilyUser(@Query("id") String id);

    /**
     * 移除家庭成员
     */
    @POST("app/my/family/delFamilyUser")
    Observable<BaseResult<String>> delFamilyUser(@Body Map<String, Object> params);


    /**
     * 根据二维码的编码获取人员信息
     */
    @POST("app/user/getUserInfoByCode")
    Observable<BaseResult<UserBO>> getUserInfoByCode(@Body Map<String, Object> params);

    /**
     * 获取我的二维码图片
     */
    @POST("app/user/QRCode")
    Observable<BaseResult<String>> getQrCode();


    /**
     * 上传图片
     */
    @Multipart
    @POST("app/common/uploadImage")
    Observable<BaseResult<String>> updateFile(@Part MultipartBody.Part file);

    /**
     * 上传用户头像
     */
    @Multipart
    @POST("app/user/uploadHeadImage")
    Observable<BaseResult<String>> uploadHeadImage(@Part MultipartBody.Part file);


    /**
     * 修改用户名称
     */
    @POST("app/user/updateNickName")
    Observable<BaseResult<String>> updateNickName(@Body Map<String, Object> params);

    /**
     * 验证身份
     */
    @POST("app/user/verifyUserInfo")
    Observable<BaseResult<String>> verifyUserInfo(@Body Map<String, Object> params);


    /**
     * 修改手机号
     */
    @POST("app/user/updatePhone")
    Observable<BaseResult<String>> updatePhone(@Body Map<String, Object> params);

    /**
     * 获取我的消息
     */
    @GET("app/my/message/getMessageList")
    Observable<BaseResult<List<MessageBO>>> getMessageList(@Query("pageNum") String pageNum,
                                                           @Query("pageSize") String pageSize);

    /**
     * 查询常见问题
     */
    @GET("app/my/question/getQuestionList")
    Observable<BaseResult<List<QuestionBO>>> getQuestionList(@Query("pageNum") String pageNum,
                                                             @Query("pageSize") String pageSize);

    /**
     * 查询反馈类型
     */
    @GET("app/my/feedback/getFeedbackType")
    Observable<BaseResult<List<FeedBackBO>>> getFeedbackType();


    /**
     * 提交反馈类型
     */
    @POST("app/my/feedback/addFeedback")
    Observable<BaseResult<String>> addFeedback(@Body Map<String, Object> params);

    /**
     * 查询我的收藏
     */
    @GET("app/my/collect/getCollectList")
    Observable<BaseResult<List<ShouCangBO>>> getCollectList(@Query("objectType") int objectType,
                                                            @Query("pageNum") String pageNum,
                                                            @Query("pageSize") String pageSize);

    /**
     * 开启或关闭推送
     */
    @POST("app/user/isDayPush")
    Observable<BaseResult<String>> isDayPush();

    /**
     * 查询精选活动分类
     */
    @GET("app/find/activity/getActivityClasss")
    Observable<BaseResult<List<FenLeiBO>>> getActivityClasss();

    /**
     * 查询精选活动列表
     */
    @GET("app/find/activity/getActivityList")
    Observable<BaseResult<List<HuodongBO>>> getActivityList(@Query("type") int type,
                                                            @Query("pageNum") String pageNum,
                                                            @Query("pageSize") String pageSize);

    /**
     * 报名活动
     */
    @POST("app/find/activity/joinActivity")
    Observable<BaseResult<String>> joinActivity(@Body Map<String, Object> params);

    /**
     * 查询课程分类
     */
    @GET("app/find/course/getCourseClasss")
    Observable<BaseResult<String>> getCourseClasss();

    /**
     * 获取课程列表
     */
    @GET("app/find/course/getCourseInfoList")
    Observable<BaseResult<String>> getCourseInfoList(@Query("pageNum") String pageNum,
                                                     @Query("pageSize") String pageSize,
                                                     @Query("classId") String classId,
                                                     @Query("isSelectAge") int isSelectAge,
                                                     @Query("isSelectHeight") int isSelectHeight,
                                                     @Query("isSelectWeight") int isSelectWeight,
                                                     @Query("title") String title);

    /**
     * 根据课程id 获取课程视频列表
     */
    @GET("app/find/course/getCourseVideoList")
    Observable<BaseResult<String>> getCourseVideoList(@Query("pageNum") String pageNum,
                                                      @Query("pageSize") String pageSize,
                                                      @Query("id") String id);

    /**
     * 添加学习记录
     */
    @POST("app/find/course/addDataLearn")
    Observable<BaseResult<String>> addDataLearn(@Body Map<String, Object> params);

}
