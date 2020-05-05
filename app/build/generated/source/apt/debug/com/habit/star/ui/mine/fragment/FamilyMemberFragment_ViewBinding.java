// Generated code from Butter Knife. Do not modify!
package com.habit.star.ui.mine.fragment;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.habit.commonlibrary.widget.ProgressbarLayout;
import com.habit.commonlibrary.widget.ToolbarWithBackRightProgress;
import com.habit.star.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class FamilyMemberFragment_ViewBinding implements Unbinder {
  private FamilyMemberFragment target;

  private View view7f080294;

  private View view7f080049;

  @UiThread
  public FamilyMemberFragment_ViewBinding(final FamilyMemberFragment target, View source) {
    this.target = target;

    View view;
    target.toolbar = Utils.findRequiredViewAsType(source, R.id.toolbar_layout_toolbar, "field 'toolbar'", ToolbarWithBackRightProgress.class);
    target.progress = Utils.findRequiredViewAsType(source, R.id.progress_fragment_family_member, "field 'progress'", ProgressbarLayout.class);
    target.mRvFamilyList = Utils.findRequiredViewAsType(source, R.id.rv_family_list_fragment_family_member, "field 'mRvFamilyList'", RecyclerView.class);
    view = Utils.findRequiredView(source, R.id.tv_my_code_fragment_family_member, "field 'mTvMyCode' and method 'onViewClicked'");
    target.mTvMyCode = Utils.castView(view, R.id.tv_my_code_fragment_family_member, "field 'mTvMyCode'", AppCompatTextView.class);
    view7f080294 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.btn_scan_fragment_family_member, "field 'mBtnScan' and method 'onViewClicked'");
    target.mBtnScan = Utils.castView(view, R.id.btn_scan_fragment_family_member, "field 'mBtnScan'", AppCompatButton.class);
    view7f080049 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    FamilyMemberFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.toolbar = null;
    target.progress = null;
    target.mRvFamilyList = null;
    target.mTvMyCode = null;
    target.mBtnScan = null;

    view7f080294.setOnClickListener(null);
    view7f080294 = null;
    view7f080049.setOnClickListener(null);
    view7f080049 = null;
  }
}
