package com.habit.star.ui.train.fragment;

import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.StringUtils;
import com.habit.commonlibrary.widget.ProgressbarLayout;
import com.habit.commonlibrary.widget.ToolbarWithBackRightProgress;
import com.habit.star.R;
import com.habit.star.api.HttpResultSubscriber;
import com.habit.star.api.HttpServerImpl;
import com.habit.star.app.App;
import com.habit.star.base.BaseFragment;
import com.habit.star.pojo.po.XIaoJiangBO;
import com.habit.star.ui.train.contract.BaseMsgInputContract;
import com.habit.star.ui.train.presenter.BaseMsgInputPresenter;
import com.habit.star.utils.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * @version V1.0
 * @date: 2020-02-11 11:48
 * @ClassName: BaseMsgInputFragment.java
 * @Description:
 * @author: sundongdong
 */
public class BaseMsgInputFragment extends BaseFragment<BaseMsgInputPresenter> implements BaseMsgInputContract.View {

    @BindView(R.id.toolbar_layout_toolbar)
    ToolbarWithBackRightProgress toolbar;
    @BindView(R.id.progress_fragment_common_view)
    ProgressbarLayout progress;
    @BindView(R.id.et_age_fragment_base_msg_input)
    AppCompatEditText etAge;
    @BindView(R.id.iv_sex_woman_fragment_base_msg_input)
    AppCompatImageView ivSexWoman;
    @BindView(R.id.ll_sex_woman_fragment_base_msg_input)
    LinearLayout llSexWoman;
    @BindView(R.id.iv_sex_man_fragment_base_msg_input)
    AppCompatImageView ivSexMan;
    @BindView(R.id.ll_sex_man_fragment_base_msg_input)
    LinearLayout llSexMan;
    @BindView(R.id.et_height_fragment_base_msg_input)
    AppCompatEditText etHeight;
    @BindView(R.id.et_weight_fragment_base_msg_input)
    AppCompatEditText etWeight;
    @BindView(R.id.btn_start_test_fragment_base_msg_input)
    AppCompatButton btnStartTest;


    private int sex;  //性别

    public static BaseMsgInputFragment newInstance(Bundle bundle) {
        BaseMsgInputFragment fragment = new BaseMsgInputFragment();
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
        return R.layout.fragment_base_msg_input;
    }

    @Override
    protected String getLogTag() {
        return "BaseMsgInputFragment %s";
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
        getYoungGeneralInfo();
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

    @OnClick({R.id.ll_sex_woman_fragment_base_msg_input,
            R.id.ll_sex_man_fragment_base_msg_input,
            R.id.btn_start_test_fragment_base_msg_input})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_sex_woman_fragment_base_msg_input:
                ivSexMan.setVisibility(View.GONE);
                ivSexWoman.setVisibility(View.VISIBLE);
                break;
            case R.id.ll_sex_man_fragment_base_msg_input:
                ivSexMan.setVisibility(View.VISIBLE);
                ivSexWoman.setVisibility(View.GONE);
                break;
            case R.id.btn_start_test_fragment_base_msg_input:
                saveGeneralInfo();
                break;
        }
    }


    /**
     * 查询小将信息
     */
    private void getYoungGeneralInfo() {
        HttpServerImpl.getYoungGeneralInfo().subscribe(new HttpResultSubscriber<XIaoJiangBO>() {
            @Override
            public void onSuccess(XIaoJiangBO s) {
                App.xIaoJiangBO = s;
//                etAge.setText(s.get);
                etHeight.setText(s.getHeight() + "");
                etWeight.setText(s.getWeight() + "");
                sex = s.getSex();
                if (s.getSex() == 0) {
                    ivSexMan.setVisibility(View.VISIBLE);
                    ivSexWoman.setVisibility(View.GONE);
                } else {
                    ivSexMan.setVisibility(View.GONE);
                    ivSexWoman.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFiled(String message) {
                showToast(message);
            }
        });
    }


    /**
     * 填写基本信息
     */
    private void saveGeneralInfo() {
        String age = etAge.getText().toString().trim();
        String weight = etWeight.getText().toString().trim();
        String height = etHeight.getText().toString().trim();
        if(StringUtils.isEmpty(age)){
            showToast("请输入年龄！");
            return;
        }
        if(StringUtils.isEmpty(weight)){
            showToast("请输入体重！");
            return;
        }
        if(StringUtils.isEmpty(height)){
            showToast("请输入身高！");
            return;
        }
        HttpServerImpl.saveGeneralInfo(age, height, sex + "", weight).subscribe(new HttpResultSubscriber<String>() {
            @Override
            public void onSuccess(String s) {
                TranHomeFragment.isEditMsg = true;
//                start(TrainPlanFragment.newInstance(null));
                _mActivity.onBackPressedSupport();
            }

            @Override
            public void onFiled(String message) {
                showToast(message);
            }
        });
    }

}
