// Generated by Dagger (https://google.github.io/dagger).
package com.habit.star.ui.mine.fragment;

import com.habit.star.base.BaseFragment_MembersInjector;
import com.habit.star.ui.mine.presenter.PersonalDataPresenter;
import dagger.MembersInjector;
import javax.inject.Provider;

public final class PersonalDataFragment_MembersInjector
    implements MembersInjector<PersonalDataFragment> {
  private final Provider<PersonalDataPresenter> mPresenterProvider;

  public PersonalDataFragment_MembersInjector(Provider<PersonalDataPresenter> mPresenterProvider) {
    this.mPresenterProvider = mPresenterProvider;
  }

  public static MembersInjector<PersonalDataFragment> create(
      Provider<PersonalDataPresenter> mPresenterProvider) {
    return new PersonalDataFragment_MembersInjector(mPresenterProvider);
  }

  @Override
  public void injectMembers(PersonalDataFragment instance) {
    BaseFragment_MembersInjector.injectMPresenter(instance, mPresenterProvider.get());
  }
}
