package com.tohabit.skip.app;

import android.app.Application;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.support.multidex.MultiDex;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.Utils;
import com.tohabit.skip.di.component.AppComponent;
import com.tohabit.skip.di.component.DaggerAppComponent;
import com.tohabit.skip.di.module.AppModule;
import com.tohabit.skip.pojo.po.MusicBO;
import com.tohabit.skip.pojo.po.MusicBeatBO;
import com.tohabit.skip.pojo.po.UserBO;
import com.tohabit.skip.pojo.po.XIaoJiangBO;
import com.tohabit.skip.service.UartService;
import com.tohabit.skip.ui.mine.bean.UserInfoMode;

import cat.ereza.customactivityoncrash.CustomActivityOnCrash;
import cn.jpush.android.api.JPushInterface;
import id.zelory.compressor.Compressor;

//import com.sdwfqin.cbt.CbtManager;

/**
 * Author:sundongdong
 * DATE:2019-07-29 13:52
 * FileName App
 * Description:
 */
public class App extends Application {

    private static App instance;
    public static AppComponent appComponent;
    public Context context;
    public UserInfoMode userInfoMode;
    public Compressor imageCompressor = null;
    public Typeface tf;

    public static String token = "";
    public static SPUtils spUtils;

    private static final String TAG = "habit_star";
    public static UserBO userBO;
    public static XIaoJiangBO xIaoJiangBO;
    public static UartService blueService;
    public static BluetoothDevice connectDevice;

    public static boolean AppInBack = false;  //App 是否在后台
//    /**
//     * 播放的音乐
//     */
//    public static MusicBO musicBO;
//
//    /**
//     * 使用的节拍
//     */
//    public static String beat;

    public static MusicBeatBO musicBeatBO;

    public static synchronized App getInstance() {
        return instance;
    }


    public static String getStringResource(int strRes) {
        if (instance == null) {
            return "";
        }
        return instance.getResources().getString(strRes);
    }

    public static AppComponent getAppComponent() {
        if (appComponent == null) {
            appComponent = DaggerAppComponent.builder()
                    .appModule(new AppModule(instance))
                    .build();
        }
        return appComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        context = getApplicationContext();
        AssetManager assetManager = context.getAssets();
        Utils.init(this);
        spUtils = SPUtils.getInstance(TAG);
        tf = Typeface.createFromAsset(assetManager, "fonts/DS-DIGI-1.ttf");
        CustomActivityOnCrash.install(this);

        JPushInterface.setDebugMode(true);    // 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);            // 初始化 JPush

        registerActivityLifecycleCallbacks(new AppLifecycleHandler());
    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }


    /**
     * 绳子是否连接上
     */
    public static boolean isConnect() {
        if (App.blueService != null && App.blueService.getConnectionState() == UartService.STATE_CONNECTED) {
            return true;
        }
        return false;
    }
}
