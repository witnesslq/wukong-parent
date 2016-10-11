package com.easemob.wukong.model.data.usertask;

import com.easemob.wukong.model.data.CommonRequest;
import lombok.Data;

/**
 * Created by dongwentao on 16/10/8.
 */
@Data
public class UserTaskRequest extends CommonRequest {
    private int userId;
    private String taskId;
    private int taskType;
    private int result;
}
