// Generated code from Butter Knife. Do not modify!
package com.habit.star.ui.login.fragment;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.AppCompatButton;
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

public class PerfectInformationFragment_ViewBinding implements Unbinder {
  private PerfectInformationFragment target;

  private View view7f0800e8;

  private View view7f0800eb;

  private View view7f0800f0;

  private View view7f0800ea;

  private View view7f08004e;

  @UiThread
  public PerfectInformationFragment_ViewBinding(final PerfectInformationFragment target,
      View source) {
    this.target = target;

    View view;
    target.toolbar = Utils.findRequiredViewAsType(source, R.id.toolbar_layout_toolbar, "field 'toolbar'", ToolbarWithBackRightProgress.class);
    target.progress = Utils.findRequiredViewAsType(source, R.id.progress_fragment_perfect_information, "field 'progress'", ProgressbarLayout.class);
    view = Utils.findRequiredView(source, R.id.item_nick_name_fragment_perfect_information, "field 'itemNickNameFragmentPerfectInformation' and method 'onViewClicked'");
    target.itemNickNameFragmentPerfectInformation = Utils.castView(view, R.id.item_nick_name_fragment_perfect_information, "field 'itemNickNameFragmentPerfectInformation'", LilayItemClickableWithHeadImageTopDivider.class);
    view7f0800e8 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.item_sg_fragment_perfect_information, "field 'itemSgFragmentPerfectInformation' and method 'onViewClicked'");
    target.itemSgFragmentPerfectInformation = Utils.castView(view, R.id.item_sg_fragment_perfect_information, "field 'itemSgFragmentPerfectInformation'", LilayItemClickableWithHeadImageTopDivider.class);
    view7f0800eb = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.item_tz_fragment_perfect_information, "field 'itemTzFragmentPerfectInformation' and method 'onViewClicked'");
    target.itemTzFragmentPerfectInformation = Utils.castView(view, R.id.item_tz_fragment_perfect_information, "field 'itemTzFragmentPerfectInformation'", LilayItemClickableWithHeadImageTopDivider.class);
    view7f0800f0 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.item_sex_fragment_perfect_information, "field 'itemSexFragmentPerfectInformation' and method 'onViewClicked'");
    target.itemSexFragmentPerfectInformation = Utils.castView(view, R.id.item_sex_fragment_perfect_information, "field 'itemSexFragmentPerfectInformation'", LilayItemClickableWithHeadImageTopDivider.class);
    view7f0800ea = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.btn_submit_fragment_feed_back, "field 'btnSubmitFragmentFeedBack' and method 'onViewClicked'");
    target.btnSubmitFragmentFeedBack = Utils.castView(view, R.id.btn_submit_fragment_feed_back, "field 'btnSubmitFragmentFeedBack'", AppCompatButton.class);
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
    PerfectInformationFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.toolbar = null;
    target.progress = null;
    target.itemNickNameFragmentPerfectInformation = null;
    target.itemSgFragmentPerfectInformation = null;
    target.itemTzFragmentPerfectInformation = null;
    target.itemSexFragmentPerfectInformation = null;
    target.btnSubmitFragmentFeedBack = null;

    view7f0800e8.setOnClickListener(null);
    view7f0800e8 = null;
    view7f0800eb.setOnClickListener(null);
    view7f0800eb = null;
    view7f0800f0.setOnClickListener(null);
    view7f0800f0 = null;
    view7f0800ea.setOnClickListener(null);
    view7f0800ea = null;
    view7f08004e.setOnClickListener(null);
    view7f08004e = null;
  }
}
