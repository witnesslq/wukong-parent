package com.easemob.wukong.model.entity.relation;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by dongwentao on 16/9/29.
 */
@Data
@Entity
@IdClass(UserTaskPK.class)
public class UserTask implements Serializable{

    static final Long serialVersionUID = 1L;

    @Id
    private int userId;

    @Id
    private String taskId;

    @Id
    private int taskType;

    private int result;

    private Date createTime;

    private Date updateTime;
}
