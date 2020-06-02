package com.tohabit.skip.ui.find.fragment;

import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.AppCompatButton;
import android.text.Html;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.bumptech.glide.Glide;
import com.tohabit.skip.R;
import com.tohabit.skip.api.HttpResultSubscriber;
import com.tohabit.skip.api.HttpServerImpl;
import com.tohabit.skip.base.BaseActivity;
import com.tohabit.skip.pojo.po.HuodongBO;
import com.tohabit.skip.utils.ImageGetterUtils;
import com.tohabit.skip.widget.PopXingZhi;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 活动详情
 */
public class HuodongDetailsActivity extends BaseActivity {

    @BindView(R.id.bt_commit)
    TextView btCommit;
    @BindView(R.id.huodong_img)
    ImageView huodongImg;
    @BindView(R.id.huodong_title)
    TextView huodongTitle;
    @BindView(R.id.huodong_time)
    TextView huodongTime;
    @BindView(R.id.huodong_message)
    TextView huodongMessage;

    private HuodongBO huodongBO;

    private int huodongStatus = 0;  //默认进行中

    @Override
    protected void initInject() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.act_huodong_details;
    }

    @Override
    protected String getLogTag() {
        return null;
    }

    @Override
    protected void initEventAndData() {
        goBack();
        setTitleText("精选活动");
        huodongBO = (HuodongBO) getIntent().getExtras().getSerializable("huodong");
        huodongStatus = getIntent().getExtras().getInt("huodongStatus");
        Glide.with(this).load(huodongBO.getImage()).into(huodongImg);
        huodongTitle.setText(huodongBO.getTitle());
        huodongTime.setText("报名时间： " + huodongBO.getTimeBucket());
        huodongMessage.setText(Html.fromHtml(huodongBO.getContent(), new ImageGetterUtils.MyImageGetter(this, huodongMessage), null));
        if (huodongStatus == 0) {
            btCommit.setEnabled(true);
            btCommit.setText("马上报名");
        } else if (huodongStatus == 1) {
            btCommit.setEnabled(false);
            btCommit.setText("活动已报名");
        } else {
            btCommit.setEnabled(false);
            btCommit.setText("活动已结束");
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

    TextView etSex;
    int selctSex = 0; //默认男
    PopupWindow popupWindow;

    @OnClick(R.id.bt_commit)
    public void commit() {
        View dialogView = getLayoutInflater().inflate(R.layout.pop_baoming, null);
        EditText editName = dialogView.findViewById(R.id.edit_name);
        EditText etAge = dialogView.findViewById(R.id.edit_age);
        EditText etPhone = dialogView.findViewById(R.id.edit_phone);
        etSex = dialogView.findViewById(R.id.edit_sex);
        LinearLayout sexLayout = dialogView.findViewById(R.id.sex_layout);
        AppCompatButton btCommit = dialogView.findViewById(R.id.btn_commit);
        ImageView close_img = dialogView.findViewById(R.id.close_img);
        close_img.setOnClickListener(v -> popupWindow.dismiss());
        sexLayout.setOnClickListener(v -> showSexDialog());
        btCommit.setOnClickListener(v -> {
            String name = editName.getText().toString().trim();
            String age = etAge.getText().toString().trim();
            String phone = etPhone.getText().toString().trim();
            if (StringUtils.isEmpty(name)) {
                showToast("请填写姓名！");
                return;
            }
            if (StringUtils.isEmpty(age)) {
                showToast("请填写年龄！");
                return;
            }
            baoming(name, age, phone);
        });
        popupWindow = new PopupWindow();
        popupWindow.setBackgroundDrawable(new ColorDrawable(0));
        popupWindow.setContentView(dialogView);
        //设置PopupWindow弹出窗体的宽
        popupWindow.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
        //设置PopupWindow弹出窗体的高
        popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        //设置PopupWindow弹出窗体可点击
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.showAtLocation(getWindow().getDecorView(), Gravity.CENTER, 0, 0);
        backgroundAlpha(0.5f);
        popupWindow.setOnDismissListener(() -> backgroundAlpha(1f));
    }


    /**
     * 设置添加屏幕的背景透明度
     */
    private void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getWindow().setAttributes(lp);
    }


    /**
     * 显示选择男女的弹窗
     */
    private void showSexDialog() {
        List<String> sexs = new ArrayList<>();
        sexs.add("男");
        sexs.add("女");
        PopXingZhi popXingZhi = new PopXingZhi(this, "", sexs);
        popXingZhi.setListener(new PopXingZhi.onSelectListener() {
            @Override
            public void commit(int position, String item) {
                selctSex = position;
                etSex.setText(item);
            }
        });
        popXingZhi.setSelectPosition(selctSex);
        popXingZhi.showAtLocation(getWindow().getDecorView());
    }


    /**
     * 报名
     */
    private void baoming(String name, String age, String phone) {
        HttpServerImpl.joinActivity(name, age, selctSex, huodongBO.getId(), phone).subscribe(new HttpResultSubscriber<String>() {
            @Override
            public void onSuccess(String s) {
                btCommit.setEnabled(false);
                btCommit.setText("活动已报名");
                popupWindow.dismiss();
            }

            @Override
            public void onFiled(String message) {
                showToast(message);
            }
        });
    }

}
