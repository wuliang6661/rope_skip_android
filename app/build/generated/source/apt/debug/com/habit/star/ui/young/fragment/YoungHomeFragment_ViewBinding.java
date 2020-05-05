// Generated code from Butter Knife. Do not modify!
package com.habit.star.ui.young.fragment;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.habit.commonlibrary.widget.ProgressbarLayout;
import com.habit.commonlibrary.widget.ToolbarWithBackRightProgress;
import com.habit.star.R;
import de.hdodenhof.circleimageview.CircleImageView;
import java.lang.IllegalStateException;
import java.lang.Override;
import pl.droidsonroids.gif.GifImageView;

public class YoungHomeFragment_ViewBinding implements Unbinder {
  private YoungHomeFragment target;

  private View view7f0800cb;

  private View view7f0800cd;

  private View view7f0800cc;

  private View view7f080104;

  private View view7f08027e;

  private View view7f08027c;

  private View view7f08027d;

  private View view7f08027b;

  private View view7f08027a;

  private View view7f08015a;

  private View view7f080142;

  @UiThread
  public YoungHomeFragment_ViewBinding(final YoungHomeFragment target, View source) {
    this.target = target;

    View view;
    target.progress = Utils.findRequiredViewAsType(source, R.id.progress_fragment_common_view, "field 'progress'", ProgressbarLayout.class);
    target.toolbarLayoutToolbar = Utils.findRequiredViewAsType(source, R.id.toolbar_layout_toolbar, "field 'toolbarLayoutToolbar'", ToolbarWithBackRightProgress.class);
    target.ivUserHeaderFragmentYoungHome = Utils.findRequiredViewAsType(source, R.id.iv_user_header_fragment_young_home, "field 'ivUserHeaderFragmentYoungHome'", CircleImageView.class);
    view = Utils.findRequiredView(source, R.id.ic_cj_fragment_young_home, "field 'icCjFragmentYoungHome' and method 'onViewClicked'");
    target.icCjFragmentYoungHome = Utils.castView(view, R.id.ic_cj_fragment_young_home, "field 'icCjFragmentYoungHome'", AppCompatImageView.class);
    view7f0800cb = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.ic_rw_fragment_young_home, "field 'icRwFragmentYoungHome' and method 'onViewClicked'");
    target.icRwFragmentYoungHome = Utils.castView(view, R.id.ic_rw_fragment_young_home, "field 'icRwFragmentYoungHome'", AppCompatImageView.class);
    view7f0800cd = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.ic_rw2_fragment_young_home, "field 'icRw2FragmentYoungHome' and method 'onViewClicked'");
    target.icRw2FragmentYoungHome = Utils.castView(view, R.id.ic_rw2_fragment_young_home, "field 'icRw2FragmentYoungHome'", AppCompatImageView.class);
    view7f0800cc = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.iv_close_bottom_fragment_young_home, "field 'ivCloseBottomFragmentYoungHome' and method 'onViewClicked'");
    target.ivCloseBottomFragmentYoungHome = Utils.castView(view, R.id.iv_close_bottom_fragment_young_home, "field 'ivCloseBottomFragmentYoungHome'", AppCompatImageView.class);
    view7f080104 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.tvRwLableFragmentYoungHome = Utils.findRequiredViewAsType(source, R.id.tv_rw_lable_fragment_young_home, "field 'tvRwLableFragmentYoungHome'", AppCompatTextView.class);
    target.dividerRwLableFragmentYoungHome = Utils.findRequiredView(source, R.id.divider_rw_lable_fragment_young_home, "field 'dividerRwLableFragmentYoungHome'");
    view = Utils.findRequiredView(source, R.id.tv_go_tz_fragment_young_home, "field 'tvGoTzFragmentYoungHome' and method 'onViewClicked'");
    target.tvGoTzFragmentYoungHome = Utils.castView(view, R.id.tv_go_tz_fragment_young_home, "field 'tvGoTzFragmentYoungHome'", AppCompatTextView.class);
    view7f08027e = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.tv_go_read_fragment_young_home, "field 'tvGoReadFragmentYoungHome' and method 'onViewClicked'");
    target.tvGoReadFragmentYoungHome = Utils.castView(view, R.id.tv_go_read_fragment_young_home, "field 'tvGoReadFragmentYoungHome'", AppCompatTextView.class);
    view7f08027c = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.tv_go_study_fragment_young_home, "field 'tvGoStudyFragmentYoungHome' and method 'onViewClicked'");
    target.tvGoStudyFragmentYoungHome = Utils.castView(view, R.id.tv_go_study_fragment_young_home, "field 'tvGoStudyFragmentYoungHome'", AppCompatTextView.class);
    view7f08027d = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.tv_go_finish_fragment_young_home, "field 'tvGoFinishFragmentYoungHome' and method 'onViewClicked'");
    target.tvGoFinishFragmentYoungHome = Utils.castView(view, R.id.tv_go_finish_fragment_young_home, "field 'tvGoFinishFragmentYoungHome'", AppCompatTextView.class);
    view7f08027b = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.tv_go_cg_fragment_young_home, "field 'tvGoCgFragmentYoungHome' and method 'onViewClicked'");
    target.tvGoCgFragmentYoungHome = Utils.castView(view, R.id.tv_go_cg_fragment_young_home, "field 'tvGoCgFragmentYoungHome'", AppCompatTextView.class);
    view7f08027a = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.rlZrwBottomFragmentYoungHome = Utils.findRequiredViewAsType(source, R.id.rl_zrw_bottom_fragment_young_home, "field 'rlZrwBottomFragmentYoungHome'", RelativeLayout.class);
    target.llIconJsPk1FragmentYoungHome = Utils.findRequiredViewAsType(source, R.id.ll_icon_js_pk1_fragment_young_home, "field 'llIconJsPk1FragmentYoungHome'", LinearLayout.class);
    target.llIconJsPk2FragmentYoungHome = Utils.findRequiredViewAsType(source, R.id.ll_icon_js_pk2_fragment_young_home, "field 'llIconJsPk2FragmentYoungHome'", LinearLayout.class);
    target.llIconJsPk3FragmentYoungHome = Utils.findRequiredViewAsType(source, R.id.ll_icon_js_pk3_fragment_young_home, "field 'llIconJsPk3FragmentYoungHome'", LinearLayout.class);
    target.llIconJsPk4FragmentYoungHome = Utils.findRequiredViewAsType(source, R.id.ll_icon_js_pk4_fragment_young_home, "field 'llIconJsPk4FragmentYoungHome'", LinearLayout.class);
    target.llIconJsPk5FragmentYoungHome = Utils.findRequiredViewAsType(source, R.id.ll_icon_js_pk5_fragment_young_home, "field 'llIconJsPk5FragmentYoungHome'", LinearLayout.class);
    target.giViewMonkeyFragmentYoungHome = Utils.findRequiredViewAsType(source, R.id.gi_view_monkey_fragment_young_home, "field 'giViewMonkeyFragmentYoungHome'", GifImageView.class);
    target.tvNlz = Utils.findRequiredViewAsType(source, R.id.tv_nlz, "field 'tvNlz'", AppCompatTextView.class);
    view = Utils.findRequiredView(source, R.id.ll_nlz, "field 'llNlz' and method 'onViewClicked'");
    target.llNlz = Utils.castView(view, R.id.ll_nlz, "field 'llNlz'", LinearLayout.class);
    view7f08015a = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.tvCj = Utils.findRequiredViewAsType(source, R.id.tv_cj, "field 'tvCj'", AppCompatImageView.class);
    view = Utils.findRequiredView(source, R.id.ll_cj, "field 'llCj' and method 'onViewClicked'");
    target.llCj = Utils.castView(view, R.id.ll_cj, "field 'llCj'", LinearLayout.class);
    view7f080142 = view;
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
    YoungHomeFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.progress = null;
    target.toolbarLayoutToolbar = null;
    target.ivUserHeaderFragmentYoungHome = null;
    target.icCjFragmentYoungHome = null;
    target.icRwFragmentYoungHome = null;
    target.icRw2FragmentYoungHome = null;
    target.ivCloseBottomFragmentYoungHome = null;
    target.tvRwLableFragmentYoungHome = null;
    target.dividerRwLableFragmentYoungHome = null;
    target.tvGoTzFragmentYoungHome = null;
    target.tvGoReadFragmentYoungHome = null;
    target.tvGoStudyFragmentYoungHome = null;
    target.tvGoFinishFragmentYoungHome = null;
    target.tvGoCgFragmentYoungHome = null;
    target.rlZrwBottomFragmentYoungHome = null;
    target.llIconJsPk1FragmentYoungHome = null;
    target.llIconJsPk2FragmentYoungHome = null;
    target.llIconJsPk3FragmentYoungHome = null;
    target.llIconJsPk4FragmentYoungHome = null;
    target.llIconJsPk5FragmentYoungHome = null;
    target.giViewMonkeyFragmentYoungHome = null;
    target.tvNlz = null;
    target.llNlz = null;
    target.tvCj = null;
    target.llCj = null;

    view7f0800cb.setOnClickListener(null);
    view7f0800cb = null;
    view7f0800cd.setOnClickListener(null);
    view7f0800cd = null;
    view7f0800cc.setOnClickListener(null);
    view7f0800cc = null;
    view7f080104.setOnClickListener(null);
    view7f080104 = null;
    view7f08027e.setOnClickListener(null);
    view7f08027e = null;
    view7f08027c.setOnClickListener(null);
    view7f08027c = null;
    view7f08027d.setOnClickListener(null);
    view7f08027d = null;
    view7f08027b.setOnClickListener(null);
    view7f08027b = null;
    view7f08027a.setOnClickListener(null);
    view7f08027a = null;
    view7f08015a.setOnClickListener(null);
    view7f08015a = null;
    view7f080142.setOnClickListener(null);
    view7f080142 = null;
  }
}
