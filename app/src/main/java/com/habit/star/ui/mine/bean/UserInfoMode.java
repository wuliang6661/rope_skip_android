package com.habit.star.ui.mine.bean;


import com.habit.star.model.bean.BaseMode;

/**
 * 创建日期：2018/6/15 10:54
 *
 * @author sundongdong
 * @version 1.0
 * @since 文件名称： UserInfoMode.java
 * 类说明：用户信息类
 */
public class UserInfoMode extends BaseMode {
    public int id;
    public String name;
    public String phone;
    public int type;
    public String head;
    public int is_company;
    public int is_factory;
    public int is_yingpin;
    public int is_company_shenhe;
    public int is_factory_shenhe;
    public int is_yingpin_shenhe;

    @Override
    public String toString() {
        return "UserInfoMode{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", type=" + type +
                ", head='" + head + '\'' +
                ", is_company=" + is_company +
                ", is_factory=" + is_factory +
                ", is_yingpin=" + is_yingpin +
                ", is_company_shenhe=" + is_company_shenhe +
                ", is_factory_shenhe=" + is_factory_shenhe +
                ", is_yingpin_shenhe=" + is_yingpin_shenhe +
                '}';
    }
}
