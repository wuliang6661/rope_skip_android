package com.tohabit.skip.ui.find.fragment;

import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.makeramen.roundedimageview.RoundedImageView;
import com.tohabit.commonlibrary.decoration.HorizontalDividerItemDecoration;
import com.tohabit.skip.R;
import com.tohabit.skip.api.HttpResultSubscriber;
import com.tohabit.skip.api.HttpServerImpl;
import com.tohabit.skip.base.BaseActivity;
import com.tohabit.skip.common.adapter.BaseRvAdapter;
import com.tohabit.skip.pojo.po.QuestionsBO;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 搜索问答界面
 */
public class SearchQuestionsActivity extends BaseActivity {


    @BindView(R.id.edit_key)
    EditText editKey;
    @BindView(R.id.search)
    TextView search;
    @BindView(R.id.recycle_view)
    RecyclerView recycleView;

    BaseRvAdapter<QuestionsBO, BaseViewHolder> adapter;

    @Override
    protected void initInject() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.act_search_question;
    }

    @Override
    protected String getLogTag() {
        return null;
    }

    @Override
    protected void initEventAndData() {
        goBack();

        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recycleView.setLayoutManager(manager);

        initAdapter();
    }

    private void initAdapter() {
        adapter = new BaseRvAdapter<QuestionsBO, BaseViewHolder>(R.layout.item_baiwenbaida, new ArrayList<>()) {
            @Override
            protected void convert(BaseViewHolder helper, QuestionsBO item) {
                helper.addOnClickListener(R.id.item_layout);
                RoundedImageView imageView = helper.getView(R.id.wenti_img);
                Glide.with(SearchQuestionsActivity.this).load(item.getImage()).into(imageView);
                RoundedImageView userImg = helper.getView(R.id.user_img);
                Glide.with(SearchQuestionsActivity.this).load(item.getHeadImage()).into(userImg);
                helper.setText(R.id.user_name, item.getUserName());
                helper.setText(R.id.user_time, item.getFriendDate());
                helper.setText(R.id.wenti_name, item.getTitle());
//                helper.setText(R.id.wenti_message,item.getContent());
                helper.setText(R.id.huifu_num, item.getCommentNum() + "回复");
            }
        };
        recycleView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this).sizeResId(R.dimen.size_list_item_divider).colorResId(R.color.color_EEEEEE)
                .margin(SizeUtils.dp2px(15)).build());
        adapter.setEmptyView(getLayoutInflater().inflate(R.layout.layout_no_datas, (ViewGroup) recycleView.getParent(), false));
        AppCompatButton mBtnRefresh = (AppCompatButton) adapter.getEmptyView().findViewById(R.id.btn_refresh);
        mBtnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search();
            }
        });
        recycleView.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter a, View view, int position) {
                LogUtils.e("ddddddddd");
                Bundle bundle = new Bundle();
                bundle.putInt("Id", adapter.getData().get(position).getId());
                gotoActivity(QuestionDetailsActivity.class, bundle, false);
            }
        });
        recycleView.setAdapter(adapter);
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

    @OnClick(R.id.search)
    public void click(){
        search();
    }


    private void search() {
        String key = editKey.getText().toString().trim();
        showProgress(null);
        HttpServerImpl.getSelectQuestionAnswerInfoList(key).subscribe(new HttpResultSubscriber<List<QuestionsBO>>() {
            @Override
            public void onSuccess(List<QuestionsBO> questionsBOS) {
                stopProgress();
               adapter.setNewData(questionsBOS);
            }

            @Override
            public void onFiled(String message) {
                stopProgress();
                showToast(message);
            }
        });
    }

}
