// Generated code from Butter Knife. Do not modify!
package com.habit.star.ui.train.fragment;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.LinearLayout;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.habit.commonlibrary.widget.ProgressbarLayout;
import com.habit.star.R;
import de.hdodenhof.circleimageview.CircleImageView;
import java.lang.IllegalStateException;
import java.lang.Override;

public class EnergyValueFragment_ViewBinding implements Unbinder {
  private EnergyValueFragment target;

  private View view7f080136;

  private View view7f08014a;

  @UiThread
  public EnergyValueFragment_ViewBinding(final EnergyValueFragment target, View source) {
    this.target = target;

    View view;
    target.progress = Utils.findRequiredViewAsType(source, R.id.progress_fragment_common_view, "field 'progress'", ProgressbarLayout.class);
    view = Utils.findRequiredView(source, R.id.ll_back_fragment_train_plan, "field 'llBackFragmentTrainPlan' and method 'onViewClicked'");
    target.llBackFragmentTrainPlan = Utils.castView(view, R.id.ll_back_fragment_train_plan, "field 'llBackFragmentTrainPlan'", LinearLayout.class);
    view7f080136 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.llSettingFragmentTrainPlan = Utils.findRequiredViewAsType(source, R.id.ll_setting_fragment_train_plan, "field 'llSettingFragmentTrainPlan'", LinearLayout.class);
    target.toolbarLayoutToolbar = Utils.findRequiredViewAsType(source, R.id.toolbar_layout_toolbar, "field 'toolbarLayoutToolbar'", LinearLayout.class);
    target.ivUserHeaderFragmentMine = Utils.findRequiredViewAsType(source, R.id.iv_user_header_fragment_mine, "field 'ivUserHeaderFragmentMine'", CircleImageView.class);
    target.vCenterTag = Utils.findRequiredView(source, R.id.v_center_tag, "field 'vCenterTag'");
    target.ivLine1Center = Utils.findRequiredViewAsType(source, R.id.iv_line1_center_fragment_energy_value, "field 'ivLine1Center'", AppCompatImageView.class);
    target.ivLine2Center = Utils.findRequiredViewAsType(source, R.id.iv_line2_center_fragment_energy_value, "field 'ivLine2Center'", AppCompatImageView.class);
    target.ivLine3Center = Utils.findRequiredViewAsType(source, R.id.iv_line3_center_fragment_energy_value, "field 'ivLine3Center'", AppCompatImageView.class);
    target.ivCircle1 = Utils.findRequiredViewAsType(source, R.id.iv_circle1_fragment_energy_value, "field 'ivCircle1'", AppCompatImageView.class);
    target.ivCircle2 = Utils.findRequiredViewAsType(source, R.id.iv_circle2_fragment_energy_value, "field 'ivCircle2'", AppCompatImageView.class);
    target.ivCircle3 = Utils.findRequiredViewAsType(source, R.id.iv_circle3_fragment_energy_value, "field 'ivCircle3'", AppCompatImageView.class);
    target.ivCircle4 = Utils.findRequiredViewAsType(source, R.id.iv_circle4_fragment_energy_value, "field 'ivCircle4'", AppCompatImageView.class);
    target.tvCount = Utils.findRequiredViewAsType(source, R.id.tv_count_fragment_energy_value, "field 'tvCount'", AppCompatTextView.class);
    view = Utils.findRequiredView(source, R.id.ll_count_fragment_energy_value, "field 'llCount' and method 'onViewClicked'");
    target.llCount = Utils.castView(view, R.id.ll_count_fragment_energy_value, "field 'llCount'", LinearLayout.class);
    view7f08014a = view;
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
    EnergyValueFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.progress = null;
    target.llBackFragmentTrainPlan = null;
    target.llSettingFragmentTrainPlan = null;
    target.toolbarLayoutToolbar = null;
    target.ivUserHeaderFragmentMine = null;
    target.vCenterTag = null;
    target.ivLine1Center = null;
    target.ivLine2Center = null;
    target.ivLine3Center = null;
    target.ivCircle1 = null;
    target.ivCircle2 = null;
    target.ivCircle3 = null;
    target.ivCircle4 = null;
    target.tvCount = null;
    target.llCount = null;

    view7f080136.setOnClickListener(null);
    view7f080136 = null;
    view7f08014a.setOnClickListener(null);
    view7f08014a = null;
  }
}
