package com.habit.star.ui.young.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.habit.commonlibrary.apt.SingleClick;
import com.habit.commonlibrary.widget.ProgressbarLayout;
import com.habit.commonlibrary.widget.ToolbarWithBackRightProgress;
import com.habit.star.R;
import com.habit.star.api.HttpResultSubscriber;
import com.habit.star.api.HttpServerImpl;
import com.habit.star.app.App;
import com.habit.star.app.RouterConstants;
import com.habit.star.base.BaseFragment;
import com.habit.star.pojo.po.NengLiangVO;
import com.habit.star.pojo.po.TaskBO;
import com.habit.star.pojo.po.XIaoJiangBO;
import com.habit.star.presenter.CommonPresenter;
import com.habit.star.presenter.contract.CommonContract;
import com.habit.star.ui.mine.activity.MineMainActivity;
import com.habit.star.ui.mine.fragment.XunLianJiHuaActivity;
import com.habit.star.ui.train.activity.TainMainActivity;
import com.habit.star.utils.ToastUtil;
import com.habit.star.widget.waterview.WaterFlake;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;
import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;


/**
 * @version V1.0
 * @date: 2020-02-13 21:31
 * @ClassName: YoungHomeFragment.java
 * @Description:小将主页
 * @author: sundongdong
 */
public class YoungHomeFragment extends BaseFragment<CommonPresenter> implements CommonContract.View {

    @BindView(R.id.progress_fragment_common_view)
    ProgressbarLayout progress;
    @BindView(R.id.toolbar_layout_toolbar)
    ToolbarWithBackRightProgress toolbarLayoutToolbar;
    @BindView(R.id.iv_user_header_fragment_young_home)
    CircleImageView ivUserHeaderFragmentYoungHome;
    @BindView(R.id.ic_cj_fragment_young_home)
    AppCompatImageView icCjFragmentYoungHome;
    @BindView(R.id.ic_rw_fragment_young_home)
    AppCompatImageView icRwFragmentYoungHome;
    @BindView(R.id.ic_rw2_fragment_young_home)
    AppCompatImageView icRw2FragmentYoungHome;
    @BindView(R.id.gi_view_monkey_fragment_young_home)
    GifImageView giViewMonkeyFragmentYoungHome;
    GifDrawable gifDrawable;
    @BindView(R.id.tv_nlz)
    AppCompatTextView tvNlz;
    @BindView(R.id.ll_nlz)
    LinearLayout llNlz;
    @BindView(R.id.tv_cj)
    AppCompatImageView tvCj;
    @BindView(R.id.ll_cj)
    LinearLayout llCj;
    @BindView(R.id.water_flake)
    WaterFlake waterFlake;
    @BindView(R.id.chenghao_img)
    AppCompatImageView chenghaoImg;
    Unbinder unbinder;
    @BindView(R.id.xiaojiang_layout)
    LinearLayout xiaojiangLayout;
    Unbinder unbinder1;

    public static YoungHomeFragment newInstance(Bundle bundle) {
        YoungHomeFragment fragment = new YoungHomeFragment();
        if (bundle != null) {
            fragment.setArguments(bundle);
        }

        return fragment;
    }

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_young_home;
    }

    @Override
    protected String getLogTag() {
        return "YoungHomeFragment %s";
    }

    @Override
    protected void initEventAndData() {
        gifDrawable = (GifDrawable) giViewMonkeyFragmentYoungHome.getDrawable();
        gifDrawable.start();
        Glide.with(getActivity()).load(App.userBO.getImage()).into(ivUserHeaderFragmentYoungHome);
        getEnergies();
    }



    @Override
    public void onStart() {
        super.onStart();
        if (gifDrawable != null) {
            gifDrawable.start();
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        getYoungGeneralInfo();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (gifDrawable != null) {
            gifDrawable.stop();

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (gifDrawable != null) {
            gifDrawable.stop();
            gifDrawable = null;
        }
    }

    @Override
    public void showProgress() {

        progress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progress.setVisibility(View.GONE);
    }

    @Override
    public void showError(String msg) {
        ToastUtil.show(msg);
    }

    @Override
    public void showError(int errorCode) {

    }


    @SingleClick
    @OnClick({R.id.ic_cj_fragment_young_home,
            R.id.ic_rw_fragment_young_home,
            R.id.ic_rw2_fragment_young_home,
            R.id.ll_nlz, R.id.ll_cj})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.ic_cj_fragment_young_home:
                intent = new Intent();
                intent.putExtra(RouterConstants.ARG_MODE, RouterConstants.SHOW_MY_ACHIVEMENT);
                intent.setClass(_mActivity, MineMainActivity.class);
                startActivity(intent);
                break;
            case R.id.ic_rw_fragment_young_home:
                getTaskList();
                break;
            case R.id.ic_rw2_fragment_young_home:
                gotoActivity(XunLianJiHuaActivity.class, false);
                break;
            case R.id.ll_nlz:
                intent = new Intent();
                intent.putExtra(RouterConstants.ARG_MODE, RouterConstants.ENERGY_VALUE);
                intent.setClass(_mActivity, TainMainActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_cj:
                intent = new Intent();
                intent.putExtra(RouterConstants.ARG_MODE, RouterConstants.SHOW_MY_PK);
                intent.setClass(_mActivity, MineMainActivity.class);
                startActivity(intent);
                break;
        }
    }


    /**
     * 获取待完成任务列表
     */
    private void getTaskList() {
        showProgress(null);
        HttpServerImpl.getTaskList().subscribe(new HttpResultSubscriber<List<TaskBO>>() {
            @Override
            public void onSuccess(List<TaskBO> s) {
                stopProgress();
                PopYoungHomeDialog popYoungHomeDialog = new PopYoungHomeDialog(getActivity(), s);
                popYoungHomeDialog.showAtLocation(getActivity().getWindow().getDecorView());
            }

            @Override
            public void onFiled(String message) {
                stopProgress();
                showToast(message);
            }
        });
    }


    /**
     * 获取所有待领取能量
     */
    private void getEnergies() {
        HttpServerImpl.getEnergies().subscribe(new HttpResultSubscriber<List<NengLiangVO>>() {
            @Override
            public void onSuccess(List<NengLiangVO> s) {
                showEnergies(s);
            }

            @Override
            public void onFiled(String message) {
                showToast(message);
            }
        });
    }

    /**
     * 显示能力球布局
     */
    private void showEnergies(List<NengLiangVO> s) {
//        waterFlake.setModelList(s, giViewMonkeyFragmentYoungHome);
        waterFlake.setModelList(s, xiaojiangLayout);
        waterFlake.setOnWaterItemListener(new WaterFlake.OnWaterItemListener() {
            @Override
            public void onItemClick(NengLiangVO waterModel) {
                receiveEnergy(waterModel.getId());
            }
        });
    }


    /**
     * 领取能量
     */
    private void receiveEnergy(int id) {
        HttpServerImpl.receiveEnergy(id).subscribe(new HttpResultSubscriber<String>() {
            @Override
            public void onSuccess(String s) {
                getYoungGeneralInfo();
            }

            @Override
            public void onFiled(String message) {
                showToast(message);
            }
        });
    }


    /**
     * 查询小将信息
     */
    private void getYoungGeneralInfo() {
        HttpServerImpl.getYoungGeneralInfo().subscribe(new HttpResultSubscriber<XIaoJiangBO>() {
            @Override
            public void onSuccess(XIaoJiangBO s) {
                App.xIaoJiangBO = s;
                if (s != null) {
                    tvNlz.setText(s.getEnergyValue());
                    Glide.with(getActivity()).load(s.getIcon()).into(chenghaoImg);
                    Glide.with(getActivity()).load(s.getImage()).into(tvCj);
                    toolbarLayoutToolbar.setTitle(s.getNickName());
                }
            }

            @Override
            public void onFiled(String message) {
                showToast(message);
            }
        });
    }

}
