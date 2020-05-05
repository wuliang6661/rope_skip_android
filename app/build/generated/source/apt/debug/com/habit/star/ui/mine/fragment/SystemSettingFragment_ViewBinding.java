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

public class SystemSettingFragment_ViewBinding implements Unbinder {
  private SystemSettingFragment target;

  private View view7f0800e9;

  private View view7f0800e0;

  private View view7f0800dc;

  @UiThread
  public SystemSettingFragment_ViewBinding(final SystemSettingFragment target, View source) {
    this.target = target;

    View view;
    target.toolbar = Utils.findRequiredViewAsType(source, R.id.toolbar_layout_toolbar, "field 'toolbar'", ToolbarWithBackRightProgress.class);
    view = Utils.findRequiredView(source, R.id.item_question_fragment_system_setting, "field 'itemQuestion' and method 'onViewClicked'");
    target.itemQuestion = Utils.castView(view, R.id.item_question_fragment_system_setting, "field 'itemQuestion'", LilayItemClickableWithHeadImageTopDivider.class);
    view7f0800e9 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.item_check_version_fragment_system_setting, "field 'itemCheckVersion' and method 'onViewClicked'");
    target.itemCheckVersion = Utils.castView(view, R.id.item_check_version_fragment_system_setting, "field 'itemCheckVersion'", LilayItemClickableWithHeadImageTopDivider.class);
    view7f0800e0 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.item_about_us_fragment_system_setting, "field 'itemAboutUs' and method 'onViewClicked'");
    target.itemAboutUs = Utils.castView(view, R.id.item_about_us_fragment_system_setting, "field 'itemAboutUs'", LilayItemClickableWithHeadImageTopDivider.class);
    view7f0800dc = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.progress = Utils.findRequiredViewAsType(source, R.id.progress_fragment_system_setting, "field 'progress'", ProgressbarLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    SystemSettingFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.toolbar = null;
    target.itemQuestion = null;
    target.itemCheckVersion = null;
    target.itemAboutUs = null;
    target.progress = null;

    view7f0800e9.setOnClickListener(null);
    view7f0800e9 = null;
    view7f0800e0.setOnClickListener(null);
    view7f0800e0 = null;
    view7f0800dc.setOnClickListener(null);
    view7f0800dc = null;
  }
}
