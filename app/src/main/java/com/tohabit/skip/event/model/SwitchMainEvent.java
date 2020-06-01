package com.tohabit.skip.event.model;

/**
 * author : wuliang
 * e-mail : wuliang6661@163.com
 * date   : 2020/5/2210:18
 * desc   : 切换主页
 * version: 1.0
 */
public class SwitchMainEvent {

    /**
     * 主页要去的页面
     */
    public int toSwitch = 2;


    /**
     * 子页面要去的页面
     */
    public int gotoSonPage = 0;


    public SwitchMainEvent(){}
}
