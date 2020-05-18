package com.habit.star.ui.find.fragment;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.guoqi.actionsheet.ActionSheet;
import com.habit.star.R;
import com.habit.star.api.HttpResultSubscriber;
import com.habit.star.api.HttpServerImpl;
import com.habit.star.base.BaseActivity;
import com.habit.star.pojo.po.FenLeiBO;
import com.habit.star.ui.mine.adapter.ImageAddAdapter;
import com.habit.star.utils.PhotoFromPhotoAlbum;
import com.habit.star.utils.StringUtils;
import com.habit.star.widget.PopXingZhi;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 发布问答
 */
public class QuestionAddActivity extends BaseActivity implements ActionSheet.OnActionSheetSelected {

    @BindView(R.id.btn_album)
    Button btnAlbum;
    @BindView(R.id.edit_title)
    EditText editTitle;
    @BindView(R.id.edit_content)
    EditText editContent;
    @BindView(R.id.fenlei_text)
    TextView fenleiText;
    @BindView(R.id.fenlei_layout)
    LinearLayout fenleiLayout;
    @BindView(R.id.add_images)
    RecyclerView addImages;

    private ArrayList<String> images;

    private File cameraSavePath;//拍照照片路径
    private Uri uri;

    List<FenLeiBO> fenLeiBOS;
    private int selectPosition = Integer.MAX_VALUE;

    @Override
    protected void initInject() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.act_add_question;
    }

    @Override
    protected String getLogTag() {
        return null;
    }

    @Override
    protected void initEventAndData() {
        goBack();
        setTitleText("");
        btnAlbum.setVisibility(View.VISIBLE);
        btnAlbum.setText("发布");
        btnAlbum.setTextColor(Color.parseColor("#7EC7F5"));
        cameraSavePath = new File(Environment.getExternalStorageDirectory().getPath() + "/" +
                System.currentTimeMillis() + ".jpg");

        GridLayoutManager manager = new GridLayoutManager(this, 3);
        addImages.setLayoutManager(manager);
        images = new ArrayList<>();
        setAdapter();
    }


    private void setAdapter() {
        ImageAddAdapter addAdapter = new ImageAddAdapter(this, images);
        addAdapter.setNum(3);
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
        addImages.setAdapter(addAdapter);
    }


    private boolean allPermissionsGranted() {
        for (String permission : getRequiredPermissions()) {
            if (ContextCompat.checkSelfPermission(this, permission)
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
            ActivityCompat.requestPermissions(this, getRequiredPermissions(), 0x11);
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
        new AlertDialog.Builder(this).setMessage("请去设置开启相关权限！").setPositiveButton("确定", new DialogInterface.OnClickListener() {
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
        ActionSheet.showSheet(this, this, null);
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
            uri = FileProvider.getUriForFile(this, "com.habit.star.fileprovider", cameraSavePath);
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
            photoPath = PhotoFromPhotoAlbum.getRealPathFromUri(this, data.getData());
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

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showError(String msg) {

    }

    @Override
    public void showError(int errorCode) {

    }


    @OnClick(R.id.btn_album)
    public void fabu() {
        String title = editTitle.getText().toString().trim();
        String content = editContent.getText().toString().trim();
        String fenlei = fenleiText.getText().toString().trim();
        if (StringUtils.isEmpty(title)) {
            showToast("请输入标题！");
            return;
        }
        if (StringUtils.isEmpty(content)) {
            showToast("请输入正文！");
            return;
        }
        if (StringUtils.isEmpty(fenlei)) {
            showToast("请选择分类！");
            return;
        }
        String imageStr = null;
        if (!images.isEmpty()) {
            StringBuilder builder = new StringBuilder();
            for (String image : images) {
                builder.append(image).append(",");
            }
            imageStr = builder.substring(0, builder.length() - 1);
        }
        HttpServerImpl.AddQuestion(fenLeiBOS.get(selectPosition).getId(), title, content, imageStr)
                .subscribe(new HttpResultSubscriber<String>() {
                    @Override
                    public void onSuccess(String s) {
                       showToast("发布成功！");
                       finish();
                    }

                    @Override
                    public void onFiled(String message) {
                        showToast(message);
                    }
                });
    }


    @OnClick(R.id.fenlei_layout)
    public void selectFenLei() {
        showProgress(null);
        HttpServerImpl.getQuestionAnswerClasss().subscribe(new HttpResultSubscriber<List<FenLeiBO>>() {
            @Override
            public void onSuccess(List<FenLeiBO> s) {
                stopProgress();
                fenLeiBOS = s;
                showDialog();
            }

            @Override
            public void onFiled(String message) {
                stopProgress();
                showToast(message);
            }
        });
    }


    private void showDialog() {
        List<String> items = new ArrayList<>();
        for (FenLeiBO fenLeiBO : fenLeiBOS) {
            items.add(fenLeiBO.getName());
        }
        PopXingZhi popXingZhi = new PopXingZhi(this, "", items);
        popXingZhi.setListener(new PopXingZhi.onSelectListener() {
            @Override
            public void commit(int position, String item) {
                selectPosition = position;
                fenleiText.setText(item);
            }
        });
        popXingZhi.setSelectPosition(selectPosition == Integer.MAX_VALUE ? 0 : selectPosition);
        popXingZhi.showAtLocation(getWindow().getDecorView());
    }
}
