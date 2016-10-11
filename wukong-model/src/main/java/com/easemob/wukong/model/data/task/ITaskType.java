package com.easemob.wukong.model.data.task;

import com.easemob.wukong.model.data.IEnum;

/**
 * Created by dongwentao on 16/9/30.
 */
public interface ITaskType extends IEnum {

    /**
     * 是否展示
     * @return
     */
    public boolean isIgnore();
}
