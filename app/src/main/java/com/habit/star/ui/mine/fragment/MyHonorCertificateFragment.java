package com.habit.star.ui.mine.fragment;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.habit.star.R;
import com.habit.star.api.HttpResultSubscriber;
import com.habit.star.api.HttpServerImpl;
import com.habit.star.base.BaseFragment;
import com.habit.star.pojo.po.ChengJiuBo;
import com.habit.star.pojo.po.RongYuBO;
import com.habit.star.ui.mine.contract.MyHonorCertificateContract;
import com.habit.star.ui.mine.presenter.MyHonorCertificatePresenter;
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
    private void getHonorList(){
        HttpServerImpl.getHonorList().subscribe(new HttpResultSubscriber<RongYuBO>() {
            @Override
            public void onSuccess(RongYuBO s) {
                recycleView.setAdapter(getAdapter(s.getAcquireHonorList()));
                noRecycleView.setAdapter(getAdapter(s.getNoAcquireHonorList()));
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
    private LGRecycleViewAdapter getAdapter(List<RongYuBO.AcquireHonorListBean> listBeans) {
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
        return adapter;
    }

}
