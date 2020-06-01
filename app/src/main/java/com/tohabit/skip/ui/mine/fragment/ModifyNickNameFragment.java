package com.tohabit.skip.ui.mine.fragment;

import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;

import com.blankj.utilcode.util.StringUtils;
import com.tohabit.commonlibrary.widget.ProgressbarLayout;
import com.tohabit.commonlibrary.widget.ToolbarWithBackRightProgress;
import com.tohabit.skip.R;
import com.tohabit.skip.api.HttpResultSubscriber;
import com.tohabit.skip.api.HttpServerImpl;
import com.tohabit.skip.app.App;
import com.tohabit.skip.base.BaseFragment;
import com.tohabit.skip.presenter.CommonPresenter;
import com.tohabit.skip.presenter.contract.CommonContract;
import com.tohabit.skip.utils.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * @version V1.0
 * @date: 2020-04-25 22:17
 * @ClassName: ModifyNickNameFragment.java
 * @Description:
 * @author: sundongdong
 */
public class ModifyNickNameFragment extends BaseFragment<CommonPresenter> implements CommonContract.View {

    @BindView(R.id.toolbar_layout_toolbar)
    ToolbarWithBackRightProgress toolbar;
    @BindView(R.id.progress_fragment_common_view)
    ProgressbarLayout progress;
    @BindView(R.id.et_tel_fragment_register)
    AppCompatEditText etNickName;
    @BindView(R.id.btn_submit_fragment_modify_nick_name)
    AppCompatButton btnSubmit;


    public static ModifyNickNameFragment newInstance(Bundle bundle) {
        ModifyNickNameFragment fragment = new ModifyNickNameFragment();
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
        return R.layout.fragment_modify_nick_name;
    }

    @Override
    protected String getLogTag() {
        return "ModifyNickNameFragment %s";
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
        etNickName.setText(App.userBO.getNickName());
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


    @OnClick(R.id.btn_submit_fragment_modify_nick_name)
    public void onViewClicked() {
        String name = etNickName.getText().toString().trim();
        if (StringUtils.isEmpty(name)) {
            showError(name);
            return;
        }
        showProgress(null);
        HttpServerImpl.updateNike(name).subscribe(new HttpResultSubscriber<String>() {
            @Override
            public void onSuccess(String s) {
                stopProgress();
                showError("修改成功！");
                _mActivity.onBackPressedSupport();
            }

            @Override
            public void onFiled(String message) {
                stopProgress();
                showError(message);
            }
        });
    }
}
