// Generated code from Butter Knife. Do not modify!
package com.habit.star.ui.mine.fragment;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.habit.commonlibrary.widget.LilayItemClickableWithHeadImageTopDivider;
import com.habit.commonlibrary.widget.ProgressbarLayout;
import com.habit.commonlibrary.widget.ToolbarWithBackRightProgress;
import com.habit.star.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class FeedbackFragment_ViewBinding implements Unbinder {
  private FeedbackFragment target;

  private View view7f0800e3;

  private View view7f08004e;

  @UiThread
  public FeedbackFragment_ViewBinding(final FeedbackFragment target, View source) {
    this.target = target;

    View view;
    target.toolbar = Utils.findRequiredViewAsType(source, R.id.toolbar_layout_toolbar, "field 'toolbar'", ToolbarWithBackRightProgress.class);
    target.progress = Utils.findRequiredViewAsType(source, R.id.progress_fragment_feed_back, "field 'progress'", ProgressbarLayout.class);
    view = Utils.findRequiredView(source, R.id.item_fklx_fragment_feed_back, "field 'mItemFklx' and method 'onViewClicked'");
    target.mItemFklx = Utils.castView(view, R.id.item_fklx_fragment_feed_back, "field 'mItemFklx'", LilayItemClickableWithHeadImageTopDivider.class);
    view7f0800e3 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.mRvImg = Utils.findRequiredViewAsType(source, R.id.rv_img_fragment_feed_back, "field 'mRvImg'", RecyclerView.class);
    view = Utils.findRequiredView(source, R.id.btn_submit_fragment_feed_back, "field 'mBtnSubmit' and method 'onViewClicked'");
    target.mBtnSubmit = Utils.castView(view, R.id.btn_submit_fragment_feed_back, "field 'mBtnSubmit'", AppCompatButton.class);
    view7f08004e = view;
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
    FeedbackFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.toolbar = null;
    target.progress = null;
    target.mItemFklx = null;
    target.mRvImg = null;
    target.mBtnSubmit = null;

    view7f0800e3.setOnClickListener(null);
    view7f0800e3 = null;
    view7f08004e.setOnClickListener(null);
    view7f08004e = null;
  }
}
