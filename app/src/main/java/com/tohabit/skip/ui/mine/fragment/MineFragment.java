package com.tohabit.skip.ui.mine.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatTextView;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.tohabit.commonlibrary.apt.SingleClick;
import com.tohabit.commonlibrary.widget.LilayItemClickableWithHeadImageTopDivider;
import com.tohabit.commonlibrary.widget.ToolbarWithBackRightProgress;
import com.tohabit.skip.R;
import com.tohabit.skip.app.App;
import com.tohabit.skip.app.Constants;
import com.tohabit.skip.app.RouterConstants;
import com.tohabit.skip.base.BaseFragment;
import com.tohabit.skip.pojo.po.DeviceBO;
import com.tohabit.skip.pojo.po.DeviceLinkBO;
import com.tohabit.skip.pojo.po.UserBO;
import com.tohabit.skip.ui.activity.MainActivity;
import com.tohabit.skip.ui.devicemanager.DeviceManagerActivity;
import com.tohabit.skip.ui.login.activity.LoginActivity;
import com.tohabit.skip.ui.mine.activity.MineMainActivity;
import com.tohabit.skip.ui.mine.contract.MineContract;
import com.tohabit.skip.ui.mine.presenter.MinePresenter;
import com.tohabit.skip.utils.DensityUtil;
import com.tohabit.skip.utils.PrefUtils;
import com.tohabit.skip.utils.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.jpush.android.api.JPushInterface;
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
    @BindView(R.id.my_shoucang)
    LilayItemClickableWithHeadImageTopDivider myShoucang;
    Unbinder unbinder1;

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
        kaiguan();
        mPresenter.getLinkDevice();
    }


    /**
     * 开关状态更改
     */
    private void kaiguan() {
        if (App.userBO.getIsDayPush() == 0) {  //关闭
            mLlMsgClose.setVisibility(View.VISIBLE);
            mLlMsgOpen.setVisibility(View.GONE);
        } else {
            mLlMsgClose.setVisibility(View.GONE);
            mLlMsgOpen.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        mPresenter.getUserInfo();
        mPresenter.getDeviceData();
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
                        JPushInterface.deleteAlias(getActivity(), 1);
                        JPushInterface.cleanTags(getActivity(), 1);
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
            R.id.btn_exit_login_fragment_mine,
            R.id.my_shoucang,
            R.id.add_device_layout})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.iv_user_header_fragment_mine:
//                intent = new Intent();
//                intent.putExtra(RouterConstants.ARG_MODE, RouterConstants.SHOW_PERSONAL_DATA);
//                intent.setClass(_mActivity, MineMainActivity.class);
//                startActivity(intent);
//                break;
                break;
            case R.id.add_device_layout:
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
            case R.id.ll_msg_open_fragment_mine:
                mPresenter.isPushDay();
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
            case R.id.my_shoucang:
                gotoActivity(MyShouCangActivity.class, false);
                break;
        }
    }

    @Override
    public void getUserInfo(UserBO userBO) {
        App.userBO = userBO;
        toolbar.setTitle(userBO.getNickName());
        Glide.with(getActivity()).load(userBO.getImage()).into(mIvUserHeader);
        kaiguan();
    }

    @Override
    public void getLinkDevice(DeviceBO deviceBO) {
        if (deviceBO == null) {
            mItemDevice.setVisibility(View.GONE);
        } else {
            mItemDevice.setVisibility(View.VISIBLE);
            mItemDevice.setItemNameText(deviceBO.getName());
        }
    }

    @Override
    public void getDeviceData(DeviceLinkBO linkBO) {
        deviceZongshu.setText(linkBO.getTotal() + "");
        deviceZaixian.setText(linkBO.getOnline() + "");
        deviceLixian.setText(linkBO.getOffline() + "");
    }


}
