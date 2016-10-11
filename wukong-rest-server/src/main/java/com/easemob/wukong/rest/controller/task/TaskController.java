package com.easemob.wukong.rest.controller.task;

import com.easemob.wukong.model.data.IResponse;
import com.easemob.wukong.model.data.task.TaskRequest;
import com.easemob.wukong.model.data.usertask.UserTaskRequest;
import com.easemob.wukong.model.entity.relation.UserTask;
import com.easemob.wukong.model.entity.task.Task;
import com.easemob.wukong.rest.controller.WuKongController;
import com.easemob.wukong.services.task.TaskService;
import com.easemob.wukong.services.usertask.UserTaskService;
import com.easemob.wukong.utils.response.ResponseUtils0;
import com.easemob.wukong.utils.wukong.ResponseUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by dongwentao on 16/9/27.
 */
@RestController
@RequestMapping("/task")
public class TaskController extends WuKongController {

    @Autowired
    private TaskService taskService;
    @Autowired
    private UserTaskService userTaskService;

    @RequestMapping("/dispatch")
    public ResponseEntity<IResponse> dispatchTask(UserTaskRequest userTaskRequest){
        UserTask userTask = userTaskService.save(userTaskRequest);
        return ResponseUtils0.buildResponse(ResponseUtils.buildSuccess(userTask));
    }

    @RequestMapping("/import")
    public ResponseEntity<IResponse> importTask(TaskRequest taskRequest){
        Task task = new Task();
        BeanUtils.copyProperties(taskRequest,task);
        task.setTaskId(DigestUtils.md5DigestAsHex(task.getTaskBody().getBytes()));
        Task saveTask = taskService.save(task);
        return ResponseUtils0.buildResponse(ResponseUtils.buildSuccess(saveTask));
    }
}
