package com.habit.star.di;

import java.lang.annotation.Retention;

import javax.inject.Scope;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by sundongdong on 2017/2/24.
 */

@Scope
@Retention(RUNTIME)
public @interface ActivityScope {
}
