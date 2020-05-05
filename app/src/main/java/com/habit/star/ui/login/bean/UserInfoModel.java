package com.habit.star.ui.login.bean;

import com.habit.star.model.bean.BaseMode;

/**
 * @date:  2020-04-23 23:28
 * @ClassName: UserInfoModel.java
 * @Description:用户信息
 * @author: sundongdong
 * @version V1.0
 */
public class UserInfoModel extends BaseMode {

    public String id;
    //头像地址
    public String image;
    //是否购买设备（0否，1是）
    public int isBuy;
    //是否每日推送（0否，1是）
    public int isDayPush;
    //昵称
    public String nickName;
    //手机号
    public String phone;
    //token(仅登录成功时返回)
    public String token;
    //用户编码
    public String userCode;
    //是否否拥有小将（0否，1是
    public int youngGeneralCount;
}
