package com.easemob.wukong.services.usertask;

import com.easemob.wukong.model.data.CommonResponse;
import com.easemob.wukong.model.data.task.DispatchTaskRequest;
import com.easemob.wukong.model.data.task.TaskRequest;
import com.easemob.wukong.model.data.task.TaskStatus;
import com.easemob.wukong.model.data.usertask.UserDispatch;
import com.easemob.wukong.model.data.usertask.UserTaskRequest;
import com.easemob.wukong.model.entity.relation.UserTask;
import com.easemob.wukong.model.entity.task.Task;
import com.easemob.wukong.model.entity.user.User;
import com.easemob.wukong.persistence.usertask.UserTaskRepository;
import com.easemob.wukong.services.task.TaskService;
import com.easemob.wukong.services.user.UserService;
import com.easemob.wukong.utils.json.JSONUtils;
import com.easemob.wukong.utils.wukong.ResponseUtils;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dongwentao on 16/10/7.
 */
@Service
@Slf4j
@Transactional
public class UserTaskServiceImpl implements UserTaskService {

    @Autowired
    private UserTaskRepository userTaskRepository;
    @Autowired
    private TaskService taskService;
    @Autowired
    private UserService userService;

    @Override
    public List<UserTask> getUserTask(UserTaskRequest userTaskRequest) {
        return userTaskRepository.findByUserIdAndTaskTypeAndResult(userTaskRequest.getUserId(), userTaskRequest.getTaskType(), userTaskRequest.getResult());
    }

    @Override
    public UserTask save(UserTaskRequest userTaskRequest) {
        UserTask userTask = new UserTask();
        BeanUtils.copyProperties(userTaskRequest, userTask);
        return userTaskRepository.saveAndFlush(userTask);
    }

    @Override
    public boolean update(UserTaskRequest userTaskRequest) {
        // 如果输入信息有误
        if (userTaskRequest.getUserId() <= 0 || userTaskRequest.getTaskType() <= 0 || StringUtils.isEmpty(userTaskRequest.getTaskId()) || userTaskRequest.getResult() < 0) {
            return false;
        }
        return userTaskRepository.updateUserTask(userTaskRequest.getTaskId(), userTaskRequest.getTaskType(), userTaskRequest.getUserId(), userTaskRequest.getResult()) == 1 ? true : false;
    }

    @Override
    public CommonResponse dispatchTask(DispatchTaskRequest dispatchTaskRequest) {
        List<Task> tasks = taskService.getTasks(dispatchTaskRequest);
        List<User> users = new ArrayList();
        if (dispatchTaskRequest.getUserId() > 0) {
            users.add(userService.getUserById(dispatchTaskRequest.getUserId()));
        } else {
            users = userService.getAllUser();
        }
        if (CollectionUtils.isEmpty(tasks) || CollectionUtils.isEmpty(users)) {
            return ResponseUtils.buildFailMessage("No dispatch, reason: no task or no user can receive the task.");
        }
        int maxReceiveTask = tasks.size() * 3 / users.size()+1;

        List<UserDispatch> userDispatches = new ArrayList();
        for (User user : users) {
            userDispatches.add(new UserDispatch(user.getId(),0, maxReceiveTask > 100 ? 100 : maxReceiveTask));
        }
        DispatchCount dispatchCount = new DispatchCount(userDispatches.size(), 0, 0,3,0,0);// 任务分派统计
        $task:for (Task task : tasks) {
            dispatchCount.tcIncrement(); // 任务数自增
            dispatchCount.setCurrentTaskDispatchCount(0); // 重置当前任务分配数
            // 同一个类型的task分派数量相同 默认3个; 同一个用户最多接收100个任务
            if(!setTaskStatus(task)){ //无法设置对应的状态
                continue ;
            }
            $user:{
                for (UserDispatch userDispatch : userDispatches) {
                    dispatchCount.ctdcIncrement(); // 当前任务分配数自增
                    if (dispatchCount.isOverDispatchLimit()) { // 如果此次任务分配超过设定的上限
                        break $user;
                    }
                    if (userDispatch.incrementIsInLimit()) { // 如果当前用户接受任务数未达上限
                        dispatchCount.dcIncrement(); // 分配次数自增
                        UserTask userTask = new UserTask();
                        userTask.setTaskId(task.getTaskId());
                        userTask.setUserId(userDispatch.getUserId());
                        userTask.setTaskType(task.getTaskType());
                        userTaskRepository.saveAndFlush(userTask);
                        TaskRequest taskRequest = new TaskRequest();
                        BeanUtils.copyProperties(task, taskRequest);
                        taskService.update(taskRequest);
                    }
                }
                dispatchCount.dtcIncrement();
            }
        }
        return ResponseUtils.buildSuccessMessage("dispatched",dispatchCount.getCountInfo());
    }

    private static boolean setTaskStatus(Task task) {
        boolean setTaskStatus = false;
        switch (TaskStatus.findByType(task.getStatus())) {
            case UN_DISPATCHED:
                task.setStatus(TaskStatus.DISPATCHED.getType());
                setTaskStatus = true;
                break;
            case DISPATCHED:
                task.setStatus(TaskStatus.DISPATCH_TWICE.getType());
                setTaskStatus = true;
                break;
            case DISPATCH_TWICE:
                task.setStatus(TaskStatus.DISPATCH_TRIPLE.getType());
                setTaskStatus = true;
                break;
            default:
                break;
        }
        return setTaskStatus;
    }


    @Data
    private class DispatchCount {

        private int dispatchUserCount = 0; // 复合条件的用户数
        private int dispatchTaskCount = 0; // 分派的任务数
        private int dispatchCount = 0; // 总分派数
        private int dispatchLimit = 0; // 每条任务最多分派数
        private int taskCount = 0; // 任务总数
        private int currentTaskDispatchCount = 0; // 当前任务分派数

        public DispatchCount() {

        }

        public DispatchCount(int dispatchUserCount, int dispatchTaskCount, int dispatchCount, int dispatchLimit,int taskCount,int currentTaskDispatchCount) {
            this.dispatchUserCount = dispatchUserCount;
            this.dispatchTaskCount = dispatchTaskCount;
            this.dispatchCount = dispatchCount;
            this.dispatchLimit = dispatchLimit;
            this.taskCount = taskCount;
            this.currentTaskDispatchCount = currentTaskDispatchCount;
        }

        public double getUserLoadAvg() {
            return new BigDecimal(dispatchCount).divide(new BigDecimal(dispatchUserCount==0?1:dispatchUserCount), 2, RoundingMode.HALF_UP).doubleValue();
        }

        public double getDispatchTaskAvg() {
            return new BigDecimal(dispatchCount).divide(new BigDecimal(dispatchTaskCount==0?1:dispatchTaskCount), 2, RoundingMode.HALF_UP).doubleValue();
        }

        public void reset() {
            dispatchUserCount = 0;
            dispatchTaskCount = 0;
            dispatchCount = 0;
            dispatchLimit = 0;
            taskCount = 0;
            currentTaskDispatchCount = 0;
        }

        public void reset(int dispatchUserCount, int dispatchTaskCount, int dispatchCount, int dispatchLimit,int taskCount,int currentTaskDispatchCount) {
            dispatchUserCount = dispatchUserCount;
            dispatchTaskCount = dispatchTaskCount;
            dispatchCount = dispatchCount;
            dispatchLimit = dispatchLimit;
            taskCount = taskCount;
            currentTaskDispatchCount = currentTaskDispatchCount;
        }

        public int ducIncrement() {
            return ++dispatchUserCount;
        }

        public int dtcIncrement() {
            return ++dispatchTaskCount;
        }

        public int dcIncrement() {
            return ++dispatchCount;
        }

        public int tcIncrement() { return ++taskCount; }

        public int ctdcIncrement() { return ++currentTaskDispatchCount; }

        public boolean isOverDispatchLimit(){
            return currentTaskDispatchCount>dispatchLimit;
        }

        public JsonNode getCountInfo() {
            return JSONUtils.objectNode()
                    .put("taskCount",getTaskCount())
                    .put("dispatchTaskCount", getDispatchTaskCount())
                    .put("dispatchUserCount", getDispatchUserCount())
                    .put("dispatchCount", getDispatchCount())
                    .put("dispatchLimit", getDispatchLimit())
                    .put("userLoadAvg", getUserLoadAvg())
                    .put("dispatchTaskAvg", getDispatchTaskAvg());
        }
    }
}
