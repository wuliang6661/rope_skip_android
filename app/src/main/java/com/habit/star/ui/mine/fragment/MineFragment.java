package com.habit.star.ui.mine.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatTextView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.habit.commonlibrary.apt.SingleClick;
import com.habit.commonlibrary.widget.LilayItemClickableWithHeadImageTopDivider;
import com.habit.commonlibrary.widget.ToolbarWithBackRightProgress;
import com.habit.star.R;
import com.habit.star.app.App;
import com.habit.star.app.Constants;
import com.habit.star.app.RouterConstants;
import com.habit.star.base.BaseFragment;
import com.habit.star.pojo.po.DeviceBO;
import com.habit.star.pojo.po.DeviceLinkBO;
import com.habit.star.pojo.po.UserBO;
import com.habit.star.ui.activity.MainActivity;
import com.habit.star.ui.devicemanager.DeviceManagerActivity;
import com.habit.star.ui.login.activity.LoginActivity;
import com.habit.star.ui.mine.activity.MineMainActivity;
import com.habit.star.ui.mine.contract.MineContract;
import com.habit.star.ui.mine.presenter.MinePresenter;
import com.habit.star.utils.DensityUtil;
import com.habit.star.utils.PrefUtils;
import com.habit.star.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 创建日期：2018/5/30 11:33
 *
 * @author dongdong
 * @version 1.0
 * @since 文件名称： MineFragment.java
 * 类说明：我的主界面
 */
public class MineFragment extends BaseFragment<MinePresenter> implements MineContract.View {


    @BindView(R.id.toolbar_layout_toolbar)
    ToolbarWithBackRightProgress toolbar;
    @BindView(R.id.iv_user_header_fragment_mine)
    CircleImageView mIvUserHeader;
    @BindView(R.id.item_device_fragment_mine)
    LilayItemClickableWithHeadImageTopDivider mItemDevice;
    @BindView(R.id.item_jtcy_fragment_mine)
    LilayItemClickableWithHeadImageTopDivider mItemJtcy;
    @BindView(R.id.item_shdd_fragment_mine)
    LilayItemClickableWithHeadImageTopDivider mItemShdd;
    @BindView(R.id.ll_msg_close_fragment_mine)
    LinearLayout mLlMsgClose;
    @BindView(R.id.ll_msg_open_fragment_mine)
    LinearLayout mLlMsgOpen;
    @BindView(R.id.item_yqhy_fragment_mine)
    LilayItemClickableWithHeadImageTopDivider mItemYqhy;
    @BindView(R.id.item_xtsz_fragment_mine)
    LilayItemClickableWithHeadImageTopDivider mItemXtsz;
    @BindView(R.id.item_bzzx_fragment_mine)
    LilayItemClickableWithHeadImageTopDivider mItemBzzx;
    @BindView(R.id.btn_exit_login_fragment_mine)
    AppCompatTextView mBtnExit;
    Dialog mBottomSheetDialog;
    @BindView(R.id.device_zongshu)
    AppCompatTextView deviceZongshu;
    @BindView(R.id.device_zaixian)
    AppCompatTextView deviceZaixian;
    @BindView(R.id.device_lixian)
    AppCompatTextView deviceLixian;
    Unbinder unbinder;

    ///退出登录对话框
    private MaterialDialog exitDialog;

    public static MineFragment newInstance(Bundle bundle) {
        MineFragment fragment = new MineFragment();
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
        return R.layout.fragment_mine;
    }

    @Override
    protected String getLogTag() {
        return "MineFragment %s";
    }

    @Override
    protected void initEventAndData() {
        initDialog();
        toolbar.setBackIBClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra(RouterConstants.ARG_MODE, RouterConstants.SHOW_PERSONAL_DATA);
                intent.setClass(_mActivity, MineMainActivity.class);
                startActivity(intent);
            }
        });
        toolbar.setRightImageBtnClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra(RouterConstants.ARG_MODE, RouterConstants.SHOW_MESSAGE_LIST);
                intent.setClass(_mActivity, MineMainActivity.class);
                startActivity(intent);
            }
        });


        mPresenter.getUserInfo();
        mPresenter.getDeviceData();
        mPresenter.getLinkDevice();
    }

    private void initDialog() {
        View view = getLayoutInflater().inflate(R.layout.dialog_invitation, null);
        view.findViewById(R.id.tv_cancer_dialog_invitation).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetDialog.hide();
            }
        });
        mBottomSheetDialog = new Dialog(getActivity(), R.style.MaterialDialogSheet);
        mBottomSheetDialog.setContentView(view);
        mBottomSheetDialog.setCancelable(true);
//        mBottomSheetDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//        mBottomSheetDialog.getWindow().setGravity(Gravity.BOTTOM);

        Window window = mBottomSheetDialog.getWindow();
        WindowManager.LayoutParams paramsWindow = window.getAttributes();
        paramsWindow.width = window.getWindowManager().getDefaultDisplay().getWidth();
        paramsWindow.height = DensityUtil.dp2px(mContext, 160);//android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
        paramsWindow.gravity = Gravity.BOTTOM;
        window.setAttributes(paramsWindow);


        exitDialog = new MaterialDialog.Builder(getActivity())
                .title(getResources().getString(R.string.remind))
                .content(getResources().getString(R.string.is_exit_account))
                .positiveText(getResources().getString(R.string.exit_account))
                .negativeText(getResources().getString(R.string.cancel))
                .positiveColor(getResources().getColor(R.color.cpb_red))
                .negativeColor(getResources().getColor(R.color.gray_text))
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
//                        mPresenter.logout();
                        PrefUtils.setPrefString(mContext, Constants.PREF_KEY_TOKEN, "");
//                        PrefUtils.clearPreference(mContext);
                        Intent intent = new Intent();
                        intent.putExtra(RouterConstants.ARG_MODE, LoginActivity.FLAG_LOGIN_TAG);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.setClass(_mActivity, LoginActivity.class);
                        startActivity(intent);
                        _mActivity.finish();
                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                    }
                }).build();
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
        getClass().getSimpleName();
    }

    @Override
    public boolean onBackPressedSupport() {
        if (_mActivity instanceof MainActivity) {
            ((MainActivity) _mActivity).moveToStack();
        }
        return true;
    }


    @SingleClick
    @OnClick({
            R.id.iv_user_header_fragment_mine,
            R.id.item_device_fragment_mine,
            R.id.item_jtcy_fragment_mine,
            R.id.item_shdd_fragment_mine,
            R.id.ll_msg_close_fragment_mine,
            R.id.ll_msg_open_fragment_mine,
            R.id.item_yqhy_fragment_mine,
            R.id.item_xtsz_fragment_mine,
            R.id.item_bzzx_fragment_mine,
            R.id.btn_exit_login_fragment_mine})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.iv_user_header_fragment_mine:
//                intent = new Intent();
//                intent.putExtra(RouterConstants.ARG_MODE, RouterConstants.SHOW_PERSONAL_DATA);
//                intent.setClass(_mActivity, MineMainActivity.class);
//                startActivity(intent);
//                break;
            case R.id.item_device_fragment_mine:
                gotoActivity(DeviceManagerActivity.class, false);
                break;
            case R.id.item_jtcy_fragment_mine:
                intent = new Intent();
                intent.putExtra(RouterConstants.ARG_MODE, RouterConstants.SHOW_FAMILY_MEMBER);
                intent.setClass(_mActivity, MineMainActivity.class);
                startActivity(intent);
                break;
            case R.id.item_shdd_fragment_mine:
                intent = new Intent();
                intent.putExtra(RouterConstants.ARG_MODE, RouterConstants.SHOW_RECEIVING_ADDRESS);
                intent.setClass(_mActivity, MineMainActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_msg_close_fragment_mine:
                mLlMsgClose.setVisibility(View.GONE);
                mLlMsgOpen.setVisibility(View.VISIBLE);
                break;
            case R.id.ll_msg_open_fragment_mine:
                mLlMsgOpen.setVisibility(View.GONE);
                mLlMsgClose.setVisibility(View.VISIBLE);
                break;
            case R.id.item_yqhy_fragment_mine:
                mBottomSheetDialog.show();
                break;
            case R.id.item_xtsz_fragment_mine:
                intent = new Intent();
                intent.putExtra(RouterConstants.ARG_MODE, RouterConstants.SHOW_SYSTEM_SETTING);
                intent.setClass(_mActivity, MineMainActivity.class);
                startActivity(intent);
                break;
            case R.id.item_bzzx_fragment_mine:
                intent = new Intent();
                intent.putExtra(RouterConstants.ARG_MODE, RouterConstants.SHOW_ENTER_HELP_CENTER);
                intent.setClass(_mActivity, MineMainActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_exit_login_fragment_mine:
                exitDialog.show();
                break;
        }
    }

    @Override
    public void getUserInfo(UserBO userBO) {
        App.userBO = userBO;
        toolbar.setTitle(userBO.getNickName());
        Glide.with(getActivity()).load(userBO.getImage()).into(mIvUserHeader);
    }

    @Override
    public void getLinkDevice(DeviceBO deviceBO) {
        mItemDevice.setItemNameText(deviceBO.getName());
    }

    @Override
    public void getDeviceData(DeviceLinkBO linkBO) {
        deviceZongshu.setText(linkBO.getTotal() + "");
        deviceZaixian.setText(linkBO.getOnline() + "");
        deviceLixian.setText(linkBO.getOffline() + "");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


//    @SingleClick
//    @OnClick({
//            R.id.item_wdzh_fragment_mine,
//            R.id.item_sz_fragment_mine,
//            R.id.item_qbdd_fragment_mine,
//            R.id.item_fb_fragment_mine,
//            R.id.item_wdyp_fragment_mine,
//            R.id.item_wdgz_fragment_mine,
//            R.id.item_wdzp_fragment_mine,
//            R.id.item_wdsc_fragment_mine,
//            R.id.item_tool_setting_fragment_mine,
//            R.id.item_swhz_fragment_mine})
//    public void onViewClicked(View view) {
//        Intent intent;
//        switch (view.getId()) {
//            case R.id.item_qbdd_fragment_mine:
//                intent = new Intent();
//                intent.putExtra(RouterConstants.ARG_MODE, RouterConstants.SHOW_FRAGMENT_WDGD);
//                intent.setClass(_mActivity, MineMainActivity.class);
//                startActivity(intent);
//                break;
//            case R.id.item_wdyp_fragment_mine:
//                intent = new Intent();
//                intent.putExtra(RouterConstants.ARG_MODE, RouterConstants.SHOW_FRAGMENT_YP);
//                intent.setClass(_mActivity, MineMainActivity.class);
//                startActivity(intent);
//                break;
//            case R.id.item_wdgz_fragment_mine:
//                showError("正在开发中");
//                break;
//            case R.id.item_wdzp_fragment_mine:
//                intent = new Intent();
//                intent.putExtra(RouterConstants.ARG_MODE, RouterConstants.SHOW_FRAGMENT_WDZP);
//                intent.setClass(_mActivity, MineMainActivity.class);
//                startActivity(intent);
//                break;
//            case R.id.item_wdsc_fragment_mine:
//                showError("正在开发中");
//                break;
//            case R.id.item_swhz_fragment_mine:
//                break;
//            case R.id.item_fb_fragment_mine:
//                intent = new Intent();
//                intent.putExtra(RouterConstants.ARG_MODE, RouterConstants.SHOW_FRAGMENT_WDFB);
//                intent.setClass(_mActivity, MineMainActivity.class);
//                startActivity(intent);
//                break;
//            case R.id.item_wdzh_fragment_mine:
//                intent = new Intent();
//                intent.putExtra(RouterConstants.ARG_MODE, RouterConstants.SHOW_FRAGMENT_ZH);
//                intent.setClass(_mActivity, MineMainActivity.class);
//                startActivity(intent);
//                break;
//            case R.id.item_sz_fragment_mine:
//                intent = new Intent();
//                intent.putExtra(RouterConstants.ARG_MODE, RouterConstants.SHOW_FRAGMENT_SETTING);
//                intent.setClass(_mActivity, MineMainActivity.class);
//                startActivity(intent);
//                break;
//            case R.id.item_tool_setting_fragment_mine:
//                intent = new Intent();
//                intent.putExtra(RouterConstants.ARG_MODE, RouterConstants.SHOW_FRAGMENT_TOOL_SETTING);
//                intent.setClass(_mActivity, MineMainActivity.class);
//                startActivity(intent);
//                break;
//        }
//    }
}
