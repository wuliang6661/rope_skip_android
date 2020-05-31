package com.habit.star.api;

import com.blankj.utilcode.util.Utils;
import com.habit.star.api.rx.RxResultHelper;
import com.habit.star.pojo.po.AddressBO;
import com.habit.star.pojo.po.BaoGaoDetailsBO;
import com.habit.star.pojo.po.BeatsBO;
import com.habit.star.pojo.po.ChengJiuBo;
import com.habit.star.pojo.po.DataBaoGaoBO;
import com.habit.star.pojo.po.DeviceBO;
import com.habit.star.pojo.po.DeviceLinkBO;
import com.habit.star.pojo.po.EnergyBO;
import com.habit.star.pojo.po.ExplainDetailsBO;
import com.habit.star.pojo.po.FamilyUserBO;
import com.habit.star.pojo.po.FamilyUserDetailsBO;
import com.habit.star.pojo.po.FeedBackBO;
import com.habit.star.pojo.po.FenLeiBO;
import com.habit.star.pojo.po.HuodongBO;
import com.habit.star.pojo.po.JiHuaBO;
import com.habit.star.pojo.po.KechengBO;
import com.habit.star.pojo.po.MessageBO;
import com.habit.star.pojo.po.MusicBO;
import com.habit.star.pojo.po.NengLiangDengjiBO;
import com.habit.star.pojo.po.NengLiangVO;
import com.habit.star.pojo.po.OnePingLunBO;
import com.habit.star.pojo.po.PkChangCiBO;
import com.habit.star.pojo.po.PkJiLuBo;
import com.habit.star.pojo.po.QuestionsBO;
import com.habit.star.pojo.po.RongYuBO;
import com.habit.star.pojo.po.ShopBO;
import com.habit.star.pojo.po.ShopDetailsBO;
import com.habit.star.pojo.po.ShouCangBO;
import com.habit.star.pojo.po.StatisticsBO;
import com.habit.star.pojo.po.TaskBO;
import com.habit.star.pojo.po.TestBO;
import com.habit.star.pojo.po.TestDataBO;
import com.habit.star.pojo.po.TestDetailsBO;
import com.habit.star.pojo.po.TrainBO;
import com.habit.star.pojo.po.TwoPingLunBO;
import com.habit.star.pojo.po.UserBO;
import com.habit.star.pojo.po.VideoBO;
import com.habit.star.pojo.po.WenDaBO;
import com.habit.star.pojo.po.XIaoJiangBO;
import com.habit.star.pojo.po.ZhiShiBO;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import id.zelory.compressor.Compressor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Observable;

public class HttpServerImpl {

    private static HttpService service;

    /**
     * 获取代理对象
     *
     */
    public static HttpService getService() {
        if (service == null)
            service = ApiManager.getInstance().configRetrofit(HttpService.class, HttpService.URL);
        return service;
    }


    /**
     * 登录
     */
    public static Observable<UserBO> login(String phone, String password) {
        Map<String, Object> params = new HashMap<>();
        params.put("phone", phone);
        params.put("password", password);
        return getService().passwordLogin(params).compose(RxResultHelper.httpRusult());
    }

    /**
     * 注册接口
     */
    public static Observable<String> regiest(String phone, String password, String code, String isBuy) {
        Map<String, Object> params = new HashMap<>();
        params.put("phone", phone);
        params.put("password", password);
        params.put("code", code);
        params.put("isBuy", isBuy);
        return getService().regiest(params).compose(RxResultHelper.httpRusult());
    }


    /**
     * 发送验证码
     */
    public static Observable<String> sendCode(String phone, int type) {
        Map<String, Object> params = new HashMap<>();
        params.put("phone", phone);
        params.put("type", type);
        return getService().sendCode(params).compose(RxResultHelper.httpRusult());
    }


    /**
     * 忘记密码时验证手机号
     */
    public static Observable<String> verifyPhone(String phone, String code) {
        Map<String, Object> params = new HashMap<>();
        params.put("phone", phone);
        params.put("code", code);
        return getService().verifyPhone(params).compose(RxResultHelper.httpRusult());
    }

    /**
     * 忘记密码
     */
    public static Observable<String> forgetPassword(String phone, String password) {
        Map<String, Object> params = new HashMap<>();
        params.put("phone", phone);
        params.put("password", password);
        return getService().forgetPassword(params).compose(RxResultHelper.httpRusult());
    }


    /**
     * token 快捷登录
     */
    public static Observable<UserBO> tokenLogin() {
        return getService().tokenLogin().compose(RxResultHelper.httpRusult());
    }


    /**
     * 获取我的设备列表
     */
    public static Observable<List<DeviceBO>> getDeviceList() {
        return getService().getDeviceList().compose(RxResultHelper.httpRusult());
    }


    /**
     * 删除设备
     */
    public static Observable<String> delDevice(int id) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        return getService().delDevice(params).compose(RxResultHelper.httpRusult());
    }


    /**
     * 保存设备
     */
    public static Observable<String> saveDevices(int id, String name, String macAddress) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        params.put("name", name);
        params.put("macAddress", macAddress);
        return getService().saveDevices(params).compose(RxResultHelper.httpRusult());
    }

    /**
     * 获取用户信息
     */
    public static Observable<UserBO> getUserInfo() {
        return getService().getUserInfo().compose(RxResultHelper.httpRusult());
    }

    /**
     * 查询当前连接的设备
     */
    public static Observable<DeviceBO> getLinkDevice() {
        return getService().getLinkDevice().compose(RxResultHelper.httpRusult());
    }

    /**
     * 查询设备数量
     */
    public static Observable<DeviceLinkBO> getDeviceData() {
        return getService().getDeviceData().compose(RxResultHelper.httpRusult());
    }

    /**
     * 查询收货地址列表
     */
    public static Observable<List<AddressBO>> getAddressList() {
        return getService().getAddressList("1", "2000").compose(RxResultHelper.httpRusult());
    }

    /**
     * 设置默认收货地址
     */
    public static Observable<String> defaultAddress(int id) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        return getService().defaultAddress(params).compose(RxResultHelper.httpRusult());
    }

    /**
     * 删除地址
     */
    public static Observable<String> delAddress(int id) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        return getService().delAddress(params).compose(RxResultHelper.httpRusult());
    }

    /**
     * 查询省市区地址
     */
    public static Observable<String> getAreaInfo() {
        return getService().getAreaInfo().compose(RxResultHelper.httpRusult());
    }

    /**
     * 保存地址
     */
    public static Observable<String> saveAddress(Map<String, Object> params) {
        return getService().saveAddress(params).compose(RxResultHelper.httpRusult());
    }

    /**
     * 获取所有家庭成员
     */
    public static Observable<List<FamilyUserBO>> getFamilyUserList() {
        return getService().getFamilyUserList().compose(RxResultHelper.httpRusult());
    }

    /**
     * 移除家庭成员
     */
    public static Observable<String> delUser(int id) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        return getService().delFamilyUser(params).compose(RxResultHelper.httpRusult());
    }

    /**
     * 获取家庭成员数据
     */
    public static Observable<FamilyUserDetailsBO> getUser(int id) {
        return getService().getFamilyUser(id + "").compose(RxResultHelper.httpRusult());
    }

    /**
     * 根据二维码获取成员信息
     */
    public static Observable<UserBO> getUserInfoByCode(String userCode) {
        Map<String, Object> params = new HashMap<>();
        params.put("userCode", userCode);
        return getService().getUserInfoByCode(params).compose(RxResultHelper.httpRusult());
    }

    /**
     * 邀请家庭成员
     */
    public static Observable<String> addFamilyUser(int id) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        return getService().addFamilyUser(params).compose(RxResultHelper.httpRusult());
    }

    public static Observable<String> getQrCode() {
        return getService().getQrCode().compose(RxResultHelper.httpRusult());
    }

    /**
     * 修改用户昵称
     */
    public static Observable<String> updateNike(String nickName) {
        Map<String, Object> params = new HashMap<>();
        params.put("nickName", nickName);
        return getService().updateNickName(params).compose(RxResultHelper.httpRusult());
    }

    /**
     * 验证身份
     */
    public static Observable<String> verifyUserInfo(String phone, String code) {
        Map<String, Object> params = new HashMap<>();
        params.put("phone", phone);
        params.put("code", code);
        return getService().verifyUserInfo(params).compose(RxResultHelper.httpRusult());
    }

    /**
     * 修改手机号
     */
    public static Observable<String> updatePhone(String phone, String code) {
        Map<String, Object> params = new HashMap<>();
        params.put("phone", phone);
        params.put("code", code);
        return getService().updatePhone(params).compose(RxResultHelper.httpRusult());
    }

    /**
     * 获取我的消息
     */
    public static Observable<List<MessageBO>> getMessageList() {
        return getService().getMessageList("1", "20000").compose(RxResultHelper.httpRusult());
    }

    /**
     * 查询常见问题
     */
    public static Observable<List<WenDaBO>> getQuestionList() {
        return getService().getQuestionList("1", "20000").compose(RxResultHelper.httpRusult());
    }

    /**
     * 查询反馈类型
     */
    public static Observable<List<FeedBackBO>> getFeedbackType() {
        return getService().getFeedbackType().compose(RxResultHelper.httpRusult());
    }

    /**
     * 提交反馈
     */
    public static Observable<String> addFeedback(int id, String message, String phone, String imageUrl) {
        Map<String, Object> params = new HashMap<>();
        params.put("contact", message);
        params.put("content", phone);
        params.put("feedbackTypeId", id);
        params.put("image", imageUrl);
        return getService().addFeedback(params).compose(RxResultHelper.httpRusult());
    }

    /**
     * 查询我的收藏
     */
    public static Observable<List<ShouCangBO>> getCollectList(int objectType) {
        return getService().getCollectList(objectType, "1", "20000").compose(RxResultHelper.httpRusult());
    }

    /**
     * 开启或关闭推送
     */
    public static Observable<String> isDayPush() {
        return getService().isDayPush().compose(RxResultHelper.httpRusult());
    }


    /**
     * 获取精选活动分类
     */
    public static Observable<List<FenLeiBO>> getActivityClasss() {
        return getService().getActivityClasss().compose(RxResultHelper.httpRusult());
    }

    /**
     * 获取精选活动列表
     */
    public static Observable<List<HuodongBO>> getActivityList(int type) {
        return getService().getActivityList(type, "1", "20000").compose(RxResultHelper.httpRusult());
    }

    /**
     * 报名活动
     */
    public static Observable<String> joinActivity(String name, String age, int sex, int selectActivityId) {
        Map<String, Object> params = new HashMap<>();
        params.put("name", name);
        params.put("age", age);
        params.put("sex", sex);
        params.put("selectActivityId", selectActivityId);
        return getService().joinActivity(params).compose(RxResultHelper.httpRusult());
    }

    /**
     * 获取课程分类
     */
    public static Observable<List<FenLeiBO>> getCourseClasss() {
        return getService().getCourseClasss().compose(RxResultHelper.httpRusult());
    }

    /**
     * 获取课程列表
     */
    public static Observable<List<KechengBO>> getCourseInfoList(String classId, int isSelectAge, int isSelectHeight,
                                                                int isSelectWeight, String title) {
        return getService().getCourseInfoList("1", "20000", classId, isSelectAge,
                isSelectHeight, isSelectWeight, title).compose(RxResultHelper.httpRusult());
    }


    /**
     * 获取课程详情
     */
    public static Observable<KechengBO> getCourseInfo(int id) {
        return getService().getCourseInfo(id).compose(RxResultHelper.httpRusult());
    }

    /**
     * 获取视频列表
     */
    public static Observable<List<VideoBO>> getCourseVideoList(int id) {
        return getService().getCourseVideoList("1", "20000", id + "")
                .compose(RxResultHelper.httpRusult());
    }

    /**
     * 添加收藏
     */
    public static Observable<String> addCollect(int objectId, int objectType) {
        Map<String, Object> params = new HashMap<>();
        params.put("objectId", objectId);
        params.put("objectType", objectType);
        return getService().addCollect(params).compose(RxResultHelper.httpRusult());
    }

    /**
     * 取消收藏
     */
    public static Observable<String> cancelCollect(int objectId, int objectType) {
        Map<String, Object> params = new HashMap<>();
        params.put("objectId", objectId);
        params.put("objectType", objectType);
        return getService().cancelCollect(params).compose(RxResultHelper.httpRusult());
    }

    /**
     * 查询知识分类
     */
    public static Observable<List<FenLeiBO>> getKnowledgeClasss() {
        return getService().getKnowledgeClasss().compose(RxResultHelper.httpRusult());
    }


    /**
     * 获取知识列表
     */
    public static Observable<List<ZhiShiBO>> getSelectKnowledgeInfoList(String classId, int isSelectAge, int isSelectHeight,
                                                                        int isSelectWeight, String title) {
        return getService().getSelectKnowledgeInfoList("1", "20000", classId, isSelectAge,
                isSelectHeight, isSelectWeight, title).compose(RxResultHelper.httpRusult());
    }


    /**
     * 查询知识详情
     */
    public static Observable<ZhiShiBO> getKnowledge(int id) {
        return getService().getKnowledge(id).compose(RxResultHelper.httpRusult());
    }

    /**
     * 查询商品列表
     */
    public static Observable<List<ShopBO>> getGoodList(int recommendStatus, int pageSize) {
        return getService().getGoodList("1", pageSize + "", recommendStatus).compose(RxResultHelper.httpRusult());
    }

    /**
     * 查询商品
     */
    public static Observable<ShopDetailsBO> getGood(int id) {
        return getService().getGood(id).compose(RxResultHelper.httpRusult());
    }

    /**
     * 查询默认收货地址
     */
    public static Observable<AddressBO> getDefaultAddress() {
        return getService().getDefaultAddress().compose(RxResultHelper.httpRusult());
    }


    /**
     * 兑换商品
     */
    public static Observable<String> exchangeGood(int addressId, int exchangeEnergy, int goodId, String price) {
        Map<String, Object> params = new HashMap<>();
        params.put("addressId", addressId);
        params.put("exchangeEnergy", exchangeEnergy);
        params.put("goodId", goodId);
        params.put("price", price);
        return getService().exchangeGood(params).compose(RxResultHelper.httpRusult());
    }


    /**
     * 获取百问百答分类
     */
    public static Observable<List<FenLeiBO>> getQuestionAnswerClasss() {
        return getService().getQuestionAnswerClasss().compose(RxResultHelper.httpRusult());
    }


    /**
     * 根据问答分类查询问答列表
     */
    public static Observable<List<QuestionsBO>> getQuestionAnswerInfoList(int id) {
        return getService().getQuestionAnswerInfoList("1", "20000", id + "").compose(RxResultHelper.httpRusult());
    }

    /**
     * 根据关键字搜索问答
     */
    public static Observable<List<QuestionsBO>> getSelectQuestionAnswerInfoList(String title) {
        return getService().getSelectQuestionAnswerInfoList("1", "20000", title).compose(RxResultHelper.httpRusult());
    }


    /**
     * 添加问答
     */
    public static Observable<String> AddQuestion(int questionAnswerClassId, String title, String content, String image) {
        Map<String, Object> params = new HashMap<>();
        params.put("questionAnswerClassId", questionAnswerClassId);
        params.put("title", title);
        params.put("content", content);
        params.put("image", image);
        return getService().AddQuestion(params).compose(RxResultHelper.httpRusult());
    }

    /**
     * 查询问答详情
     */
    public static Observable<QuestionsBO> getQuestionAnswerInfo(int id) {
        return getService().getQuestionAnswerInfo(id).compose(RxResultHelper.httpRusult());
    }

    /**
     * 查询所有一级评论
     */
    public static Observable<List<OnePingLunBO>> getOneCommentList(String id) {
        return getService().getOneCommentList("1", "20000", id + "").compose(RxResultHelper.httpRusult());
    }

    /**
     * 查询所有二级评论
     */
    public static Observable<List<TwoPingLunBO>> getTwoCommentList(String id) {
        return getService().getTwoCommentList("1", "20000", id + "").compose(RxResultHelper.httpRusult());
    }

    /**
     * 发布评论
     */
    public static Observable<String> AddComment(int objectId, int parentId, String content, String image) {
        Map<String, Object> params = new HashMap<>();
        params.put("objectType", 0);
        params.put("objectId", objectId);
        params.put("parentId", parentId);
        params.put("image", image);
        params.put("content", content);
        return getService().AddComment(params).compose(RxResultHelper.httpRusult());
    }


    /**
     * 创建小将
     */
    public static Observable<String> addGeneralInfo(String nickName, String age,
                                                    String sex, String height, String weight) {
        Map<String, Object> params = new HashMap<>();
        params.put("nickName", nickName);
        params.put("age", age);
        params.put("sex", sex);
        params.put("height", height);
        params.put("weight", weight);
        return getService().addGeneralInfo(params).compose(RxResultHelper.httpRusult());
    }


    /**
     * 查询未领取的能量
     */
    public static Observable<List<NengLiangVO>> getEnergies() {
        return getService().getEnergies().compose(RxResultHelper.httpRusult());
    }

    /**
     * 领取能量
     */
    public static Observable<String> receiveEnergy(int id) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        return getService().receiveEnergy(params).compose(RxResultHelper.httpRusult());
    }


    /**
     * 获取勋章成就
     */
    public static Observable<ChengJiuBo> getMedalList() {
        return getService().getMedalList().compose(RxResultHelper.httpRusult());
    }

    /**
     * 获取我的荣誉证书
     */
    public static Observable<RongYuBO> getHonorList() {
        return getService().getHonorList().compose(RxResultHelper.httpRusult());
    }


    /**
     * 查询小将信息
     */
    public static Observable<XIaoJiangBO> getYoungGeneralInfo() {
        return getService().getYoungGeneralInfo().compose(RxResultHelper.httpRusult());
    }

    /**
     * 查询Pk挑战信息列表
     */
    public static Observable<List<PkChangCiBO>> getPkChallengeList() {
        return getService().getPkChallengeList().compose(RxResultHelper.httpRusult());
    }

    /**
     * 查询Pk明细列表
     */
    public static Observable<List<PkJiLuBo>> getDataPkList() {
        return getService().getDataPkList("1", "20000").compose(RxResultHelper.httpRusult());
    }


    /**
     * 查询能量数据
     */
    public static Observable<EnergyBO> getEnergyData() {
        return getService().getEnergyData().compose(RxResultHelper.httpRusult());
    }


    /**
     * 查询能量明细列表
     */
    public static Observable<List<NengLiangVO>> getDataEnergyList() {
        return getService().getDataEnergyList("1", "20000").compose(RxResultHelper.httpRusult());
    }

    /**
     * 查询待完成任务列表
     */
    public static Observable<List<TaskBO>> getTaskList() {
        return getService().getTaskList().compose(RxResultHelper.httpRusult());
    }

    /**
     * 获取所有能量等级
     */
    public static Observable<List<NengLiangDengjiBO>> getEnergyLevelInfoList() {
        return getService().getEnergyLevelInfoList().compose(RxResultHelper.httpRusult());
    }

    /**
     * 获取我的能量等级
     */
    public static Observable<String> getMyEnergyLevelInfo() {
        return getService().getMyEnergyLevelInfo().compose(RxResultHelper.httpRusult());
    }

    /**
     * 查询训练计划列表
     */
    public static Observable<List<JiHuaBO>> getTrainList(int isComplete) {
        return getService().getTrainList("1", "20000", isComplete).compose(RxResultHelper.httpRusult());
    }

    /**
     * 查询测试累积数据
     */
    public static Observable<TestDataBO> getTestTotal() {
        return getService().getTestTotal().compose(RxResultHelper.httpRusult());
    }

    /**
     * 查询测试记录
     */
    public static Observable<List<TestBO>> getTestList() {
        return getService().getTestList("1", "20000").compose(RxResultHelper.httpRusult());
    }


    /**
     * 查询测试结果
     */
    public static Observable<TestDetailsBO> getTest(String id) {
        return getService().getTest(id).compose(RxResultHelper.httpRusult());
    }


    /**
     * 添加测试记录
     */
    public static Observable<String> addTest(Map<String, Object> params) {
        return getService().addTest(params).compose(RxResultHelper.httpRusult());
    }


    /**
     * 获取数据报告
     */
    public static Observable<List<DataBaoGaoBO>> getDataReportList() {
        return getService().getDataReportList("1", "20000").compose(RxResultHelper.httpRusult());
    }

    /**
     * 获取数据报告详情
     */
    public static Observable<BaoGaoDetailsBO> getDataReport(int id) {
        return getService().getDataReport(id).compose(RxResultHelper.httpRusult());
    }

    /**
     * 查询数据统计
     */
    public static Observable<List<StatisticsBO>> getDataStatistic() {
        return getService().getDataStatistic().compose(RxResultHelper.httpRusult());
    }

    /**
     * 提交手动
     */
    public static Observable<String> input(Map<String,Object> params) {
        return getService().input(params).compose(RxResultHelper.httpRusult());
    }

    /**
     * 查询改良方案
     */
    public static Observable<ExplainDetailsBO> getImprovePlan(int id) {
        return getService().getImprovePlan(id).compose(RxResultHelper.httpRusult());
    }

    /**
     * 查询所有节拍
     */
    public static Observable<List<BeatsBO>> getBeats() {
        return getService().getBeats().compose(RxResultHelper.httpRusult());
    }

    /**
     * 查询所有系统音乐
     */
    public static Observable<List<MusicBO>> getMusicList() {
        return getService().getMusicList().compose(RxResultHelper.httpRusult());
    }

    /**
     * 添加训练结果
     */
    public static Observable<String> addTrain(Map<String, Object> params) {
        return getService().addTrain(params).compose(RxResultHelper.httpRusult());
    }

    /**
     * 获取训练结果
     */
    public static Observable<TrainBO> getTrain(String id) {
        return getService().getTrain(id).compose(RxResultHelper.httpRusult());
    }

    /**
     * 添加训练计划
     */
    public static Observable<String> addImprovePlan(int id) {
        Map<String, Object> params = new HashMap<>();
        params.put("improvePlanId", id);
        return getService().addImprovePlan(params).compose(RxResultHelper.httpRusult());
    }

    /**
     * 填写基本信息
     */
    public static Observable<String> saveGeneralInfo(String age, String height, String sex, String weight) {
        Map<String, Object> params = new HashMap<>();
        params.put("age", age);
        params.put("height", height);
        params.put("sex", sex);
        params.put("weight", weight);
        return getService().saveGeneralInfo(params).compose(RxResultHelper.httpRusult());
    }


    /**
     * 提交图片
     */
    public static Observable<String> updateFile(File file) {
        File compressedImageFile;
        try {
            compressedImageFile = new Compressor(Utils.getApp()).setQuality(30).compressToFile(file);
        } catch (IOException e) {
            e.printStackTrace();
            compressedImageFile = file;
        }
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpg"), compressedImageFile);
        MultipartBody.Part body = MultipartBody.Part.createFormData("fileName", file.getName(), requestFile);
        return getService().updateFile(body).compose(RxResultHelper.httpRusult());
    }


    /**
     * 上传用户头像
     */
    public static Observable<String> uploadHeadImage(File file) {
        File compressedImageFile;
        try {
            compressedImageFile = new Compressor(Utils.getApp()).setQuality(30).compressToFile(file);
        } catch (IOException e) {
            e.printStackTrace();
            compressedImageFile = file;
        }
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpg"), compressedImageFile);
        MultipartBody.Part body = MultipartBody.Part.createFormData("fileName", file.getName(), requestFile);
        return getService().uploadHeadImage(body).compose(RxResultHelper.httpRusult());
    }

}
