package com.tohabit.skip.api;

import com.tohabit.skip.pojo.BaseResult;
import com.tohabit.skip.pojo.po.AddressBO;
import com.tohabit.skip.pojo.po.BaoGaoDetailsBO;
import com.tohabit.skip.pojo.po.BeatsBO;
import com.tohabit.skip.pojo.po.ChengJiuBo;
import com.tohabit.skip.pojo.po.DataBaoGaoBO;
import com.tohabit.skip.pojo.po.DeviceBO;
import com.tohabit.skip.pojo.po.DeviceLinkBO;
import com.tohabit.skip.pojo.po.EnergyBO;
import com.tohabit.skip.pojo.po.ExplainDetailsBO;
import com.tohabit.skip.pojo.po.FamilyUserBO;
import com.tohabit.skip.pojo.po.FamilyUserDetailsBO;
import com.tohabit.skip.pojo.po.FeedBackBO;
import com.tohabit.skip.pojo.po.FenLeiBO;
import com.tohabit.skip.pojo.po.HuodongBO;
import com.tohabit.skip.pojo.po.JiHuaBO;
import com.tohabit.skip.pojo.po.KechengBO;
import com.tohabit.skip.pojo.po.MessageBO;
import com.tohabit.skip.pojo.po.MusicBO;
import com.tohabit.skip.pojo.po.MusicBeatBO;
import com.tohabit.skip.pojo.po.NengLiangDengjiBO;
import com.tohabit.skip.pojo.po.NengLiangVO;
import com.tohabit.skip.pojo.po.OnePingLunBO;
import com.tohabit.skip.pojo.po.PkChangCiBO;
import com.tohabit.skip.pojo.po.PkJiLuBo;
import com.tohabit.skip.pojo.po.QuestionsBO;
import com.tohabit.skip.pojo.po.RongYuBO;
import com.tohabit.skip.pojo.po.ShareBO;
import com.tohabit.skip.pojo.po.ShopBO;
import com.tohabit.skip.pojo.po.ShopDetailsBO;
import com.tohabit.skip.pojo.po.ShouCangBO;
import com.tohabit.skip.pojo.po.StatisticsBO;
import com.tohabit.skip.pojo.po.TaskBO;
import com.tohabit.skip.pojo.po.TestBO;
import com.tohabit.skip.pojo.po.TestDataBO;
import com.tohabit.skip.pojo.po.TestDetailsBO;
import com.tohabit.skip.pojo.po.TrainBO;
import com.tohabit.skip.pojo.po.TwoPingLunBO;
import com.tohabit.skip.pojo.po.UserBO;
import com.tohabit.skip.pojo.po.VersionBO;
import com.tohabit.skip.pojo.po.VideoBO;
import com.tohabit.skip.pojo.po.WenDaBO;
import com.tohabit.skip.pojo.po.XIaoJiangBO;
import com.tohabit.skip.pojo.po.ZhiShiBO;

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

//    String URL = "http://47.96.126.117:8084/rope_skipping_webservice/";

    String URL = "https://tohabit.zjtmcloud.com/rope_skipping_webservice/";

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
    Observable<BaseResult<List<WenDaBO>>> getQuestionList(@Query("pageNum") String pageNum,
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
     * 查询精选活动详情
     */
    @GET("app/find/activity/getActivity")
    Observable<BaseResult<HuodongBO>> getActivity(@Query("id") String id);

    /**
     * 报名活动
     */
    @POST("app/find/activity/joinActivity")
    Observable<BaseResult<String>> joinActivity(@Body Map<String, Object> params);

    /**
     * 查询课程分类
     */
    @GET("app/find/course/getCourseClasss")
    Observable<BaseResult<List<FenLeiBO>>> getCourseClasss();

    /**
     * 获取课程列表
     */
    @GET("app/find/course/getCourseInfoList")
    Observable<BaseResult<List<KechengBO>>> getCourseInfoList(@Query("pageNum") String pageNum,
                                                              @Query("pageSize") String pageSize,
                                                              @Query("classId") String classId,
                                                              @Query("isSelectAge") int isSelectAge,
                                                              @Query("isSelectHeight") int isSelectHeight,
                                                              @Query("isSelectWeight") int isSelectWeight,
                                                              @Query("title") String title);


    /**
     * 获取课程详情
     */
    @GET("app/find/course/getCourseInfo")
    Observable<BaseResult<KechengBO>> getCourseInfo(@Query("id") int id);


    /**
     * 根据课程id 获取课程视频列表
     */
    @GET("app/find/course/getCourseVideoList")
    Observable<BaseResult<List<VideoBO>>> getCourseVideoList(@Query("pageNum") String pageNum,
                                                             @Query("pageSize") String pageSize,
                                                             @Query("id") String id);

    /**
     * 添加收藏
     */
    @POST("app/my/collect/addCollect")
    Observable<BaseResult<String>> addCollect(@Body Map<String, Object> params);

    /**
     * 取消收藏
     */
    @POST("app/my/collect/cancelCollect")
    Observable<BaseResult<String>> cancelCollect(@Body Map<String, Object> params);

    /**
     * 查询知识分类
     */
    @GET("app/find/knowledge/getKnowledgeClasss")
    Observable<BaseResult<List<FenLeiBO>>> getKnowledgeClasss();

    /**
     * 查询知识列表
     */
    @GET("app/find/knowledge/getSelectKnowledgeInfoList")
    Observable<BaseResult<List<ZhiShiBO>>> getSelectKnowledgeInfoList(@Query("pageNum") String pageNum,
                                                                      @Query("pageSize") String pageSize,
                                                                      @Query("classId") String classId,
                                                                      @Query("isSelectAge") int isSelectAge,
                                                                      @Query("isSelectHeight") int isSelectHeight,
                                                                      @Query("isSelectWeight") int isSelectWeight,
                                                                      @Query("title") String title);

    /**
     * 查询跳绳知识详情
     */
    @GET("app/find/knowledge/getKnowledge")
    Observable<BaseResult<ZhiShiBO>> getKnowledge(@Query("id") int id);

    /**
     * 查询商品列表
     */
    @GET("app/find/good/getGoodList")
    Observable<BaseResult<List<ShopBO>>> getGoodList(@Query("pageNum") String pageNum,
                                                     @Query("pageSize") String pageSize,
                                                     @Query("recommendStatus") int recommendStatus);

    /**
     * 查询商品详情
     */
    @GET("app/find/good/getGood")
    Observable<BaseResult<ShopDetailsBO>> getGood(@Query("id") int id);

    /**
     * 兑换商品
     */
    @POST("app/find/good/exchangeGood")
    Observable<BaseResult<String>> exchangeGood(@Body Map<String, Object> params);

    /**
     * 查询所有百问百答分类
     */
    @GET("app/find/questionAnswer/getQuestionAnswerClasss")
    Observable<BaseResult<List<FenLeiBO>>> getQuestionAnswerClasss();

    /**
     * 查询问答列表
     */
    @GET("app/find/questionAnswer/getQuestionAnswerInfoList")
    Observable<BaseResult<List<QuestionsBO>>> getQuestionAnswerInfoList(@Query("pageNum") String pageNum,
                                                                        @Query("pageSize") String pageSize,
                                                                        @Query("id") String id);

    /**
     * 根据关键字搜索问答
     */
    @GET("app/find/questionAnswer/getSelectQuestionAnswerInfoList")
    Observable<BaseResult<List<QuestionsBO>>> getSelectQuestionAnswerInfoList(@Query("pageNum") String pageNum,
                                                                              @Query("pageSize") String pageSize,
                                                                              @Query("title") String title);

    /**
     * 发布问答
     */
    @POST("app/find/questionAnswer/AddQuestion")
    Observable<BaseResult<String>> AddQuestion(@Body Map<String, Object> params);

    /**
     * 查询问答详情
     */
    @GET("app/find/questionAnswer/getQuestionAnswerInfo")
    Observable<BaseResult<QuestionsBO>> getQuestionAnswerInfo(@Query("id") int id);

    /**
     * 查询所有一级评论
     */
    @GET("app/find/questionAnswer/getOneCommentList")
    Observable<BaseResult<List<OnePingLunBO>>> getOneCommentList(@Query("pageNum") String pageNum,
                                                                 @Query("pageSize") String pageSize,
                                                                 @Query("id") String id);


    /**
     * 根据评论ID查询所有二级评论
     */
    @GET("app/find/questionAnswer/getTwoCommentList")
    Observable<BaseResult<List<TwoPingLunBO>>> getTwoCommentList(@Query("pageNum") String pageNum,
                                                                 @Query("pageSize") String pageSize,
                                                                 @Query("id") String id);

    /**
     * 添加评论
     */
    @POST("app/find/questionAnswer/AddComment")
    Observable<BaseResult<String>> AddComment(@Body Map<String, Object> params);


    /**
     * 添加学习记录
     */
    @POST("app/find/course/addDataLearn")
    Observable<BaseResult<String>> addDataLearn(@Body Map<String, Object> params);


    /**
     * 创建小将
     */
    @POST("app/general/addGeneralInfo")
    Observable<BaseResult<String>> addGeneralInfo(@Body Map<String, Object> params);

    /**
     * 查询未领取能量
     */
    @GET("app/general/getEnergies")
    Observable<BaseResult<List<NengLiangVO>>> getEnergies();


    /**
     * 领取能量
     */
    @POST("app/general/receiveEnergy")
    Observable<BaseResult<String>> receiveEnergy(@Body Map<String, Object> params);

    /**
     * 获取我的勋章成就
     */
    @GET("app/general/getMedalList")
    Observable<BaseResult<ChengJiuBo>> getMedalList();


    /**
     * 获取我的荣誉证书
     */
    @GET("app/general/getHonorList")
    Observable<BaseResult<RongYuBO>> getHonorList();

    /**
     * 查询小将信息
     */
    @GET("app/general/getYoungGeneralInfo")
    Observable<BaseResult<XIaoJiangBO>> getYoungGeneralInfo();

    /**
     * 查询Pk挑战信息列表
     */
    @GET("app/general/getPkChallengeList")
    Observable<BaseResult<List<PkChangCiBO>>> getPkChallengeList();

    /**
     * 查询Pk明细列表
     */
    @GET("app/general/getDataPkList")
    Observable<BaseResult<List<PkJiLuBo>>> getDataPkList(@Query("pageNum") String pageNum,
                                                         @Query("pageSize") String pageSize);

    /**
     * 查询能量数据(累计能量、消耗能量、可用能量)
     */
    @GET("app/general/getEnergyData")
    Observable<BaseResult<EnergyBO>> getEnergyData();

    /**
     * 查询能量明细列表
     */
    @GET("app/general/getDataEnergyList")
    Observable<BaseResult<List<NengLiangVO>>> getDataEnergyList(@Query("pageNum") String pageNum,
                                                                @Query("pageSize") String pageSize);

    /**
     * 查询待完成任务列表
     */
    @GET("app/general/getTaskList")
    Observable<BaseResult<List<TaskBO>>> getTaskList();


    /**
     * 查询所有能量等级
     */
    @GET("app/general/getEnergyLevelInfoList")
    Observable<BaseResult<List<NengLiangDengjiBO>>> getEnergyLevelInfoList();

    /**
     * 查询我的能量等级
     */
    @GET("app/general/getMyEnergyLevelInfo")
    Observable<BaseResult<String>> getMyEnergyLevelInfo();


    /**
     * 查询训练计划
     */
    @GET("app/general/getTrainList")
    Observable<BaseResult<List<JiHuaBO>>> getTrainList(@Query("pageNum") String pageNum,
                                                       @Query("pageSize") String pageSize,
                                                       @Query("isComplete") int isComplete);

    /**
     * 查询测试总记录
     */
    @GET("app/test/getTestTotal")
    Observable<BaseResult<TestDataBO>> getTestTotal();

    /**
     * 查询测试记录列表
     */
    @GET("app/test/getTestList")
    Observable<BaseResult<List<TestBO>>> getTestList(@Query("pageNum") String pageNum,
                                                     @Query("pageSize") String pageSize);

    /**
     * 查询测试结果
     */
    @GET("app/test/getTest")
    Observable<BaseResult<TestDetailsBO>> getTest(@Query("id") String id);

    /**
     * 添加测试记录
     */
    @POST("app/test/addTest")
    Observable<BaseResult<String>> addTest(@Body Map<String, Object> params);

    /**
     * 同步历史记录
     */
    @POST("app/test/addTestBatch")
    Observable<BaseResult<String>> addTestBatch(@Body Map<String, Object> params);


    /**
     * 查询数据报告记录
     */
    @GET("app/test/getDataReportList")
    Observable<BaseResult<List<DataBaoGaoBO>>> getDataReportList(@Query("pageNum") String pageNum,
                                                                 @Query("pageSize") String pageSize);

    /**
     * 查询数据报告详情
     */
    @GET("app/test/getDataReport")
    Observable<BaseResult<BaoGaoDetailsBO>> getDataReport(@Query("id") int id);

    /**
     * 查询数据统计
     */
    @GET("app/test/getDataStatistic")
    Observable<BaseResult<List<StatisticsBO>>> getDataStatistic();

    /**
     * 手动输入接口
     */
    @POST("app/test/input")
    Observable<BaseResult<String>> input(@Body Map<String, Object> params);

    /**
     * 查询改良方案
     */
    @GET("app/test/getImprovePlan")
    Observable<BaseResult<ExplainDetailsBO>> getImprovePlan(@Query("id") int id);

    /**
     * 查询所有节拍
     */
    @GET("app/test/getBeats")
    Observable<BaseResult<List<BeatsBO>>> getBeats();

    /**
     * 查询系统音乐
     */
    @GET("app/test/getMusicList")
    Observable<BaseResult<List<MusicBO>>> getMusicList();


    /**
     * 添加训练结果
     */
    @POST("app/test/addTrain")
    Observable<BaseResult<String>> addTrain(@Body Map<String, Object> params);

    /**
     * 获取训练结果
     */
    @GET("app/test/getTrain")
    Observable<BaseResult<TrainBO>> getTrain(@Query("id") String id);


    /**
     * 添加改良方案至训练计划
     */
    @POST("app/test/addImprovePlan")
    Observable<BaseResult<String>> addImprovePlan(@Body Map<String, Object> params);


    /**
     * 填写基础信息
     */
    @POST("app/test/saveGeneralInfo")
    Observable<BaseResult<String>> saveGeneralInfo(@Body Map<String, Object> params);


    /**
     * 查询客服号码
     */
    @GET("app/my/feedback/getTelephone")
    Observable<BaseResult<String>> getTelephone();


    /**
     * 检查更新
     */
    @GET("app/my/version/getVersion")
    Observable<BaseResult<VersionBO>> getVersion();


    /**
     * 获取邀请好友的规则
     */
    @POST("app/share/getWxShareMessage")
    Observable<BaseResult<ShareBO>> getWxShareMessage();

    /**
     * 获取音乐/节拍
     */
    @GET("app/user/getMusicAndBeat")
    Observable<BaseResult<MusicBeatBO>> getMusicAndBeat();

    /**
     * 保存音乐节拍
     */
    @POST("app/user/saveMusicAndBeat")
    Observable<BaseResult<String>> saveMusicAndBeat(@Body Map<String, Object> params);

    /**
     * 完成训练计划
     */
    @POST("app/general/completeTrainPlan")
    Observable<BaseResult<String>> completeTrainPlan(@Body Map<String, Object> params);

    /**
     * 增加分享记录
     */
    @POST("app/share/addDataShare")
    Observable<BaseResult<String>> addDataShare(@Body Map<String, Object> params);

    /**
     * 修改生日
     */
    @POST("app/user/updateBirthDate")
    Observable<BaseResult<String>> updateBirthDate(@Body Map<String, Object> params);


    /**
     * 修改性别
     */
    @POST("app/user/updateSex")
    Observable<BaseResult<String>> updateSex(@Body Map<String, Object> params);

    /**
     * 修改身高
     */
    @POST("app/user/updateHeight")
    Observable<BaseResult<String>> updateHeight(@Body Map<String, Object> params);

    /**
     * 修改体重
     */
    @POST("app/user/updateWeight")
    Observable<BaseResult<String>> updateWeight(@Body Map<String, Object> params);

}
