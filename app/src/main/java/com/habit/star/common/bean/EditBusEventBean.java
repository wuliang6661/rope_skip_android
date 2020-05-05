package com.habit.star.common.bean;

import com.habit.star.model.bean.BaseMode;

/**
 * 创建日期：2018/11/19 10:10
 * @author sundongdong6
 * @version 1.0 
 * @since  
 * 文件名称： EditBusEventBean.java 
 * 类说明：编辑事件
 */
public class EditBusEventBean extends BaseMode {
    public static final int FEED_BACK_KEY = 001;
    /**
     * tag
     * 001反馈
     */
    public int evnetTag;
    /**
     * 内容
     */
    public String content;

    @Override
    public String toString() {
        return "EditBusEventBean{" +
                "evnetTag=" + evnetTag +
                ", content='" + content + '\'' +
                '}';
    }
}
