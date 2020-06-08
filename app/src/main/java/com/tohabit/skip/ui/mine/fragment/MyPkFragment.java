package com.tohabit.skip.ui.mine.fragment;

import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bumptech.glide.Glide;
import com.tohabit.commonlibrary.widget.ProgressbarLayout;
import com.tohabit.commonlibrary.widget.ToolbarWithBackRightProgress;
import com.tohabit.skip.R;
import com.tohabit.skip.api.HttpResultSubscriber;
import com.tohabit.skip.api.HttpServerImpl;
import com.tohabit.skip.app.App;
import com.tohabit.skip.base.BaseFragment;
import com.tohabit.skip.pojo.po.PkChangCiBO;
import com.tohabit.skip.pojo.po.PkJiLuBo;
import com.tohabit.skip.ui.mine.contract.MyPkContract;
import com.tohabit.skip.ui.mine.presenter.MyPkPresenter;
import com.tohabit.skip.utils.ToastUtil;
import com.tohabit.skip.widget.lgrecycleadapter.LGRecycleViewAdapter;
import com.tohabit.skip.widget.lgrecycleadapter.LGViewHolder;

import java.util.List;

import butterknife.BindView;
import butterknife.Unbinder;

/*
 * 创建日期：2019-10-21 21:30
 * @author sundongdong
 * @version 1.0
 * @since
 * 文件名称：MyPkFragment.java
 * 类说明：我的Pk值
 */
public class MyPkFragment extends BaseFragment<MyPkPresenter> implements MyPkContract.View {

    @BindView(R.id.toolbar_layout_toolbar)
    ToolbarWithBackRightProgress toolbar;
    @BindView(R.id.progress_fragment_common_view)
    ProgressbarLayout progress;
    @BindView(R.id.iv_ic_nl_mk)
    AppCompatImageView ivIcNlMk;
    @BindView(R.id.pk_num)
    AppCompatTextView pkNum;
    @BindView(R.id.chenghao_name)
    AppCompatImageView chenghaoName;
    @BindView(R.id.pk_recycle)
    RecyclerView pkRecycle;
    @BindView(R.id.rv_pk_list)
    RecyclerView rvPkList;
    Unbinder unbinder;


    public static MyPkFragment newInstance(Bundle bundle) {
        MyPkFragment fragment = new MyPkFragment();
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
        return R.layout.fragment_my_pk;
    }

    @Override
    protected String getLogTag() {
        return "MyPkFragment %s";
    }

    @Override
    protected void initEventAndData() {
        toolbar.setTitle("我的PK");
        toolbar.setBackIBClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _mActivity.onBackPressedSupport();
            }
        });
        Glide.with(getActivity()).load(App.xIaoJiangBO.getIcon()).into(ivIcNlMk);
        pkNum.setText(App.xIaoJiangBO.getPkValue());
        Glide.with(this).load(App.xIaoJiangBO.getImage()).into(chenghaoName);
//        chenghaoName.setText(App.xIaoJiangBO.getPkName());

        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        pkRecycle.setLayoutManager(manager);

        rvPkList.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvPkList.setNestedScrollingEnabled(false);

        getPkChallengeList();
        getDataPkList();
    }


    @Override
    public void showProgress() {

        progress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progress.setVisibility(View.GONE);
    }

    @Override
    public void showError(String msg) {
        ToastUtil.show(msg);
    }

    @Override
    public void showError(int errorCode) {

    }

    /**
     * 获取PK场次列表
     */
    private void getPkChallengeList() {
        HttpServerImpl.getPkChallengeList().subscribe(new HttpResultSubscriber<List<PkChangCiBO>>() {
            @Override
            public void onSuccess(List<PkChangCiBO> s) {
                setPkChallengeAdapter(s);
            }

            @Override
            public void onFiled(String message) {
                showToast(message);
            }
        });
    }


    /**
     * 设置适配器
     */
    private void setPkChallengeAdapter(List<PkChangCiBO> list) {
        LGRecycleViewAdapter<PkChangCiBO> adapter = new LGRecycleViewAdapter<PkChangCiBO>(list) {
            @Override
            public int getLayoutId(int viewType) {
                return R.layout.item_pk_changci;
            }

            @Override
            public void convert(LGViewHolder holder, PkChangCiBO pkChangCiBO, int position) {
                holder.setText(R.id.item_pk_num, pkChangCiBO.getValue() + "PK");
                holder.setText(R.id.item_pk_name, pkChangCiBO.getTitle());
                holder.setImageUrl(getActivity(), R.id.item_pk_img, pkChangCiBO.getIcon());
            }
        };
        pkRecycle.setAdapter(adapter);
    }


    /**
     * 获取Pk记录
     */
    private void getDataPkList() {
        showProgress(null);
        HttpServerImpl.getDataPkList().subscribe(new HttpResultSubscriber<List<PkJiLuBo>>() {
            @Override
            public void onSuccess(List<PkJiLuBo> s) {
                stopProgress();
                setDataAdapter(s);
            }

            @Override
            public void onFiled(String message) {
                stopProgress();
                showToast(message);
            }
        });
    }


    /**
     * 设置记录适配器
     */
    private void setDataAdapter(List<PkJiLuBo> s) {
        LGRecycleViewAdapter<PkJiLuBo> adapter = new LGRecycleViewAdapter<PkJiLuBo>(s) {
            @Override
            public int getLayoutId(int viewType) {
                return R.layout.item_pk;
            }

            @Override
            public void convert(LGViewHolder holder, PkJiLuBo pkJiLuBo, int position) {
                holder.setText(R.id.pk_title, pkJiLuBo.getTitle());
                holder.setText(R.id.pk_vaule, "+" + pkJiLuBo.getValue() + "  PK值");
                holder.setText(R.id.pk_date, pkJiLuBo.getCreateDate().split(" ")[0]);
            }
        };
        rvPkList.setAdapter(adapter);
    }

}
