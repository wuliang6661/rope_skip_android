// Generated code from Butter Knife. Do not modify!
package com.habit.star.ui.train.fragment;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.habit.commonlibrary.widget.ProgressbarLayout;
import com.habit.star.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class TranHomeFragment_ViewBinding implements Unbinder {
  private TranHomeFragment target;

  private View view7f080260;

  private View view7f080163;

  private View view7f080106;

  private View view7f0801e4;

  private View view7f0802bd;

  private View view7f08011d;

  private View view7f080035;

  @UiThread
  public TranHomeFragment_ViewBinding(final TranHomeFragment target, View source) {
    this.target = target;

    View view;
    target.progress = Utils.findRequiredViewAsType(source, R.id.progress_fragment_common_view, "field 'progress'", ProgressbarLayout.class);
    view = Utils.findRequiredView(source, R.id.tv_battery_fragment_train_main, "field 'tvBattery' and method 'onViewClicked'");
    target.tvBattery = Utils.castView(view, R.id.tv_battery_fragment_train_main, "field 'tvBattery'", AppCompatTextView.class);
    view7f080260 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.ll_sd_input_fragment_train_main, "field 'llSdInput' and method 'onViewClicked'");
    target.llSdInput = Utils.castView(view, R.id.ll_sd_input_fragment_train_main, "field 'llSdInput'", LinearLayout.class);
    view7f080163 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.toolbarLayoutToolbar = Utils.findRequiredViewAsType(source, R.id.toolbar_layout_toolbar, "field 'toolbarLayoutToolbar'", LinearLayout.class);
    view = Utils.findRequiredView(source, R.id.iv_fresh_fragment_train_main, "field 'ivFresh' and method 'onViewClicked'");
    target.ivFresh = Utils.castView(view, R.id.iv_fresh_fragment_train_main, "field 'ivFresh'", AppCompatImageView.class);
    view7f080106 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.tvBlueConnectStatus = Utils.findRequiredViewAsType(source, R.id.tv_blue_connect_statusfragment_train_main, "field 'tvBlueConnectStatus'", AppCompatTextView.class);
    target.tvTimeCount = Utils.findRequiredViewAsType(source, R.id.tv_time_count_fragment_train_main, "field 'tvTimeCount'", AppCompatTextView.class);
    view = Utils.findRequiredView(source, R.id.rl_count_fragment_train_main, "field 'rlCount' and method 'getNum'");
    target.rlCount = Utils.castView(view, R.id.rl_count_fragment_train_main, "field 'rlCount'", FrameLayout.class);
    view7f0801e4 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.getNum();
      }
    });
    view = Utils.findRequiredView(source, R.id.tv_tj_fragment_train_main, "field 'tvTj' and method 'onViewClicked'");
    target.tvTj = Utils.castView(view, R.id.tv_tj_fragment_train_main, "field 'tvTj'", LinearLayout.class);
    view7f0802bd = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.rvTestRecord = Utils.findRequiredViewAsType(source, R.id.rv_test_record_fragment_train_main, "field 'rvTestRecord'", RecyclerView.class);
    view = Utils.findRequiredView(source, R.id.iv_start_test_fragment_train_main, "field 'ivStartTest', method 'onViewClicked', and method 'onViewClicked'");
    target.ivStartTest = Utils.castView(view, R.id.iv_start_test_fragment_train_main, "field 'ivStartTest'", AppCompatImageView.class);
    view7f08011d = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
        target.onViewClicked();
      }
    });
    target.tvTime = Utils.findRequiredViewAsType(source, R.id.tv_test_time_train, "field 'tvTime'", AppCompatTextView.class);
    view = Utils.findRequiredView(source, R.id.blue_status_layout, "method 'clickBlue'");
    view7f080035 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.clickBlue();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    TranHomeFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.progress = null;
    target.tvBattery = null;
    target.llSdInput = null;
    target.toolbarLayoutToolbar = null;
    target.ivFresh = null;
    target.tvBlueConnectStatus = null;
    target.tvTimeCount = null;
    target.rlCount = null;
    target.tvTj = null;
    target.rvTestRecord = null;
    target.ivStartTest = null;
    target.tvTime = null;

    view7f080260.setOnClickListener(null);
    view7f080260 = null;
    view7f080163.setOnClickListener(null);
    view7f080163 = null;
    view7f080106.setOnClickListener(null);
    view7f080106 = null;
    view7f0801e4.setOnClickListener(null);
    view7f0801e4 = null;
    view7f0802bd.setOnClickListener(null);
    view7f0802bd = null;
    view7f08011d.setOnClickListener(null);
    view7f08011d = null;
    view7f080035.setOnClickListener(null);
    view7f080035 = null;
  }
}
