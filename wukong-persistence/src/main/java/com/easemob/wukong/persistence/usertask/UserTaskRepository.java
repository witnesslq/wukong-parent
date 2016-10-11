package com.easemob.wukong.persistence.usertask;

import com.easemob.wukong.model.entity.relation.UserTask;
import com.easemob.wukong.model.entity.relation.UserTaskPK;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by dongwentao on 16/9/30.
 */
public interface UserTaskRepository extends JpaRepository<UserTask,UserTaskPK> {
    List<UserTask> findByUserIdAndTaskTypeAndResult(int userId, int taskType,int result);
}
