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
import com.habit.star.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class LoginFragment_ViewBinding implements Unbinder {
  private LoginFragment target;

  private View view7f08016f;

  private View view7f080171;

  private View view7f080279;

  private View view7f08004f;

  private View view7f0802a7;

  @UiThread
  public LoginFragment_ViewBinding(final LoginFragment target, View source) {
    this.target = target;

    View view;
    target.mEtTel = Utils.findRequiredViewAsType(source, R.id.et_tel_fragment_login, "field 'mEtTel'", AppCompatEditText.class);
    target.mEtPassword = Utils.findRequiredViewAsType(source, R.id.et_password_fragment_login, "field 'mEtPassword'", AppCompatEditText.class);
    view = Utils.findRequiredView(source, R.id.ll_taggle_close_fragment_login, "field 'mLlTaggleClose' and method 'onViewClicked'");
    target.mLlTaggleClose = Utils.castView(view, R.id.ll_taggle_close_fragment_login, "field 'mLlTaggleClose'", LinearLayout.class);
    view7f08016f = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.ll_taggle_open_fragment_login, "field 'mLlTaggleOpen' and method 'onViewClicked'");
    target.mLlTaggleOpen = Utils.castView(view, R.id.ll_taggle_open_fragment_login, "field 'mLlTaggleOpen'", LinearLayout.class);
    view7f080171 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.tv_forget_password_fragment_login, "field 'mTvForgetPassword' and method 'onViewClicked'");
    target.mTvForgetPassword = Utils.castView(view, R.id.tv_forget_password_fragment_login, "field 'mTvForgetPassword'", AppCompatButton.class);
    view7f080279 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.btn_submit_fragment_login, "field 'mBtnSubmit' and method 'onViewClicked'");
    target.mBtnSubmit = Utils.castView(view, R.id.btn_submit_fragment_login, "field 'mBtnSubmit'", AppCompatButton.class);
    view7f08004f = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.tv_regist_new_user_fragment_login, "field 'mTvRegistNewUser' and method 'onViewClicked'");
    target.mTvRegistNewUser = Utils.castView(view, R.id.tv_regist_new_user_fragment_login, "field 'mTvRegistNewUser'", AppCompatTextView.class);
    view7f0802a7 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.progressbar = Utils.findRequiredViewAsType(source, R.id.progress_fragment_login, "field 'progressbar'", ProgressbarLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    LoginFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mEtTel = null;
    target.mEtPassword = null;
    target.mLlTaggleClose = null;
    target.mLlTaggleOpen = null;
    target.mTvForgetPassword = null;
    target.mBtnSubmit = null;
    target.mTvRegistNewUser = null;
    target.progressbar = null;

    view7f08016f.setOnClickListener(null);
    view7f08016f = null;
    view7f080171.setOnClickListener(null);
    view7f080171 = null;
    view7f080279.setOnClickListener(null);
    view7f080279 = null;
    view7f08004f.setOnClickListener(null);
    view7f08004f = null;
    view7f0802a7.setOnClickListener(null);
    view7f0802a7 = null;
  }
}
