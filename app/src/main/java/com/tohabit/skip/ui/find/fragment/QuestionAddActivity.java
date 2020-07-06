package com.tohabit.skip.ui.find.fragment;

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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.guoqi.actionsheet.ActionSheet;
import com.tohabit.skip.R;
import com.tohabit.skip.api.HttpResultSubscriber;
import com.tohabit.skip.api.HttpServerImpl;
import com.tohabit.skip.base.BaseActivity;
import com.tohabit.skip.pojo.po.FenLeiBO;
import com.tohabit.skip.pojo.vo.AddQuestionVO;
import com.tohabit.skip.utils.PhotoFromPhotoAlbum;
import com.tohabit.skip.utils.StringUtils;
import com.tohabit.skip.widget.PopXingZhi;
import com.tohabit.skip.widget.lgrecycleadapter.LGRecycleViewAdapter;
import com.tohabit.skip.widget.lgrecycleadapter.LGViewHolder;

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
    @BindView(R.id.fenlei_text)
    TextView fenleiText;
    @BindView(R.id.fenlei_layout)
    LinearLayout fenleiLayout;
    @BindView(R.id.add_images)
    RecyclerView addContents;
    @BindView(R.id.add_img)
    ImageView addImg;
    @BindView(R.id.add_text)
    ImageView addText;

    private File cameraSavePath;//拍照照片路径
    private Uri uri;

    List<FenLeiBO> fenLeiBOS;
    private int selectPosition = Integer.MAX_VALUE;

    private List<AddQuestionVO> questionVOS = new ArrayList<>();

    private int bianjiPosition = Integer.MAX_VALUE;

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

        addContents.setLayoutManager(new LinearLayoutManager(this));
        setAdapter();
    }


    @OnClick({R.id.add_img, R.id.add_text})
    public void clickView(View view) {
        switch (view.getId()) {
            case R.id.add_img:
                checkPermissions();
                break;
            case R.id.add_text:
                Intent intent = new Intent(this, QuestionAddTextActivity.class);
                startActivityForResult(intent, 0x11);
                break;
        }
    }


    private void setAdapter() {
        LGRecycleViewAdapter<AddQuestionVO> adapter = new LGRecycleViewAdapter<AddQuestionVO>(questionVOS) {
            @Override
            public int getLayoutId(int viewType) {
                return R.layout.item_add_question;
            }

            @Override
            public void convert(LGViewHolder holder, AddQuestionVO addQuestionVO, int position) {
                if (StringUtils.isEmpty(addQuestionVO.getFont())) {
                    holder.getView(R.id.text_layout).setVisibility(View.GONE);
                } else {
                    holder.getView(R.id.text_layout).setVisibility(View.VISIBLE);
                    holder.setText(R.id.item_text, addQuestionVO.getFont());
                }
                if (StringUtils.isEmpty(addQuestionVO.getImage())) {
                    holder.getView(R.id.image_layout).setVisibility(View.GONE);
                } else {
                    holder.getView(R.id.image_layout).setVisibility(View.VISIBLE);
                    holder.setImageUrl(QuestionAddActivity.this, R.id.item_img, addQuestionVO.getImage());
                }
            }
        };
        adapter.setOnItemClickListener(R.id.delete_img, new LGRecycleViewAdapter.ItemClickListener() {
            @Override
            public void onItemClicked(View view, int position) {
                questionVOS.remove(position);
                adapter.notifyDataSetChanged();
            }
        });
        adapter.setOnItemClickListener(R.id.delete_text, new LGRecycleViewAdapter.ItemClickListener() {
            @Override
            public void onItemClicked(View view, int position) {
                questionVOS.remove(position);
                adapter.notifyDataSetChanged();
            }
        });
        adapter.setOnItemClickListener(R.id.item_img, new LGRecycleViewAdapter.ItemClickListener() {
            @Override
            public void onItemClicked(View view, int position) {
                bianjiPosition = position;
                checkPermissions();
            }
        });
        adapter.setOnItemClickListener(R.id.item_text, new LGRecycleViewAdapter.ItemClickListener() {
            @Override
            public void onItemClicked(View view, int position) {
                bianjiPosition = position;
                Intent intent = new Intent(QuestionAddActivity.this, QuestionAddTextActivity.class);
                intent.putExtra("type", 1);
                intent.putExtra("msg", questionVOS.get(position).getFont());
                startActivityForResult(intent, 0x11);
            }
        });
        addContents.setAdapter(adapter);
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
            uri = FileProvider.getUriForFile(this, "com.tohabit.skip.fileProvider", cameraSavePath);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } else {
            uri = Uri.fromFile(cameraSavePath);
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        startActivityForResult(intent, 1);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == 0x11) {
            String content = data.getStringExtra("content");
            AddQuestionVO addQuestionVO = new AddQuestionVO();
            addQuestionVO.setFont(content);
            if (bianjiPosition != Integer.MAX_VALUE) {
                questionVOS.set(bianjiPosition, addQuestionVO);
                bianjiPosition = Integer.MAX_VALUE;
            } else {
                questionVOS.add(addQuestionVO);
            }
            setAdapter();
            return;
        }
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
                AddQuestionVO addQuestionVO = new AddQuestionVO();
                addQuestionVO.setImage(s);
                if (bianjiPosition != Integer.MAX_VALUE) {
                    questionVOS.set(bianjiPosition, addQuestionVO);
                    bianjiPosition = Integer.MAX_VALUE;
                } else {
                    questionVOS.add(addQuestionVO);
                }
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
        String fenlei = fenleiText.getText().toString().trim();
        if (StringUtils.isEmpty(fenlei)) {
            showToast("请选择分类！");
            return;
        }
        if (StringUtils.isEmpty(title)) {
            showToast("请输入标题！");
            return;
        }
        if (questionVOS.isEmpty()) {
            showToast("请输入正文！");
            return;
        }
        HttpServerImpl.AddQuestion(fenLeiBOS.get(selectPosition).getId(), title, null, null, questionVOS)
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
