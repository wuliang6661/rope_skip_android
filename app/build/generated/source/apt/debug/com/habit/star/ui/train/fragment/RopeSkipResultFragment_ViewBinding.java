// Generated code from Butter Knife. Do not modify!
package com.habit.star.ui.train.fragment;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.AppCompatButton;
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

public class RopeSkipResultFragment_ViewBinding implements Unbinder {
  private RopeSkipResultFragment target;

  private View view7f080134;

  private View view7f080048;

  private View view7f080133;

  private View view7f080168;

  @UiThread
  public RopeSkipResultFragment_ViewBinding(final RopeSkipResultFragment target, View source) {
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
    target.ivJzFragmentRopeResult = Utils.findRequiredViewAsType(source, R.id.iv_jz_fragment_rope_result, "field 'ivJzFragmentRopeResult'", AppCompatImageView.class);
    view = Utils.findRequiredView(source, R.id.btn_save_fragment_rope_skip_setting, "field 'btnSaveFragmentRopeSkipSetting' and method 'onViewClicked'");
    target.btnSaveFragmentRopeSkipSetting = Utils.castView(view, R.id.btn_save_fragment_rope_skip_setting, "field 'btnSaveFragmentRopeSkipSetting'", AppCompatButton.class);
    view7f080048 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.ll_again_fragment_rope_result, "field 'llAgainFragmentRopeResult' and method 'onViewClicked'");
    target.llAgainFragmentRopeResult = Utils.castView(view, R.id.ll_again_fragment_rope_result, "field 'llAgainFragmentRopeResult'", LinearLayout.class);
    view7f080133 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.ll_share_fragment_rope_result, "field 'llShareFragmentRopeResult' and method 'onViewClicked'");
    target.llShareFragmentRopeResult = Utils.castView(view, R.id.ll_share_fragment_rope_result, "field 'llShareFragmentRopeResult'", LinearLayout.class);
    view7f080168 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.ivUserHeaderLayoutDialogToastShareSuccess = Utils.findRequiredViewAsType(source, R.id.iv_user_header_layout_dialog_toast_share_success, "field 'ivUserHeaderLayoutDialogToastShareSuccess'", CircleImageView.class);
    target.tvNameLayoutDialogToastShareSuccess = Utils.findRequiredViewAsType(source, R.id.tv_name_layout_dialog_toast_share_success, "field 'tvNameLayoutDialogToastShareSuccess'", AppCompatTextView.class);
    target.tvTimeLayoutDialogToastShareSuccess = Utils.findRequiredViewAsType(source, R.id.tv_time_layout_dialog_toast_share_success, "field 'tvTimeLayoutDialogToastShareSuccess'", AppCompatTextView.class);
    target.ivQrCodeLayoutDialogToastShareSuccess = Utils.findRequiredViewAsType(source, R.id.iv_qr_code_layout_dialog_toast_share_success, "field 'ivQrCodeLayoutDialogToastShareSuccess'", AppCompatImageView.class);
    target.llLayoutDialogToastShareSuccess = Utils.findRequiredViewAsType(source, R.id.ll_layout_dialog_toast_share_success, "field 'llLayoutDialogToastShareSuccess'", LinearLayout.class);
    target.tvTimeFragmentRopeResult = Utils.findRequiredViewAsType(source, R.id.tv_time_fragment_rope_result, "field 'tvTimeFragmentRopeResult'", AppCompatTextView.class);
    target.tvNumberFragmentRopeResult = Utils.findRequiredViewAsType(source, R.id.tv_number_fragment_rope_result, "field 'tvNumberFragmentRopeResult'", AppCompatTextView.class);
    target.tvBreakNumberFragmentRopeResult = Utils.findRequiredViewAsType(source, R.id.tv_break_number_fragment_rope_result, "field 'tvBreakNumberFragmentRopeResult'", AppCompatTextView.class);
    target.tvAverageNumberFragmentRopeResult = Utils.findRequiredViewAsType(source, R.id.tv_average_number_fragment_rope_result, "field 'tvAverageNumberFragmentRopeResult'", AppCompatTextView.class);
    target.tvAccessFragmentRopeResult = Utils.findRequiredViewAsType(source, R.id.tv_access_fragment_rope_result, "field 'tvAccessFragmentRopeResult'", AppCompatTextView.class);
    target.tvHeightFragmentRopeResult = Utils.findRequiredViewAsType(source, R.id.tv_height_fragment_rope_result, "field 'tvHeightFragmentRopeResult'", AppCompatTextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    RopeSkipResultFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.progress = null;
    target.llBackFragmentEnergyDetail = null;
    target.toolbarLayoutToolbar = null;
    target.ivJzFragmentRopeResult = null;
    target.btnSaveFragmentRopeSkipSetting = null;
    target.llAgainFragmentRopeResult = null;
    target.llShareFragmentRopeResult = null;
    target.ivUserHeaderLayoutDialogToastShareSuccess = null;
    target.tvNameLayoutDialogToastShareSuccess = null;
    target.tvTimeLayoutDialogToastShareSuccess = null;
    target.ivQrCodeLayoutDialogToastShareSuccess = null;
    target.llLayoutDialogToastShareSuccess = null;
    target.tvTimeFragmentRopeResult = null;
    target.tvNumberFragmentRopeResult = null;
    target.tvBreakNumberFragmentRopeResult = null;
    target.tvAverageNumberFragmentRopeResult = null;
    target.tvAccessFragmentRopeResult = null;
    target.tvHeightFragmentRopeResult = null;

    view7f080134.setOnClickListener(null);
    view7f080134 = null;
    view7f080048.setOnClickListener(null);
    view7f080048 = null;
    view7f080133.setOnClickListener(null);
    view7f080133 = null;
    view7f080168.setOnClickListener(null);
    view7f080168 = null;
  }
}
