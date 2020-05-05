// Generated code from Butter Knife. Do not modify!
package com.habit.star.ui.mine.fragment;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import android.widget.CheckBox;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.habit.commonlibrary.widget.ProgressbarLayout;
import com.habit.commonlibrary.widget.ToolbarWithBackRightProgress;
import com.habit.star.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class AddAddressFragment_ViewBinding implements Unbinder {
  private AddAddressFragment target;

  @UiThread
  public AddAddressFragment_ViewBinding(AddAddressFragment target, View source) {
    this.target = target;

    target.toolbar = Utils.findRequiredViewAsType(source, R.id.toolbar_layout_toolbar, "field 'toolbar'", ToolbarWithBackRightProgress.class);
    target.progress = Utils.findRequiredViewAsType(source, R.id.progress_fragment_add_address, "field 'progress'", ProgressbarLayout.class);
    target.etNameFragmentAddAddress = Utils.findRequiredViewAsType(source, R.id.et_name_fragment_add_address, "field 'etNameFragmentAddAddress'", AppCompatEditText.class);
    target.etTelFragmentAddAddress = Utils.findRequiredViewAsType(source, R.id.et_tel_fragment_add_address, "field 'etTelFragmentAddAddress'", AppCompatEditText.class);
    target.cbMrFragmentAddAddress = Utils.findRequiredViewAsType(source, R.id.cb_mr_fragment_add_address, "field 'cbMrFragmentAddAddress'", CheckBox.class);
    target.btnSaveFragmentAddAddress = Utils.findRequiredViewAsType(source, R.id.btn_save_fragment_add_address, "field 'btnSaveFragmentAddAddress'", AppCompatButton.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    AddAddressFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.toolbar = null;
    target.progress = null;
    target.etNameFragmentAddAddress = null;
    target.etTelFragmentAddAddress = null;
    target.cbMrFragmentAddAddress = null;
    target.btnSaveFragmentAddAddress = null;
  }
}
