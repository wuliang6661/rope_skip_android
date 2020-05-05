// Generated code from Butter Knife. Do not modify!
package com.habit.star.ui.train.fragment;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
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

public class EnergyDetailFragment_ViewBinding implements Unbinder {
  private EnergyDetailFragment target;

  private View view7f080134;

  private View view7f08029b;

  @UiThread
  public EnergyDetailFragment_ViewBinding(final EnergyDetailFragment target, View source) {
    this.target = target;

    View view;
    target.progress = Utils.findRequiredViewAsType(source, R.id.progress_fragment_common_view, "field 'progress'", ProgressbarLayout.class);
    view = Utils.findRequiredView(source, R.id.ll_back_fragment_energy_detail, "field 'llBackFragmentEnergyDetail' and method 'onViewClicked'");
    target.llBackFragmentEnergyDetail = Utils.castView(view, R.id.ll_back_fragment_energy_detail, "field 'llBackFragmentEnergyDetail'", LinearLayout.class);
    view7f080134 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.toolbarLayoutToolbar = Utils.findRequiredViewAsType(source, R.id.toolbar_layout_toolbar, "field 'toolbarLayoutToolbar'", LinearLayout.class);
    target.tvValue1FragmentEnergyDetail = Utils.findRequiredViewAsType(source, R.id.tv_value1_fragment_energy_detail, "field 'tvValue1FragmentEnergyDetail'", AppCompatTextView.class);
    target.tvValue2FragmentEnergyDetail = Utils.findRequiredViewAsType(source, R.id.tv_value2_fragment_energy_detail, "field 'tvValue2FragmentEnergyDetail'", AppCompatTextView.class);
    view = Utils.findRequiredView(source, R.id.tv_nlz_detail_fragment_energy_detail, "field 'tvNlzDetailFragmentEnergyDetail' and method 'onViewClicked'");
    target.tvNlzDetailFragmentEnergyDetail = Utils.castView(view, R.id.tv_nlz_detail_fragment_energy_detail, "field 'tvNlzDetailFragmentEnergyDetail'", AppCompatTextView.class);
    view7f08029b = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.rvNlzDetailListFragmentEnergyDetail = Utils.findRequiredViewAsType(source, R.id.rv_nlz_detail_list_fragment_energy_detail, "field 'rvNlzDetailListFragmentEnergyDetail'", RecyclerView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    EnergyDetailFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.progress = null;
    target.llBackFragmentEnergyDetail = null;
    target.toolbarLayoutToolbar = null;
    target.tvValue1FragmentEnergyDetail = null;
    target.tvValue2FragmentEnergyDetail = null;
    target.tvNlzDetailFragmentEnergyDetail = null;
    target.rvNlzDetailListFragmentEnergyDetail = null;

    view7f080134.setOnClickListener(null);
    view7f080134 = null;
    view7f08029b.setOnClickListener(null);
    view7f08029b = null;
  }
}
