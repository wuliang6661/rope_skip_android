// Generated code from Butter Knife. Do not modify!
package com.habit.star.ui.mine.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.FrameLayout;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.habit.star.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class MineMainActivity_ViewBinding implements Unbinder {
  private MineMainActivity target;

  @UiThread
  public MineMainActivity_ViewBinding(MineMainActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public MineMainActivity_ViewBinding(MineMainActivity target, View source) {
    this.target = target;

    target.containerFrame = Utils.findRequiredViewAsType(source, R.id.frame_container_activity_mine_main, "field 'containerFrame'", FrameLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    MineMainActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.containerFrame = null;
  }
}
