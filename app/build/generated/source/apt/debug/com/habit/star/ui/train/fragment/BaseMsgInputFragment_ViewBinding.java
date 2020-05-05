// Generated code from Butter Knife. Do not modify!
package com.habit.star.ui.train.fragment;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.widget.LinearLayout;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.habit.commonlibrary.widget.ProgressbarLayout;
import com.habit.commonlibrary.widget.ToolbarWithBackRightProgress;
import com.habit.star.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class BaseMsgInputFragment_ViewBinding implements Unbinder {
  private BaseMsgInputFragment target;

  private View view7f080167;

  private View view7f080166;

  private View view7f08004c;

  @UiThread
  public BaseMsgInputFragment_ViewBinding(final BaseMsgInputFragment target, View source) {
    this.target = target;

    View view;
    target.toolbar = Utils.findRequiredViewAsType(source, R.id.toolbar_layout_toolbar, "field 'toolbar'", ToolbarWithBackRightProgress.class);
    target.progress = Utils.findRequiredViewAsType(source, R.id.progress_fragment_common_view, "field 'progress'", ProgressbarLayout.class);
    target.etAge = Utils.findRequiredViewAsType(source, R.id.et_age_fragment_base_msg_input, "field 'etAge'", AppCompatEditText.class);
    target.ivSexWoman = Utils.findRequiredViewAsType(source, R.id.iv_sex_woman_fragment_base_msg_input, "field 'ivSexWoman'", AppCompatImageView.class);
    view = Utils.findRequiredView(source, R.id.ll_sex_woman_fragment_base_msg_input, "field 'llSexWoman' and method 'onViewClicked'");
    target.llSexWoman = Utils.castView(view, R.id.ll_sex_woman_fragment_base_msg_input, "field 'llSexWoman'", LinearLayout.class);
    view7f080167 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.ivSexMan = Utils.findRequiredViewAsType(source, R.id.iv_sex_man_fragment_base_msg_input, "field 'ivSexMan'", AppCompatImageView.class);
    view = Utils.findRequiredView(source, R.id.ll_sex_man_fragment_base_msg_input, "field 'llSexMan' and method 'onViewClicked'");
    target.llSexMan = Utils.castView(view, R.id.ll_sex_man_fragment_base_msg_input, "field 'llSexMan'", LinearLayout.class);
    view7f080166 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.etHeight = Utils.findRequiredViewAsType(source, R.id.et_height_fragment_base_msg_input, "field 'etHeight'", AppCompatEditText.class);
    target.etWeight = Utils.findRequiredViewAsType(source, R.id.et_weight_fragment_base_msg_input, "field 'etWeight'", AppCompatEditText.class);
    view = Utils.findRequiredView(source, R.id.btn_start_test_fragment_base_msg_input, "field 'btnStartTest' and method 'onViewClicked'");
    target.btnStartTest = Utils.castView(view, R.id.btn_start_test_fragment_base_msg_input, "field 'btnStartTest'", AppCompatButton.class);
    view7f08004c = view;
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
    BaseMsgInputFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.toolbar = null;
    target.progress = null;
    target.etAge = null;
    target.ivSexWoman = null;
    target.llSexWoman = null;
    target.ivSexMan = null;
    target.llSexMan = null;
    target.etHeight = null;
    target.etWeight = null;
    target.btnStartTest = null;

    view7f080167.setOnClickListener(null);
    view7f080167 = null;
    view7f080166.setOnClickListener(null);
    view7f080166 = null;
    view7f08004c.setOnClickListener(null);
    view7f08004c = null;
  }
}
