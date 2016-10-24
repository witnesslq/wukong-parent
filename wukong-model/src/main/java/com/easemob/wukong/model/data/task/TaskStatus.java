package com.easemob.wukong.model.data.task;

import com.easemob.wukong.model.data.IEnum;

/**
 * Created by dongwentao on 16/10/17.
 */
public enum TaskStatus implements IEnum {

    UN_DISPATCHED(0,"un_dispatched","未分配"),
    DISPATCHING(1,"dispatching","正在分配"),
    DISPATCHED(2,"dispatched","已分配"),
    DISPATCH_TWICE(3,"dispatch_twice","第二次分配"),
    DISPATCH_TRIPLE(4,"dispatch_triple","第三次分配"),

    ;

    private int type;
    private String name;
    private String description;

    TaskStatus(int type, String name, String description) {
        this.type = type;
        this.name = name;
        this.description = description;
    }

    @Override
    public int getType() {
        return type;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public static TaskStatus findByType(int type) {
        for (TaskStatus taskStatus : TaskStatus.values()) {
            if (taskStatus.getType() == type) {
                return taskStatus;
            }
        }
        return null;
    }
}
