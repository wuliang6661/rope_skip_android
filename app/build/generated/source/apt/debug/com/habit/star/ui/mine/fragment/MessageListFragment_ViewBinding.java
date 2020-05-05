// Generated code from Butter Knife. Do not modify!
package com.habit.star.ui.mine.fragment;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.habit.commonlibrary.widget.ProgressbarLayout;
import com.habit.commonlibrary.widget.ToolbarWithBackRightProgress;
import com.habit.star.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class MessageListFragment_ViewBinding implements Unbinder {
  private MessageListFragment target;

  @UiThread
  public MessageListFragment_ViewBinding(MessageListFragment target, View source) {
    this.target = target;

    target.mRecyclerView = Utils.findRequiredViewAsType(source, R.id.rv_layout_swipe_to_refresh, "field 'mRecyclerView'", RecyclerView.class);
    target.mSwipeRefreshLayout = Utils.findRequiredViewAsType(source, R.id.swipeLayout_layout_swipe_to_refresh, "field 'mSwipeRefreshLayout'", SwipeRefreshLayout.class);
    target.progress = Utils.findRequiredViewAsType(source, R.id.progress_fragment_fragment_train_plan_list, "field 'progress'", ProgressbarLayout.class);
    target.toolbar = Utils.findRequiredViewAsType(source, R.id.toolbar_layout_toolbar, "field 'toolbar'", ToolbarWithBackRightProgress.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    MessageListFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mRecyclerView = null;
    target.mSwipeRefreshLayout = null;
    target.progress = null;
    target.toolbar = null;
  }
}
