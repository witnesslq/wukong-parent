package com.easemob.wukong.model.data.task;

import lombok.Data;

/**
 * Created by dongwentao on 16/10/19.
 */
@Data
public class TaskDescriptor {
    private int taskType;
    private DispatchDescriptor dispatch;
}
