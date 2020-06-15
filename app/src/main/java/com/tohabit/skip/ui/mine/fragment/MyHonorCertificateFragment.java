package com.tohabit.skip.ui.mine.fragment;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.tohabit.skip.R;
import com.tohabit.skip.api.HttpResultSubscriber;
import com.tohabit.skip.api.HttpServerImpl;
import com.tohabit.skip.base.BaseFragment;
import com.tohabit.skip.pojo.po.RongYuBO;
import com.tohabit.skip.ui.mine.contract.MyHonorCertificateContract;
import com.tohabit.skip.ui.mine.presenter.MyHonorCertificatePresenter;
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
 * 文件名称：MyHonorCertificateFragment.java
 * 类说明：荣誉证书
 */
public class MyHonorCertificateFragment extends BaseFragment<MyHonorCertificatePresenter> implements MyHonorCertificateContract.View {

    @BindView(R.id.recycle_view)
    RecyclerView recycleView;
    @BindView(R.id.no_recycle_view)
    RecyclerView noRecycleView;

    public static MyHonorCertificateFragment newInstance(Bundle bundle) {
        MyHonorCertificateFragment fragment = new MyHonorCertificateFragment();
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
        return R.layout.fragment_my_honor_certificate_list;
    }

    @Override
    protected String getLogTag() {
        return "MyHonorCertificateFragment %s";
    }

    @Override
    protected void initEventAndData() {
        recycleView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        noRecycleView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        recycleView.setNestedScrollingEnabled(false);
        noRecycleView.setNestedScrollingEnabled(false);

        getHonorList();
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
     * 获取荣誉证书
     */
    private void getHonorList() {
        HttpServerImpl.getHonorList().subscribe(new HttpResultSubscriber<RongYuBO>() {
            @Override
            public void onSuccess(RongYuBO s) {
                recycleView.setAdapter(getAdapter(s.getAcquireHonorList(), 0));
                noRecycleView.setAdapter(getAdapter(s.getNoAcquireHonorList(), 1));
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
    private LGRecycleViewAdapter getAdapter(List<RongYuBO.AcquireHonorListBean> listBeans, int type) {
        LGRecycleViewAdapter<RongYuBO.AcquireHonorListBean> adapter = new LGRecycleViewAdapter<RongYuBO.AcquireHonorListBean>(listBeans) {
            @Override
            public int getLayoutId(int viewType) {
                return R.layout.item_chengjiu;
            }

            @Override
            public void convert(LGViewHolder holder, RongYuBO.AcquireHonorListBean acquireMedalListBean, int position) {
                holder.setImageUrl(getActivity(), R.id.xunzhang_img, acquireMedalListBean.getImage());
                holder.setText(R.id.xunzhang_text, acquireMedalListBean.getName());
            }
        };
        adapter.setOnItemClickListener(R.id.item_layout, new LGRecycleViewAdapter.ItemClickListener() {
            @Override
            public void onItemClicked(View view, int position) {
//                if (type == 1) {
//                    return;
//                }
                Bundle bundle = new Bundle();
                bundle.putInt("type", 2);   //证书
                bundle.putInt("status", type);  //是否获得
                bundle.putSerializable("zhengshu", listBeans.get(position));
                gotoActivity(ShapeChengJiuActivity.class, bundle, false);
            }
        });
        return adapter;
    }

}
