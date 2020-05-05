// Generated code from Butter Knife. Do not modify!
package com.habit.star.ui.login.fragment;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
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

public class RetrievePasswordFragment_ViewBinding implements Unbinder {
  private RetrievePasswordFragment target;

  private View view7f080043;

  private View view7f080045;

  @UiThread
  public RetrievePasswordFragment_ViewBinding(final RetrievePasswordFragment target, View source) {
    this.target = target;

    View view;
    target.toolbar = Utils.findRequiredViewAsType(source, R.id.toolbar_layout_toolbar, "field 'toolbar'", ToolbarWithBackRightProgress.class);
    target.progress = Utils.findRequiredViewAsType(source, R.id.progress_fragment_common_view, "field 'progress'", ProgressbarLayout.class);
    target.etTel = Utils.findRequiredViewAsType(source, R.id.et_tel_fragment_register, "field 'etTel'", AppCompatEditText.class);
    target.etPleaseInputMsgCode = Utils.findRequiredViewAsType(source, R.id.et_please_input_msg_code_fragment_register, "field 'etPleaseInputMsgCode'", AppCompatEditText.class);
    target.btnSendCode = Utils.findRequiredViewAsType(source, R.id.btn_send_code_fragment_register, "field 'btnSendCode'", AppCompatTextView.class);
    view = Utils.findRequiredView(source, R.id.btn_next_fragment_register, "field 'btnNext' and method 'onViewClicked'");
    target.btnNext = Utils.castView(view, R.id.btn_next_fragment_register, "field 'btnNext'", AppCompatButton.class);
    view7f080043 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.llType1 = Utils.findRequiredViewAsType(source, R.id.ll_type1_fragment_retrieve_password, "field 'llType1'", LinearLayout.class);
    target.etPassword = Utils.findRequiredViewAsType(source, R.id.et_password_fragment_register, "field 'etPassword'", AppCompatEditText.class);
    target.etComfirmPassword = Utils.findRequiredViewAsType(source, R.id.et_comfirm_password_fragment_register, "field 'etComfirmPassword'", AppCompatEditText.class);
    view = Utils.findRequiredView(source, R.id.btn_reset_confirm_fragment_register, "field 'btnReset' and method 'onViewClicked'");
    target.btnReset = Utils.castView(view, R.id.btn_reset_confirm_fragment_register, "field 'btnReset'", AppCompatButton.class);
    view7f080045 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.llType2 = Utils.findRequiredViewAsType(source, R.id.ll_type2_fragment_retrieve_password, "field 'llType2'", LinearLayout.class);
    target.tvType2Lable = Utils.findRequiredViewAsType(source, R.id.tv_type2_lable_fragment_retrieve_password, "field 'tvType2Lable'", AppCompatTextView.class);
    target.tvType1Lable = Utils.findRequiredViewAsType(source, R.id.tv_type1_lable_fragment_retrieve_password, "field 'tvType1Lable'", AppCompatTextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    RetrievePasswordFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.toolbar = null;
    target.progress = null;
    target.etTel = null;
    target.etPleaseInputMsgCode = null;
    target.btnSendCode = null;
    target.btnNext = null;
    target.llType1 = null;
    target.etPassword = null;
    target.etComfirmPassword = null;
    target.btnReset = null;
    target.llType2 = null;
    target.tvType2Lable = null;
    target.tvType1Lable = null;

    view7f080043.setOnClickListener(null);
    view7f080043 = null;
    view7f080045.setOnClickListener(null);
    view7f080045 = null;
  }
}
