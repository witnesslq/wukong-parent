package com.easemob.wukong.services.task;

import com.easemob.wukong.model.entity.task.Task;

import java.util.List;

/**
 * Created by dongwentao on 16/9/28.
 */
public interface TaskService {
    List<Task> getTasks(List<String> taskIds);
    Task save(Task task);
}
