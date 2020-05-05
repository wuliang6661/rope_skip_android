package com.habit.star.di.component;


import com.habit.star.app.App;
import com.habit.star.di.module.AppModule;
import com.habit.star.model.http.RetrofitHelper;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by sundongdong on 2017/2/24.
 */

@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {

    App getContext();  // 提供App的Context

    RetrofitHelper retrofitHelper();  //提供http的帮助类


}