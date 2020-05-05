package com.habit.star.ui.mine.fragment;

import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.habit.commonlibrary.apt.SingleClick;
import com.habit.commonlibrary.decoration.HorizontalDividerItemDecoration;
import com.habit.commonlibrary.widget.ProgressbarLayout;
import com.habit.commonlibrary.widget.ToolbarWithBackRightProgress;
import com.habit.star.R;
import com.habit.star.app.RouterConstants;
import com.habit.star.base.BaseFragment;
import com.habit.star.ui.mine.adapter.FamilyMemberListAdapter;
import com.habit.star.ui.mine.bean.FamilyMemberModel;
import com.habit.star.ui.mine.contract.FamilyMemberContract;
import com.habit.star.ui.mine.presenter.FamilyMemberPresenter;
import com.habit.star.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/*
 * 创建日期：2020-01-22 08:59
 * @author sundongdong
 * @version 1.0
 * @since
 * 文件名称：FamilyMemberFragment.java
 * 类说明：家庭成员
 */
public class FamilyMemberFragment extends BaseFragment<FamilyMemberPresenter> implements FamilyMemberContract.View {

    @BindView(R.id.toolbar_layout_toolbar)
    ToolbarWithBackRightProgress toolbar;
    @BindView(R.id.progress_fragment_family_member)
    ProgressbarLayout progress;
    @BindView(R.id.rv_family_list_fragment_family_member)
    RecyclerView mRvFamilyList;
    @BindView(R.id.tv_my_code_fragment_family_member)
    AppCompatTextView mTvMyCode;
    @BindView(R.id.btn_scan_fragment_family_member)
    AppCompatButton mBtnScan;

    private FamilyMemberListAdapter mMemberListAdapter;

    public static FamilyMemberFragment newInstance(Bundle bundle) {
        FamilyMemberFragment fragment = new FamilyMemberFragment();
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
        return R.layout.fragment_family_member;
    }

    @Override
    protected String getLogTag() {
        return "FamilyMemberFragment %s";
    }

    @Override
    protected void initEventAndData() {
        initDialog();
        initAdapter();
        toolbar.setBackIBClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _mActivity.onBackPressedSupport();
            }
        });
    }

    private void initAdapter(){
        mRvFamilyList.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getActivity()).sizeResId(R.dimen.size_list_item_divider_member).colorResId(R.color.transparent).build());
        mRvFamilyList.setLayoutManager(new LinearLayoutManager(getActivity()));
        mMemberListAdapter = new FamilyMemberListAdapter(mContext);
        mRvFamilyList.setAdapter(mMemberListAdapter);
        mRvFamilyList.addOnItemTouchListener(new OnItemChildClickListener() {
            @SingleClick
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.ll_layout_fragment_family_member_list_item){
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(RouterConstants.ARG_BEAN,mMemberListAdapter.getItem(position));
                    start(FamilyMemberDetailFragment.newInstance(bundle));
                }
            }
        });

        ///添加测试数据
        List list= new ArrayList<FamilyMemberModel>();
        FamilyMemberModel model1 = new FamilyMemberModel();
        model1.img = "https://tupian.qqw21.com/article/UploadPic/2019-12/2019122220574824440.jpg";
        model1.name = "可爱的小兔兔";
        list.add(model1);
        FamilyMemberModel model2 = new FamilyMemberModel();
        model2.img = "http://a.hiphotos.baidu.com/zhidao/pic/item/8d5494eef01f3a295d66c3bd9a25bc315c607c12.jpg";
        model2.name = "爱丽丝豆豆";
        list.add(model2);
        FamilyMemberModel model3 = new FamilyMemberModel();
        model3.img = "http://pic4.zhimg.com/50/v2-2ef01343920e66f878b05ff380d902d7_hd.jpg";
        model3.name = "功夫假小子";
        list.add(model3);
        mMemberListAdapter.setNewData(list);
    }

    private void initDialog() {

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

    @SingleClick
    @OnClick({R.id.tv_my_code_fragment_family_member,
            R.id.btn_scan_fragment_family_member})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_my_code_fragment_family_member:
                break;
            case R.id.btn_scan_fragment_family_member:
                break;
        }
    }
}
