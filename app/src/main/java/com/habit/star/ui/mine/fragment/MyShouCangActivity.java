package com.habit.star.ui.mine.fragment;

import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.habit.star.R;
import com.habit.star.api.HttpResultSubscriber;
import com.habit.star.api.HttpServerImpl;
import com.habit.star.base.BaseActivity;
import com.habit.star.pojo.po.ShouCangBO;
import com.habit.star.widget.lgrecycleadapter.LGRecycleViewAdapter;
import com.habit.star.widget.lgrecycleadapter.LGViewHolder;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * author : wuliang
 * e-mail : wuliang6661@163.com
 * date   : 2020/5/1415:06
 * desc   :  我的收藏
 * version: 1.0
 */
public class MyShouCangActivity extends BaseActivity {


    @BindView(R.id.zhishi_text)
    TextView zhishiText;
    @BindView(R.id.zhishi_view)
    View zhishiView;
    @BindView(R.id.my_zhishi)
    RelativeLayout myZhishi;
    @BindView(R.id.kecheng_text)
    TextView kechengText;
    @BindView(R.id.kecheng_view)
    View kechengView;
    @BindView(R.id.my_kecheng)
    RelativeLayout myKecheng;
    @BindView(R.id.recycle_view)
    RecyclerView recycleView;

    @Override
    protected void initInject() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.act_my_shoucang;
    }

    @Override
    protected String getLogTag() {
        return null;
    }

    @Override
    protected void initEventAndData() {
        goBack();
        setTitleText("我的收藏");

        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recycleView.setLayoutManager(manager);
        getData(1);
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


    @OnClick({R.id.my_zhishi, R.id.my_kecheng})
    public void clickTitle(View view) {
        switch (view.getId()) {
            case R.id.my_zhishi:
                setTitleStyle(0);
                getData(1);
                break;
            case R.id.my_kecheng:
                setTitleStyle(1);
                getData(0);
                break;
        }
    }

    private void setTitleStyle(int i) {
        if (i == 0) {
            kechengText.setTextColor(Color.parseColor("#AAAAAA"));
            kechengView.setVisibility(View.GONE);
            zhishiText.setTextColor(Color.parseColor("#7EC7F5"));
            zhishiView.setVisibility(View.VISIBLE);
        } else {
            zhishiText.setTextColor(Color.parseColor("#AAAAAA"));
            zhishiView.setVisibility(View.GONE);
            kechengText.setTextColor(Color.parseColor("#7EC7F5"));
            kechengView.setVisibility(View.VISIBLE);
        }
    }


    private void getData(int type) {
        showProgress(null);
        HttpServerImpl.getCollectList(type).subscribe(new HttpResultSubscriber<List<ShouCangBO>>() {
            @Override
            public void onSuccess(List<ShouCangBO> s) {
                stopProgress();
                if (type == 1) {
                    setZhishiAdapter(s);
                } else {
                    setKechengAdapter(s);
                }
            }

            @Override
            public void onFiled(String message) {
                stopProgress();
                showToast(message);
            }
        });
    }


    private void setZhishiAdapter(List<ShouCangBO> s) {
        LGRecycleViewAdapter<ShouCangBO> adapter = new LGRecycleViewAdapter<ShouCangBO>(s) {
            @Override
            public int getLayoutId(int viewType) {
                return R.layout.item_zhishi;
            }

            @Override
            public void convert(LGViewHolder holder, ShouCangBO item, int position) {
                holder.setText(R.id.zhishi_title, item.getTitle());
                holder.setText(R.id.tizhong, item.getWeight() + "kg");
                holder.setText(R.id.shengao, item.getHeight() + "cm");
                holder.setText(R.id.shijian, item.getCourseNum() + "节课");
                holder.setImageUrl(MyShouCangActivity.this, R.id.zhishi_img, item.getImage());
                holder.setText(R.id.beizhu_text, "作者  " + item.getReleaseName() + "  " + item.getReleaseDate());
            }
        };
        recycleView.setAdapter(adapter);
    }


    private void setKechengAdapter(List<ShouCangBO> s) {
        LGRecycleViewAdapter<ShouCangBO> adapter = new LGRecycleViewAdapter<ShouCangBO>(s) {
            @Override
            public int getLayoutId(int viewType) {
                return R.layout.item_kecheng;
            }

            @Override
            public void convert(LGViewHolder holder, ShouCangBO item, int position) {
                holder.setText(R.id.zhishi_title, item.getTitle());
                holder.setText(R.id.tizhong, item.getWeight() + "kg");
                holder.setText(R.id.shengao, item.getHeight() + "cm");
                holder.setText(R.id.shijian, item.getCourseNum() + "节课");
                holder.setText(R.id.shiyongnianling, item.getAge() + "岁");
                holder.setImageUrl(MyShouCangActivity.this, R.id.kecheng_img, item.getImage());
                holder.setText(R.id.remark, item.getReleaseName() + "  " + item.getReleaseDate());
            }
        };
        recycleView.setAdapter(adapter);
    }
}
