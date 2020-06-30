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
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.guoqi.actionsheet.ActionSheet;
import com.tohabit.commonlibrary.apt.SingleClick;
import com.tohabit.commonlibrary.widget.LilayItemClickableWithHeadImageTopDivider;
import com.tohabit.commonlibrary.widget.ProgressbarLayout;
import com.tohabit.commonlibrary.widget.ToolbarWithBackRightProgress;
import com.tohabit.skip.R;
import com.tohabit.skip.api.HttpResultSubscriber;
import com.tohabit.skip.api.HttpServerImpl;
import com.tohabit.skip.base.BaseFragment;
import com.tohabit.skip.pojo.po.FeedBackBO;
import com.tohabit.skip.ui.mine.adapter.ImageAddAdapter;
import com.tohabit.skip.ui.mine.contract.FeedBackContract;
import com.tohabit.skip.ui.mine.presenter.FeedBackPresenter;
import com.tohabit.skip.utils.PhotoFromPhotoAlbum;
import com.tohabit.skip.utils.StringUtils;
import com.tohabit.skip.utils.ToastUtil;
import com.tohabit.skip.widget.PopXingZhi;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/*
 * 创建日期：2020-01-21 19:19
 * @author sundongdong
 * @version 1.0
 * @since
 * 文件名称：FeedbackFragment.java
 * 类说明：意见反馈
 */
public class FeedbackFragment extends BaseFragment<FeedBackPresenter> implements FeedBackContract.View,
        ActionSheet.OnActionSheetSelected {

    @BindView(R.id.toolbar_layout_toolbar)
    ToolbarWithBackRightProgress toolbar;
    @BindView(R.id.progress_fragment_feed_back)
    ProgressbarLayout progress;
    @BindView(R.id.item_fklx_fragment_feed_back)
    LilayItemClickableWithHeadImageTopDivider mItemFklx;
    @BindView(R.id.rv_img_fragment_feed_back)
    RecyclerView mRvImg;
    @BindView(R.id.btn_submit_fragment_feed_back)
    AppCompatButton mBtnSubmit;
    @BindView(R.id.et_feed_back_message)
    AppCompatEditText etFeedBackMessage;
    @BindView(R.id.et_phone)
    AppCompatEditText etPhone;

    Unbinder unbinder;
    private File cameraSavePath;//拍照照片路径
    private Uri uri;

    private int feedBackId = Integer.MAX_VALUE;
    private ArrayList<String> images;

    public static FeedbackFragment newInstance(Bundle bundle) {
        FeedbackFragment fragment = new FeedbackFragment();
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
        return R.layout.fragment_feed_back;
    }

    @Override
    protected String getLogTag() {
        return "FeedbackFragment %s";
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
        images = new ArrayList<>();
        initAdapter();
    }


    private void initAdapter() {
        mRvImg.setLayoutManager(new GridLayoutManager(getActivity(), 3));
//        mImageGridAdapter = new ImageGridAdapter(new ArrayList<>(), new ArrayList<>(), mContext);
//        mImageGridAdapter.setImageNumber(6);
//        mRvImg.setAdapter(mImageGridAdapter);
//        mImageGridAdapter.notifyDataSetChanged();
//        mRvImg.addOnItemTouchListener(new OnItemChildClickListener() {
//            @Override
//            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
//                switch (view.getId()) {
//                    case R.id.frame_layout_image_grid_fragment_task_order_feedback:
//                        checkPermissions();
//                        break;
//                }
//            }
//        });
        setAdapter();
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
            uri = FileProvider.getUriForFile(getActivity(), "com.tohabit.skip.fileProvider", cameraSavePath);
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
        HttpServerImpl.updateFile(file).subscribe(new HttpResultSubscriber<String>() {
            @Override
            public void onSuccess(String s) {
                stopProgress();
                images.add(s);
                setAdapter();
            }

            @Override
            public void onFiled(String message) {
                stopProgress();
                showToast(message);
            }
        });
    }

    private void setAdapter() {
        ImageAddAdapter addAdapter = new ImageAddAdapter(getActivity(), images);
        addAdapter.setListener(new ImageAddAdapter.onAddImageAdapterListener() {
            @Override
            public void addImage() {
                checkPermissions();
            }

            @Override
            public void deleteImage(int position, String imageBO) {
                images.remove(position);
                addAdapter.setDatas(images);
            }

            @Override
            public void editName(int position) {

            }
        });
        mRvImg.setAdapter(addAdapter);
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

    @SingleClick
    @OnClick({
            R.id.item_fklx_fragment_feed_back,
            R.id.btn_submit_fragment_feed_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.item_fklx_fragment_feed_back:
                mPresenter.getFeedbackType();
                break;
            case R.id.btn_submit_fragment_feed_back:
                if (feedBackId == Integer.MAX_VALUE) {
                    showToast("请选择反馈类型！");
                    return;
                }
                String message = etFeedBackMessage.getText().toString().trim();
                String phone = etPhone.getText().toString().trim();
                if (StringUtils.isEmpty(message)) {
                    showToast("请输入反馈详情!");
                    return;
                }
                String allImage = "";
                for (String image : images) {
                    allImage += (image + ",");
                }
                mPresenter.addFeedback(feedBackId, phone, message, allImage);
                break;
        }
    }

    @Override
    public void getFeedBack(List<FeedBackBO> feedBackBOS) {
        List<String> arrays = new ArrayList<>();
        for (FeedBackBO feedBackBO : feedBackBOS) {
            arrays.add(feedBackBO.getName());
        }
        PopXingZhi popXingZhi = new PopXingZhi(getActivity(), "", arrays);
        popXingZhi.setListener((position, item) -> {
            feedBackId = feedBackBOS.get(position).getId();
            mItemFklx.setRemindContent(item);
        });
        popXingZhi.showAtLocation(getActivity().getWindow().getDecorView());
    }

    @Override
    public void addSouress() {
        showToast("提交成功！");
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
}
