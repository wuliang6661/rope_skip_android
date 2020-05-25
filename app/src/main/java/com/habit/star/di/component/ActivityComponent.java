package com.habit.star.di.component;

import android.app.Activity;

import com.habit.star.di.ActivityScope;
import com.habit.star.di.module.ActivityModule;
import com.habit.star.ui.activity.MainActivity;
import com.habit.star.ui.login.activity.LoginActivity;
import com.habit.star.ui.mine.activity.MineMainActivity;
import com.habit.star.ui.mine.fragment.AddAddressFragment;
import com.habit.star.ui.mine.fragment.MyAddressListFragment;
import com.habit.star.ui.train.activity.TainMainActivity;
import com.habit.star.ui.young.activity.YoungMainActivity;

import dagger.Component;

/**
 * Created by sundongdong on 2017/2/24.
 */

@ActivityScope
@Component(dependencies = AppComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    Activity getActivity();


    void inject(LoginActivity activity);
    void inject(MineMainActivity activity);
    void inject(YoungMainActivity activity);
    void inject(MainActivity activity);
    void inject(TainMainActivity activity);
    void inject(MyAddressListFragment activity);
    void inject(AddAddressFragment activity);
}
