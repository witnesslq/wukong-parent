package com.easemob.wukong.persistence.task;

import com.easemob.wukong.model.entity.task.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

/**
 * Created by dongwentao on 16/9/24.
 */
public interface TaskRepository extends JpaRepository<Task,String>,JpaSpecificationExecutor {

    Task findByTaskId(String taskId);

    List<Task> findByTaskIdIn(Collection<String> taskIds);

    @Modifying
    @Query("update Task t set t.status=:status where t.taskId=:taskId and t.taskType=:taskType")
    int updateTask(@Param("taskId") String taskId, @Param("taskType") int taskType, @Param("status") int status);

}
