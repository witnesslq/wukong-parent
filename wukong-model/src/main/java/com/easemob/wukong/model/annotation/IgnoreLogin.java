package com.easemob.wukong.model.annotation;

/**
 * Created by dongwentao on 16/9/27.
 */

import java.lang.annotation.*;

/**
 * Created by dongwentao on 16/9/27.
 */

@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface IgnoreLogin {

}
