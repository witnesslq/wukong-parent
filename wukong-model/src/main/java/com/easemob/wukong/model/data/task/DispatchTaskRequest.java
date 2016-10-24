package com.easemob.wukong.model.data.task;

import com.easemob.wukong.model.data.CommonRequest;
import lombok.Data;

/**
 * Created by dongwentao on 16/9/27.
 */
@Data
public class DispatchTaskRequest extends CommonRequest {
    private String taskId; // 需要分派的任务id
    private int taskType; // 需要分派的任务类型
    private int userId; // 指明分配给谁
    private long beginDate; // 开始时间
    private long endDate; // 结束时间
    private int status; // 目前的状态类型
}
