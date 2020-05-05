package com.habit.star.ui.mine.fragment;

import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import android.widget.CheckBox;

import com.habit.commonlibrary.widget.ProgressbarLayout;
import com.habit.commonlibrary.widget.ToolbarWithBackRightProgress;
import com.habit.star.R;
import com.habit.star.app.RouterConstants;
import com.habit.star.base.BaseFragment;
import com.habit.star.ui.mine.bean.AddressModel;
import com.habit.star.ui.mine.contract.AddAddressContract;
import com.habit.star.ui.mine.presenter.AddAddressPresenter;
import com.habit.star.utils.ToastUtil;

import butterknife.BindView;

/*
 * 创建日期：2020-01-21 20:09
 * @author sundongdong
 * @version 1.0
 * @since
 * 文件名称：AddAddressFragment.java
 * 类说明：新增地址
 */
public class AddAddressFragment extends BaseFragment<AddAddressPresenter> implements AddAddressContract.View {

    @BindView(R.id.toolbar_layout_toolbar)
    ToolbarWithBackRightProgress toolbar;
    @BindView(R.id.progress_fragment_add_address)
    ProgressbarLayout progress;
    @BindView(R.id.et_name_fragment_add_address)
    AppCompatEditText etNameFragmentAddAddress;
    @BindView(R.id.et_tel_fragment_add_address)
    AppCompatEditText etTelFragmentAddAddress;
//    @BindView(R.id.et_region_fragment_add_address)
//    AppCompatEditText etRegionFragmentAddAddress;
//    @BindView(R.id.item_address_detail_fragment_add_address)
//    LilayItemClickableWithHeadImageTopDivider itemAddressDetailFragmentAddAddress;
    @BindView(R.id.cb_mr_fragment_add_address)
    CheckBox cbMrFragmentAddAddress;
    @BindView(R.id.btn_save_fragment_add_address)
    AppCompatButton btnSaveFragmentAddAddress;


    private AddressModel mAddressModel;

    public static AddAddressFragment newInstance(Bundle bundle) {
        AddAddressFragment fragment = new AddAddressFragment();
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
        return R.layout.fragment_add_address;
    }

    @Override
    protected String getLogTag() {
        return "AddAddressFragment %s";
    }

    @Override
    protected void initEventAndData() {
        initDialog();
        toolbar.setBackIBClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _mActivity.onBackPressedSupport();
            }
        });
        Bundle bundle = getArguments();
        if (bundle != null) {
            mAddressModel = (AddressModel) bundle.getSerializable(RouterConstants.ARG_BEAN);
        }
        if (mAddressModel != null) {
//            toolbar.setTitle("编辑地址");
            etNameFragmentAddAddress.setText(mAddressModel.title);
            etNameFragmentAddAddress.setText(mAddressModel.tel);
            etNameFragmentAddAddress.setText(mAddressModel.address);
        }
    }


    private void initDialog() {

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

//    @OnClick({R.id.item_address_detail_fragment_add_address, R.id.btn_save_fragment_add_address})
//    public void onViewClicked(View view) {
//        switch (view.getId()) {
//            case R.id.item_address_detail_fragment_add_address:
//                break;
//            case R.id.btn_save_fragment_add_address:
//                break;
//        }
//    }
}
