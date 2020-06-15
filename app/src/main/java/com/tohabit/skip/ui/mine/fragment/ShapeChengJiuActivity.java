package com.tohabit.skip.ui.mine.fragment;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageView;
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

    private Dialog mBottomSheetDialog;
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
            xunzhangName.setText(chengjiu.getName() + "勋章");
            xunzhangNum.setText("累积跳绳总数高达" + chengjiu.getSkipTotalNum() + "个");
            xunzhang_miaoshu.setText("恭喜获得" + chengjiu.getName() + "勋章");
        } else {    //证书
            RongYuBO.AcquireHonorListBean rongyu = (RongYuBO.AcquireHonorListBean) getIntent().getExtras().getSerializable("zhengshu");
            Glide.with(this).load(rongyu.getImage()).into(xunzhangImg);
            xunzhangName.setText(rongyu.getName() + "证书");
            xunzhangNum.setText("pk胜利总数高达" + rongyu.getPkVTotalNum() + "次");
            xunzhang_miaoshu.setText("恭喜获得" + rongyu.getName() + "勋章");
        }
        Glide.with(this).load(App.userBO.getImage()).into(userImg);
        userName.setText(App.userBO.getNickName());
        shapeDate.setText(TimeUtils.getNowString(new SimpleDateFormat("yyyy-MM-dd")));
        getQrCode();
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
