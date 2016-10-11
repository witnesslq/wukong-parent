package com.easemob.wukong.model.data;

/**
 * Created by dongwentao on 16/9/30.
 */
public interface IEnum {
    /**
     * 类型标记
     * @return
     */
    public int getType();

    /**
     * 名称
     * @return
     */
    public String getName();

    /**
     * 描述
     * @return
     */
    public String getDescription();

}
