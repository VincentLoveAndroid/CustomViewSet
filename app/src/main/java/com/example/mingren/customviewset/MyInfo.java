package com.example.mingren.customviewset;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by vincent on 2016/10/28.
 * email-address:674928145@qq.com
 * description:
 */
@Documented()
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface MyInfo {

    String name() default "vincent";

    int num() default 1;


}
