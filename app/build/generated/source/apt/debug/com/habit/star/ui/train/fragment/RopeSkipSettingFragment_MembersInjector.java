// Generated by Dagger (https://google.github.io/dagger).
package com.habit.star.ui.train.fragment;

import com.habit.star.base.BaseFragment_MembersInjector;
import com.habit.star.ui.train.presenter.RoseSkipSettingPresenter;
import dagger.MembersInjector;
import javax.inject.Provider;

public final class RopeSkipSettingFragment_MembersInjector
    implements MembersInjector<RopeSkipSettingFragment> {
  private final Provider<RoseSkipSettingPresenter> mPresenterProvider;

  public RopeSkipSettingFragment_MembersInjector(
      Provider<RoseSkipSettingPresenter> mPresenterProvider) {
    this.mPresenterProvider = mPresenterProvider;
  }

  public static MembersInjector<RopeSkipSettingFragment> create(
      Provider<RoseSkipSettingPresenter> mPresenterProvider) {
    return new RopeSkipSettingFragment_MembersInjector(mPresenterProvider);
  }

  @Override
  public void injectMembers(RopeSkipSettingFragment instance) {
    BaseFragment_MembersInjector.injectMPresenter(instance, mPresenterProvider.get());
  }
}
