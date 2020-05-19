package com.habit.star.ui.train.fragment;

import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.habit.commonlibrary.apt.SingleClick;
import com.habit.commonlibrary.widget.ProgressbarLayout;
import com.habit.star.R;
import com.habit.star.api.HttpResultSubscriber;
import com.habit.star.api.HttpServerImpl;
import com.habit.star.app.App;
import com.habit.star.base.BaseFragment;
import com.habit.star.ui.train.contract.EnergyValueContract;
import com.habit.star.ui.train.presenter.EnergyValuePresenter;
import com.habit.star.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;


/**
 * @version V1.0
 * @date: 2020-02-12 22:23
 * @ClassName: EnergyValueFragment.java
 * @Description:能量值页面
 * @author: sundongdong
 */
public class EnergyValueFragment extends BaseFragment<EnergyValuePresenter> implements EnergyValueContract.View {

    @BindView(R.id.progress_fragment_common_view)
    ProgressbarLayout progress;
    @BindView(R.id.ll_back_fragment_train_plan)
    LinearLayout llBackFragmentTrainPlan;
    @BindView(R.id.ll_setting_fragment_train_plan)
    LinearLayout llSettingFragmentTrainPlan;
    @BindView(R.id.toolbar_layout_toolbar)
    LinearLayout toolbarLayoutToolbar;
    @BindView(R.id.iv_user_header_fragment_mine)
    CircleImageView ivUserHeaderFragmentMine;
    @BindView(R.id.v_center_tag)
    View vCenterTag;
    @BindView(R.id.tv_count_fragment_energy_value)
    AppCompatTextView tvCount;
    @BindView(R.id.ll_count_fragment_energy_value)
    LinearLayout llCount;
    @BindView(R.id.xiaojiang_name)
    AppCompatTextView xiaojiangName;
    @BindView(R.id.nengliang_dengji)
    RecyclerView nengliangDengji;
    Unbinder unbinder;


    public static EnergyValueFragment newInstance(Bundle bundle) {
        EnergyValueFragment fragment = new EnergyValueFragment();
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
        return R.layout.fragment_energy_value;
    }

    @Override
    protected String getLogTag() {
        return "EnergyValueFragment %s";
    }

    @Override
    protected void initEventAndData() {

        xiaojiangName.setText(App.xIaoJiangBO.getNickName());
        Glide.with(getActivity()).load(App.userBO.getImage()).into(ivUserHeaderFragmentMine);
        tvCount.setText(App.xIaoJiangBO.getEnergyValue());

        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        nengliangDengji.setLayoutManager(manager);
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
    @OnClick({R.id.ll_back_fragment_train_plan,
            R.id.ll_count_fragment_energy_value})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_back_fragment_train_plan:
                _mActivity.onBackPressedSupport();
                break;
            case R.id.ll_count_fragment_energy_value:
                start(EnergyDetailFragment.newInstance(null));
                break;
        }
    }


    /**
     * 获取所有能量登记
     */
    private void getEnergyLevelInfoList(){
        HttpServerImpl.getEnergyLevelInfoList().subscribe(new HttpResultSubscriber<String>() {
            @Override
            public void onSuccess(String s) {

            }

            @Override
            public void onFiled(String message) {

            }
        });
    }

}
