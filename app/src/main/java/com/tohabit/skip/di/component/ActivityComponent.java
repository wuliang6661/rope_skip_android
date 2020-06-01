package com.tohabit.skip.di.component;

import android.app.Activity;

import com.tohabit.skip.di.ActivityScope;
import com.tohabit.skip.di.module.ActivityModule;
import com.tohabit.skip.ui.activity.MainActivity;
import com.tohabit.skip.ui.login.activity.LoginActivity;
import com.tohabit.skip.ui.mine.activity.MineMainActivity;
import com.tohabit.skip.ui.mine.fragment.AddAddressFragment;
import com.tohabit.skip.ui.mine.fragment.MyAddressListFragment;
import com.tohabit.skip.ui.train.activity.TainMainActivity;
import com.tohabit.skip.ui.young.activity.YoungMainActivity;

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
