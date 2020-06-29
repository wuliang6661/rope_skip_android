package com.tohabit.skip.ui.mine.fragment;

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
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.guoqi.actionsheet.ActionSheet;
import com.tohabit.commonlibrary.widget.ProgressbarLayout;
import com.tohabit.commonlibrary.widget.ToolbarWithBackRightProgress;
import com.tohabit.skip.R;
import com.tohabit.skip.api.HttpResultSubscriber;
import com.tohabit.skip.api.HttpServerImpl;
import com.tohabit.skip.app.App;
import com.tohabit.skip.base.BaseFragment;
import com.tohabit.skip.pojo.po.UserBO;
import com.tohabit.skip.ui.mine.contract.PersonalDataContract;
import com.tohabit.skip.ui.mine.presenter.PersonalDataPresenter;
import com.tohabit.skip.utils.PhotoFromPhotoAlbum;
import com.tohabit.skip.utils.ToastUtil;
import com.tohabit.skip.utils.Utils;
import com.tohabit.skip.widget.DateDialog;
import com.tohabit.skip.widget.PopXingZhi;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;
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
    LinearLayout person_name;
    @BindView(R.id.item_shoujihaoma_fragment_perfect_information)
    LinearLayout person_phone;
    @BindView(R.id.btn_submit_fragment_feed_back)
    AppCompatButton btnSubmitFragmentFeedBack;
    @BindView(R.id.iv_head_fragment_personal_data)
    CircleImageView ivHeadFragmentPersonalData;
    @BindView(R.id.tv_title_fragment_personal_data)
    AppCompatTextView tvTitleFragmentPersonalData;
    @BindView(R.id.ll_head_layout_fragment_personal_data)
    LinearLayout llHeadLayoutFragmentPersonalData;
    @BindView(R.id.nike_name)
    TextView nikeName;
    @BindView(R.id.phone_num)
    TextView phoneNum;
    @BindView(R.id.birth_day)
    TextView birthDay;
    @BindView(R.id.sex_text)
    TextView sexText;
    @BindView(R.id.height)
    TextView height;
    @BindView(R.id.weight)
    TextView weight;
    Unbinder unbinder;

    private File cameraSavePath;//拍照照片路径
    private Uri uri;


    private int sex;

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
        nikeName.setText(userBO.getNickName());
        phoneNum.setText(Utils.settingphone(userBO.getPhone()));
        tvTitleFragmentPersonalData.setText("ID " + userBO.getUserCode());
        sex = userBO.getSex();
        sexText.setText(userBO.getSex() == 0 ? "男" : "女");
        birthDay.setText(userBO.getBirthDate());
        height.setText(userBO.getHeight() + "cm");
        weight.setText(userBO.getWeight() + "kg");
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


    @OnClick({R.id.birth_day_layout, R.id.sex_layout, R.id.weight_layout, R.id.height_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.birth_day_layout:
                DateDialog.show(getActivity(), birthDay, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        updateBirth();
                    }
                });
                break;
            case R.id.sex_layout:
                showSexDialog();
                break;
            case R.id.weight_layout:
                Bundle bundle = new Bundle();
                bundle.putInt("type", 0);
                gotoActivity(UpdatePersonActivty.class, bundle, false);
                break;
            case R.id.height_layout:
                Bundle bundle1 = new Bundle();
                bundle1.putInt("type", 1);
                gotoActivity(UpdatePersonActivty.class, bundle1, false);
                break;
        }
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
                sexText.setText(item);
                updateSex();
            }
        });
        popXingZhi.setSelectPosition(sex);
        popXingZhi.showAtLocation(getActivity().getWindow().getDecorView());
    }


    /**
     * 修改性别
     */
    private void updateSex() {
        HttpServerImpl.updateSex(sex).subscribe(new HttpResultSubscriber<String>() {
            @Override
            public void onSuccess(String s) {
                mPresenter.getUserInfo();
            }

            @Override
            public void onFiled(String message) {
                showToast(message);
                mPresenter.getUserInfo();
            }
        });
    }


    /**
     * 修改生日
     */
    private void updateBirth() {
        HttpServerImpl.updateBirthDate(birthDay.getText().toString().trim()).subscribe(new HttpResultSubscriber<String>() {
            @Override
            public void onSuccess(String s) {
                mPresenter.getUserInfo();
            }

            @Override
            public void onFiled(String message) {
                showToast(message);
                mPresenter.getUserInfo();
            }
        });
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
        new AlertDialog.Builder(getActivity()).setMessage("请去设置开启相关权限！").setPositiveButton("确定", new DialogInterface.OnClickListener() {
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
