package com.habit.star.common.bean;

import com.habit.star.model.bean.BaseMode;

/*
 * 创建日期：2019-10-22 20:04
 * @author sundongdong
 * @version 1.0
 * @since
 * 文件名称：CommonEventBean.java
 * 类说明：
 */
public class CommonEventBean extends BaseMode {
    public static final int PRAISE_CONSULTATION_SUCCESS = 001;
    public int evnetTag;
    /**
     * 内容
     */
    public String content;

    @Override
    public String toString() {
        return "CommonEventBean{" +
                "evnetTag=" + evnetTag +
                ", content='" + content + '\'' +
                '}';
    }
}
