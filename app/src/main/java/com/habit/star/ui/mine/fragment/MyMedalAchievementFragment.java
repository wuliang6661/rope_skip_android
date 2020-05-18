package com.habit.star.ui.mine.fragment;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.habit.star.R;
import com.habit.star.api.HttpResultSubscriber;
import com.habit.star.api.HttpServerImpl;
import com.habit.star.base.BaseFragment;
import com.habit.star.pojo.po.ChengJiuBo;
import com.habit.star.ui.mine.contract.MyMedalAchievementContract;
import com.habit.star.ui.mine.presenter.MyMedalAchievementPresenter;
import com.habit.star.utils.ToastUtil;
import com.habit.star.widget.lgrecycleadapter.LGRecycleViewAdapter;
import com.habit.star.widget.lgrecycleadapter.LGViewHolder;

import java.util.List;

import butterknife.BindView;

/*
 * 创建日期：2020-01-22 15:08
 * @author sundongdong
 * @version 1.0
 * @since
 * 文件名称：MyMedalAchievementFragment.java
 * 类说明：勋章成就
 */
public class MyMedalAchievementFragment extends BaseFragment<MyMedalAchievementPresenter> implements MyMedalAchievementContract.View {

    @BindView(R.id.recycle_view)
    RecyclerView recycleView;
    @BindView(R.id.no_recycle_view)
    RecyclerView noRecycleView;

    public static MyMedalAchievementFragment newInstance(Bundle bundle) {
        MyMedalAchievementFragment fragment = new MyMedalAchievementFragment();
        if (bundle != null) {
            fragment.setArguments(bundle);
        }

        return fragment;
    }

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_my_medal_achievement_list;
    }

    @Override
    protected String getLogTag() {
        return "MyMedalAchievementFragment %s";
    }

    @Override
    protected void initEventAndData() {
        recycleView.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        noRecycleView.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        recycleView.setNestedScrollingEnabled(false);
        noRecycleView.setNestedScrollingEnabled(false);

        getMedalList();
    }


    @Override
    public void showProgress() {
    }

    @Override
    public void hideProgress() {
    }

    @Override
    public void showError(String msg) {
        ToastUtil.show(msg);
    }

    @Override
    public void showError(int errorCode) {

    }


    /**
     * 获取我的成就
     */
    private void getMedalList() {
        HttpServerImpl.getMedalList().subscribe(new HttpResultSubscriber<ChengJiuBo>() {
            @Override
            public void onSuccess(ChengJiuBo s) {
                recycleView.setAdapter(getAdapter(s.getAcquireMedalList()));
                noRecycleView.setAdapter(getAdapter(s.getNoAcquireMedalList()));
            }

            @Override
            public void onFiled(String message) {
                showToast(message);
            }
        });
    }


    /**
     * 获取适配器
     */
    private LGRecycleViewAdapter getAdapter(List<ChengJiuBo.AcquireMedalListBean> listBeans) {
        LGRecycleViewAdapter<ChengJiuBo.AcquireMedalListBean> adapter = new LGRecycleViewAdapter<ChengJiuBo.AcquireMedalListBean>(listBeans) {
            @Override
            public int getLayoutId(int viewType) {
                return R.layout.item_chengjiu;
            }

            @Override
            public void convert(LGViewHolder holder, ChengJiuBo.AcquireMedalListBean acquireMedalListBean, int position) {
                holder.setImageUrl(getActivity(), R.id.xunzhang_img, acquireMedalListBean.getImage());
                holder.setText(R.id.xunzhang_text, acquireMedalListBean.getName());
            }
        };
        return adapter;
    }

}
