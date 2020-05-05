// Generated by Dagger (https://google.github.io/dagger).
package com.habit.star.ui.login.presenter;

import com.habit.star.model.http.RetrofitHelper;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class LoginPresenter_Factory implements Factory<LoginPresenter> {
  private final Provider<RetrofitHelper> retrofitHelperProvider;

  public LoginPresenter_Factory(Provider<RetrofitHelper> retrofitHelperProvider) {
    this.retrofitHelperProvider = retrofitHelperProvider;
  }

  @Override
  public LoginPresenter get() {
    return new LoginPresenter(retrofitHelperProvider.get());
  }

  public static LoginPresenter_Factory create(Provider<RetrofitHelper> retrofitHelperProvider) {
    return new LoginPresenter_Factory(retrofitHelperProvider);
  }
}
