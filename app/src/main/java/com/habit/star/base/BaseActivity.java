package com.habit.star.base;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.svprogresshud.SVProgressHUD;
import com.habit.commonlibrary.utils.CompatResourceUtils;
import com.habit.commonlibrary.utils.CustomToast;
import com.habit.star.R;
import com.habit.star.anim.FadeAnimator;
import com.habit.star.app.App;
import com.habit.star.di.component.ActivityComponent;
import com.habit.star.di.component.DaggerActivityComponent;
import com.habit.star.di.module.ActivityModule;
import com.habit.star.utils.AppManager;
import com.habit.star.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation.SupportActivity;

/**
 * Created by sundongdong on 2017/2/24.
 * activity基类
 */
public abstract class BaseActivity<T extends BasePresenter> extends SupportActivity implements BaseView {
    @Inject
    protected T mPresenter;
    protected Context mContext;
    private Unbinder mUnBinder;
    private String TAG = "";
    private SVProgressHUD svProgressHUD;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        svProgressHUD = new SVProgressHUD(this);
        mUnBinder = ButterKnife.bind(this);
        mContext = getApplicationContext();
        TAG = getLogTag();
        initInject();
        AppManager.getAppManager().addActivity(this);
        if (mPresenter != null)
            mPresenter.attachView(this);
        //绑定TRouter，以便传值到目标Activity中
        //设置fragment切换的动画
        setFragmentAnimator(new FadeAnimator());
        EventBus.getDefault().register(this);
        initEventAndData();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(Object event) {

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    protected void setToolBar(Toolbar toolbar, String title) {
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressedSupport();
            }
        });

    }


    /**
     * 显示加载进度弹窗
     */
    public void showProgress(String message) {
        svProgressHUD.showWithStatus(message, SVProgressHUD.SVProgressHUDMaskType.Black);
    }

    /**
     * 停止弹窗
     */
    protected void stopProgress() {
        if (svProgressHUD.isShowing()) {
            svProgressHUD.dismiss();
        }
    }


    /**
     * 设置返回
     */
    protected void goBack() {
        LinearLayout imageView = findViewById(R.id.back);
        imageView.setVisibility(View.VISIBLE);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * 设置标题
     *
     * @param title
     */
    protected void setTitleText(String title) {
        TextView titleTex = findViewById(R.id.title_text);
        titleTex.setText(title);
    }


    public void showToast(String message) {
        ToastUtil.show(message);
    }


    /**
     * 常用的跳转方法
     */
    public void gotoActivity(Class<?> cls, boolean isFinish) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
        if (isFinish) {
            finish();
        }
    }

    public void gotoActivity(Class<?> cls, Bundle bundle, boolean isFinish) {
        Intent intent = new Intent(this, cls);
        intent.putExtras(bundle);
        startActivity(intent);
        if (isFinish) {
            finish();
        }
    }


    protected ActivityComponent getActivityComponent() {
        return DaggerActivityComponent.builder()
                .appComponent(App.getAppComponent())
                .activityModule(getActivityModule())
                .build();
    }

    protected ActivityModule getActivityModule() {
        return new ActivityModule(this);
    }

    /**
     * 打印日志
     *
     * @param content
     */

    public void log(String content) {
        Log.i(getLogTag(), content);
    }

    /**
     * 获取string资源文件
     *
     * @param StringId
     * @return
     */
    protected String getStringResource(int StringId) {
        return getResources().getString(StringId);
    }

    public void showMsg(String msg) {
        showCustomToast(msg);
//        ToastUtil.show(msg);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null)
            mPresenter.detachView();
        mUnBinder.unbind();
        EventBus.getDefault().unregister(this);
        AppManager.getAppManager().removeActivity(this);
    }

    public final void showCustomToast(CharSequence txt) {
        new CustomToast.Builder(this)
                .setText(txt)
                .setBackgroundColor(CompatResourceUtils.getColor(this, R.color.colorAccent))
                .setTextColor(Color.WHITE)
                .show();
    }


    protected abstract void initInject();


    protected abstract int getLayoutId();


    protected abstract String getLogTag();


    protected abstract void initEventAndData();

}
