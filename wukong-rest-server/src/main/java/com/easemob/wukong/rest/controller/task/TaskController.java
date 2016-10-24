package com.easemob.wukong.rest.controller.task;

import com.easemob.wukong.model.data.CommonResponse;
import com.easemob.wukong.model.data.IResponse;
import com.easemob.wukong.model.data.task.DispatchTaskRequest;
import com.easemob.wukong.model.data.task.TaskRequest;
import com.easemob.wukong.rest.controller.WuKongController;
import com.easemob.wukong.services.task.TaskService;
import com.easemob.wukong.services.usertask.UserTaskService;
import com.easemob.wukong.utils.response.ResponseUtils0;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
    public ResponseEntity<IResponse> dispatchTask(DispatchTaskRequest dispatchTaskRequest){
        return ResponseUtils0.buildResponse(userTaskService.dispatchTask(dispatchTaskRequest));
    }

    @RequestMapping("/import")
    public ResponseEntity<IResponse> importTask(TaskRequest taskRequest){
        CommonResponse commonResponse = taskService.save(taskRequest);
        return ResponseUtils0.buildResponse(commonResponse);
    }
    @RequestMapping("/importlist")
    public ResponseEntity<IResponse> importTask(@RequestBody List<TaskRequest> taskRequestList){
        CommonResponse commonResponse = taskService.save(taskRequestList);
        return ResponseUtils0.buildResponse(commonResponse);
    }
}
