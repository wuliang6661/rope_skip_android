// Generated by Dagger (https://google.github.io/dagger).
package com.habit.star.ui.train.activity;

import com.habit.star.base.BaseActivity_MembersInjector;
import com.habit.star.ui.mine.presenter.MineMainPresenter;
import dagger.MembersInjector;
import javax.inject.Provider;

public final class TainMainActivity_MembersInjector implements MembersInjector<TainMainActivity> {
  private final Provider<MineMainPresenter> mPresenterProvider;

  public TainMainActivity_MembersInjector(Provider<MineMainPresenter> mPresenterProvider) {
    this.mPresenterProvider = mPresenterProvider;
  }

  public static MembersInjector<TainMainActivity> create(
      Provider<MineMainPresenter> mPresenterProvider) {
    return new TainMainActivity_MembersInjector(mPresenterProvider);
  }

  @Override
  public void injectMembers(TainMainActivity instance) {
    BaseActivity_MembersInjector.injectMPresenter(instance, mPresenterProvider.get());
  }
}
