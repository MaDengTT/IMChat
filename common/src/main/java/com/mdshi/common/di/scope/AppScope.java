package com.mdshi.common.di.scope;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;

import javax.inject.Scope;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by MaDeng on 2018/9/20.
 */
@Scope
@Documented
@Retention(RUNTIME)
public @interface AppScope {
}
