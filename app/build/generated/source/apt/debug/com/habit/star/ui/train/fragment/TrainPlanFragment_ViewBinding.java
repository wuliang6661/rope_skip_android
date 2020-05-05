// Generated code from Butter Knife. Do not modify!
package com.habit.star.ui.train.fragment;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.habit.commonlibrary.widget.ProgressbarLayout;
import com.habit.star.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class TrainPlanFragment_ViewBinding implements Unbinder {
  private TrainPlanFragment target;

  private View view7f080136;

  private View view7f080165;

  private View view7f080161;

  private View view7f0801e6;

  @UiThread
  public TrainPlanFragment_ViewBinding(final TrainPlanFragment target, View source) {
    this.target = target;

    View view;
    target.progress = Utils.findRequiredViewAsType(source, R.id.progress_fragment_common_view, "field 'progress'", ProgressbarLayout.class);
    view = Utils.findRequiredView(source, R.id.ll_back_fragment_train_plan, "field 'llBack' and method 'onViewClicked'");
    target.llBack = Utils.castView(view, R.id.ll_back_fragment_train_plan, "field 'llBack'", LinearLayout.class);
    view7f080136 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.ll_setting_fragment_train_plan, "field 'llSetting' and method 'onViewClicked'");
    target.llSetting = Utils.castView(view, R.id.ll_setting_fragment_train_plan, "field 'llSetting'", LinearLayout.class);
    view7f080165 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.toolbarLayoutToolbar = Utils.findRequiredViewAsType(source, R.id.toolbar_layout_toolbar, "field 'toolbarLayoutToolbar'", LinearLayout.class);
    target.tvBattery = Utils.findRequiredViewAsType(source, R.id.tv_battery_fragment_train_plan, "field 'tvBattery'", AppCompatTextView.class);
    target.llBattery = Utils.findRequiredViewAsType(source, R.id.ll_battery_fragment_train_plan, "field 'llBattery'", LinearLayout.class);
    target.tvConnectState = Utils.findRequiredViewAsType(source, R.id.tv_connect_state_fragment_train_plan, "field 'tvConnectState'", AppCompatTextView.class);
    target.llConnectState = Utils.findRequiredViewAsType(source, R.id.ll_connect_state_fragment_train_plan, "field 'llConnectState'", LinearLayout.class);
    target.ivFreshFragmentTrainMain = Utils.findRequiredViewAsType(source, R.id.iv_fresh_fragment_train_main, "field 'ivFreshFragmentTrainMain'", AppCompatImageView.class);
    view = Utils.findRequiredView(source, R.id.ll_record_model_fragment_train_plan, "field 'llRecordModel' and method 'onViewClicked'");
    target.llRecordModel = Utils.castView(view, R.id.ll_record_model_fragment_train_plan, "field 'llRecordModel'", LinearLayout.class);
    view7f080161 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.tvTimeSecond = Utils.findRequiredViewAsType(source, R.id.tv_time_second_fragment_train_plan, "field 'tvTimeSecond'", AppCompatTextView.class);
    target.rlCountFragmentTrainMain = Utils.findRequiredViewAsType(source, R.id.rl_count_fragment_train_main, "field 'rlCountFragmentTrainMain'", FrameLayout.class);
    target.tvTimeCountFragmentTrainMain = Utils.findRequiredViewAsType(source, R.id.tv_time_count_fragment_train_main, "field 'tvTimeCountFragmentTrainMain'", AppCompatTextView.class);
    target.tvContral = Utils.findRequiredViewAsType(source, R.id.tv_contral_fragment_train_plan, "field 'tvContral'", AppCompatTextView.class);
    view = Utils.findRequiredView(source, R.id.rl_start_fragment_train_plan, "field 'rlStart' and method 'onViewClicked'");
    target.rlStart = Utils.castView(view, R.id.rl_start_fragment_train_plan, "field 'rlStart'", RelativeLayout.class);
    view7f0801e6 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.ivConnnetState = Utils.findRequiredViewAsType(source, R.id.iv_connnet_state_fragment_train_plan, "field 'ivConnnetState'", AppCompatImageView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    TrainPlanFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.progress = null;
    target.llBack = null;
    target.llSetting = null;
    target.toolbarLayoutToolbar = null;
    target.tvBattery = null;
    target.llBattery = null;
    target.tvConnectState = null;
    target.llConnectState = null;
    target.ivFreshFragmentTrainMain = null;
    target.llRecordModel = null;
    target.tvTimeSecond = null;
    target.rlCountFragmentTrainMain = null;
    target.tvTimeCountFragmentTrainMain = null;
    target.tvContral = null;
    target.rlStart = null;
    target.ivConnnetState = null;

    view7f080136.setOnClickListener(null);
    view7f080136 = null;
    view7f080165.setOnClickListener(null);
    view7f080165 = null;
    view7f080161.setOnClickListener(null);
    view7f080161 = null;
    view7f0801e6.setOnClickListener(null);
    view7f0801e6 = null;
  }
}
