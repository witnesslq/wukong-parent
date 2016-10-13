package com.easemob.wukong.model.data.task;

import com.easemob.wukong.model.annotation.JsonEnum;
import com.easemob.wukong.model.data.EnumSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Created by dongwentao on 16/9/30.
 */
@JsonSerialize(using = EnumSerializer.class)
public enum TaskType implements ITaskType {

    SIMILAR(1, "similar", false, "标记两个句子是否相似"),
    ;

    private int type;
    private String name;
    private boolean ignore;
    private String description;

    TaskType(int type, String name, boolean ignore, String description) {
        this.type = type;
        this.name = name;
        this.ignore = ignore;
        this.description = description;
    }

    @Override
    @JsonEnum
    public int getType() {
        return type;
    }

    @Override
    @JsonEnum
    public String getName() {
        return name;
    }

    @Override
    @JsonEnum
    public String getDescription() {
        return description;
    }

    @Override
    @JsonEnum
    public boolean isIgnore() {
        return ignore;
    }

    public static TaskType findByType(int type) {
        for (TaskType taskType : TaskType.values()) {
            if (taskType.getType() == type) {
                return taskType;
            }
        }
        return null;
    }
}
