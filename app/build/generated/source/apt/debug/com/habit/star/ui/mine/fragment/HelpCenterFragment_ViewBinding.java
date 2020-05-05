// Generated code from Butter Knife. Do not modify!
package com.habit.star.ui.mine.fragment;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
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

public class HelpCenterFragment_ViewBinding implements Unbinder {
  private HelpCenterFragment target;

  private View view7f0800f2;

  private View view7f0800e5;

  private View view7f0800f3;

  @UiThread
  public HelpCenterFragment_ViewBinding(final HelpCenterFragment target, View source) {
    this.target = target;

    View view;
    target.toolbar = Utils.findRequiredViewAsType(source, R.id.toolbar_layout_toolbar, "field 'toolbar'", ToolbarWithBackRightProgress.class);
    target.progress = Utils.findRequiredViewAsType(source, R.id.progress_fragment_help_center, "field 'progress'", ProgressbarLayout.class);
    view = Utils.findRequiredView(source, R.id.item_xytk_fragment_help_center, "field 'mItemXytk' and method 'onViewClicked'");
    target.mItemXytk = Utils.castView(view, R.id.item_xytk_fragment_help_center, "field 'mItemXytk'", LilayItemClickableWithHeadImageTopDivider.class);
    view7f0800f2 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.item_kfrx_fragment_help_center, "field 'mItemKfrx' and method 'onViewClicked'");
    target.mItemKfrx = Utils.castView(view, R.id.item_kfrx_fragment_help_center, "field 'mItemKfrx'", LilayItemClickableWithHeadImageTopDivider.class);
    view7f0800e5 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.item_yjfk_fragment_help_center, "field 'mItemYjfk' and method 'onViewClicked'");
    target.mItemYjfk = Utils.castView(view, R.id.item_yjfk_fragment_help_center, "field 'mItemYjfk'", LilayItemClickableWithHeadImageTopDivider.class);
    view7f0800f3 = view;
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
    HelpCenterFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.toolbar = null;
    target.progress = null;
    target.mItemXytk = null;
    target.mItemKfrx = null;
    target.mItemYjfk = null;

    view7f0800f2.setOnClickListener(null);
    view7f0800f2 = null;
    view7f0800e5.setOnClickListener(null);
    view7f0800e5 = null;
    view7f0800f3.setOnClickListener(null);
    view7f0800f3 = null;
  }
}
