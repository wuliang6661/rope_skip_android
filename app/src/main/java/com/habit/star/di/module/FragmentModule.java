package com.habit.star.di.module;

import android.app.Activity;
import android.support.v4.app.Fragment;

import com.habit.star.di.FragmentScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by sundongdong on 2017/2/24.
 */

@Module
public class FragmentModule {

    private Fragment fragment;

    public FragmentModule(Fragment fragment) {
        this.fragment = fragment;
    }

    @Provides
    @FragmentScope
    public Activity provideActivity() {
        return fragment.getActivity();
    }
}

