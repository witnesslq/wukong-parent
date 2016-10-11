package com.easemob.wukong.persistence.task;

import com.easemob.wukong.model.entity.task.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

/**
 * Created by dongwentao on 16/9/24.
 */
public interface TaskRepository extends JpaRepository<Task,String> {

    Task findByTaskId(String taskId);

    List<Task> findByTaskIdIn(Collection<String> taskIds);

}
