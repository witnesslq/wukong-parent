package com.easemob.wukong.model.annotation;

import java.lang.annotation.*;

/**
 * Created by dongwentao on 16/9/30.
 * @Description 配合 @ EnumSerializer 使用
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface JsonEnum {
}
