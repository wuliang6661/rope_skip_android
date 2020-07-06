package com.tohabit.skip.ui.find.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.tohabit.skip.R;
import com.tohabit.skip.base.BaseActivity;
import com.tohabit.skip.utils.StringUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class QuestionAddTextActivity extends BaseActivity {

    @BindView(R.id.btn_album)
    Button btnAlbum;
    @BindView(R.id.et_text)
    EditText etText;

    @Override
    protected void initInject() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.act_add_question_text;
    }

    @Override
    protected String getLogTag() {
        return null;
    }

    @Override
    protected void initEventAndData() {
        goBack();
        setTitleText("添加文字");
        btnAlbum.setVisibility(View.VISIBLE);
        btnAlbum.setText("保存");
        btnAlbum.setTextColor(Color.parseColor("#7EC7F5"));

        int type = getIntent().getIntExtra("type",0);
        if(type == 1){
            setTitleText("编辑文字");
            String msg = getIntent().getStringExtra("msg");
            etText.setText(msg);
        }
    }


    @OnClick(R.id.btn_album)
    public void save() {
        String text = etText.getText().toString().trim();
        if (StringUtils.isEmpty(text)) {
            showToast("请输入内容！");
            return;
        }
        Intent intent = new Intent();
        intent.putExtra("content", text);
        setResult(0x11, intent);
        finish();
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

}
