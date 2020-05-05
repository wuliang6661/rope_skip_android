// Generated by Dagger (https://google.github.io/dagger).
package com.habit.star.ui.login.fragment;

import com.habit.star.base.BaseFragment_MembersInjector;
import com.habit.star.ui.login.presenter.LoginPresenter;
import dagger.MembersInjector;
import javax.inject.Provider;

public final class LoginFragment_MembersInjector implements MembersInjector<LoginFragment> {
  private final Provider<LoginPresenter> mPresenterProvider;

  public LoginFragment_MembersInjector(Provider<LoginPresenter> mPresenterProvider) {
    this.mPresenterProvider = mPresenterProvider;
  }

  public static MembersInjector<LoginFragment> create(Provider<LoginPresenter> mPresenterProvider) {
    return new LoginFragment_MembersInjector(mPresenterProvider);
  }

  @Override
  public void injectMembers(LoginFragment instance) {
    BaseFragment_MembersInjector.injectMPresenter(instance, mPresenterProvider.get());
  }
}
