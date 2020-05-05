// Generated code from Butter Knife. Do not modify!
package com.habit.star.ui.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.LinearLayout;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.habit.commonlibrary.widget.NoScrollViewPager;
import com.habit.star.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class MainActivity_ViewBinding implements Unbinder {
  private MainActivity target;

  private View view7f08016b;

  private View view7f08016c;

  private View view7f08016d;

  private View view7f08016e;

  @UiThread
  public MainActivity_ViewBinding(MainActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public MainActivity_ViewBinding(final MainActivity target, View source) {
    this.target = target;

    View view;
    target.fragmentVp = Utils.findRequiredViewAsType(source, R.id.fragment_vp, "field 'fragmentVp'", NoScrollViewPager.class);
    target.ivTabItem1 = Utils.findRequiredViewAsType(source, R.id.iv_tab_item1, "field 'ivTabItem1'", AppCompatImageView.class);
    target.tvXunlianItemBottom = Utils.findRequiredViewAsType(source, R.id.tv_xunlian_item_bottom, "field 'tvXunlianItemBottom'", AppCompatTextView.class);
    view = Utils.findRequiredView(source, R.id.ll_tab_item1, "field 'llTabItem1' and method 'onViewClicked'");
    target.llTabItem1 = Utils.castView(view, R.id.ll_tab_item1, "field 'llTabItem1'", LinearLayout.class);
    view7f08016b = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.ivTabItem2 = Utils.findRequiredViewAsType(source, R.id.iv_tab_item2, "field 'ivTabItem2'", AppCompatImageView.class);
    target.tvXiaojiangItemBottom = Utils.findRequiredViewAsType(source, R.id.tv_xiaojiang_item_bottom, "field 'tvXiaojiangItemBottom'", AppCompatTextView.class);
    view = Utils.findRequiredView(source, R.id.ll_tab_item2, "field 'llTabItem2' and method 'onViewClicked'");
    target.llTabItem2 = Utils.castView(view, R.id.ll_tab_item2, "field 'llTabItem2'", LinearLayout.class);
    view7f08016c = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.ivTabItem3 = Utils.findRequiredViewAsType(source, R.id.iv_tab_item3, "field 'ivTabItem3'", AppCompatImageView.class);
    target.tvFindItemBottom = Utils.findRequiredViewAsType(source, R.id.tv_find_item_bottom, "field 'tvFindItemBottom'", AppCompatTextView.class);
    view = Utils.findRequiredView(source, R.id.ll_tab_item3, "field 'llTabItem3' and method 'onViewClicked'");
    target.llTabItem3 = Utils.castView(view, R.id.ll_tab_item3, "field 'llTabItem3'", LinearLayout.class);
    view7f08016d = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.ivTabItem4 = Utils.findRequiredViewAsType(source, R.id.iv_tab_item4, "field 'ivTabItem4'", AppCompatImageView.class);
    target.tvMineItemBottom = Utils.findRequiredViewAsType(source, R.id.tv_mine_item_bottom, "field 'tvMineItemBottom'", AppCompatTextView.class);
    view = Utils.findRequiredView(source, R.id.ll_tab_item4, "field 'llTabItem4' and method 'onViewClicked'");
    target.llTabItem4 = Utils.castView(view, R.id.ll_tab_item4, "field 'llTabItem4'", LinearLayout.class);
    view7f08016e = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.llTab = Utils.findRequiredViewAsType(source, R.id.ll_tab, "field 'llTab'", LinearLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    MainActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.fragmentVp = null;
    target.ivTabItem1 = null;
    target.tvXunlianItemBottom = null;
    target.llTabItem1 = null;
    target.ivTabItem2 = null;
    target.tvXiaojiangItemBottom = null;
    target.llTabItem2 = null;
    target.ivTabItem3 = null;
    target.tvFindItemBottom = null;
    target.llTabItem3 = null;
    target.ivTabItem4 = null;
    target.tvMineItemBottom = null;
    target.llTabItem4 = null;
    target.llTab = null;

    view7f08016b.setOnClickListener(null);
    view7f08016b = null;
    view7f08016c.setOnClickListener(null);
    view7f08016c = null;
    view7f08016d.setOnClickListener(null);
    view7f08016d = null;
    view7f08016e.setOnClickListener(null);
    view7f08016e = null;
  }
}
