package com.tohabit.skip.ui.young.websocket;

import android.os.Handler;
import android.util.Log;

import com.blankj.utilcode.util.LogUtils;
import com.tohabit.skip.app.App;

import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

/**
 * 连接websocket 的工具类
 */
public class WebSocketUtils {

    private static WebSocketUtils socketUtils;

    /**
     * websocket
     */
    private JWebSocketClient client;

    /**
     * websocket 是否连接  默认未连接
     */
    private boolean isOpen = false;

    private onNotifiListener listener;


    public static synchronized WebSocketUtils getInstance() {
        if (socketUtils == null) {
            socketUtils = new WebSocketUtils();

        }
        return socketUtils;
    }

    private WebSocketUtils() {
        initSocket();
        mHandler.postDelayed(heartBeatRunnable, HEART_BEAT_RATE);//开启心跳检测
    }


    /**
     * 初始化websocket
     */
    private void initSocket() {
        URI uri = URI.create("ws://47.103.87.189:8081/websocket/" + App.userBO.getId());
        client = new JWebSocketClient(uri) {
            @Override
            public void onMessage(String message) {
                //message就是接收到的消息
                Log.e("JWebSClientService", message);
                if (listener != null) {
                    listener.onNotifi(message);
                }
            }

            //连接成功
            @Override
            public void onOpen(ServerHandshake handshakedata) {
                super.onOpen(handshakedata);
                LogUtils.e("socket连接已成功！");
                isOpen = true;
            }

            //连接断开
            @Override
            public void onClose(int code, String reason, boolean remote) {
                super.onClose(code, reason, remote);
                isOpen = false;
                LogUtils.e("socket连接断开！");
            }
        };
    }


    /**
     * 连接websocket
     */
    public void connect() {
        new Thread() {
            @Override
            public void run() {
                try {
                    //connectBlocking多出一个等待操作，会先连接再发送，否则未连接发送会报错
                    client.connectBlocking();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }


    /**
     * 发送消息
     *
     * @param msg
     */
    public void sendMsg(String msg) {
        if (null != client) {
            Log.e("JWebSocketClientService", "发送的消息：" + msg);
            client.send(msg);
        }
    }

    /**
     * 断开连接
     */
    public void closeConnect() {
        try {
            if (null != client) {
                client.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            client = null;
            mHandler.removeCallbacksAndMessages(null);
            socketUtils = null;
        }
    }


    /**
     * 监听websocket的连接
     */
    public interface onNotifiListener {

        void onNotifi(String message);

    }


    public void setOnNotifiListener(onNotifiListener listener) {
        this.listener = listener;
    }


    /**
     * 获取websocket连接状态
     */
    public boolean getState() {
        return isOpen;
    }


    //    -------------------------------------websocket心跳检测------------------------------------------------
    private static final long HEART_BEAT_RATE = 10 * 1000;//每隔10秒进行一次对长连接的心跳检测

    private Handler mHandler = new Handler();

    private Runnable heartBeatRunnable = new Runnable() {
        @Override
        public void run() {
            Log.e("JWebSocketClientService", "心跳包检测websocket连接状态");
            if (client != null) {
                if (client.isClosed()) {
                    reconnectWs();
                }
            } else {
                //如果client已为空，重新初始化连接
                client = null;
                initSocket();
            }
            //每隔一定的时间，对长连接进行一次心跳检测
            mHandler.postDelayed(this, HEART_BEAT_RATE);
        }
    };

    /**
     * 开启重连
     */
    private void reconnectWs() {
        mHandler.removeCallbacks(heartBeatRunnable);
        new Thread() {
            @Override
            public void run() {
                try {
                    Log.e("JWebSocketClientService", "开启重连");
                    client.reconnectBlocking();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
