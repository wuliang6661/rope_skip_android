package com.tohabit.skip.ui.mine.fragment;

import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.StringUtils;
import com.tohabit.commonlibrary.widget.ProgressbarLayout;
import com.tohabit.commonlibrary.widget.ToolbarWithBackRightProgress;
import com.tohabit.skip.R;
import com.tohabit.skip.app.RouterConstants;
import com.tohabit.skip.base.BaseActivity;
import com.tohabit.skip.pojo.po.AddressBO;
import com.tohabit.skip.ui.mine.contract.AddAddressContract;
import com.tohabit.skip.ui.mine.presenter.AddAddressPresenter;
import com.tohabit.skip.utils.ToastUtil;
import com.lljjcoder.Interface.OnCityItemClickListener;
import com.lljjcoder.bean.CityBean;
import com.lljjcoder.bean.DistrictBean;
import com.lljjcoder.bean.ProvinceBean;
import com.lljjcoder.citywheel.CityConfig;
import com.lljjcoder.style.citypickerview.CityPickerView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;

/*
 * 创建日期：2020-01-21 20:09
 * @author sundongdong
 * @version 1.0
 * @since
 * 文件名称：AddAddressFragment.java
 * 类说明：新增地址
 */
public class AddAddressFragment extends BaseActivity<AddAddressPresenter> implements AddAddressContract.View {

    @BindView(R.id.toolbar_layout_toolbar)
    ToolbarWithBackRightProgress toolbar;
    @BindView(R.id.progress_fragment_add_address)
    ProgressbarLayout progress;
    @BindView(R.id.et_name_fragment_add_address)
    AppCompatEditText etNameFragmentAddAddress;
    @BindView(R.id.et_tel_fragment_add_address)
    AppCompatEditText etTelFragmentAddAddress;
    @BindView(R.id.cb_mr_fragment_add_address)
    CheckBox cbMrFragmentAddAddress;
    @BindView(R.id.btn_save_fragment_add_address)
    AppCompatButton btnSaveFragmentAddAddress;
    @BindView(R.id.quyu_layout)
    LinearLayout quyuLayout;
    @BindView(R.id.et_address_fragment_add_address)
    AppCompatEditText etAddressFragmentAddAddress;

    Unbinder unbinder;
    @BindView(R.id.et_region_fragment_add_address)
    AppCompatTextView etRegionFragmentAddAddress;


    private AddressBO mAddressModel;
    //申明对象
    CityPickerView mPicker = new CityPickerView();


    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
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
                finish();
            }
        });
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mAddressModel = (AddressBO) bundle.getSerializable(RouterConstants.ARG_BEAN);
        }
        if (mAddressModel != null) {
//            toolbar.setTitle("编辑地址");
            etNameFragmentAddAddress.setText(mAddressModel.getName());
            etTelFragmentAddAddress.setText(mAddressModel.getPhone());
            etAddressFragmentAddAddress.setText(mAddressModel.getAddress());
        }
        //预先加载仿iOS滚轮实现的全部数据
        mPicker.init(this);
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


    @OnClick({R.id.quyu_layout})
    public void clickQuyu() {
        //添加默认的配置，不需要自己定义，当然也可以自定义相关熟悉，详细属性请看demo
        CityConfig cityConfig = new CityConfig.Builder()
                .title("选择城市")//标题
                .titleTextSize(18)//标题文字大小
                .titleTextColor("#585858")//标题文字颜  色
                .titleBackgroundColor("#E9E9E9")//标题栏背景色
                .confirTextColor("#585858")//确认按钮文字颜色
                .confirmText("确定")//确认按钮文字
                .confirmTextSize(16)//确认按钮文字大小
                .cancelTextColor("#585858")//取消按钮文字颜色
                .cancelText("取消")//取消按钮文字
                .cancelTextSize(16)//取消按钮文字大小
                .showBackground(true)//是否显示半透明背景
                .visibleItemsCount(7)//显示item的数量
                .province("浙江省")//默认显示的省份
                .city("杭州市")//默认显示省份下面的城市
                .district("滨江区")//默认显示省市下面的区县数据
                .setLineColor("#03a9f4")//中间横线的颜色
                .setLineHeigh(3)//中间横线的高度
                .build();
        mPicker.setConfig(cityConfig);
        //监听选择点击事件及返回结果
        mPicker.setOnCityItemClickListener(new OnCityItemClickListener() {
            @Override
            public void onSelected(ProvinceBean province, CityBean city, DistrictBean district) {

                //省份province
                //城市city
                //地区district
                etRegionFragmentAddAddress.setText(province.getName() + city.getName() + district.getName());
            }

            @Override
            public void onCancel() {
//                ToastUtils.showLongToast(this, "已取消");
            }
        });

        //显示
        mPicker.showCityPicker();
    }

    @OnClick(R.id.btn_save_fragment_add_address)
    public void save() {
        String name = etNameFragmentAddAddress.getText().toString().trim();
        String phone = etTelFragmentAddAddress.getText().toString().trim();
        String address = etAddressFragmentAddAddress.getText().toString().trim();
        String area = etRegionFragmentAddAddress.getText().toString().trim();
        if (StringUtils.isEmpty(name)) {
            showError("请填写收货人姓名！");
            return;
        }
        if (StringUtils.isEmpty(phone)) {
            showError("请填写收货人联系方式！");
            return;
        }
        if (StringUtils.isEmpty(area)) {
            showError("请填写收货人联系方式！");
            return;
        }
        if (StringUtils.isEmpty(address)) {
            showError("请选择所在省份城市区县信息！");
            return;
        }
        Map<String, Object> params = new HashMap<>();
        params.put("id", mAddressModel != null ? mAddressModel.getId() : 0);
        params.put("name", name);
        params.put("phone", phone);
        params.put("address", address);
        params.put("isDefault", cbMrFragmentAddAddress.isChecked() ? 1 : 0);
        params.put("area", area);
        mPresenter.saveAddress(params);
    }

    @Override
    public void saveSouress() {
        showError("保存成功！");
        finish();
    }
}
