package com.tohabit.skip.ui.train.fragment;

import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.tohabit.commonlibrary.apt.SingleClick;
import com.tohabit.commonlibrary.decoration.HorizontalDividerItemDecoration;
import com.tohabit.commonlibrary.widget.ProgressbarLayout;
import com.tohabit.skip.R;
import com.tohabit.skip.api.HttpResultSubscriber;
import com.tohabit.skip.api.HttpServerImpl;
import com.tohabit.skip.base.BaseFragment;
import com.tohabit.skip.pojo.po.EnergyBO;
import com.tohabit.skip.pojo.po.NengLiangVO;
import com.tohabit.skip.ui.train.adapter.EnergyRewardListAdapter;
import com.tohabit.skip.ui.train.bean.EnergyRewardModel;
import com.tohabit.skip.ui.train.contract.EnergyDetailContract;
import com.tohabit.skip.ui.train.presenter.EnergyDetailPresenter;
import com.tohabit.skip.utils.ToastUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * @version V1.0
 * @date: 2020-02-13 01:19
 * @ClassName: EnergyDetailFragment.java
 * @Description:
 * @author: sundongdong
 */
public class EnergyDetailFragment extends BaseFragment<EnergyDetailPresenter> implements EnergyDetailContract.View {

    @BindView(R.id.progress_fragment_common_view)
    ProgressbarLayout progress;
    @BindView(R.id.ll_back_fragment_energy_detail)
    LinearLayout llBackFragmentEnergyDetail;
    @BindView(R.id.toolbar_layout_toolbar)
    LinearLayout toolbarLayoutToolbar;
    @BindView(R.id.tv_value1_fragment_energy_detail)
    AppCompatTextView tvValue1FragmentEnergyDetail;
    @BindView(R.id.tv_value2_fragment_energy_detail)
    AppCompatTextView tvValue2FragmentEnergyDetail;
    @BindView(R.id.tv_nlz_detail_fragment_energy_detail)
    AppCompatTextView tvNlzDetailFragmentEnergyDetail;
    @BindView(R.id.rv_nlz_detail_list_fragment_energy_detail)
    RecyclerView rvNlzDetailListFragmentEnergyDetail;
    @BindView(R.id.tv_energy_count_fragment_test_result)
    AppCompatTextView tvEnergyCountFragmentTestResult;
    Unbinder unbinder;


    private EnergyRewardListAdapter mListAdapter;

    public static EnergyDetailFragment newInstance(Bundle bundle) {
        EnergyDetailFragment fragment = new EnergyDetailFragment();
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
        return R.layout.fragment_energy_detail;
    }

    @Override
    protected String getLogTag() {
        return "EnergyDetailFragment %s";
    }

    @Override
    protected void initEventAndData() {
        initAdapter();
//        mPresenter.getList();
        getEnergyData();
        getDataEnergyList();
    }

    private void initAdapter() {
        rvNlzDetailListFragmentEnergyDetail.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getActivity()).sizeResId(R.dimen.size_list_item_divider_address).colorResId(R.color.transparent).build());
        rvNlzDetailListFragmentEnergyDetail.setLayoutManager(new LinearLayoutManager(getActivity()));
        mListAdapter = new EnergyRewardListAdapter(mContext);
        rvNlzDetailListFragmentEnergyDetail.setAdapter(mListAdapter);
    }

    @Override
    public void setList(List<EnergyRewardModel> data) {

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
    @OnClick({R.id.ll_back_fragment_energy_detail,
            R.id.tv_nlz_detail_fragment_energy_detail})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_back_fragment_energy_detail:
                _mActivity.onBackPressedSupport();
                break;
            case R.id.tv_nlz_detail_fragment_energy_detail:
                break;
        }
    }


    private void getEnergyData() {
        HttpServerImpl.getEnergyData().subscribe(new HttpResultSubscriber<EnergyBO>() {
            @Override
            public void onSuccess(EnergyBO s) {
                tvValue1FragmentEnergyDetail.setText(s.getVailEnergyValue() + "");
                tvValue2FragmentEnergyDetail.setText(s.getConsumeEnergyValue() + "");
                tvEnergyCountFragmentTestResult.setText(s.getEnergyValue() + "");
            }

            @Override
            public void onFiled(String message) {
                showToast(message);
            }
        });
    }


    private void getDataEnergyList() {
        HttpServerImpl.getDataEnergyList().subscribe(new HttpResultSubscriber<List<NengLiangVO>>() {
            @Override
            public void onSuccess(List<NengLiangVO> s) {
                mListAdapter.setNewData(s);
            }

            @Override
            public void onFiled(String message) {
                showToast(message);
            }
        });
    }

}
