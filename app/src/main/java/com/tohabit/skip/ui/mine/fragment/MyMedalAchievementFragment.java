package com.tohabit.skip.ui.mine.fragment;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.tohabit.skip.R;
import com.tohabit.skip.api.HttpResultSubscriber;
import com.tohabit.skip.api.HttpServerImpl;
import com.tohabit.skip.base.BaseFragment;
import com.tohabit.skip.pojo.po.ChengJiuBo;
import com.tohabit.skip.ui.mine.contract.MyMedalAchievementContract;
import com.tohabit.skip.ui.mine.presenter.MyMedalAchievementPresenter;
import com.tohabit.skip.utils.ToastUtil;
import com.tohabit.skip.widget.lgrecycleadapter.LGRecycleViewAdapter;
import com.tohabit.skip.widget.lgrecycleadapter.LGViewHolder;

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
                recycleView.setAdapter(getAdapter(s.getAcquireMedalList(), 0));
                noRecycleView.setAdapter(getAdapter(s.getNoAcquireMedalList(), 1));
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
    private LGRecycleViewAdapter getAdapter(List<ChengJiuBo.AcquireMedalListBean> listBeans, int type) {
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
        adapter.setOnItemClickListener(R.id.item_layout, new LGRecycleViewAdapter.ItemClickListener() {
            @Override
            public void onItemClicked(View view, int position) {
                if(type == 1){
                    return;
                }
                Bundle bundle = new Bundle();
                bundle.putInt("type", 1);   //成就勋章
                bundle.putSerializable("xunzhang", listBeans.get(position));
                gotoActivity(ShapeChengJiuActivity.class, bundle, false);
            }
        });
        return adapter;
    }

}
