// Generated code from Butter Knife. Do not modify!
package com.habit.star.ui.mine.fragment;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.LinearLayout;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.habit.commonlibrary.widget.ProgressbarLayout;
import com.habit.commonlibrary.widget.ToolbarWithBackRightProgress;
import com.habit.star.R;
import de.hdodenhof.circleimageview.CircleImageView;
import java.lang.IllegalStateException;
import java.lang.Override;

public class FamilyMemberDetailFragment_ViewBinding implements Unbinder {
  private FamilyMemberDetailFragment target;

  private View view7f0802c5;

  private View view7f0802c6;

  private View view7f0802c8;

  private View view7f0802c9;

  @UiThread
  public FamilyMemberDetailFragment_ViewBinding(final FamilyMemberDetailFragment target,
      View source) {
    this.target = target;

    View view;
    target.toolbar = Utils.findRequiredViewAsType(source, R.id.toolbar_layout_toolbar, "field 'toolbar'", ToolbarWithBackRightProgress.class);
    target.progress = Utils.findRequiredViewAsType(source, R.id.progress_fragment_family_member_detail, "field 'progress'", ProgressbarLayout.class);
    target.tvNameFragmentFamilyMemberDetail = Utils.findRequiredViewAsType(source, R.id.tv_name_fragment_family_member_detail, "field 'tvNameFragmentFamilyMemberDetail'", AppCompatTextView.class);
    target.ivSexFragmentFamilyMemberDetail = Utils.findRequiredViewAsType(source, R.id.iv_sex_fragment_family_member_detail, "field 'ivSexFragmentFamilyMemberDetail'", AppCompatImageView.class);
    target.ivHeadFragmentFamilyMemberDetail = Utils.findRequiredViewAsType(source, R.id.iv_head_fragment_family_member_detail, "field 'ivHeadFragmentFamilyMemberDetail'", CircleImageView.class);
    target.tvShengGaoFragmentFamilyMemberDetail = Utils.findRequiredViewAsType(source, R.id.tv_sheng_gao_fragment_family_member_detail, "field 'tvShengGaoFragmentFamilyMemberDetail'", AppCompatTextView.class);
    target.tvTiZhongFragmentFamilyMemberDetail = Utils.findRequiredViewAsType(source, R.id.tv_ti_zhong_fragment_family_member_detail, "field 'tvTiZhongFragmentFamilyMemberDetail'", AppCompatTextView.class);
    target.tvNianLingFragmentFamilyMemberDetail = Utils.findRequiredViewAsType(source, R.id.tv_nian_ling_fragment_family_member_detail, "field 'tvNianLingFragmentFamilyMemberDetail'", AppCompatTextView.class);
    target.tvTiaoShengDataFragmentFamilyMemberDetail = Utils.findRequiredViewAsType(source, R.id.tv_tiao_sheng_data_fragment_family_member_detail, "field 'tvTiaoShengDataFragmentFamilyMemberDetail'", AppCompatTextView.class);
    view = Utils.findRequiredView(source, R.id.tv_ts1_last_fragment_family_member_detail, "field 'tvTs1LastFragmentFamilyMemberDetail' and method 'onViewClicked'");
    target.tvTs1LastFragmentFamilyMemberDetail = Utils.castView(view, R.id.tv_ts1_last_fragment_family_member_detail, "field 'tvTs1LastFragmentFamilyMemberDetail'", AppCompatTextView.class);
    view7f0802c5 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.llPreDayFragmentFamilyMemberDetail = Utils.findRequiredViewAsType(source, R.id.ll_pre_day_fragment_family_member_detail, "field 'llPreDayFragmentFamilyMemberDetail'", LinearLayout.class);
    view = Utils.findRequiredView(source, R.id.tv_ts1_next_fragment_family_member_detail, "field 'tvTs1NextFragmentFamilyMemberDetail' and method 'onViewClicked'");
    target.tvTs1NextFragmentFamilyMemberDetail = Utils.castView(view, R.id.tv_ts1_next_fragment_family_member_detail, "field 'tvTs1NextFragmentFamilyMemberDetail'", AppCompatTextView.class);
    view7f0802c6 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.llNextDayFragmentFamilyMemberDetail = Utils.findRequiredViewAsType(source, R.id.ll_next_day_fragment_family_member_detail, "field 'llNextDayFragmentFamilyMemberDetail'", LinearLayout.class);
    target.tvTsTotalFragmentFamilyMemberDetail = Utils.findRequiredViewAsType(source, R.id.tv_ts_total_fragment_family_member_detail, "field 'tvTsTotalFragmentFamilyMemberDetail'", AppCompatTextView.class);
    target.tvTsTotalTimeFragmentFamilyMemberDetail = Utils.findRequiredViewAsType(source, R.id.tv_ts_total_time_fragment_family_member_detail, "field 'tvTsTotalTimeFragmentFamilyMemberDetail'", AppCompatTextView.class);
    target.tvTsNianLingFragmentFamilyMemberDetail = Utils.findRequiredViewAsType(source, R.id.tv_ts_nian_ling_fragment_family_member_detail, "field 'tvTsNianLingFragmentFamilyMemberDetail'", AppCompatTextView.class);
    target.tvTiaoShengData2FragmentFamilyMemberDetail = Utils.findRequiredViewAsType(source, R.id.tv_tiao_sheng_data2_fragment_family_member_detail, "field 'tvTiaoShengData2FragmentFamilyMemberDetail'", AppCompatTextView.class);
    view = Utils.findRequiredView(source, R.id.tv_ts_last_fragment_family_member_detail, "field 'tvTsLastFragmentFamilyMemberDetail' and method 'onViewClicked'");
    target.tvTsLastFragmentFamilyMemberDetail = Utils.castView(view, R.id.tv_ts_last_fragment_family_member_detail, "field 'tvTsLastFragmentFamilyMemberDetail'", AppCompatTextView.class);
    view7f0802c8 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.llPreDay2FragmentFamilyMemberDetail = Utils.findRequiredViewAsType(source, R.id.ll_pre_day2_fragment_family_member_detail, "field 'llPreDay2FragmentFamilyMemberDetail'", LinearLayout.class);
    view = Utils.findRequiredView(source, R.id.tv_ts_next_fragment_family_member_detail, "field 'tvTsNextFragmentFamilyMemberDetail' and method 'onViewClicked'");
    target.tvTsNextFragmentFamilyMemberDetail = Utils.castView(view, R.id.tv_ts_next_fragment_family_member_detail, "field 'tvTsNextFragmentFamilyMemberDetail'", AppCompatTextView.class);
    view7f0802c9 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.llNextDay2FragmentFamilyMemberDetail = Utils.findRequiredViewAsType(source, R.id.ll_next_day2_fragment_family_member_detail, "field 'llNextDay2FragmentFamilyMemberDetail'", LinearLayout.class);
    target.tvTsTotal2FragmentFamilyMemberDetail = Utils.findRequiredViewAsType(source, R.id.tv_ts_total2_fragment_family_member_detail, "field 'tvTsTotal2FragmentFamilyMemberDetail'", AppCompatTextView.class);
    target.tvTsTotalTime2FragmentFamilyMemberDetail = Utils.findRequiredViewAsType(source, R.id.tv_ts_total_time2_fragment_family_member_detail, "field 'tvTsTotalTime2FragmentFamilyMemberDetail'", AppCompatTextView.class);
    target.tvTsNianLing2FragmentFamilyMemberDetail = Utils.findRequiredViewAsType(source, R.id.tv_ts_nian_ling2_fragment_family_member_detail, "field 'tvTsNianLing2FragmentFamilyMemberDetail'", AppCompatTextView.class);
    target.tvTs1DayFragmentFamilyMemberDetail = Utils.findRequiredViewAsType(source, R.id.tv_ts1_day_fragment_family_member_detail, "field 'tvTs1DayFragmentFamilyMemberDetail'", AppCompatTextView.class);
    target.tvTsDayFragmentFamilyMemberDetail = Utils.findRequiredViewAsType(source, R.id.tv_ts_day_fragment_family_member_detail, "field 'tvTsDayFragmentFamilyMemberDetail'", AppCompatTextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    FamilyMemberDetailFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.toolbar = null;
    target.progress = null;
    target.tvNameFragmentFamilyMemberDetail = null;
    target.ivSexFragmentFamilyMemberDetail = null;
    target.ivHeadFragmentFamilyMemberDetail = null;
    target.tvShengGaoFragmentFamilyMemberDetail = null;
    target.tvTiZhongFragmentFamilyMemberDetail = null;
    target.tvNianLingFragmentFamilyMemberDetail = null;
    target.tvTiaoShengDataFragmentFamilyMemberDetail = null;
    target.tvTs1LastFragmentFamilyMemberDetail = null;
    target.llPreDayFragmentFamilyMemberDetail = null;
    target.tvTs1NextFragmentFamilyMemberDetail = null;
    target.llNextDayFragmentFamilyMemberDetail = null;
    target.tvTsTotalFragmentFamilyMemberDetail = null;
    target.tvTsTotalTimeFragmentFamilyMemberDetail = null;
    target.tvTsNianLingFragmentFamilyMemberDetail = null;
    target.tvTiaoShengData2FragmentFamilyMemberDetail = null;
    target.tvTsLastFragmentFamilyMemberDetail = null;
    target.llPreDay2FragmentFamilyMemberDetail = null;
    target.tvTsNextFragmentFamilyMemberDetail = null;
    target.llNextDay2FragmentFamilyMemberDetail = null;
    target.tvTsTotal2FragmentFamilyMemberDetail = null;
    target.tvTsTotalTime2FragmentFamilyMemberDetail = null;
    target.tvTsNianLing2FragmentFamilyMemberDetail = null;
    target.tvTs1DayFragmentFamilyMemberDetail = null;
    target.tvTsDayFragmentFamilyMemberDetail = null;

    view7f0802c5.setOnClickListener(null);
    view7f0802c5 = null;
    view7f0802c6.setOnClickListener(null);
    view7f0802c6 = null;
    view7f0802c8.setOnClickListener(null);
    view7f0802c8 = null;
    view7f0802c9.setOnClickListener(null);
    view7f0802c9 = null;
  }
}
