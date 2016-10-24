package com.easemob.wukong.model.data.usertask;

import lombok.Data;

/**
 * Created by dongwentao on 16/10/18.
 */
@Data
public class UserDispatch{
    int userId;
    int currentReceiveTask;
    int maxReceiveTask = 100;

    public UserDispatch(int userId) {
        this.userId=userId;
    }
    public UserDispatch(int userId,int currentReceiveTask){
        this.userId=userId;
        this.currentReceiveTask=currentReceiveTask;
    }
    public UserDispatch(int userId,int currentReceiveTask,int maxReceiveTask){
        this.userId=userId;
        this.currentReceiveTask=currentReceiveTask;
        this.maxReceiveTask=maxReceiveTask;
    }

    public boolean incrementIsInLimit(){
        return ++currentReceiveTask<=maxReceiveTask;
    }
}
