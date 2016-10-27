package com.easemob.wukong.services.task;

import com.easemob.wukong.model.data.CommonResponse;
import com.easemob.wukong.model.data.task.DispatchTaskRequest;
import com.easemob.wukong.model.data.task.TaskRequest;
import com.easemob.wukong.model.entity.task.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by dongwentao on 16/9/28.
 */
public interface TaskService {
    List<Task> getTasks(List<String> taskIds);
    CommonResponse save(TaskRequest taskRequest);
    CommonResponse save(List<TaskRequest> taskRequests);
    Page<Task> getTasks(DispatchTaskRequest request, Pageable pageable);
    boolean update(TaskRequest taskRequest);
}
