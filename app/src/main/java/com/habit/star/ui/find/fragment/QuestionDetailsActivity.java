package com.habit.star.ui.find.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.webkit.WebView;
import android.widget.TextView;

import com.habit.star.R;
import com.habit.star.api.HttpResultSubscriber;
import com.habit.star.api.HttpServerImpl;
import com.habit.star.base.BaseActivity;
import com.habit.star.pojo.po.QuestionsBO;
import com.makeramen.roundedimageview.RoundedImageView;

import butterknife.BindView;

/**
 * 问答详情页面
 */
public class QuestionDetailsActivity extends BaseActivity {

    @BindView(R.id.user_img)
    RoundedImageView userImg;
    @BindView(R.id.edit_pinglun)
    TextView editPinglun;
    @BindView(R.id.wenda_title)
    TextView wendaTitle;
    @BindView(R.id.wenda_person_message)
    TextView wendaPersonMessage;
    @BindView(R.id.web_view)
    WebView webView;
    @BindView(R.id.pinglun_num)
    TextView pinglunNum;
    @BindView(R.id.recycle_view)
    RecyclerView recycleView;

    private int id;

    private QuestionsBO questionsBO;

    @Override
    protected void initInject() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.act_question_details;
    }

    @Override
    protected String getLogTag() {
        return null;
    }

    @Override
    protected void initEventAndData() {
        goBack();
        setTitleText("百问百答");

        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recycleView.setLayoutManager(manager);

        id = getIntent().getExtras().getInt("Id");
        getQuestionAnswerInfo();
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

    /**
     * 查询问答详情
     */
    private void getQuestionAnswerInfo() {
        HttpServerImpl.getQuestionAnswerInfo(id).subscribe(new HttpResultSubscriber<QuestionsBO>() {
            @Override
            public void onSuccess(QuestionsBO s) {
               questionsBO = s;

            }

            @Override
            public void onFiled(String message) {
                showToast(message);
            }
        });
    }


    private void showData(){
        wendaTitle.setText(questionsBO.getTitle());
        wendaPersonMessage.setText("发布人  " + questionsBO.getUserName() + "     发布时间   " + questionsBO.getFriendDate());
    }
}
