package com.tohabit.skip.ui.login.fragment;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.StringUtils;
import com.tohabit.skip.R;
import com.tohabit.skip.app.App;
import com.tohabit.skip.app.Constants;
import com.tohabit.skip.base.BaseFragment;
import com.tohabit.skip.pojo.po.UserBO;
import com.tohabit.skip.ui.XieYiActivity;
import com.tohabit.skip.ui.activity.MainActivity;
import com.tohabit.skip.ui.login.contract.SplashContract;
import com.tohabit.skip.ui.login.presenter.SplashPresenter;
import com.tohabit.skip.utils.ToastUtil;

import butterknife.BindView;
import me.yokeyword.fragmentation.anim.DefaultNoAnimator;

/**
 * 创建日期：2018/6/1 11:04
 *
 * @author sundongdong
 * @version 3.0
 * @since 文件名称： SplashFragment.java
 * 类说明：启动界面
 */
public final class SplashFragment extends BaseFragment<SplashPresenter> implements SplashContract.View {

    @BindView(R.id.splash_layout)
    RelativeLayout splashLayout;

    public static final int splashTime = 1500;
    Handler mHandler;

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            startJump();
        }
    };

    /**
     * 开始界面跳转
     */
    private void startJump() {
        App.token = App.spUtils.getString(Constants.PREF_KEY_TOKEN);
        if (StringUtils.isEmpty(App.token)) {
            startWithPop(LoginFragment.newInstance(null));
        } else {
            mPresenter.getUserInfo();
        }
    }


    public static SplashFragment newInstance() {
        Bundle args = new Bundle();
        SplashFragment fragment = new SplashFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected String getLogTag() {
        return "SplashFragment";
    }

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_splash;
    }

    @Override
    protected void initEventAndData() {
        _mActivity.setFragmentAnimator(new DefaultNoAnimator());
        mHandler = new Handler();
        boolean isFirst = App.spUtils.getBoolean("isFirst", true);   //默认第一次登陆
        if (isFirst) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    showDialog();
                }
            }, 500);
        } else {
            mHandler.postDelayed(runnable, splashTime);
        }
    }


    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
    }

    @Override
    public void getUserInfo(UserBO userInfoMode) {
        App.userBO = userInfoMode;
        ///保存token
        App.spUtils.put(Constants.PREF_KEY_TOKEN, userInfoMode.getToken());
        gotoActivity(MainActivity.class, true);
    }

    @Override
    public void getUserInfoError() {
        startWithPop(LoginFragment.newInstance(null));
    }

    @Override
    public void setInitComponentSuccess() {

    }

    @Override
    public void setInitComponentFailed() {

    }

    @Override
    public void showProgress() {
    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showError(String msg) {
        ToastUtil.show(msg);
    }


    @Override
    public void showError(int errorCode) {

    }


    PopupWindow popupWindow;

    /**
     * 显示温馨提示
     */
    private void showDialog() {
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_wenxin, null);
        TextView cancle = dialogView.findViewById(R.id.cancle);
        TextView commit = dialogView.findViewById(R.id.commit);
        TextView tishi_msg = dialogView.findViewById(R.id.tishi_msg);
        cancle.setOnClickListener(v -> System.exit(0));
        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                App.spUtils.put("isFirst", false);
                startJump();
            }
        });

        SpannableString spannableString = new SpannableString(getString(R.string.xieyiandtiaokuan));
        spannableString.setSpan(new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                Bundle bundle = new Bundle();
                bundle.putInt("type", 0);
                gotoActivity(XieYiActivity.class, bundle, false);
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(false);
                ds.setColor(Color.parseColor("#7EC7F5"));
            }
        }, 227, 235, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        spannableString.setSpan(new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                Bundle bundle1 = new Bundle();
                bundle1.putInt("type", 1);
                gotoActivity(XieYiActivity.class, bundle1, false);
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(Color.parseColor("#7EC7F5"));
                ds.setUnderlineText(false);
            }
        }, 236, 242, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        tishi_msg.setText(spannableString);
        tishi_msg.setMovementMethod(LinkMovementMethod.getInstance());
        tishi_msg.setHighlightColor(ContextCompat.getColor(getActivity(), R.color.transparent));
        popupWindow = new PopupWindow();
        popupWindow.setBackgroundDrawable(new ColorDrawable(0));
        popupWindow.setContentView(dialogView);
        //设置PopupWindow弹出窗体的宽
        popupWindow.setWidth(SizeUtils.dp2px(268));
        //设置PopupWindow弹出窗体的高
        popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        //设置PopupWindow弹出窗体可点击
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(false);
        popupWindow.showAtLocation(splashLayout, Gravity.CENTER, 0, 0);
    }


    @Override
    public void onDestroyView() {
        if (mHandler != null) {
            mHandler.removeCallbacks(runnable);
        }
        super.onDestroyView();
    }

    @Override
    public boolean onBackPressedSupport() {
        _mActivity.finish();
        return true;
    }

}
