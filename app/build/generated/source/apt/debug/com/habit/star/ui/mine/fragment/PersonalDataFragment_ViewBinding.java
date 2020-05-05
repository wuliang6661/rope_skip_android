// Generated code from Butter Knife. Do not modify!
package com.habit.star.ui.mine.fragment;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.LinearLayout;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.habit.commonlibrary.widget.LilayItemClickableWithHeadImageTopDivider;
import com.habit.commonlibrary.widget.ProgressbarLayout;
import com.habit.commonlibrary.widget.ToolbarWithBackRightProgress;
import com.habit.star.R;
import de.hdodenhof.circleimageview.CircleImageView;
import java.lang.IllegalStateException;
import java.lang.Override;

public class PersonalDataFragment_ViewBinding implements Unbinder {
  private PersonalDataFragment target;

  private View view7f0800f0;

  private View view7f0800ed;

  private View view7f08004e;

  private View view7f080108;

  private View view7f0802b9;

  private View view7f08014d;

  @UiThread
  public PersonalDataFragment_ViewBinding(final PersonalDataFragment target, View source) {
    this.target = target;

    View view;
    target.toolbar = Utils.findRequiredViewAsType(source, R.id.toolbar_layout_toolbar, "field 'toolbar'", ToolbarWithBackRightProgress.class);
    target.progress = Utils.findRequiredViewAsType(source, R.id.progress_fragment_personal_data, "field 'progress'", ProgressbarLayout.class);
    view = Utils.findRequiredView(source, R.id.item_tz_fragment_perfect_information, "field 'itemTzFragmentPerfectInformation' and method 'onViewClicked'");
    target.itemTzFragmentPerfectInformation = Utils.castView(view, R.id.item_tz_fragment_perfect_information, "field 'itemTzFragmentPerfectInformation'", LilayItemClickableWithHeadImageTopDivider.class);
    view7f0800f0 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.item_shoujihaoma_fragment_perfect_information, "field 'itemShoujihaomaFragmentPerfectInformation' and method 'onViewClicked'");
    target.itemShoujihaomaFragmentPerfectInformation = Utils.castView(view, R.id.item_shoujihaoma_fragment_perfect_information, "field 'itemShoujihaomaFragmentPerfectInformation'", LilayItemClickableWithHeadImageTopDivider.class);
    view7f0800ed = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.btn_submit_fragment_feed_back, "field 'btnSubmitFragmentFeedBack' and method 'onViewClicked'");
    target.btnSubmitFragmentFeedBack = Utils.castView(view, R.id.btn_submit_fragment_feed_back, "field 'btnSubmitFragmentFeedBack'", AppCompatButton.class);
    view7f08004e = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.iv_head_fragment_personal_data, "field 'ivHeadFragmentPersonalData' and method 'onViewClicked'");
    target.ivHeadFragmentPersonalData = Utils.castView(view, R.id.iv_head_fragment_personal_data, "field 'ivHeadFragmentPersonalData'", CircleImageView.class);
    view7f080108 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.tv_title_fragment_personal_data, "field 'tvTitleFragmentPersonalData' and method 'onViewClicked'");
    target.tvTitleFragmentPersonalData = Utils.castView(view, R.id.tv_title_fragment_personal_data, "field 'tvTitleFragmentPersonalData'", AppCompatTextView.class);
    view7f0802b9 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.ll_head_layout_fragment_personal_data, "field 'llHeadLayoutFragmentPersonalData' and method 'onViewClicked'");
    target.llHeadLayoutFragmentPersonalData = Utils.castView(view, R.id.ll_head_layout_fragment_personal_data, "field 'llHeadLayoutFragmentPersonalData'", LinearLayout.class);
    view7f08014d = view;
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
    PersonalDataFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.toolbar = null;
    target.progress = null;
    target.itemTzFragmentPerfectInformation = null;
    target.itemShoujihaomaFragmentPerfectInformation = null;
    target.btnSubmitFragmentFeedBack = null;
    target.ivHeadFragmentPersonalData = null;
    target.tvTitleFragmentPersonalData = null;
    target.llHeadLayoutFragmentPersonalData = null;

    view7f0800f0.setOnClickListener(null);
    view7f0800f0 = null;
    view7f0800ed.setOnClickListener(null);
    view7f0800ed = null;
    view7f08004e.setOnClickListener(null);
    view7f08004e = null;
    view7f080108.setOnClickListener(null);
    view7f080108 = null;
    view7f0802b9.setOnClickListener(null);
    view7f0802b9 = null;
    view7f08014d.setOnClickListener(null);
    view7f08014d = null;
  }
}
