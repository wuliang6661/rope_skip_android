package com.habit.star.ui.mine.fragment;

import android.os.Bundle;

import com.habit.star.R;
import com.habit.star.base.BaseFragment;
import com.habit.star.ui.mine.contract.MyHonorCertificateContract;
import com.habit.star.ui.mine.presenter.MyHonorCertificatePresenter;
import com.habit.star.utils.ToastUtil;

/*
 * 创建日期：2020-01-22 15:08
 * @author sundongdong
 * @version 1.0
 * @since
 * 文件名称：MyHonorCertificateFragment.java
 * 类说明：荣誉证书
 */
public class MyHonorCertificateFragment extends BaseFragment<MyHonorCertificatePresenter> implements MyHonorCertificateContract.View {

    public static MyHonorCertificateFragment newInstance(Bundle bundle) {
        MyHonorCertificateFragment fragment = new MyHonorCertificateFragment();
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
        return R.layout.fragment_my_honor_certificate_list;
    }

    @Override
    protected String getLogTag() {
        return "MyHonorCertificateFragment %s";
    }

    @Override
    protected void initEventAndData() {
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

}
