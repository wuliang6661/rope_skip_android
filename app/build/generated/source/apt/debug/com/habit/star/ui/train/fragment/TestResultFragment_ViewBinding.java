// Generated code from Butter Knife. Do not modify!
package com.habit.star.ui.train.fragment;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.habit.commonlibrary.widget.ProgressbarLayout;
import com.habit.star.R;
import com.habit.star.widget.CutRelativeLayout;
import de.hdodenhof.circleimageview.CircleImageView;
import java.lang.IllegalStateException;
import java.lang.Override;

public class TestResultFragment_ViewBinding implements Unbinder {
  private TestResultFragment target;

  private View view7f080135;

  private View view7f080169;

  @UiThread
  public TestResultFragment_ViewBinding(final TestResultFragment target, View source) {
    this.target = target;

    View view;
    target.progress = Utils.findRequiredViewAsType(source, R.id.progress_fragment_common_view, "field 'progress'", ProgressbarLayout.class);
    view = Utils.findRequiredView(source, R.id.ll_back_fragment_test_result, "field 'llBack' and method 'onViewClicked'");
    target.llBack = Utils.castView(view, R.id.ll_back_fragment_test_result, "field 'llBack'", LinearLayout.class);
    view7f080135 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.ll_share_fragment_test_result, "field 'llShare' and method 'onViewClicked'");
    target.llShare = Utils.castView(view, R.id.ll_share_fragment_test_result, "field 'llShare'", LinearLayout.class);
    view7f080169 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.toolbarLayoutToolbar = Utils.findRequiredViewAsType(source, R.id.toolbar_layout_toolbar, "field 'toolbarLayoutToolbar'", LinearLayout.class);
    target.ivCircle1 = Utils.findRequiredViewAsType(source, R.id.iv_circle1, "field 'ivCircle1'", AppCompatImageView.class);
    target.llCircle1 = Utils.findRequiredViewAsType(source, R.id.ll_circle1, "field 'llCircle1'", LinearLayout.class);
    target.ivCircle2 = Utils.findRequiredViewAsType(source, R.id.iv_circle2, "field 'ivCircle2'", AppCompatImageView.class);
    target.llCircle2 = Utils.findRequiredViewAsType(source, R.id.ll_circle2, "field 'llCircle2'", LinearLayout.class);
    target.ivCircle3 = Utils.findRequiredViewAsType(source, R.id.iv_circle3, "field 'ivCircle3'", AppCompatImageView.class);
    target.llCircle3 = Utils.findRequiredViewAsType(source, R.id.ll_circle3, "field 'llCircle3'", LinearLayout.class);
    target.ivCircle4 = Utils.findRequiredViewAsType(source, R.id.iv_circle4, "field 'ivCircle4'", AppCompatImageView.class);
    target.llCircle4 = Utils.findRequiredViewAsType(source, R.id.ll_circle4, "field 'llCircle4'", LinearLayout.class);
    target.ivCircle5 = Utils.findRequiredViewAsType(source, R.id.iv_circle5, "field 'ivCircle5'", AppCompatImageView.class);
    target.llCircle5 = Utils.findRequiredViewAsType(source, R.id.ll_circle5, "field 'llCircle5'", LinearLayout.class);
    target.ivCircle6 = Utils.findRequiredViewAsType(source, R.id.iv_circle6, "field 'ivCircle6'", AppCompatImageView.class);
    target.llCircle6 = Utils.findRequiredViewAsType(source, R.id.ll_circle6, "field 'llCircle6'", LinearLayout.class);
    target.rcImprovementPlan = Utils.findRequiredViewAsType(source, R.id.rc_improvement_plan_fragment_test_result, "field 'rcImprovementPlan'", RecyclerView.class);
    target.tvThisTestGradeFragmentTestResult = Utils.findRequiredViewAsType(source, R.id.tv_this_test_grade_fragment_test_result, "field 'tvThisTestGradeFragmentTestResult'", AppCompatTextView.class);
    target.ivUserHeaderLayoutDialogToastShareSuccess = Utils.findRequiredViewAsType(source, R.id.iv_user_header_layout_dialog_toast_share_success, "field 'ivUserHeaderLayoutDialogToastShareSuccess'", CircleImageView.class);
    target.tvNameLayoutDialogToastShareSuccess = Utils.findRequiredViewAsType(source, R.id.tv_name_layout_dialog_toast_share_success, "field 'tvNameLayoutDialogToastShareSuccess'", AppCompatTextView.class);
    target.tvTimeLayoutDialogToastShareSuccess = Utils.findRequiredViewAsType(source, R.id.tv_time_layout_dialog_toast_share_success, "field 'tvTimeLayoutDialogToastShareSuccess'", AppCompatTextView.class);
    target.ivQrCodeLayoutDialogToastShareSuccess = Utils.findRequiredViewAsType(source, R.id.iv_qr_code_layout_dialog_toast_share_success, "field 'ivQrCodeLayoutDialogToastShareSuccess'", AppCompatImageView.class);
    target.crFullScreen = Utils.findRequiredViewAsType(source, R.id.cr_full_screen_fragment_test_result, "field 'crFullScreen'", CutRelativeLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    TestResultFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.progress = null;
    target.llBack = null;
    target.llShare = null;
    target.toolbarLayoutToolbar = null;
    target.ivCircle1 = null;
    target.llCircle1 = null;
    target.ivCircle2 = null;
    target.llCircle2 = null;
    target.ivCircle3 = null;
    target.llCircle3 = null;
    target.ivCircle4 = null;
    target.llCircle4 = null;
    target.ivCircle5 = null;
    target.llCircle5 = null;
    target.ivCircle6 = null;
    target.llCircle6 = null;
    target.rcImprovementPlan = null;
    target.tvThisTestGradeFragmentTestResult = null;
    target.ivUserHeaderLayoutDialogToastShareSuccess = null;
    target.tvNameLayoutDialogToastShareSuccess = null;
    target.tvTimeLayoutDialogToastShareSuccess = null;
    target.ivQrCodeLayoutDialogToastShareSuccess = null;
    target.crFullScreen = null;

    view7f080135.setOnClickListener(null);
    view7f080135 = null;
    view7f080169.setOnClickListener(null);
    view7f080169 = null;
  }
}
