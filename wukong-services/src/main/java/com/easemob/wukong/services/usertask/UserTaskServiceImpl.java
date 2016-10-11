package com.easemob.wukong.services.usertask;

import com.easemob.wukong.model.data.usertask.UserTaskRequest;
import com.easemob.wukong.model.entity.relation.UserTask;
import com.easemob.wukong.persistence.usertask.UserTaskRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by dongwentao on 16/10/7.
 */
@Service
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
        BeanUtils.copyProperties(userTaskRequest,userTask);
        System.out.println(userTask);
        System.out.println(userTaskRequest);
        return userTaskRepository.saveAndFlush(userTask);
    }
}
