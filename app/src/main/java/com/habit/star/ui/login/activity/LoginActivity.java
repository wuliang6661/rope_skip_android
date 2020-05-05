package com.habit.star.ui.login.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.FrameLayout;

import com.habit.star.R;
import com.habit.star.app.RouterConstants;
import com.habit.star.base.BaseActivity;
import com.habit.star.ui.login.contract.LoginActivityContract;
import com.habit.star.ui.login.fragment.LoginFragment;
import com.habit.star.ui.login.fragment.SplashFragment;
import com.habit.star.ui.login.presenter.LoginActivityPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import me.yokeyword.fragmentation.anim.DefaultNoAnimator;

/**
 * 创建日期：2018/6/12 17:22
 * @author sundongdong
 * @version 1.0
 * @since
 * 文件名称： LoginActivity.java
 * 类说明：
 */
public class LoginActivity extends BaseActivity<LoginActivityPresenter> implements LoginActivityContract.View {

    @BindView(R.id.frame_container_activity_login)
    FrameLayout containerFrame;

    public static final int FLAG_LOGIN_TAG = 1;
    public static final int FLAG_SPLASH_TAG = 2;

//    @Extra(RouterConstants.HEAD_DATA)
    public int flag=2;

    public boolean isRestore = true;

    //申请两个权限，录音和文件读写
    //1、首先声明一个数组permissions，将需要的权限都放在里面
    String[] permissions = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
            Manifest.permission.CALL_PHONE,
    };
    //2、创建一个mPermissionList，逐个判断哪些权限未授予，未授予的权限存储到mPerrrmissionList中
    List<String> mPermissionList = new ArrayList<>();

    private final int mRequestCode = 100;//权限请求码
    private boolean isPermissionGranted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected String getLogTag() {
        return "LoginActivity %s";
    }

//    @Permission(value = {Manifest.permission.WRITE_EXTERNAL_STORAGE
//            , Manifest.permission.ACCESS_FINE_LOCATION
//            , Manifest.permission.CALL_PHONE
//            ,Manifest.permission.READ_PHONE_STATE
//            , Manifest.permission.CAMERA}, remindResId = R.string.need_permission_to_use_this_function)
    @Override
    protected void initEventAndData() {
        Intent intent = getIntent();
        flag = intent.getIntExtra(RouterConstants.ARG_MODE, -1);
        mPresenter.initComponent(getApplicationContext());

//        Glide.get(this).register(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory(RetrofitHelper.getOkHttpClient()));
        setFragmentAnimator(new DefaultNoAnimator());

//        String addr = PrefUtils.getPrefString(mContext,
//                Constants.PREFERENCE_KEY_SERVER_ADDRESS, "");
//
//        if (TextUtils.isEmpty(addr)) {
//            RetrofitHelper.getInstance().setHostUrl(Constants.HOST_DEFAULT);
//            loadRootFragment(R.id.frame_container_activity_login, LoginMainFragment.newInstance(null));
//            return;
//        }

        if (flag==FLAG_LOGIN_TAG){
            loadRootFragment(R.id.frame_container_activity_login, LoginFragment.newInstance(null));
        }else {
            loadRootFragment(R.id.frame_container_activity_login, SplashFragment.newInstance());
        }

        if (Build.VERSION.SDK_INT >= 23) {//6.0才用动态权限
            initPermission();
        }

    }

    @Override
    public void setInitComponentSuccess() {

    }

    @Override
    public void setInitComponentFailed() {
        showError(getStringResource(R.string.error_init_failed));
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showError(String msg) {
        showMsg(msg);
    }

    @Override
    public void showError(int errorCode) {

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        isRestore = true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        isRestore = false;
        super.onSaveInstanceState(outState);
    }

    //权限判断和申请
    private void initPermission() {

        mPermissionList.clear();//清空没有通过的权限

        //逐个判断你要的权限是否已经通过
        for (int i = 0; i < permissions.length; i++) {
            if (ContextCompat.checkSelfPermission(this, permissions[i]) != PackageManager.PERMISSION_GRANTED) {
                mPermissionList.add(permissions[i]);//添加还未授予的权限
            }
        }

        log( "initPermission: "+mPermissionList.toString());
        //申请权限
        if (mPermissionList.size() > 0) {//有权限没有通过，需要申请
            ActivityCompat.requestPermissions(this, permissions, mRequestCode);
        }else{
            //说明权限都已经通过，可以做你想做的事情去
        }
    }

    //请求权限后回调的方法
    //参数： requestCode  是我们自己定义的权限请求码
    //参数： permissions  是我们请求的权限名称数组
    //参数： grantResults 是我们在弹出页面后是否允许权限的标识数组，数组的长度对应的是权限名称数组的长度，数组的数据0表示允许权限，-1表示我们点击了禁止权限
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean hasPermissionDismiss = false;//有权限没有通过
        if (mRequestCode == requestCode) {
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] == -1) {
                    hasPermissionDismiss = true;
                }
            }
            //如果有权限没有被允许
            if (hasPermissionDismiss) {
                showPermissionDialog();//跳转到系统设置权限页面，或者直接关闭页面，不让他继续访问
            }else{
                //全部权限通过，可以进行下一步操作。。。
                isPermissionGranted = true;
            }
        }

    }


    /**
     * 不再提示权限时的展示对话框
     */
    AlertDialog mPermissionDialog;
    String mPackName = "com.habit.star";

    private void showPermissionDialog() {
        if (mPermissionDialog == null) {
            mPermissionDialog = new AlertDialog.Builder(this)
                    .setMessage("已禁用权限，请手动授予")
                    .setPositiveButton("设置", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            cancelPermissionDialog();

                            Uri packageURI = Uri.parse("package:" + mPackName);
                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, packageURI);
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //关闭页面或者做其他操作
                            cancelPermissionDialog();

                        }
                    })
                    .create();
        }
        mPermissionDialog.show();
    }

    //关闭对话框
    private void cancelPermissionDialog() {
        mPermissionDialog.cancel();
    }
}
