// Generated code from Butter Knife. Do not modify!
package com.habit.star.ui.login.fragment;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.habit.commonlibrary.widget.ProgressbarLayout;
import com.habit.commonlibrary.widget.ToolbarWithBackRightProgress;
import com.habit.star.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class RegisterFragment_ViewBinding implements Unbinder {
  private RegisterFragment target;

  private View view7f08004a;

  private View view7f080170;

  private View view7f080172;

  private View view7f0802a5;

  private View view7f080043;

  private View view7f08027f;

  @UiThread
  public RegisterFragment_ViewBinding(final RegisterFragment target, View source) {
    this.target = target;

    View view;
    target.mToolbar = Utils.findRequiredViewAsType(source, R.id.toolbar_layout_toolbar, "field 'mToolbar'", ToolbarWithBackRightProgress.class);
    target.mEtTelFragmentRegister = Utils.findRequiredViewAsType(source, R.id.et_tel_fragment_register, "field 'mEtTelFragmentRegister'", AppCompatEditText.class);
    target.mEtPleaseInputMsgCodeFragmentRegister = Utils.findRequiredViewAsType(source, R.id.et_please_input_msg_code_fragment_register, "field 'mEtPleaseInputMsgCodeFragmentRegister'", AppCompatEditText.class);
    view = Utils.findRequiredView(source, R.id.btn_send_code_fragment_register, "field 'mBtnSendCodeFragmentRegister' and method 'onViewClicked'");
    target.mBtnSendCodeFragmentRegister = Utils.castView(view, R.id.btn_send_code_fragment_register, "field 'mBtnSendCodeFragmentRegister'", AppCompatTextView.class);
    view7f08004a = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.mEtPasswordFragmentRegister = Utils.findRequiredViewAsType(source, R.id.et_password_fragment_register, "field 'mEtPasswordFragmentRegister'", AppCompatEditText.class);
    view = Utils.findRequiredView(source, R.id.ll_taggle_close_fragment_register, "field 'mLlTaggleCloseFragmentRegister' and method 'onViewClicked'");
    target.mLlTaggleCloseFragmentRegister = Utils.castView(view, R.id.ll_taggle_close_fragment_register, "field 'mLlTaggleCloseFragmentRegister'", LinearLayout.class);
    view7f080170 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.ll_taggle_open_fragment_register, "field 'mLlTaggleOpenFragmentRegister' and method 'onViewClicked'");
    target.mLlTaggleOpenFragmentRegister = Utils.castView(view, R.id.ll_taggle_open_fragment_register, "field 'mLlTaggleOpenFragmentRegister'", LinearLayout.class);
    view7f080172 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.mCbReadFragmentRegister = Utils.findRequiredViewAsType(source, R.id.cb_read_fragment_register, "field 'mCbReadFragmentRegister'", CheckBox.class);
    view = Utils.findRequiredView(source, R.id.tv_read_remind_fragment_register, "field 'mTvReadRemindFragmentRegister' and method 'onViewClicked'");
    target.mTvReadRemindFragmentRegister = Utils.castView(view, R.id.tv_read_remind_fragment_register, "field 'mTvReadRemindFragmentRegister'", AppCompatTextView.class);
    view7f0802a5 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.btn_next_fragment_register, "field 'mBtnNextFragmentRegister' and method 'onViewClicked'");
    target.mBtnNextFragmentRegister = Utils.castView(view, R.id.btn_next_fragment_register, "field 'mBtnNextFragmentRegister'", AppCompatButton.class);
    view7f080043 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.progressbarLayout = Utils.findRequiredViewAsType(source, R.id.progress_fragment_register, "field 'progressbarLayout'", ProgressbarLayout.class);
    view = Utils.findRequiredView(source, R.id.tv_has_account_fragment_register, "method 'onViewClicked'");
    view7f08027f = view;
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
    RegisterFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mToolbar = null;
    target.mEtTelFragmentRegister = null;
    target.mEtPleaseInputMsgCodeFragmentRegister = null;
    target.mBtnSendCodeFragmentRegister = null;
    target.mEtPasswordFragmentRegister = null;
    target.mLlTaggleCloseFragmentRegister = null;
    target.mLlTaggleOpenFragmentRegister = null;
    target.mCbReadFragmentRegister = null;
    target.mTvReadRemindFragmentRegister = null;
    target.mBtnNextFragmentRegister = null;
    target.progressbarLayout = null;

    view7f08004a.setOnClickListener(null);
    view7f08004a = null;
    view7f080170.setOnClickListener(null);
    view7f080170 = null;
    view7f080172.setOnClickListener(null);
    view7f080172 = null;
    view7f0802a5.setOnClickListener(null);
    view7f0802a5 = null;
    view7f080043.setOnClickListener(null);
    view7f080043 = null;
    view7f08027f.setOnClickListener(null);
    view7f08027f = null;
  }
}
