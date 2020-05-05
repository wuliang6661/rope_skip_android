// Generated code from Butter Knife. Do not modify!
package com.habit.star.common.fragment;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.habit.commonlibrary.widget.ToolbarWithBackRightProgress;
import com.habit.star.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class EditContentFragment_ViewBinding implements Unbinder {
  private EditContentFragment target;

  private View view7f08004d;

  @UiThread
  public EditContentFragment_ViewBinding(final EditContentFragment target, View source) {
    this.target = target;

    View view;
    target.toolbar = Utils.findRequiredViewAsType(source, R.id.toolbar_layout_toolbar, "field 'toolbar'", ToolbarWithBackRightProgress.class);
    target.contentEt = Utils.findRequiredViewAsType(source, R.id.et_content_fragment_edit_content, "field 'contentEt'", EditText.class);
    target.clearBtn = Utils.findRequiredViewAsType(source, R.id.btn_clear_fragment_resource_attribute_edit, "field 'clearBtn'", Button.class);
    view = Utils.findRequiredView(source, R.id.btn_submit, "field 'submitBtn' and method 'setResultAndFinish'");
    target.submitBtn = Utils.castView(view, R.id.btn_submit, "field 'submitBtn'", AppCompatButton.class);
    view7f08004d = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.setResultAndFinish();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    EditContentFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.toolbar = null;
    target.contentEt = null;
    target.clearBtn = null;
    target.submitBtn = null;

    view7f08004d.setOnClickListener(null);
    view7f08004d = null;
  }
}
