package com.easemob.wukong.model.data.task;

import com.easemob.wukong.model.data.CommonRequest;
import lombok.Data;

/**
 * Created by dongwentao on 16/9/27.
 */
@Data
public class TaskRequest extends CommonRequest {
    private String taskId;
    private int taskType;
    private String taskBody;
    private int status;
}
