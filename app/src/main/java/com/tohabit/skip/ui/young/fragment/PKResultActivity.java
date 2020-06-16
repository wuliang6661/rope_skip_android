package com.tohabit.skip.ui.young.fragment;

import android.graphics.Bitmap;
import android.os.Handler;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
import android.view.View;
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
import com.tohabit.skip.pojo.po.PkResultBO;
import com.tohabit.skip.utils.ScreenShotUtils;
import com.tohabit.skip.utils.ShareUtils;
import com.tohabit.skip.widget.ShapeDialog;
import com.tohabit.skip.zxing.encoding.EncodingHandler;

import java.text.SimpleDateFormat;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Pk结果页面
 */
public class PKResultActivity extends BaseActivity {


    @BindView(R.id.guangmang)
    ImageView guangmang;
    @BindView(R.id.jieguo_img)
    ImageView jieguoImg;
    @BindView(R.id.jieguo_text)
    TextView jieguoText;
    @BindView(R.id.jieguo_miaoshu)
    TextView jieguoMiaoshu;
    @BindView(R.id.value_miaoshu)
    TextView valueMiaoshu;
    @BindView(R.id.value)
    TextView value;
    @BindView(R.id.restart)
    AppCompatButton restart;
    @BindView(R.id.share_msg)
    TextView shareMsg;
    @BindView(R.id.base_layout)
    RelativeLayout baseLayout;
    @BindView(R.id.title_layout)
    RelativeLayout titleLayout;
    @BindView(R.id.layout1)
    LinearLayout layout1;
    @BindView(R.id.user_img)
    RoundedImageView userImg;
    @BindView(R.id.user_name)
    TextView userName;
    @BindView(R.id.shape_date)
    TextView shapeDate;
    @BindView(R.id.user_qr_code)
    ImageView userQrCode;
    @BindView(R.id.buttom_layout)
    LinearLayout buttomLayout;

    private int type = 0;  //type  0  成功  1 失败

    ShapeDialog shapeDialog;

    @Override
    protected void initInject() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.act_pk_result;
    }

    @Override
    protected String getLogTag() {
        return null;
    }

    @Override
    protected void initEventAndData() {
        goBack();
        if (App.xIaoJiangBO.getSex() == 0) {  //男
            baseLayout.setBackgroundResource(R.mipmap.pk_nan_bg);
        } else {
            baseLayout.setBackgroundResource(R.mipmap.pk_nv_bg);
        }
        type = getIntent().getExtras().getInt("type");
        PkResultBO resultBO = (PkResultBO) getIntent().getExtras().getSerializable("data");
        if (type == 0) {   //成功
            jieguoImg.setImageResource(R.mipmap.pk_chenggong);
            valueMiaoshu.setText("本场胜利获得PK值");
            jieguoMiaoshu.setText("距下下级称号又近了一步！");
            jieguoText.setText("新手挑战成功");
            value.setText("+" + resultBO.getPkChallengeValue());
        } else {
            guangmang.setVisibility(View.GONE);
            valueMiaoshu.setText("本场失败消耗PK值");
            jieguoMiaoshu.setText("亲，继续加油哦~");
            jieguoText.setText("新手挑战失败");
            value.setText("-" + resultBO.getPkChallengeValue());
            switch (App.xIaoJiangBO.getSex()) {
                case 0:
                    jieguoImg.setImageResource(R.mipmap.pk_shibai);
                    break;
                case 1:
                    jieguoImg.setImageResource(R.mipmap.nv_shibai);
                    break;
            }
        }
        shapeDialog = ShapeDialog.getInatance(this);
        shapeDialog.setListener(new ShapeDialog.onSelectListener() {
            @Override
            public void clickWeiXin() {
                shareImage(0);
            }

            @Override
            public void clickPYQ() {
                shareImage(1);
            }

            @Override
            public void saveImg() {
                saveImgs();
            }
        });
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


    @OnClick({R.id.restart, R.id.share_msg})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.restart:
                finish();
                break;
            case R.id.share_msg:
                shapeDialog.show();
                break;
        }
    }


    private void getQrCode() {
        HttpServerImpl.getQrCode().subscribe(new HttpResultSubscriber<String>() {
            @Override
            public void onSuccess(String s) {
                stopProgress();
                Glide.with(PKResultActivity.this).load(s).into(userQrCode);
            }

            @Override
            public void onFiled(String message) {
                stopProgress();
                showToast(message);
            }
        });
    }


    /**
     * 保存图片
     */
    private void saveImgs() {
        titleLayout.setVisibility(View.GONE);
        restart.setVisibility(View.GONE);
        shareMsg.setVisibility(View.GONE);
        buttomLayout.setVisibility(View.VISIBLE);
        showProgress(null);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                stopProgress();
                boolean isSave = ScreenShotUtils.shotScreen(PKResultActivity.this);
                if (isSave) {
                    showToast("保存成功！");
                } else {
                    showToast("保存失败！");
                }
                titleLayout.setVisibility(View.VISIBLE);
                restart.setVisibility(View.VISIBLE);
                shareMsg.setVisibility(View.VISIBLE);
                buttomLayout.setVisibility(View.GONE);
            }
        }, 500);
    }


    /**
     * 分享图片
     */
    private void shareImage(int flags) {
        titleLayout.setVisibility(View.GONE);
        restart.setVisibility(View.GONE);
        shareMsg.setVisibility(View.GONE);
        buttomLayout.setVisibility(View.VISIBLE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap = ScreenShotUtils.getBitMap(PKResultActivity.this);
                ShareUtils.shareImage(flags, bitmap);
                titleLayout.setVisibility(View.VISIBLE);
                restart.setVisibility(View.VISIBLE);
                shareMsg.setVisibility(View.VISIBLE);
                buttomLayout.setVisibility(View.GONE);
            }
        }, 500);
    }

}
