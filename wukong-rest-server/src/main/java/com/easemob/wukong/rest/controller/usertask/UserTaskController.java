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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
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
@Slf4j
public class UserTaskController extends WuKongController {

    @Autowired
    private TaskService taskService;
    @Autowired
    private UserTaskService userTaskService;

    @RequestMapping(value = "",method = {RequestMethod.GET})
    public ResponseEntity<IResponse> getTaskType(){
        return ResponseUtils0.buildResponse(ResponseUtils.buildSuccess(TaskType.values()));
    }

    @RequestMapping(value = "/{userId}/tasks",method = {RequestMethod.GET})
    public ResponseEntity<IResponse> getTasks(UserTaskRequest userTaskRequest){
        log.info("UserTaskController|UserTaskRequest:{}",userTaskRequest);
        List<UserTask> userTasks = userTaskService.getUserTask(userTaskRequest);
        List<String> taskIds = new ArrayList<>();
        for (UserTask userTask:userTasks){
            taskIds.add(userTask.getTaskId());
        }
        List<Task> tasks = taskService.getTasks(taskIds);
        log.info("UserTaskController|UserTaskResponse:{}", tasks.size());
        return ResponseUtils0.buildResponse(ResponseUtils.buildSuccess(tasks));
    }
    @RequestMapping(value = "/{userId}/tasks",method = {RequestMethod.PUT})
    public ResponseEntity<IResponse> updateTasks(@RequestBody List<UserTaskRequest> userTaskRequestList){
        for (UserTaskRequest userTaskRequest:userTaskRequestList) {
            userTaskService.update(userTaskRequest);
        }
        return ResponseUtils0.buildResponse(ResponseUtils.buildSuccessMessage("OK"));
    }

    @RequestMapping(value = "/{userId}/task/{taskId}",method = {RequestMethod.PUT})
    public ResponseEntity<IResponse> updateTask(UserTaskRequest userTaskRequest){
        boolean result = userTaskService.update(userTaskRequest);
        if(result){
            return ResponseUtils0.buildResponse(ResponseUtils.buildSuccessMessage("update success"));
        }else{
            return ResponseUtils0.buildResponse(ResponseUtils.buildFailMessage("update fail"));
        }

    }
}
