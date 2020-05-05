// Generated code from Butter Knife. Do not modify!
package com.habit.star.ui.mine.fragment;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
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

public class MyAddressListFragment_ViewBinding implements Unbinder {
  private MyAddressListFragment target;

  private View view7f08013b;

  @UiThread
  public MyAddressListFragment_ViewBinding(final MyAddressListFragment target, View source) {
    this.target = target;

    View view;
    target.toolbar = Utils.findRequiredViewAsType(source, R.id.toolbar_layout_toolbar, "field 'toolbar'", ToolbarWithBackRightProgress.class);
    target.progress = Utils.findRequiredViewAsType(source, R.id.progress_fragment_my_address_list, "field 'progress'", ProgressbarLayout.class);
    target.rvAddressContent = Utils.findRequiredViewAsType(source, R.id.rv_address_content_fragment_my_address_list, "field 'rvAddressContent'", RecyclerView.class);
    view = Utils.findRequiredView(source, R.id.ll_btn_submit_fragment_feed_back, "field 'llBtnSubmitFragmentFeedBack' and method 'onViewClicked'");
    target.llBtnSubmitFragmentFeedBack = Utils.castView(view, R.id.ll_btn_submit_fragment_feed_back, "field 'llBtnSubmitFragmentFeedBack'", LinearLayout.class);
    view7f08013b = view;
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
    MyAddressListFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.toolbar = null;
    target.progress = null;
    target.rvAddressContent = null;
    target.llBtnSubmitFragmentFeedBack = null;

    view7f08013b.setOnClickListener(null);
    view7f08013b = null;
  }
}
