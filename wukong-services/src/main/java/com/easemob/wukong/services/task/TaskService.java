package com.easemob.wukong.services.task;

import com.easemob.wukong.model.data.CommonResponse;
import com.easemob.wukong.model.data.task.DispatchTaskRequest;
import com.easemob.wukong.model.data.task.TaskRequest;
import com.easemob.wukong.model.entity.task.Task;

import java.util.List;

/**
 * Created by dongwentao on 16/9/28.
 */
public interface TaskService {
    List<Task> getTasks(List<String> taskIds);
    CommonResponse save(TaskRequest taskRequest);
    CommonResponse save(List<TaskRequest> taskRequests);
    List<Task> getTasks(DispatchTaskRequest request);
    boolean update(TaskRequest taskRequest);
}
