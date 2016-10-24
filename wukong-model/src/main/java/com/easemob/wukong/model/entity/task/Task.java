package com.easemob.wukong.model.entity.task;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by dongwentao on 16/9/27.
 */
@Data
@Entity
@IdClass(TaskPK.class)
public class Task implements Serializable{

    static final Long serialVersionUID = 1L;
    @Id
    private String taskId;
    @Id
    private int taskType;
    private String taskBody;
    private int status;
    private Date createTime;
    private Date updateTime;

}
