package com.tohabit.skip.ui.train.fragment;

import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.tohabit.commonlibrary.apt.SingleClick;
import com.tohabit.commonlibrary.widget.ProgressbarLayout;
import com.tohabit.skip.R;
import com.tohabit.skip.api.HttpResultSubscriber;
import com.tohabit.skip.api.HttpServerImpl;
import com.tohabit.skip.app.App;
import com.tohabit.skip.base.BaseFragment;
import com.tohabit.skip.pojo.po.NengLiangDengjiBO;
import com.tohabit.skip.ui.train.contract.EnergyValueContract;
import com.tohabit.skip.ui.train.presenter.EnergyValuePresenter;
import com.tohabit.skip.utils.ToastUtil;
import com.tohabit.skip.widget.lgrecycleadapter.LGRecycleViewAdapter;
import com.tohabit.skip.widget.lgrecycleadapter.LGViewHolder;

import java.util.List;

import butterknife.BindView;
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
        getEnergyLevelInfoList();
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
    private void getEnergyLevelInfoList() {
        HttpServerImpl.getEnergyLevelInfoList().subscribe(new HttpResultSubscriber<List<NengLiangDengjiBO>>() {
            @Override
            public void onSuccess(List<NengLiangDengjiBO> s) {
                setAdapter(s);
            }

            @Override
            public void onFiled(String message) {
                showToast(message);
            }
        });
    }


    private void setAdapter(List<NengLiangDengjiBO> dengjiBOS) {
        LGRecycleViewAdapter<NengLiangDengjiBO> adapter = new LGRecycleViewAdapter<NengLiangDengjiBO>(dengjiBOS) {
            @Override
            public int getLayoutId(int viewType) {
                return R.layout.item_nengliang_dengji;
            }

            @Override
            public void convert(LGViewHolder holder, NengLiangDengjiBO nengLiangDengjiBO, int position) {
                if (getItemCount() != 0 && position == (getItemCount() - 1)) {
                    holder.getView(R.id.iv_line3_center_fragment_energy_value).setVisibility(View.GONE);
                } else {
                    holder.getView(R.id.iv_line3_center_fragment_energy_value).setVisibility(View.VISIBLE);
                }
                holder.setText(R.id.nengliang_text, nengLiangDengjiBO.getName());
                holder.setImageUrl(getActivity(), R.id.nengliang_img, nengLiangDengjiBO.getImage());
            }
        };
        nengliangDengji.setAdapter(adapter);
    }
}
