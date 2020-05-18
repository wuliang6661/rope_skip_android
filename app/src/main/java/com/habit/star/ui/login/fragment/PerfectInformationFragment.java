package com.habit.star.ui.login.fragment;

import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.EditText;

import com.habit.commonlibrary.widget.LilayItemClickableWithHeadImageTopDivider;
import com.habit.commonlibrary.widget.ProgressbarLayout;
import com.habit.commonlibrary.widget.ToolbarWithBackRightProgress;
import com.habit.star.R;
import com.habit.star.api.HttpResultSubscriber;
import com.habit.star.api.HttpServerImpl;
import com.habit.star.base.BaseFragment;
import com.habit.star.ui.login.contract.PerfectInformationContract;
import com.habit.star.ui.login.presenter.PerfectInformationPresenter;
import com.habit.star.utils.StringUtils;
import com.habit.star.utils.ToastUtil;
import com.habit.star.widget.PopXingZhi;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/*
 * 创建日期：2020-01-22 10:54
 * @author sundongdong
 * @version 1.0
 * @since
 * 文件名称：PerfectInformationFragment.java
 * 类说明：完善资料
 */
public class PerfectInformationFragment extends BaseFragment<PerfectInformationPresenter> implements PerfectInformationContract.View {

    @BindView(R.id.toolbar_layout_toolbar)
    ToolbarWithBackRightProgress toolbar;
    @BindView(R.id.progress_fragment_perfect_information)
    ProgressbarLayout progress;
    @BindView(R.id.btn_submit_fragment_feed_back)
    AppCompatButton btnSubmitFragmentFeedBack;
    @BindView(R.id.edit_name)
    EditText editName;
    @BindView(R.id.edit_height)
    EditText editHeight;
    @BindView(R.id.edit_weight)
    EditText editWeight;
    @BindView(R.id.select_sex)
    LilayItemClickableWithHeadImageTopDivider selectSex;


    private int sex = 0;   //默认男


    public static PerfectInformationFragment newInstance(Bundle bundle) {
        PerfectInformationFragment fragment = new PerfectInformationFragment();
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
        return R.layout.fragment_perfect_information;
    }

    @Override
    protected String getLogTag() {
        return "PerfectInformationFragment %s";
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

    @OnClick(R.id.btn_submit_fragment_feed_back)
    public void onViewClicked(View view) {
        String nikeName = editName.getText().toString().trim();
        String height = editHeight.getText().toString().trim();
        String weight = editWeight.getText().toString().trim();
        if(StringUtils.isEmpty(nikeName)){
            showToast("请输入昵称！");
            return;
        }
        if(StringUtils.isEmpty(height)){
            showToast("请输入身高！");
            return;
        }
        if(StringUtils.isEmpty(weight)){
            showToast("请输入体重！");
            return;
        }
        HttpServerImpl.addGeneralInfo(nikeName,null,sex+"",height,weight).subscribe(new HttpResultSubscriber<String>() {
            @Override
            public void onSuccess(String s) {
                showError("创建成功");
                _mActivity.onBackPressedSupport();
            }

            @Override
            public void onFiled(String message) {
                showToast(message);
            }
        });
    }


    @OnClick(R.id.select_sex)
    public void selectSex(){
        showSexDialog();
    }


    /**
     * 显示选择男女的弹窗
     */
    private void showSexDialog() {
        List<String> sexs = new ArrayList<>();
        sexs.add("男");
        sexs.add("女");
        PopXingZhi popXingZhi = new PopXingZhi(getActivity(), "", sexs);
        popXingZhi.setListener(new PopXingZhi.onSelectListener() {
            @Override
            public void commit(int position, String item) {
                sex = position;
                selectSex.setRemindContent(item);
            }
        });
        popXingZhi.setSelectPosition(sex);
        popXingZhi.showAtLocation(getActivity().getWindow().getDecorView());
    }

}
