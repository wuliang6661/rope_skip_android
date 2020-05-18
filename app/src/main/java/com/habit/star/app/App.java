package com.habit.star.app;

import android.app.Application;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.support.multidex.MultiDex;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.Utils;
import com.habit.star.di.component.AppComponent;
import com.habit.star.di.component.DaggerAppComponent;
import com.habit.star.di.module.AppModule;
import com.habit.star.pojo.po.UserBO;
import com.habit.star.ui.login.bean.LoginBean;
import com.habit.star.ui.mine.bean.UserInfoMode;
import com.sdwfqin.cbt.CbtManager;

import cat.ereza.customactivityoncrash.CustomActivityOnCrash;
import id.zelory.compressor.Compressor;

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
    public LoginBean loginBean;
    public UserInfoMode userInfoMode;
    public Compressor imageCompressor = null;
    public Typeface tf;

    public static String token = "";
    public static SPUtils spUtils;

    private static final String TAG = "habit_star";
    public static UserBO userBO;

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

        //初始化经典蓝牙
        CbtManager
                .getInstance()
                // 初始化
                .init(this)
                // 是否打印相关日志
                .enableLog(true);
    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }



}
