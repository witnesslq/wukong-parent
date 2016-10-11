package com.easemob.wukong.rest.controller.usertask;

import com.easemob.wukong.model.data.IResponse;
import com.easemob.wukong.model.data.task.TaskType;
import com.easemob.wukong.model.data.usertask.UserTaskRequest;
import com.easemob.wukong.model.entity.relation.UserTask;
import com.easemob.wukong.model.entity.task.Task;
import com.easemob.wukong.rest.controller.WuKongController;
import com.easemob.wukong.services.task.TaskService;
import com.easemob.wukong.services.usertask.UserTaskService;
import com.easemob.wukong.utils.response.ResponseUtils0;
import com.easemob.wukong.utils.wukong.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dongwentao on 16/9/27.
 */
@RestController
@RequestMapping("/usertask")
public class UserTaskController extends WuKongController {

    @Autowired
    private TaskService taskService;
    @Autowired
    private UserTaskService userTaskService;

    @RequestMapping("")
    public ResponseEntity<IResponse> getTask(){
        return ResponseUtils0.buildResponse(ResponseUtils.buildSuccess(TaskType.values()));
    }

    @RequestMapping(value = "/{userId}/{taskType}",method = {RequestMethod.GET})
    public ResponseEntity<IResponse> getTask(@PathVariable("userId") int userId, @PathVariable("taskType") int taskType, UserTaskRequest userTaskRequest){
        userTaskRequest.setTaskType(taskType);
        userTaskRequest.setUserId(userId);
        List<UserTask> userTasks = userTaskService.getUserTask(userTaskRequest);
        List<String> taskIds = new ArrayList<>();
        for (UserTask userTask:userTasks){
            taskIds.add(userTask.getTaskId());
        }
        List<Task> tasks = taskService.getTasks(taskIds);
        return ResponseUtils0.buildResponse(ResponseUtils.buildSuccess(tasks));
    }

    @RequestMapping(value = "/{userId}/{taskType}/{taskId}")
    public ResponseEntity<IResponse> updateTask(@PathVariable("userId") int userId, @PathVariable("taskType") int taskType,@PathVariable("taskId") String taskId, UserTaskRequest userTaskRequest){
        userTaskRequest.setUserId(userId);
        userTaskRequest.setTaskType(taskType);
        userTaskRequest.setTaskId(taskId);
        UserTask saveUserTask = userTaskService.save(userTaskRequest);
        return ResponseUtils0.buildResponse(ResponseUtils.buildSuccess(saveUserTask));
    }
}
