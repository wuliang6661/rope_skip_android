// Generated by Dagger (https://google.github.io/dagger).
package com.habit.star.presenter;

import dagger.internal.Factory;

public final class MainPresenter_Factory implements Factory<MainPresenter> {
  private static final MainPresenter_Factory INSTANCE = new MainPresenter_Factory();

  @Override
  public MainPresenter get() {
    return new MainPresenter();
  }

  public static MainPresenter_Factory create() {
    return INSTANCE;
  }
}
