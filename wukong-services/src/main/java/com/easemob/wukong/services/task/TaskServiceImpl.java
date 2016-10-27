package com.easemob.wukong.services.task;

import com.easemob.wukong.model.data.CommonResponse;
import com.easemob.wukong.model.data.task.DispatchTaskRequest;
import com.easemob.wukong.model.data.task.TaskRequest;
import com.easemob.wukong.model.data.task.TaskType;
import com.easemob.wukong.model.data.task.similar.SimilarTaskDescriptor;
import com.easemob.wukong.model.entity.task.Task;
import com.easemob.wukong.persistence.task.TaskRepository;
import com.easemob.wukong.utils.date.DateUtils0;
import com.easemob.wukong.utils.json.JSONUtils;
import com.easemob.wukong.utils.wukong.ResponseUtils;
import com.fasterxml.jackson.databind.JsonNode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
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
    public Page<Task> getTasks(DispatchTaskRequest request, Pageable pageable){
        return taskRepository.findAll(new Specification<Task>() {

            @Override
            public Predicate toPredicate(Root<Task> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

                List<Predicate> list = new ArrayList<>();

                if (request.getTaskType() > 0) {
                    list.add(cb.equal(root.get("taskType"), request.getTaskType()));
                }
                if (StringUtils.isNotEmpty(request.getTaskId())) {
                    list.add(cb.equal(root.get("taskId"), request.getTaskId()));
                }
                if (request.getStatus() >= 0) {
                    list.add(cb.equal(root.get("status"), request.getStatus()));
                }
                if (request.getBeginDate() > 0) {
                    list.add(cb.greaterThanOrEqualTo(root.get("createTime"), DateUtils0.fromLong(request.getBeginDate())));
                }
                if (request.getEndDate() > 0) {
                    list.add(cb.lessThan(root.get("createTime"), DateUtils0.fromLong(request.getEndDate())));
                }
                return cb.and(list.toArray(new Predicate[list.size()]));
            }
        },pageable);
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
        JsonNode jsonNode = JSONUtils.fromObject(taskRequest.getTaskBody());
        Task task = null;
        switch (TaskType.findByType(jsonNode.get("taskType").asInt())) {
            case SIMILAR:
                SimilarTaskDescriptor taskDescriptor = JSONUtils.toBean(taskRequest.getTaskBody(), SimilarTaskDescriptor.class);
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
