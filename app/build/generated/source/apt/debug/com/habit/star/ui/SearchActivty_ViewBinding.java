// Generated code from Butter Knife. Do not modify!
package com.habit.star.ui;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.habit.star.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class SearchActivty_ViewBinding implements Unbinder {
  private SearchActivty target;

  private View view7f080205;

  @UiThread
  public SearchActivty_ViewBinding(SearchActivty target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public SearchActivty_ViewBinding(final SearchActivty target, View source) {
    this.target = target;

    View view;
    target.recycleView = Utils.findRequiredViewAsType(source, R.id.recycle_view, "field 'recycleView'", RecyclerView.class);
    view = Utils.findRequiredView(source, R.id.search_btn, "method 'search'");
    view7f080205 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.search();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    SearchActivty target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.recycleView = null;

    view7f080205.setOnClickListener(null);
    view7f080205 = null;
  }
}
