package com.tohabit.commonlibrary.apt;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by baixiaokang on 16/12/9.
 * 防止View被连续点击
 */

@Retention(RetentionPolicy.CLASS)
@Target(ElementType.METHOD)
public @interface SingleClick {
    /* 点击间隔时间 */
    long value() default 600;
}
