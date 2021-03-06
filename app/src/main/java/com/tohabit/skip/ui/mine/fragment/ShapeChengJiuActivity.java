package com.tohabit.skip.ui.mine.fragment;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.TimeUtils;
import com.bumptech.glide.Glide;
import com.google.zxing.WriterException;
import com.makeramen.roundedimageview.RoundedImageView;
import com.tohabit.skip.R;
import com.tohabit.skip.api.HttpResultSubscriber;
import com.tohabit.skip.api.HttpServerImpl;
import com.tohabit.skip.app.App;
import com.tohabit.skip.base.BaseActivity;
import com.tohabit.skip.pojo.po.ChengJiuBo;
import com.tohabit.skip.pojo.po.RongYuBO;
import com.tohabit.skip.utils.DensityUtil;
import com.tohabit.skip.utils.ScreenShotUtils;
import com.tohabit.skip.utils.ShareUtils;
import com.tohabit.skip.zxing.encoding.EncodingHandler;

import java.text.SimpleDateFormat;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 分享成就的界面
 */
public class ShapeChengJiuActivity extends BaseActivity {

    @BindView(R.id.xunzhang_img)
    AppCompatImageView xunzhangImg;
    @BindView(R.id.xunzhang_name)
    TextView xunzhangName;
    @BindView(R.id.xunzhang_num)
    TextView xunzhangNum;
    @BindView(R.id.btn_commit)
    AppCompatButton btnCommit;
    @BindView(R.id.user_img)
    RoundedImageView userImg;
    @BindView(R.id.user_name)
    TextView userName;
    @BindView(R.id.shape_date)
    TextView shapeDate;
    @BindView(R.id.user_qr_code)
    ImageView userQrCode;
    @BindView(R.id.xunzhang_miaoshu)
    TextView xunzhang_miaoshu;
    @BindView(R.id.title_layout)
    RelativeLayout title_layout;
    @BindView(R.id.buttom_layout)
    LinearLayout buttom_layout;
    @BindView(R.id.leiji_shuliang)
    TextView leijiShuliang;

    private Dialog mBottomSheetDialog;
    private int id;
    private int shareType;

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
        }
    };

    @Override
    protected void initInject() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.act_chengjiu_shape;
    }

    @Override
    protected String getLogTag() {
        return null;
    }

    @Override
    protected void initEventAndData() {
        goBack();
        setTitleText("我的成就");

        int type = getIntent().getExtras().getInt("type");
        int status = getIntent().getExtras().getInt("status");
        if (status == 1) {
            xunzhang_miaoshu.setVisibility(View.GONE);
        }
        if (type == 1) {  //成就
            ChengJiuBo.AcquireMedalListBean chengjiu = (ChengJiuBo.AcquireMedalListBean) getIntent().getExtras().getSerializable("xunzhang");
            Glide.with(this).load(chengjiu.getImage()).into(xunzhangImg);
            id = chengjiu.getId();
            xunzhangName.setText(chengjiu.getName() + "勋章");
            xunzhangNum.setText("累计跳绳总数高达" + chengjiu.getSkipTotalNum() + "个");
            leijiShuliang.setText("累计完成任务总数高达" + chengjiu.getCompleteTotalNum() + "次");
            xunzhang_miaoshu.setText("恭喜获得" + chengjiu.getName() + "勋章");
            shareType = 0;
        } else {    //证书
            RongYuBO.AcquireHonorListBean rongyu = (RongYuBO.AcquireHonorListBean) getIntent().getExtras().getSerializable("zhengshu");
            Glide.with(this).load(rongyu.getImage()).into(xunzhangImg);
            xunzhangName.setText(rongyu.getName() + "证书");
            id = rongyu.getId();
            xunzhangNum.setText("pk胜利总数高达" + rongyu.getPkVTotalNum() + "次");
            leijiShuliang.setText("评级S总数高达" + rongyu.getSkipSTotalNum() + "次");
            xunzhang_miaoshu.setText("恭喜获得" + rongyu.getName() + "证书");
            shareType = 1;
        }
        Glide.with(this).load(App.userBO.getImage()).into(userImg);
        userName.setText(App.userBO.getNickName());
        shapeDate.setText(TimeUtils.getNowString(new SimpleDateFormat("yyyy-MM-dd")));
        if (!TextUtils.isEmpty(App.userBO.getDownloadUrl())) {
            // 根据字符串生成二维码图片并显示在界面上，第二个参数为图片的大小（350*350）
            try {
                Bitmap bitmap = EncodingHandler.createQRCode(App.userBO.getDownloadUrl(), 300);
                userQrCode.setImageBitmap(bitmap);
            } catch (WriterException e) {
                e.printStackTrace();
            }
        }
        initDialog();
    }


    private void initDialog() {
        View view = getLayoutInflater().inflate(R.layout.dialog_test_invitation, null);
        view.findViewById(R.id.tv_cancer_dialog_invitation).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetDialog.hide();
            }
        });
        view.findViewById(R.id.ll_weixin_dialog_invitation).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetDialog.hide();
                title_layout.setVisibility(View.GONE);
                btnCommit.setVisibility(View.GONE);
                buttom_layout.setVisibility(View.VISIBLE);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Bitmap bitmap = ScreenShotUtils.getBitMap(ShapeChengJiuActivity.this);
                        ShareUtils.shareImage(0, bitmap);
                        ShareUtils.addShare(id, shareType, 0);
                        title_layout.setVisibility(View.VISIBLE);
                        btnCommit.setVisibility(View.VISIBLE);
                        buttom_layout.setVisibility(View.GONE);
                    }
                }, 500);
            }
        });
        view.findViewById(R.id.ll_pyq_dialog_invitation).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetDialog.hide();
                title_layout.setVisibility(View.GONE);
                btnCommit.setVisibility(View.GONE);
                buttom_layout.setVisibility(View.VISIBLE);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Bitmap bitmap = ScreenShotUtils.getBitMap(ShapeChengJiuActivity.this);
                        ShareUtils.shareImage(1, bitmap);
                        ShareUtils.addShare(id, shareType, 1);
                        title_layout.setVisibility(View.VISIBLE);
                        btnCommit.setVisibility(View.VISIBLE);
                        buttom_layout.setVisibility(View.GONE);
                    }
                }, 500);
            }
        });
        view.findViewById(R.id.ll_save_picture_dialog_invitation).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetDialog.hide();
                title_layout.setVisibility(View.GONE);
                btnCommit.setVisibility(View.GONE);
                buttom_layout.setVisibility(View.VISIBLE);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        boolean isSave = ScreenShotUtils.shotScreen(ShapeChengJiuActivity.this);
                        if (isSave) {
                            showToast("保存成功！");
                        } else {
                            showToast("保存失败！");
                        }
                        title_layout.setVisibility(View.VISIBLE);
                        btnCommit.setVisibility(View.VISIBLE);
                        buttom_layout.setVisibility(View.GONE);
                    }
                }, 500);
            }
        });
        mBottomSheetDialog = new Dialog(this, R.style.MaterialDialogSheet);
        mBottomSheetDialog.setContentView(view);
        mBottomSheetDialog.setCancelable(true);

        Window window = mBottomSheetDialog.getWindow();
        WindowManager.LayoutParams paramsWindow = window.getAttributes();
        paramsWindow.width = window.getWindowManager().getDefaultDisplay().getWidth();
        paramsWindow.height = DensityUtil.dp2px(mContext, 160);//android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
        paramsWindow.gravity = Gravity.BOTTOM;
        window.setAttributes(paramsWindow);
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


    private void getQrCode() {
        HttpServerImpl.getQrCode().subscribe(new HttpResultSubscriber<String>() {
            @Override
            public void onSuccess(String s) {
                stopProgress();
                Glide.with(ShapeChengJiuActivity.this).load(s).into(userQrCode);
            }

            @Override
            public void onFiled(String message) {
                stopProgress();
                showToast(message);
            }
        });
    }


    @OnClick(R.id.btn_commit)
    public void commit() {
        mBottomSheetDialog.show();
    }


}
