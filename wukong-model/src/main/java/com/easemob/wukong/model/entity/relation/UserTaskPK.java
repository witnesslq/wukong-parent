package com.easemob.wukong.model.entity.relation;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by dongwentao on 16/9/29.
 */
@Data
public class UserTaskPK implements Serializable{

    static final Long serialVersionUID = 1L;

    private int userId;
    private String taskId;
    private int taskType;
}
