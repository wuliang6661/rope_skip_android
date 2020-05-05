// Generated code from Butter Knife. Do not modify!
package com.habit.star.ui.mine.fragment;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.habit.commonlibrary.widget.ProgressbarLayout;
import com.habit.commonlibrary.widget.ToolbarWithBackRightProgress;
import com.habit.star.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ModifyNickNameFragment_ViewBinding implements Unbinder {
  private ModifyNickNameFragment target;

  private View view7f080050;

  @UiThread
  public ModifyNickNameFragment_ViewBinding(final ModifyNickNameFragment target, View source) {
    this.target = target;

    View view;
    target.toolbar = Utils.findRequiredViewAsType(source, R.id.toolbar_layout_toolbar, "field 'toolbar'", ToolbarWithBackRightProgress.class);
    target.progress = Utils.findRequiredViewAsType(source, R.id.progress_fragment_common_view, "field 'progress'", ProgressbarLayout.class);
    target.etNickName = Utils.findRequiredViewAsType(source, R.id.et_tel_fragment_register, "field 'etNickName'", AppCompatEditText.class);
    view = Utils.findRequiredView(source, R.id.btn_submit_fragment_modify_nick_name, "field 'btnSubmit' and method 'onViewClicked'");
    target.btnSubmit = Utils.castView(view, R.id.btn_submit_fragment_modify_nick_name, "field 'btnSubmit'", AppCompatButton.class);
    view7f080050 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    ModifyNickNameFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.toolbar = null;
    target.progress = null;
    target.etNickName = null;
    target.btnSubmit = null;

    view7f080050.setOnClickListener(null);
    view7f080050 = null;
  }
}
