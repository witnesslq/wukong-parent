package com.easemob.wukong.services.task;

import com.easemob.wukong.model.entity.relation.UserTask;
import com.easemob.wukong.model.entity.task.Task;
import com.easemob.wukong.persistence.task.TaskRepository;
import com.easemob.wukong.services.usertask.UserTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dongwentao on 16/9/28.
 */
@Service
public class TaskServiceImpl implements TaskService{


    @Autowired
    private UserTaskService userTaskService;
    @Autowired
    private TaskRepository taskRepository;

    @Override
    public List<Task> getTasks(List<String> taskIds) {
        return taskRepository.findByTaskIdIn(taskIds);
    }

    @Override
    public Task save(Task task) {
        return taskRepository.saveAndFlush(task);
    }

}
