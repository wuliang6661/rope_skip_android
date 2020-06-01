package com.tohabit.skip.ui.find.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.SizeUtils;
import com.bumptech.glide.Glide;
import com.tohabit.commonlibrary.decoration.HorizontalDividerItemDecoration;
import com.tohabit.skip.R;
import com.tohabit.skip.api.HttpResultSubscriber;
import com.tohabit.skip.api.HttpServerImpl;
import com.tohabit.skip.app.App;
import com.tohabit.skip.base.BaseActivity;
import com.tohabit.skip.pojo.po.TwoPingLunBO;
import com.tohabit.skip.utils.StringUtils;
import com.tohabit.skip.widget.lgrecycleadapter.LGRecycleViewAdapter;
import com.tohabit.skip.widget.lgrecycleadapter.LGViewHolder;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 二级评论界面
 */
public class TwoPingLunActivity extends BaseActivity {

    @BindView(R.id.user_img)
    RoundedImageView userImg;
    @BindView(R.id.buttom_layout)
    LinearLayout buttomLayout;
    @BindView(R.id.recycle_view)
    RecyclerView recycleView;

    /**
     * 问答ID
     */
    private int classId;

    /**
     * 上级评论ID
     */
    private int parentId;

    @Override
    protected void initInject() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.act_question_two_conmon;
    }

    @Override
    protected String getLogTag() {
        return null;
    }

    @Override
    protected void initEventAndData() {
        goBack();

        int commentNum = getIntent().getIntExtra("commentNum", 0);
        classId = getIntent().getIntExtra("classId", 0);    //问答id
        parentId = getIntent().getIntExtra("parentId", 0);   //上级评论id

        setTitleText(commentNum + "条回复");

        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recycleView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this).sizeResId(R.dimen.size_list_item_divider).colorResId(R.color.color_EEEEEE)
                .margin(SizeUtils.dp2px(15)).build());
        recycleView.setLayoutManager(manager);
        Glide.with(this).load(App.userBO.getImage()).into(userImg);

    }


    @Override
    protected void onResume() {
        super.onResume();
        getTwoCommentList();
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


    @OnClick(R.id.edit_pinglun)
    public void addCommon() {
        Intent intent = new Intent(this, AddPingLunActivity.class);
        intent.putExtra("isFirst", false);
        intent.putExtra("objectId", classId);
        intent.putExtra("parentId", parentId);
        startActivity(intent);
    }


    /**
     * 获取所有二级评论
     */
    private void getTwoCommentList() {
        showProgress(null);
        HttpServerImpl.getTwoCommentList(parentId + "").subscribe(new HttpResultSubscriber<List<TwoPingLunBO>>() {
            @Override
            public void onSuccess(List<TwoPingLunBO> twoPingLunBOS) {
                stopProgress();
                showAdapter(twoPingLunBOS);
            }

            @Override
            public void onFiled(String message) {
                stopProgress();
                showToast(message);
            }
        });
    }


    /**
     * 设置列表
     */
    private void showAdapter(List<TwoPingLunBO> twoPingLunBOS) {
        LGRecycleViewAdapter<TwoPingLunBO> adapter = new LGRecycleViewAdapter<TwoPingLunBO>(twoPingLunBOS) {
            @Override
            public int getLayoutId(int viewType) {
                return R.layout.item_two_pinglun;
            }

            @Override
            public void convert(LGViewHolder holder, TwoPingLunBO twoPingLunBO, int position) {
                holder.setImageUrl(TwoPingLunActivity.this, R.id.user_img, twoPingLunBO.getHeadImage());
                holder.setText(R.id.user_name, twoPingLunBO.getUserName());
                holder.setText(R.id.pinglun_date, twoPingLunBO.getFriendDate());
                TextView txContent = (TextView) holder.getView(R.id.pinglun_message);
                if (!StringUtils.isEmpty(twoPingLunBO.getToUserName())) {
                    String message = "回复" + twoPingLunBO.getToUserName() + "：" + twoPingLunBO.getContent();
                    SpannableString spannableString = new SpannableString(message);
                    spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#7EC7F5")), 2, twoPingLunBO.getToUserName().length(),
                            Spanned.SPAN_INCLUSIVE_INCLUSIVE);
                    txContent.setText(spannableString);
                } else {
                    txContent.setText(twoPingLunBO.getContent());
                }
            }
        };
        recycleView.setAdapter(adapter);
    }

}
