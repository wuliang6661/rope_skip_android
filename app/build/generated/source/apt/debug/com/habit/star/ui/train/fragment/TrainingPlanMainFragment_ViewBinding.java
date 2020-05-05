// Generated code from Butter Knife. Do not modify!
package com.habit.star.ui.train.fragment;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.habit.commonlibrary.widget.HackyViewPager;
import com.habit.commonlibrary.widget.ProgressbarLayout;
import com.habit.commonlibrary.widget.ToolbarWithBackRightProgress;
import com.habit.star.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class TrainingPlanMainFragment_ViewBinding implements Unbinder {
  private TrainingPlanMainFragment target;

  @UiThread
  public TrainingPlanMainFragment_ViewBinding(TrainingPlanMainFragment target, View source) {
    this.target = target;

    target.toolbar = Utils.findRequiredViewAsType(source, R.id.toolbar_layout_toolbar, "field 'toolbar'", ToolbarWithBackRightProgress.class);
    target.progress = Utils.findRequiredViewAsType(source, R.id.progress_fragment_my_achivement, "field 'progress'", ProgressbarLayout.class);
    target.tabLayout = Utils.findRequiredViewAsType(source, R.id.tabs_fragment_my_achivement, "field 'tabLayout'", TabLayout.class);
    target.appbar = Utils.findRequiredViewAsType(source, R.id.appbar_fragment_my_achivement, "field 'appbar'", AppBarLayout.class);
    target.viewPager = Utils.findRequiredViewAsType(source, R.id.viewpager_fragment_my_achivement, "field 'viewPager'", HackyViewPager.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    TrainingPlanMainFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.toolbar = null;
    target.progress = null;
    target.tabLayout = null;
    target.appbar = null;
    target.viewPager = null;
  }
}
