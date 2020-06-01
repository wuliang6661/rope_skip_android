package com.tohabit.skip.di.component;


import com.tohabit.skip.app.App;
import com.tohabit.skip.di.module.AppModule;
import com.tohabit.skip.model.http.RetrofitHelper;

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