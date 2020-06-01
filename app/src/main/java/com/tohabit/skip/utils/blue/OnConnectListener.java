package com.tohabit.skip.utils.blue;

/**
 * author : wuliang
 * e-mail : wuliang6661@163.com
 * date   : 2020/5/2011:00
 * desc   : 蓝牙的连接监听
 * version: 1.0
 */
public interface OnConnectListener {


    /**
     * 连接成功
     */
    void onConnectSourcess();

    /**
     * 连接失败
     */
    void onConnectError();


    /**
     * 监听返回的数据
     */
    void onNitifion(byte[] bytes);

}
