// Generated code from Butter Knife. Do not modify!
package com.habit.star.ui.mine.fragment;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.LinearLayout;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.habit.commonlibrary.widget.LilayItemClickableWithHeadImageTopDivider;
import com.habit.commonlibrary.widget.ToolbarWithBackRightProgress;
import com.habit.star.R;
import de.hdodenhof.circleimageview.CircleImageView;
import java.lang.IllegalStateException;
import java.lang.Override;

public class MineFragment_ViewBinding implements Unbinder {
  private MineFragment target;

  private View view7f080122;

  private View view7f0800e2;

  private View view7f0800e4;

  private View view7f0800ec;

  private View view7f080156;

  private View view7f080157;

  private View view7f0800f4;

  private View view7f0800f1;

  private View view7f0800de;

  private View view7f080042;

  @UiThread
  public MineFragment_ViewBinding(final MineFragment target, View source) {
    this.target = target;

    View view;
    target.toolbar = Utils.findRequiredViewAsType(source, R.id.toolbar_layout_toolbar, "field 'toolbar'", ToolbarWithBackRightProgress.class);
    view = Utils.findRequiredView(source, R.id.iv_user_header_fragment_mine, "field 'mIvUserHeader' and method 'onViewClicked'");
    target.mIvUserHeader = Utils.castView(view, R.id.iv_user_header_fragment_mine, "field 'mIvUserHeader'", CircleImageView.class);
    view7f080122 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.item_device_fragment_mine, "field 'mItemDevice' and method 'onViewClicked'");
    target.mItemDevice = Utils.castView(view, R.id.item_device_fragment_mine, "field 'mItemDevice'", LilayItemClickableWithHeadImageTopDivider.class);
    view7f0800e2 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.item_jtcy_fragment_mine, "field 'mItemJtcy' and method 'onViewClicked'");
    target.mItemJtcy = Utils.castView(view, R.id.item_jtcy_fragment_mine, "field 'mItemJtcy'", LilayItemClickableWithHeadImageTopDivider.class);
    view7f0800e4 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.item_shdd_fragment_mine, "field 'mItemShdd' and method 'onViewClicked'");
    target.mItemShdd = Utils.castView(view, R.id.item_shdd_fragment_mine, "field 'mItemShdd'", LilayItemClickableWithHeadImageTopDivider.class);
    view7f0800ec = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.ll_msg_close_fragment_mine, "field 'mLlMsgClose' and method 'onViewClicked'");
    target.mLlMsgClose = Utils.castView(view, R.id.ll_msg_close_fragment_mine, "field 'mLlMsgClose'", LinearLayout.class);
    view7f080156 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.ll_msg_open_fragment_mine, "field 'mLlMsgOpen' and method 'onViewClicked'");
    target.mLlMsgOpen = Utils.castView(view, R.id.ll_msg_open_fragment_mine, "field 'mLlMsgOpen'", LinearLayout.class);
    view7f080157 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.item_yqhy_fragment_mine, "field 'mItemYqhy' and method 'onViewClicked'");
    target.mItemYqhy = Utils.castView(view, R.id.item_yqhy_fragment_mine, "field 'mItemYqhy'", LilayItemClickableWithHeadImageTopDivider.class);
    view7f0800f4 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.item_xtsz_fragment_mine, "field 'mItemXtsz' and method 'onViewClicked'");
    target.mItemXtsz = Utils.castView(view, R.id.item_xtsz_fragment_mine, "field 'mItemXtsz'", LilayItemClickableWithHeadImageTopDivider.class);
    view7f0800f1 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.item_bzzx_fragment_mine, "field 'mItemBzzx' and method 'onViewClicked'");
    target.mItemBzzx = Utils.castView(view, R.id.item_bzzx_fragment_mine, "field 'mItemBzzx'", LilayItemClickableWithHeadImageTopDivider.class);
    view7f0800de = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.btn_exit_login_fragment_mine, "field 'mBtnExit' and method 'onViewClicked'");
    target.mBtnExit = Utils.castView(view, R.id.btn_exit_login_fragment_mine, "field 'mBtnExit'", AppCompatTextView.class);
    view7f080042 = view;
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
    MineFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.toolbar = null;
    target.mIvUserHeader = null;
    target.mItemDevice = null;
    target.mItemJtcy = null;
    target.mItemShdd = null;
    target.mLlMsgClose = null;
    target.mLlMsgOpen = null;
    target.mItemYqhy = null;
    target.mItemXtsz = null;
    target.mItemBzzx = null;
    target.mBtnExit = null;

    view7f080122.setOnClickListener(null);
    view7f080122 = null;
    view7f0800e2.setOnClickListener(null);
    view7f0800e2 = null;
    view7f0800e4.setOnClickListener(null);
    view7f0800e4 = null;
    view7f0800ec.setOnClickListener(null);
    view7f0800ec = null;
    view7f080156.setOnClickListener(null);
    view7f080156 = null;
    view7f080157.setOnClickListener(null);
    view7f080157 = null;
    view7f0800f4.setOnClickListener(null);
    view7f0800f4 = null;
    view7f0800f1.setOnClickListener(null);
    view7f0800f1 = null;
    view7f0800de.setOnClickListener(null);
    view7f0800de = null;
    view7f080042.setOnClickListener(null);
    view7f080042 = null;
  }
}
