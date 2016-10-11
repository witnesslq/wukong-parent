package com.easemob.wukong.model.annotation;

import javax.persistence.Inheritance;
import java.lang.annotation.*;

/**
 * Created by dongwentao on 16/9/29.
 */
@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inheritance
public @interface Logined {
}
