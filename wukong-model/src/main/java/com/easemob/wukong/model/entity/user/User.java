package com.easemob.wukong.model.entity.user;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by dongwentao on 16/9/23.
 */
@Data
@Entity
public class User implements Serializable{

    static final Long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String email;
    private String mobile;
    private String password;
    private int role;
    private Date registTime;
}
