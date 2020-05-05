// Generated code from Butter Knife. Do not modify!
package com.habit.star.ui.find.fragment;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.habit.commonlibrary.widget.ProgressbarLayout;
import com.habit.star.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class FindListFragment_ViewBinding implements Unbinder {
  private FindListFragment target;

  private View view7f0802a2;

  private View view7f08025e;

  private View view7f080278;

  @UiThread
  public FindListFragment_ViewBinding(final FindListFragment target, View source) {
    this.target = target;

    View view;
    target.mRecyclerView = Utils.findRequiredViewAsType(source, R.id.rv_layout_swipe_to_refresh, "field 'mRecyclerView'", RecyclerView.class);
    target.mSwipeRefreshLayout = Utils.findRequiredViewAsType(source, R.id.swipeLayout_layout_swipe_to_refresh, "field 'mSwipeRefreshLayout'", SwipeRefreshLayout.class);
    target.progress = Utils.findRequiredViewAsType(source, R.id.progress_fragment_fragment_find_list, "field 'progress'", ProgressbarLayout.class);
    view = Utils.findRequiredView(source, R.id.tv_proccess_fragment_find_list, "field 'tvProccessFragmentFindList' and method 'onViewClicked'");
    target.tvProccessFragmentFindList = Utils.castView(view, R.id.tv_proccess_fragment_find_list, "field 'tvProccessFragmentFindList'", AppCompatTextView.class);
    view7f0802a2 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.tv_already_report_fragment_find_list, "field 'tvAlreadyReportFragmentFindList' and method 'onViewClicked'");
    target.tvAlreadyReportFragmentFindList = Utils.castView(view, R.id.tv_already_report_fragment_find_list, "field 'tvAlreadyReportFragmentFindList'", AppCompatTextView.class);
    view7f08025e = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.tv_finish_fragment_find_list, "field 'tvFinishFragmentFindList' and method 'onViewClicked'");
    target.tvFinishFragmentFindList = Utils.castView(view, R.id.tv_finish_fragment_find_list, "field 'tvFinishFragmentFindList'", AppCompatTextView.class);
    view7f080278 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.llToolbarLayoutToolbar = Utils.findRequiredViewAsType(source, R.id.ll_toolbar_layout_toolbar, "field 'llToolbarLayoutToolbar'", LinearLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    FindListFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mRecyclerView = null;
    target.mSwipeRefreshLayout = null;
    target.progress = null;
    target.tvProccessFragmentFindList = null;
    target.tvAlreadyReportFragmentFindList = null;
    target.tvFinishFragmentFindList = null;
    target.llToolbarLayoutToolbar = null;

    view7f0802a2.setOnClickListener(null);
    view7f0802a2 = null;
    view7f08025e.setOnClickListener(null);
    view7f08025e = null;
    view7f080278.setOnClickListener(null);
    view7f080278 = null;
  }
}
