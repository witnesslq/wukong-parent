package com.easemob.wukong.services.usertask;

import com.easemob.wukong.model.data.CommonResponse;
import com.easemob.wukong.model.data.task.DispatchTaskRequest;
import com.easemob.wukong.model.data.usertask.UserTaskRequest;
import com.easemob.wukong.model.entity.relation.UserTask;

import java.util.List;

/**
 * Created by dongwentao on 16/10/7.
 */
public interface UserTaskService {
    List<UserTask> getUserTask(UserTaskRequest userTaskRequest);
    UserTask save(UserTaskRequest userTaskRequest);
    boolean update(UserTaskRequest userTaskRequest);
    CommonResponse dispatchTask(DispatchTaskRequest dispatchTaskRequest);
}
