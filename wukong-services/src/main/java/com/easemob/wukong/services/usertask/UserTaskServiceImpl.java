package com.easemob.wukong.services.usertask;

import com.easemob.wukong.model.data.usertask.UserTaskRequest;
import com.easemob.wukong.model.entity.relation.UserTask;
import com.easemob.wukong.persistence.usertask.UserTaskRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by dongwentao on 16/10/7.
 */
@Service
@Slf4j
public class UserTaskServiceImpl implements UserTaskService {

    @Autowired
    private UserTaskRepository userTaskRepository;

    @Override
    public List<UserTask> getUserTask(UserTaskRequest userTaskRequest) {
        return userTaskRepository.findByUserIdAndTaskTypeAndResult(userTaskRequest.getUserId(), userTaskRequest.getTaskType(), userTaskRequest.getResult());
    }

    @Override
    public UserTask save(UserTaskRequest userTaskRequest) {
        UserTask userTask = new UserTask();
        BeanUtils.copyProperties(userTaskRequest, userTask);
        System.out.println(userTask);
        System.out.println(userTaskRequest);
        return userTaskRepository.saveAndFlush(userTask);
    }

    @Override
    @Transactional
    public boolean update(UserTaskRequest userTaskRequest) {
        // 如果输入信息有误
        if (userTaskRequest.getUserId() <= 0 || userTaskRequest.getTaskType() <= 0 || StringUtils.isEmpty(userTaskRequest.getTaskId()) || userTaskRequest.getResult() < 0) {
            return false;
        }
        return userTaskRepository.updateUserTask(userTaskRequest.getTaskId(), userTaskRequest.getTaskType(), userTaskRequest.getUserId(), userTaskRequest.getResult()) == 1 ? true : false;
    }
}
