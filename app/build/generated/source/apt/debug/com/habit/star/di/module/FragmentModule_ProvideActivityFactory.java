// Generated by Dagger (https://google.github.io/dagger).
package com.habit.star.di.module;

import android.app.Activity;
import dagger.internal.Factory;
import dagger.internal.Preconditions;

public final class FragmentModule_ProvideActivityFactory implements Factory<Activity> {
  private final FragmentModule module;

  public FragmentModule_ProvideActivityFactory(FragmentModule module) {
    this.module = module;
  }

  @Override
  public Activity get() {
    return Preconditions.checkNotNull(
        module.provideActivity(), "Cannot return null from a non-@Nullable @Provides method");
  }

  public static FragmentModule_ProvideActivityFactory create(FragmentModule module) {
    return new FragmentModule_ProvideActivityFactory(module);
  }

  public static Activity proxyProvideActivity(FragmentModule instance) {
    return Preconditions.checkNotNull(
        instance.provideActivity(), "Cannot return null from a non-@Nullable @Provides method");
  }
}
