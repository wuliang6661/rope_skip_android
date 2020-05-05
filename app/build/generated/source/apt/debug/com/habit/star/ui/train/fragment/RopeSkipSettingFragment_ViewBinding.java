// Generated code from Butter Knife. Do not modify!
package com.habit.star.ui.train.fragment;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
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

public class RopeSkipSettingFragment_ViewBinding implements Unbinder {
  private RopeSkipSettingFragment target;

  private View view7f080139;

  private View view7f080153;

  private View view7f080048;

  @UiThread
  public RopeSkipSettingFragment_ViewBinding(final RopeSkipSettingFragment target, View source) {
    this.target = target;

    View view;
    target.toolbar = Utils.findRequiredViewAsType(source, R.id.toolbar_layout_toolbar, "field 'toolbar'", ToolbarWithBackRightProgress.class);
    target.progress = Utils.findRequiredViewAsType(source, R.id.progress_fragment_common_view, "field 'progress'", ProgressbarLayout.class);
    target.tvBgMusicName = Utils.findRequiredViewAsType(source, R.id.tv_bg_music_name_fragment_rope_skip_setting, "field 'tvBgMusicName'", AppCompatTextView.class);
    view = Utils.findRequiredView(source, R.id.ll_bg_music_fragment_rope_skip_setting, "field 'llBgMusic' and method 'onViewClicked'");
    target.llBgMusic = Utils.castView(view, R.id.ll_bg_music_fragment_rope_skip_setting, "field 'llBgMusic'", LinearLayout.class);
    view7f080139 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.tvJzName = Utils.findRequiredViewAsType(source, R.id.tv_jz_name_fragment_rope_skip_setting, "field 'tvJzName'", AppCompatTextView.class);
    view = Utils.findRequiredView(source, R.id.ll_jz_fragment_rope_skip_setting, "field 'llJz' and method 'onViewClicked'");
    target.llJz = Utils.castView(view, R.id.ll_jz_fragment_rope_skip_setting, "field 'llJz'", LinearLayout.class);
    view7f080153 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.btn_save_fragment_rope_skip_setting, "field 'btnSave' and method 'onViewClicked'");
    target.btnSave = Utils.castView(view, R.id.btn_save_fragment_rope_skip_setting, "field 'btnSave'", AppCompatButton.class);
    view7f080048 = view;
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
    RopeSkipSettingFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.toolbar = null;
    target.progress = null;
    target.tvBgMusicName = null;
    target.llBgMusic = null;
    target.tvJzName = null;
    target.llJz = null;
    target.btnSave = null;

    view7f080139.setOnClickListener(null);
    view7f080139 = null;
    view7f080153.setOnClickListener(null);
    view7f080153 = null;
    view7f080048.setOnClickListener(null);
    view7f080048 = null;
  }
}
