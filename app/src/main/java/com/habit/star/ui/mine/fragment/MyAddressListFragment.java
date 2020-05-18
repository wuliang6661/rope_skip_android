package com.habit.star.ui.mine.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.habit.commonlibrary.apt.SingleClick;
import com.habit.commonlibrary.decoration.HorizontalDividerItemDecoration;
import com.habit.commonlibrary.widget.ProgressbarLayout;
import com.habit.commonlibrary.widget.ToolbarWithBackRightProgress;
import com.habit.star.R;
import com.habit.star.app.RouterConstants;
import com.habit.star.base.BaseActivity;
import com.habit.star.pojo.po.AddressBO;
import com.habit.star.ui.mine.adapter.AddressListAdapter;
import com.habit.star.ui.mine.contract.MyAddressListContract;
import com.habit.star.ui.mine.presenter.MyAddressListPresenter;
import com.habit.star.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;

/*
 * 创建日期：2019-10-21 21:30
 * @author sundongdong
 * @version 1.0
 * @since
 * 文件名称：MyAddressListFragment.java
 * 类说明：收货地址
 */
public class MyAddressListFragment extends BaseActivity<MyAddressListPresenter> implements MyAddressListContract.View {

    @BindView(R.id.toolbar_layout_toolbar)
    ToolbarWithBackRightProgress toolbar;
    @BindView(R.id.progress_fragment_my_address_list)
    ProgressbarLayout progress;
    @BindView(R.id.rv_address_content_fragment_my_address_list)
    RecyclerView rvAddressContent;
    @BindView(R.id.ll_btn_submit_fragment_feed_back)
    LinearLayout llBtnSubmitFragmentFeedBack;
    Unbinder unbinder;

    private MaterialDialog exitDialog;
    private AddressListAdapter mListAdapter;
    private int position = 0;

    private int isSelect = 0;  //默认不是选择地址

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_my_address_list;
    }

    @Override
    protected String getLogTag() {
        return "MyAddressListFragment %s";
    }

    @Override
    protected void initEventAndData() {
        initDialog();
        toolbar.setBackIBClick(new View.OnClickListener() {
            @SingleClick
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        isSelect = getIntent().getIntExtra("isSelect", 0);
        mPresenter.getAddressList();
    }


    private void initDialog() {
        exitDialog = new MaterialDialog.Builder(this)
                .title(getResources().getString(R.string.remind))
                .content("是否确定删除")
                .positiveText("确定")
                .negativeText(getResources().getString(R.string.cancel))
                .positiveColor(getResources().getColor(R.color.cpb_red))
                .negativeColor(getResources().getColor(R.color.gray_text))
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        mPresenter.deleteAddress(mListAdapter.getItem(position).getId());
                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                    }
                }).build();


        rvAddressContent.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this).sizeResId(R.dimen.size_list_item_divider_address).colorResId(R.color.transparent).build());
        rvAddressContent.setLayoutManager(new LinearLayoutManager(this));
        mListAdapter = new AddressListAdapter(mContext);
        rvAddressContent.setAdapter(mListAdapter);
        rvAddressContent.addOnItemTouchListener(new OnItemChildClickListener() {
            @SingleClick
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.ll_select_layout_fragment_address_list_item:
                        mPresenter.setDefaltAddress(mListAdapter.getItem(position).getId());
                        break;
                    case R.id.ll_bianji_layout_fragment_address_list_item:
                        Bundle bundle = new Bundle();
                        bundle.putSerializable(RouterConstants.ARG_BEAN, mListAdapter.getItem(position));
                        start(AddAddressFragment.newInstance(bundle));
                        break;
                    case R.id.ll_delete_layout_fragment_address_list_item:
                        MyAddressListFragment.this.position = position;
                        exitDialog.show();
                        break;
                    case R.id.address_layout:
                        if(isSelect != 0){
                            EventBus.getDefault().post(mListAdapter.getItem(position));
                            finish();
                            return;
                        }
                        break;
                }
            }
        });

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
        stopProgress();
        ToastUtil.show(msg);
    }

    @Override
    public void showError(int errorCode) {

    }

    @OnClick(R.id.ll_btn_submit_fragment_feed_back)
    public void onViewClicked() {
        start(AddAddressFragment.newInstance(null));
    }

    @Override
    public void getAddressList(List<AddressBO> list) {
        stopProgress();
        mListAdapter.setNewData(list);
    }

    @Override
    public void defaltAddressSourcess() {
        stopProgress();
        mPresenter.getAddressList();
    }

    @Override
    public void deleteAddressSouress() {
        stopProgress();
        mPresenter.getAddressList();
    }
}
