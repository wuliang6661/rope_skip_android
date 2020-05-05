// Generated by Dagger (https://google.github.io/dagger).
package com.habit.star.ui.login.fragment;

import com.habit.star.base.BaseFragment_MembersInjector;
import com.habit.star.ui.login.presenter.PerfectInformationPresenter;
import dagger.MembersInjector;
import javax.inject.Provider;

public final class PerfectInformationFragment_MembersInjector
    implements MembersInjector<PerfectInformationFragment> {
  private final Provider<PerfectInformationPresenter> mPresenterProvider;

  public PerfectInformationFragment_MembersInjector(
      Provider<PerfectInformationPresenter> mPresenterProvider) {
    this.mPresenterProvider = mPresenterProvider;
  }

  public static MembersInjector<PerfectInformationFragment> create(
      Provider<PerfectInformationPresenter> mPresenterProvider) {
    return new PerfectInformationFragment_MembersInjector(mPresenterProvider);
  }

  @Override
  public void injectMembers(PerfectInformationFragment instance) {
    BaseFragment_MembersInjector.injectMPresenter(instance, mPresenterProvider.get());
  }
}
