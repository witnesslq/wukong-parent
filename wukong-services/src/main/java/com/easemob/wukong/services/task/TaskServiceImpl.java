package com.easemob.wukong.services.task;

import com.easemob.wukong.model.data.CommonResponse;
import com.easemob.wukong.model.data.task.DispatchTaskRequest;
import com.easemob.wukong.model.data.task.TaskRequest;
import com.easemob.wukong.model.data.task.TaskType;
import com.easemob.wukong.model.data.task.similar.SimilarTaskDescriptor;
import com.easemob.wukong.model.entity.task.Task;
import com.easemob.wukong.persistence.task.TaskRepository;
import com.easemob.wukong.utils.json.JSONUtils;
import com.easemob.wukong.utils.wukong.ResponseUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Created by dongwentao on 16/9/28.
 */
@Service
public class TaskServiceImpl implements TaskService{


    @Autowired
    private TaskRepository taskRepository;

    @Override
    public List<Task> getTasks(List<String> taskIds) {
        return taskRepository.findByTaskIdIn(taskIds);
    }

    @Override
    public CommonResponse save(TaskRequest taskRequest) {
        Task task = getTask(taskRequest);
        taskRepository.saveAndFlush(task);
        return ResponseUtils.buildSuccessMessage("save success");
    }

    @Override
    public CommonResponse save(List<TaskRequest> taskRequests){
        for (TaskRequest taskRequest:taskRequests) {
            save(taskRequest);
        }
        return ResponseUtils.buildSuccessMessage("save success");
    }

    @Override
    public List<Task>  getTasks(DispatchTaskRequest request){
        return taskRepository.findAll(new Specification<Task>() {

            @Override
            public Predicate toPredicate(Root<Task> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                if (request.getTaskType() > 0) {
                    query.where(cb.equal(root.get("taskType"), request.getTaskType()));
                }
                if (StringUtils.isNotEmpty(request.getTaskId())) {
                    query.where(cb.equal(root.get("taskId"), request.getTaskId()));
                }
                if (request.getStatus() >= 0) {
                    query.where(cb.equal(root.get("status"), request.getStatus()));
                }
                if (request.getBeginDate() > 0) {
                    query.where(cb.gt(root.get("createTime"), request.getBeginDate()));
                }
                if (request.getEndDate() > 0) {
                    query.where(cb.lt(root.get("createTime"), request.getEndDate()));
                }
                return query.getRestriction();
            }
        });
    }

    @Override
    public boolean update(TaskRequest taskRequest) {
        // 如果输入信息有误
        if (StringUtils.isEmpty(taskRequest.getTaskId()) || taskRequest.getTaskType() <= 0) {
            return false;
        }
        return taskRepository.updateTask(taskRequest.getTaskId(), taskRequest.getTaskType(), taskRequest.getStatus()) == 1 ? true : false;
    }
    private Task getTask(TaskRequest taskRequest) {
        SimilarTaskDescriptor taskDescriptor = JSONUtils.toBean(taskRequest.getTaskBody(), SimilarTaskDescriptor.class);
        if (null == taskDescriptor) return null;
        Task task = null;
        switch (TaskType.findByType(taskDescriptor.getTaskType())) {
            case SIMILAR:
                task = new Task();
                task.setTaskId(DigestUtils.md5DigestAsHex(taskRequest.getTaskBody().getBytes()));
                task.setTaskType(taskDescriptor.getTaskType());
                task.setTaskBody(taskRequest.getTaskBody());
                break;
            default:
                break;
        }
        return task;
    }

}
