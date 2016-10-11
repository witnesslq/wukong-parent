package com.easemob.wukong.model.entity.task;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by dongwentao on 16/9/29.
 */
@Data
public class TaskPK implements Serializable{

    static final Long serialVersionUID = 1L;

    private String taskId;
    private int taskType;
}
