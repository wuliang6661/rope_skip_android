package com.habit.star.api;

import com.blankj.utilcode.util.Utils;
import com.habit.star.api.rx.RxResultHelper;
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
     * @return
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
    public static Observable<List<QuestionBO>> getQuestionList() {
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
    public static Observable<String> joinActivity(String name, int age, int sex, int selectActivityId) {
        Map<String, Object> params = new HashMap<>();
        params.put("name", name);
        params.put("age", age);
        params.put("sex", sex);
        params.put("selectActivityId", selectActivityId);
        return getService().joinActivity(params).compose(RxResultHelper.httpRusult());
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
        return getService().updateFile(body).compose(RxResultHelper.httpRusult());
    }

}
