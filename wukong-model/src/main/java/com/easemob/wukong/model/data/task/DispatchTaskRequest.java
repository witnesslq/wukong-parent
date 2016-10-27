package com.easemob.wukong.model.data.task;

import com.easemob.wukong.model.data.CommonRequest;
import lombok.Data;

/**
 * Created by dongwentao on 16/9/27.
 */
@Data
public class DispatchTaskRequest extends CommonRequest {
    private String taskId = null; // 需要分派的任务id
    private int taskType = 0; // 需要分派的任务类型
    private int userId = 0; // 指明分配给谁
    private long beginDate = 0; // 开始时间
    private long endDate =0 ; // 结束时间
    private int status = 0; // 目前的状态类型
    private int page = 0; // 起始页
    private int size = 1000; // 限制取的数据量
    private int perTaskDispatchLimit = 3; // 每个任务被分派的次数
    private int perManMaxReceiveTask = 100; // 每个人的最大接收数
}
