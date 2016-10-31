package com.easemob.wukong.services.usertask;

import com.easemob.wukong.model.data.CommonResponse;
import com.easemob.wukong.model.data.task.DispatchTaskRequest;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

        List<User> users = new ArrayList();
        if (dispatchTaskRequest.getUserId() > 0) {
            users.add(userService.getUserById(dispatchTaskRequest.getUserId()));
        } else {
            users = userService.getAllUser();
        }

        if (CollectionUtils.isEmpty(users)) {
            return ResponseUtils.buildFailMessage("No dispatch, reason: no user can receive the task.");
        }

        PageRequest pageRequest = new PageRequest(dispatchTaskRequest.getPage(), dispatchTaskRequest.getSize() * users.size()); // 设置分页信息

        Page<Task> tasks = taskService.getTasks(dispatchTaskRequest, pageRequest);

        if (CollectionUtils.isEmpty(tasks.getContent())) {
            return ResponseUtils.buildFailMessage("No dispatch, reason: no task can be dispatch.");
        }

        long maxReceiveTask = tasks.getContent().size() * dispatchTaskRequest.getPerTaskDispatchLimit() / users.size() + 1; // 理论上最多会给每个人分配的任务数量
        long realMaxReceiveTask = maxReceiveTask > dispatchTaskRequest.getPerManMaxReceiveTask() ? dispatchTaskRequest.getPerManMaxReceiveTask() : maxReceiveTask; // 事实上最多会给每个人分配的任务数量

        List<UserDispatch> userDispatches = new ArrayList();
        for (User user : users) {
            userDispatches.add(new UserDispatch(user.getId(), 0, realMaxReceiveTask));
        }

        DispatchCount dispatchCount = new DispatchCount(users.size(), 0, tasks.getTotalElements(), 0,0,dispatchTaskRequest.getPerTaskDispatchLimit(), 0);// 任务分派统计

        long dc = 0;
        TaskStatus taskStatus = null;
        $task:
        for (Task task : tasks) {
            taskStatus = getTaskStatus(task);
            if (null==taskStatus) { //无法进行状态更新
                continue $task;
            }

            dc = dispatchCount.getDispatchCount();
            dispatchCount.setCurrentTaskDispatchCount(0); // 重置当前任务分配数
            $user:
            for (UserDispatch userDispatch : userDispatches) {
                if (!dispatchCount.ctdcIsInLimit()) { // 如果此次任务分配超过设定的上限
                    break $user;
                }
                if (!userDispatch.crtIsInLimit()) { // 如果当前用户接受任务数达上限
                    continue $user;
                }
                UserTask userTask = new UserTask();
                userTask.setTaskId(task.getTaskId());
                userTask.setUserId(userDispatch.getUserId());
                userTask.setTaskType(task.getTaskType());
                userTaskRepository.saveAndFlush(userTask);
                task.setStatus(taskStatus.getType()); // 更新task状态
                userDispatch.crtIncrement(); // 当前用户接受任务数自增
                dispatchCount.ctdcIncrement(); // 当前任务分配数自增
                dispatchCount.dcIncrement(); // 分配次数自增
            }

            if (dispatchCount.getDispatchCount() > dc) { // 任务被分配了
                dispatchCount.dtcIncrement();
            }
        }

        for(UserDispatch userDispatch : userDispatches){ // 统计有多少人接收了任务
            if (userDispatch.getCurrentReceiveTask()>0){
                dispatchCount.ducIncrement();
            }
        }
        return ResponseUtils.buildSuccessMessage("dispatched", dispatchCount.getCountInfo());
    }

    private static TaskStatus getTaskStatus(Task task) {
        return TaskStatus.findByType(task.getTaskType());
    }


    @Data
    private class DispatchCount {

        private long dispatchUserCount = 0; // 分配到任务的用户数
        private long dispatchTaskCount = 0; // 分派的任务数
        private long dispatchCount = 0; // 总分派数
        private long dispatchLimit = 0; // 每条任务最多分派数
        private long taskCount = 0; // 任务总数
        private long userCount = 0; // 用户总数
        private long currentTaskDispatchCount = 0; // 当前任务分派数

        public DispatchCount() {

        }

        public DispatchCount(long userCount,long dispatchUserCount,long taskCount,long dispatchTaskCount,long dispatchCount, long dispatchLimit, long currentTaskDispatchCount) {
            this.dispatchUserCount = dispatchUserCount;
            this.userCount = userCount;
            this.dispatchTaskCount = dispatchTaskCount;
            this.dispatchCount = dispatchCount;
            this.dispatchLimit = dispatchLimit;
            this.taskCount = taskCount;
            this.currentTaskDispatchCount = currentTaskDispatchCount;
        }

        public double getUserLoadAvg() {
            return new BigDecimal(dispatchCount).divide(new BigDecimal(dispatchUserCount == 0 ? 1 : dispatchUserCount), 2, RoundingMode.HALF_UP).doubleValue();
        }

        public double getDispatchTaskAvg() {
            return new BigDecimal(dispatchCount).divide(new BigDecimal(dispatchTaskCount == 0 ? 1 : dispatchTaskCount), 2, RoundingMode.HALF_UP).doubleValue();
        }

        public void reset() {
            dispatchUserCount = 0;
            dispatchTaskCount = 0;
            dispatchCount = 0;
            dispatchLimit = 0;
            taskCount = 0;
            userCount = 0;
            currentTaskDispatchCount = 0;
        }

        public void reset(long taskCount,long dispatchTaskCount,long userCount,long dispatchUserCount,long dispatchCount, long dispatchLimit, long currentTaskDispatchCount) {
            this.dispatchUserCount = dispatchUserCount;
            this.dispatchTaskCount = dispatchTaskCount;
            this.dispatchCount = dispatchCount;
            this.dispatchLimit = dispatchLimit;
            this.taskCount = taskCount;
            this.userCount = userCount;
            this.currentTaskDispatchCount = currentTaskDispatchCount;
        }

        public long ducIncrement() {
            return ++dispatchUserCount;
        }

        public long dtcIncrement() {
            return ++dispatchTaskCount;
        }

        public long dcIncrement() {
            return ++dispatchCount;
        }

        public boolean ctdcIsInLimit() {
            return currentTaskDispatchCount < dispatchLimit;
        }

        public long ctdcIncrement() {
            return ++currentTaskDispatchCount;
        }

        public JsonNode getCountInfo() {
            return JSONUtils.objectNode()
                    .put("userCount", getUserCount())
                    .put("dispatchUserCount", getDispatchUserCount())
                    .put("userLoadAvg", getUserLoadAvg())
                    .put("taskCount", getTaskCount())
                    .put("dispatchTaskCount", getDispatchTaskCount())
                    .put("dispatchTaskAvg", getDispatchTaskAvg())
                    .put("dispatchCount", getDispatchCount())
                    .put("dispatchLimit", getDispatchLimit());
        }
    }
}
