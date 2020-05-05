package com.habit.star.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.transition.Fade;
import android.transition.TransitionSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bigkoo.svprogresshud.SVProgressHUD;
import com.habit.star.anim.DetailTransition;
import com.habit.star.app.App;
import com.habit.star.di.component.DaggerFragmentComponent;
import com.habit.star.di.component.FragmentComponent;
import com.habit.star.di.module.FragmentModule;
import com.habit.star.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by sundongdong on 2017/2/24.
 * Fragment基类
 */
public abstract class BaseFragment<T extends BasePresenter> extends SupportFragment implements BaseView {

    @Inject
    protected T mPresenter;
    protected View mView;
    protected Activity mActivity;
    protected Context mContext;
    protected boolean isInited = false;
    private Unbinder mUnBinder;
    private String TAG = "";
    private SVProgressHUD svProgressHUD;

    @Override
    public void onAttach(Context context) {
        mActivity = (Activity) context;
        mContext = getContext();
        super.onAttach(context);
    }

    protected FragmentComponent getFragmentComponent() {
        return DaggerFragmentComponent.builder()
                .appComponent(App.getAppComponent())
                .fragmentModule(getFragmentModule())
                .build();
    }

    protected FragmentModule getFragmentModule() {
        return new FragmentModule(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(getLayoutId(), null);
        }
        initInject();
        return mView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        svProgressHUD = new SVProgressHUD(getActivity());
        if (mPresenter!=null){
            mPresenter.attachView(this);
        }
        mUnBinder = ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        TAG = getLogTag();
        if (savedInstanceState == null) {
            if (!isHidden()) {
                isInited = true;
                initEventAndData();
            }
        } else {
            if (!isHidden()) {
                isInited = true;
                initEventAndData();
            }
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!isInited && !hidden) {
            isInited = true;
            initEventAndData();
        }
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



    public void showToast(String message) {
        ToastUtil.show(message);
    }


    /**
     * 常用的跳转方法
     */
    public void gotoActivity(Class<?> cls, boolean isFinish) {
        Intent intent = new Intent(getActivity(), cls);
        startActivity(intent);
        if (isFinish) {
            getActivity().finish();
        }
    }

    public void gotoActivity(Class<?> cls, Bundle bundle, boolean isFinish) {
        Intent intent = new Intent(getActivity(), cls);
        intent.putExtras(bundle);
        startActivity(intent);
        if (isFinish) {
            getActivity().finish();
        }
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
     * 如果fragment有toolbar则设置toolbar有导航功能
     *
     * @param toolbar
     * @param title
     */
    protected void setNavToolBar(Toolbar toolbar, int drawableId, String title) {
        toolbar.setTitle(title);
        toolbar.setNavigationIcon(drawableId);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pop();
            }
        });
    }

    /**
     * 如果fragment有toolbar则设置toolbar有导航功能
     *
     * @param toolbar
     * @param drawableId
     * @param title
     * @param clickListener
     */
    protected void setNavToolBar(Toolbar toolbar, int drawableId, String title, View.OnClickListener clickListener) {
        toolbar.setTitle(title);
        toolbar.setNavigationIcon(drawableId);
        toolbar.setNavigationOnClickListener(clickListener);
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

    /**
     * 检查对象是否为空
     *
     * @param o
     * @return
     */
    protected boolean checkNotNull(Object o) {
        if (o == null) {
            return false;
        }
        return true;
    }

    /**
     * fragment跳转转场动画
     *
     * @param sharedView
     * @param shareElementName
     * @param toFragment
     */
    public void startWithTransition(View sharedView, String shareElementName, SupportFragment toFragment) {
        startWithTransition(sharedView, shareElementName, new DetailTransition(), false, toFragment);
    }

    /**
     * fragment跳转转场动画
     *
     * @param sharedView
     * @param shareElementName
     * @param toFragment
     */
    public void startWithTransition(View sharedView, String shareElementName, TransitionSet transitionSet, SupportFragment toFragment) {
        startWithTransition(sharedView, shareElementName, transitionSet, false, toFragment);
    }

    /**
     * fragment跳转转场动画(目前startWithPop如果有转场动画会失效，暂不支持startWithPop)
     *
     * @param sharedView
     * @param shareElementName
     * @param toFragment
     */
    public void startWithPopWithTransition(View sharedView, String shareElementName, SupportFragment toFragment) {
        startWithTransition(sharedView, shareElementName, new DetailTransition(), true, toFragment);
    }

    /**
     * fragment跳转转场动画
     *
     * @param sharedView
     * @param shareElementName
     * @param toFragment
     */
    public void startWithTransition(View sharedView, String shareElementName, TransitionSet transition, boolean isPop, SupportFragment toFragment) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            setExitTransition(new Fade());
            setEnterTransition(new Fade());
            if (!isPop) {
                toFragment.setSharedElementReturnTransition(transition);
            }
            toFragment.setSharedElementEnterTransition(transition);
            // 25.1.0以下的support包,Material过渡动画只有在进栈时有,返回时没有;
            // 25.1.0+的support包，SharedElement正常
            toFragment.transaction()
                    .addSharedElement(sharedView, shareElementName)
                    .withPop(isPop)
                    .commit();
            start(toFragment);
//            getFragmentManager()
//                    .beginTransaction()
//                    .addSharedElement(sharedView, ViewCompat.getTransitionName(sharedView))
//                    .addToBackStack(TAG)
//                    .replace(R.id.frame_container_activity_login, toFragment)
//                    .commit();
        } else {
            if (isPop) {
                startWithPop(toFragment);
            } else {
                start(toFragment);

            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mUnBinder != null) {
            mUnBinder.unbind();
        }
        EventBus.getDefault().unregister(this);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(Object event) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) mPresenter.detachView();
    }
    protected abstract void initInject();

    protected abstract int getLayoutId();

    protected abstract String getLogTag();

    protected abstract void initEventAndData();
}

