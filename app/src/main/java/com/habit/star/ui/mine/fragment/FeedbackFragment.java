package com.habit.star.ui.mine.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.habit.commonlibrary.apt.SingleClick;
import com.habit.commonlibrary.widget.LilayItemClickableWithHeadImageTopDivider;
import com.habit.commonlibrary.widget.ProgressbarLayout;
import com.habit.commonlibrary.widget.ToolbarWithBackRightProgress;
import com.habit.star.BuildConfig;
import com.habit.star.R;
import com.habit.star.base.BaseFragment;
import com.habit.star.ui.mine.adapter.ImageGridAdapter;
import com.habit.star.ui.mine.contract.FeedBackContract;
import com.habit.star.ui.mine.presenter.FeedBackPresenter;
import com.habit.star.utils.ToastUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.UUID;

import butterknife.BindView;
import butterknife.OnClick;

/*
 * 创建日期：2020-01-21 19:19
 * @author sundongdong
 * @version 1.0
 * @since
 * 文件名称：FeedbackFragment.java
 * 类说明：意见反馈
 */
public class FeedbackFragment extends BaseFragment<FeedBackPresenter> implements FeedBackContract.View {

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

    private static final int REQUEST_CODE_ALBUM = 0x01;
    private static final int REQUEST_CODE_CAMERA = 0x02;
    private static final int OPEN_CANMER = 0x03;

    private String mCurrentPhotoPath;
    private ArrayList<String> selectList;
    //选择照片或拍照对话框
    private MaterialDialog takeOrChoPhotoDialog;

    ImageGridAdapter mImageGridAdapter;
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
        initDialog();
        toolbar.setBackIBClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _mActivity.onBackPressedSupport();
            }
        });
        initAdapter();
    }



    private void initDialog() {
        selectList = new ArrayList<>();
        selectList.add(getResources().getString(R.string.take_photo));
        selectList.add(getResources().getString(R.string.from_gallery));
        takeOrChoPhotoDialog = new MaterialDialog.Builder(_mActivity)
                .items(selectList)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog,
                                            View itemView, int which, CharSequence text) {
                        if (which >= 0) {
                            if (selectList.get(which).equals(getResources().getString(R.string.take_photo))) {
                                if (Build.VERSION.SDK_INT >= 23) {
                                    int checkCallPhonePermission = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA);
                                    if(checkCallPhonePermission != PackageManager.PERMISSION_GRANTED){
                                        ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.CAMERA},OPEN_CANMER);
                                        return;
                                    }else {
                                        showCamera();
                                    }
                                }else {
                                    showCamera();
                                }
                            } else if (selectList.get(which).equals(getResources().getString(R.string.from_gallery))) {
                                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                                i.addCategory(Intent.CATEGORY_OPENABLE);
                                i.setType("image/*");
                                startActivityForResult(Intent.createChooser(i, "选择图片"), REQUEST_CODE_ALBUM);
                            }
                        }
                    }
                }).build();
    }

    private void initAdapter() {
        mRvImg.setLayoutManager(new GridLayoutManager(getActivity(),3));
        mImageGridAdapter = new ImageGridAdapter(new ArrayList<>(), new ArrayList<>(), mContext);
        mImageGridAdapter.setImageNumber(6);
        mRvImg.setAdapter(mImageGridAdapter);
        mImageGridAdapter.notifyDataSetChanged();
        mRvImg.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()){
                    case R.id.frame_layout_image_grid_fragment_task_order_feedback:
                        takeOrChoPhotoDialog.show();
                        break;
                }
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //拍照成功和选取照片时
        if (resultCode == RESULT_OK) {
            Uri imageUri = null;
            switch (requestCode) {
                case REQUEST_CODE_ALBUM:
                    if (data != null) {
                        imageUri = data.getData();
//                        mPresenter.uploadFile(imageUri);
                    }
                    break;
                case REQUEST_CODE_CAMERA:
                    if (!TextUtils.isEmpty(mCurrentPhotoPath)) {
                        File file = new File(mCurrentPhotoPath);
                        Uri localUri = Uri.fromFile(file);
//                        mPresenter.uploadFile(localUri);
                    }
                    break;
            }
        }

    }

    private void showCamera(){
        StringBuilder fileName = new StringBuilder();
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        fileName.append(UUID.randomUUID()).append("_upload.png");
        File tempFile = new File(mContext.getExternalFilesDir(Environment.DIRECTORY_PICTURES), fileName.toString());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri uri = FileProvider.getUriForFile(mContext, BuildConfig.APPLICATION_ID + ".fileProvider", tempFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        } else {
            Uri uri = Uri.fromFile(tempFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        }
        mCurrentPhotoPath = tempFile.getAbsolutePath();
        startActivityForResult(intent, REQUEST_CODE_CAMERA);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case OPEN_CANMER:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    showCamera();
                } else {
                    Toast.makeText(getActivity(), "相机权限禁用了。请务必开启相机权", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
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
                break;
            case R.id.btn_submit_fragment_feed_back:
                break;
        }
    }
}
