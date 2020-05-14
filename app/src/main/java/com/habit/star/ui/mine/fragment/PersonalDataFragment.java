package com.habit.star.ui.mine.fragment;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.guoqi.actionsheet.ActionSheet;
import com.habit.commonlibrary.widget.LilayItemClickableWithHeadImageTopDivider;
import com.habit.commonlibrary.widget.ProgressbarLayout;
import com.habit.commonlibrary.widget.ToolbarWithBackRightProgress;
import com.habit.star.R;
import com.habit.star.api.HttpResultSubscriber;
import com.habit.star.api.HttpServerImpl;
import com.habit.star.app.App;
import com.habit.star.base.BaseFragment;
import com.habit.star.pojo.po.UserBO;
import com.habit.star.ui.mine.contract.PersonalDataContract;
import com.habit.star.ui.mine.presenter.PersonalDataPresenter;
import com.habit.star.utils.PhotoFromPhotoAlbum;
import com.habit.star.utils.ToastUtil;

import java.io.File;
import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @version V1.0
 * @date: 2020-03-30 21:49
 * @ClassName: PersonalDataFragment.java
 * @Description:个人资料
 * @author: sundongdong
 */
public class PersonalDataFragment extends BaseFragment<PersonalDataPresenter>
        implements PersonalDataContract.View, ActionSheet.OnActionSheetSelected {

    @BindView(R.id.toolbar_layout_toolbar)
    ToolbarWithBackRightProgress toolbar;
    @BindView(R.id.progress_fragment_personal_data)
    ProgressbarLayout progress;
    @BindView(R.id.item_tz_fragment_perfect_information)
    LilayItemClickableWithHeadImageTopDivider person_name;
    @BindView(R.id.item_shoujihaoma_fragment_perfect_information)
    LilayItemClickableWithHeadImageTopDivider person_phone;
    @BindView(R.id.btn_submit_fragment_feed_back)
    AppCompatButton btnSubmitFragmentFeedBack;
    @BindView(R.id.iv_head_fragment_personal_data)
    CircleImageView ivHeadFragmentPersonalData;
    @BindView(R.id.tv_title_fragment_personal_data)
    AppCompatTextView tvTitleFragmentPersonalData;
    @BindView(R.id.ll_head_layout_fragment_personal_data)
    LinearLayout llHeadLayoutFragmentPersonalData;

    private File cameraSavePath;//拍照照片路径
    private Uri uri;


    public static PersonalDataFragment newInstance(Bundle bundle) {
        PersonalDataFragment fragment = new PersonalDataFragment();
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
        return R.layout.fragment_personal_data;
    }

    @Override
    protected String getLogTag() {
        return "PersonalDataFragment %s";
    }

    @Override
    protected void initEventAndData() {
        toolbar.setBackIBClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _mActivity.onBackPressedSupport();
            }
        });
        cameraSavePath = new File(Environment.getExternalStorageDirectory().getPath() + "/" +
                System.currentTimeMillis() + ".jpg");
    }


    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        mPresenter.getUserInfo();
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

    @Override
    public void getUserInfo(UserBO userBO) {
        App.userBO = userBO;
        Glide.with(getActivity()).load(userBO.getImage()).into(ivHeadFragmentPersonalData);
        person_name.setItemContent(userBO.getNickName());
        person_phone.setItemContent(userBO.getPhone());
        tvTitleFragmentPersonalData.setText("ID " + userBO.getUserCode());
    }


    @OnClick({R.id.item_tz_fragment_perfect_information,
            R.id.item_shoujihaoma_fragment_perfect_information,
            R.id.btn_submit_fragment_feed_back,
            R.id.iv_head_fragment_personal_data,
            R.id.tv_title_fragment_personal_data,
            R.id.ll_head_layout_fragment_personal_data})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.item_tz_fragment_perfect_information:
                start(ModifyNickNameFragment.newInstance(null));
                break;
            case R.id.item_shoujihaoma_fragment_perfect_information:
                start(ModifyTelephoneFragment.newInstance(null));
                break;
            case R.id.btn_submit_fragment_feed_back:
                break;
            case R.id.iv_head_fragment_personal_data:
                break;
            case R.id.tv_title_fragment_personal_data:
                break;
            case R.id.ll_head_layout_fragment_personal_data:
                checkPermissions();
                break;
        }
    }


    private boolean allPermissionsGranted() {
        for (String permission : getRequiredPermissions()) {
            if (ContextCompat.checkSelfPermission(getActivity(), permission)
                    != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }


    /**
     * Detect camera authorization
     */
    public void checkPermissions() {
        if (allPermissionsGranted()) {
            onPermissionGranted();
        } else {
            ActivityCompat.requestPermissions(getActivity(), getRequiredPermissions(), 0x11);
        }
    }

    public String[] getRequiredPermissions() {
        return new String[]{Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 0x11) {
            //已授权
            if (allGranted(grantResults)) {
                onPermissionGranted();
            } else {
                onPermissionRefused();
            }
        }
    }

    /**
     * Denied camera permissions
     */
    public void onPermissionRefused() {
        new android.support.v7.app.AlertDialog.Builder(getActivity()).setMessage("请去设置开启相关权限！").setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).create().show();
    }

    private boolean allGranted(int[] grantResults) {
        boolean hasPermission = true;
        for (int i = 0; i < grantResults.length; i++) {
            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                hasPermission = false;
            }
        }
        return hasPermission;
    }

    /**
     * Got camera permissions
     */
    public void onPermissionGranted() {
        ActionSheet.showSheet(getActivity(), this, null);
    }


    //激活相册操作
    private void goPhotoAlbum() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 2);
    }

    //激活相机操作
    private void goCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            uri = FileProvider.getUriForFile(getActivity(), "com.habit.star.fileprovider", cameraSavePath);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } else {
            uri = Uri.fromFile(cameraSavePath);
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        startActivityForResult(intent, 1);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        String photoPath;
        if (requestCode == 1 && resultCode == RESULT_OK) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                photoPath = String.valueOf(cameraSavePath);
            } else {
                photoPath = uri.getEncodedPath();
            }
            Log.d("拍照返回图片路径:", photoPath);
            updateFile(new File(Objects.requireNonNull(photoPath)));
        } else if (requestCode == 2 && resultCode == RESULT_OK) {
            photoPath = PhotoFromPhotoAlbum.getRealPathFromUri(getActivity(), data.getData());
            updateFile(new File(photoPath));
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(int whichButton) {
        switch (whichButton) {
            case ActionSheet.CHOOSE_PICTURE:
                //相册
                goPhotoAlbum();
                break;
            case ActionSheet.TAKE_PICTURE:
                //拍照
                goCamera();
                break;
            case ActionSheet.CANCEL:
                //取消
                break;
        }
    }


    private void updateFile(File file) {
        showProgress(null);
        HttpServerImpl.uploadHeadImage(file).subscribe(new HttpResultSubscriber<String>() {
            @Override
            public void onSuccess(String s) {
                stopProgress();
                Glide.with(getActivity()).load(s).into(ivHeadFragmentPersonalData);
            }

            @Override
            public void onFiled(String message) {
                stopProgress();
                showToast(message);
            }
        });
    }
}
