package com.tohabit.skip.ui.mine.fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.SizeUtils;
import com.tohabit.commonlibrary.apt.SingleClick;
import com.tohabit.commonlibrary.decoration.HorizontalDividerItemDecoration;
import com.tohabit.commonlibrary.utils.ImageLoader;
import com.tohabit.commonlibrary.widget.ProgressbarLayout;
import com.tohabit.commonlibrary.widget.ToolbarWithBackRightProgress;
import com.tohabit.skip.R;
import com.tohabit.skip.app.RouterConstants;
import com.tohabit.skip.base.BaseFragment;
import com.tohabit.skip.pojo.po.FamilyUserBO;
import com.tohabit.skip.ui.mine.contract.FamilyMemberContract;
import com.tohabit.skip.ui.mine.presenter.FamilyMemberPresenter;
import com.tohabit.skip.utils.ToastUtil;
import com.tohabit.skip.widget.AlertDialog;
import com.tohabit.skip.widget.lgrecycleadapter.LGRecycleViewAdapter;
import com.tohabit.skip.widget.lgrecycleadapter.LGViewHolder;
import com.tohabit.skip.zxing.activity.CaptureActivity;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

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
public class FamilyMemberFragment extends BaseFragment<FamilyMemberPresenter>
        implements FamilyMemberContract.View {

    @BindView(R.id.toolbar_layout_toolbar)
    ToolbarWithBackRightProgress toolbar;
    @BindView(R.id.progress_fragment_family_member)
    ProgressbarLayout progress;
    @BindView(R.id.rv_family_list_fragment_family_member)
    SwipeMenuRecyclerView mRvFamilyList;
    @BindView(R.id.tv_my_code_fragment_family_member)
    AppCompatTextView mTvMyCode;
    @BindView(R.id.btn_scan_fragment_family_member)
    AppCompatButton mBtnScan;

    List<FamilyUserBO> list;

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


    private void setSwipeMenu() {
        // 创建菜单：
        SwipeMenuCreator mSwipeMenuCreator = (leftMenu, rightMenu, viewType) -> {
            // 2 删除
            SwipeMenuItem deleteItem = new SwipeMenuItem(getActivity());
            deleteItem.setText("删除")
                    .setBackgroundColor(Color.parseColor("#7EC7F5"))
                    .setTextColor(Color.WHITE) // 文字颜色。
                    .setTextSize(14) // 文字大小。
                    .setWidth(SizeUtils.dp2px(60))
                    .setHeight(LinearLayout.LayoutParams.MATCH_PARENT);
            rightMenu.addMenuItem(deleteItem);
            // 注意：哪边不想要菜单，那么不要添加即可。
        };
        // 设置监听器。
        mRvFamilyList.setSwipeMenuCreator(mSwipeMenuCreator);
        SwipeMenuItemClickListener mMenuItemClickListener = (menuBridge, position) -> {
            // 任何操作必须先关闭菜单，否则可能出现Item菜单打开状态错乱。
            menuBridge.closeMenu();
            new AlertDialog(getActivity()).builder().setGone().setMsg("是否确认移除该家庭成员？")
                    .setNegativeButton("取消", null)
                    .setPositiveButton("确定", v -> mPresenter.delFamliayUser(list.get(position).getId())).show();

        };
        // 菜单点击监听。
        mRvFamilyList.setSwipeMenuItemClickListener(mMenuItemClickListener);
    }


    private void initAdapter() {
        mRvFamilyList.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getActivity()).sizeResId(R.dimen.size_list_item_divider_member).colorResId(R.color.transparent).build());
        mRvFamilyList.setLayoutManager(new LinearLayoutManager(getActivity()));
        setSwipeMenu();

    }


    private void setAdapter(){
        LGRecycleViewAdapter<FamilyUserBO> adapter = new LGRecycleViewAdapter<FamilyUserBO>(list) {
            @Override
            public int getLayoutId(int viewType) {
                return R.layout.layout_fragment_family_member_list_item;
            }

            @Override
            public void convert(LGViewHolder holder, FamilyUserBO item, int position) {
                        holder.setText(R.id.tv_title_layout_fragment_family_member_list_item, item.getNickName());
                ImageLoader.load(mContext, (ImageView) holder.getView(R.id.iv_img_fragment_family_member_list_item), item.getImage());
            }
        };
        adapter.setOnItemClickListener(R.id.ll_layout_fragment_family_member_list_item, new LGRecycleViewAdapter.ItemClickListener() {
            @Override
            public void onItemClicked(View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putSerializable(RouterConstants.ARG_BEAN, adapter.getItem(position));
                start(FamilyMemberDetailFragment.newInstance(bundle));
            }
        });
        mRvFamilyList.setAdapter(adapter);
    }

    private void initDialog() {

    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        mPresenter.getFamilyUserList();
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
                gotoActivity(MyQrCodeActivity.class,false);
                break;
            case R.id.btn_scan_fragment_family_member:
                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    // 申请权限
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, 1);
                    return;
                }
                gotoActivity(CaptureActivity.class,false);
                break;
        }
    }

    @Override
    public void getAllUserBO(List<FamilyUserBO> list) {
        this.list = list;
        setAdapter();
    }
}
