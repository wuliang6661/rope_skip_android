package com.tohabit.skip.ui.find.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sak.ultilviewlib.UltimateRefreshView;
import com.sak.ultilviewlib.interfaces.OnHeaderRefreshListener;
import com.tohabit.skip.R;
import com.tohabit.skip.api.HttpResultSubscriber;
import com.tohabit.skip.api.HttpServerImpl;
import com.tohabit.skip.base.BaseFragment;
import com.tohabit.skip.pojo.po.ShopBO;
import com.tohabit.skip.widget.TraditionHeaderAdapter;
import com.tohabit.skip.widget.lgrecycleadapter.LGRecycleViewAdapter;
import com.tohabit.skip.widget.lgrecycleadapter.LGViewHolder;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 热门兑换Fragment
 */
public class ExChangeFragment extends BaseFragment {


    @BindView(R.id.tuijian1)
    ImageView tuijian1;
    @BindView(R.id.tuijian1_text)
    TextView tuijian1Text;
    @BindView(R.id.tuijian2)
    ImageView tuijian2;
    @BindView(R.id.tuijian2_text)
    TextView tuijian2Text;
    @BindView(R.id.tuijian3)
    ImageView tuijian3;
    @BindView(R.id.tuijian3_text)
    TextView tuijian3Text;
    @BindView(R.id.recycle_view)
    RecyclerView recycleView;
    Unbinder unbinder;

    List<ShopBO> tuijianList;
    @BindView(R.id.refresh_view)
    UltimateRefreshView refreshView;
    Unbinder unbinder1;

    @Override
    protected void initInject() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fra_exange;
    }

    @Override
    protected String getLogTag() {
        return null;
    }

    @Override
    protected void initEventAndData() {
        GridLayoutManager manager = new GridLayoutManager(getActivity(), 2);
        recycleView.setLayoutManager(manager);
        recycleView.setNestedScrollingEnabled(false);

        initRefresh();
    }


    private void initRefresh() {
        refreshView.setBaseHeaderAdapter(new TraditionHeaderAdapter(getActivity()));
        refreshView.setOnHeaderRefreshListener(new OnHeaderRefreshListener() {
            @Override
            public void onHeaderRefresh(UltimateRefreshView view) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getGoodTuijian();
                        getGoodList();
                    }
                }, 1000);
            }
        });
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        getGoodTuijian();
        getGoodList();
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


    @OnClick({R.id.tuijian1_layout, R.id.tuijian2_layout, R.id.tuijian3_layout})
    public void onClickTuiJian(View view) {
        switch (view.getId()) {
            case R.id.tuijian1_layout:
                if (tuijianList.size() >= 1) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("Id", tuijianList.get(0).getId());
                    gotoActivity(ShopDetailsActivity.class, bundle, false);
                }
                break;
            case R.id.tuijian2_layout:
                if (tuijianList.size() >= 2) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("Id", tuijianList.get(1).getId());
                    gotoActivity(ShopDetailsActivity.class, bundle, false);
                }
                break;
            case R.id.tuijian3_layout:
                if (tuijianList.size() >= 3) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("Id", tuijianList.get(2).getId());
                    gotoActivity(ShopDetailsActivity.class, bundle, false);
                }
                break;
        }
    }


    /**
     * 查询热门推荐商品
     */
    private void getGoodTuijian() {
        HttpServerImpl.getGoodList(1, 3).subscribe(new HttpResultSubscriber<List<ShopBO>>() {
            @Override
            public void onSuccess(List<ShopBO> s) {
                refreshView.onHeaderRefreshComplete();
                tuijianList = s;
                if (s == null || s.isEmpty()) {
                    return;
                }
                if (s.size() >= 1) {
                    ShopBO shopBO = s.get(0);
                    Glide.with(getActivity()).load(shopBO.getImage()).into(tuijian1);
                    tuijian1Text.setText(shopBO.getName());
                }
                if (s.size() >= 2) {
                    ShopBO shopBO = s.get(1);
                    Glide.with(getActivity()).load(shopBO.getImage()).into(tuijian2);
                    tuijian2Text.setText(shopBO.getName());
                }
                if (s.size() >= 3) {
                    ShopBO shopBO = s.get(2);
                    Glide.with(getActivity()).load(shopBO.getImage()).into(tuijian3);
                    tuijian3Text.setText(shopBO.getName());
                }
            }

            @Override
            public void onFiled(String message) {
                refreshView.onHeaderRefreshComplete();
                showToast(message);
            }
        });
    }


    /**
     * 查询所有兑换商品
     */
    private void getGoodList() {
        HttpServerImpl.getGoodList(0, 20000).subscribe(new HttpResultSubscriber<List<ShopBO>>() {
            @Override
            public void onSuccess(List<ShopBO> s) {
                refreshView.onHeaderRefreshComplete();
                showAdapter(s);
            }

            @Override
            public void onFiled(String message) {
                refreshView.onHeaderRefreshComplete();
                showToast(message);
            }
        });
    }


    private void showAdapter(List<ShopBO> shopBOS) {
        LGRecycleViewAdapter<ShopBO> adapter = new LGRecycleViewAdapter<ShopBO>(shopBOS) {
            @Override
            public int getLayoutId(int viewType) {
                return R.layout.item_shop;
            }

            @Override
            public void convert(LGViewHolder holder, ShopBO shopBO, int position) {
                holder.setImageUrl(getActivity(), R.id.shop_img, shopBO.getImage());
                holder.setText(R.id.shop_name, shopBO.getName());
                holder.setText(R.id.nengliang_num, shopBO.getExchangeEnergy() + "");
                holder.setText(R.id.price_num, "¥" + shopBO.getPrice());
            }
        };
        adapter.setOnItemClickListener(R.id.item_layout, (view, position) -> {
            Bundle bundle = new Bundle();
            bundle.putInt("Id", adapter.getItem(position).getId());
            gotoActivity(ShopDetailsActivity.class, bundle, false);
        });
        recycleView.setAdapter(adapter);
    }

}
