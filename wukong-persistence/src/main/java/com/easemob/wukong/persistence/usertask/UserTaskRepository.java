package com.easemob.wukong.persistence.usertask;

import com.easemob.wukong.model.entity.relation.UserTask;
import com.easemob.wukong.model.entity.relation.UserTaskPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by dongwentao on 16/9/30.
 */
public interface UserTaskRepository extends JpaRepository<UserTask,UserTaskPK> {
    List<UserTask> findByUserIdAndTaskTypeAndResult(int userId, int taskType,int result);
    @Modifying
    @Query("update UserTask ut set ut.result=:result where ut.taskId=:taskId and ut.taskType=:taskType and ut.userId=:userId")
    int updateUserTask(@Param("taskId") String taskId,@Param("taskType") int taskType,@Param("userId") int userId, @Param("result") int result);
}
