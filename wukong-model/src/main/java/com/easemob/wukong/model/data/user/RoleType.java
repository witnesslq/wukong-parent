package com.easemob.wukong.model.data.user;

import com.easemob.wukong.model.annotation.JsonEnum;
import com.easemob.wukong.model.data.EnumSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Created by dongwentao on 16/9/30.
 */
@JsonSerialize(using = EnumSerializer.class)
public enum  RoleType implements IRoleType{

    SUPER_ADMIN(9,"super_admin","上帝之手"),
    ADMIN(5,"admin","管理员"),
    USER(0,"user","普通用户"),

    ;
    private int type;
    private String name;
    private String description;

    RoleType(int type,String name,String description){
        this.type=type;
        this.name=name;
        this.description=description;
    }

    @Override
    @JsonEnum
    public int getType() {
        return type;
    }

    @Override
    @JsonEnum
    public String getName() {
        return name;
    }

    @Override
    @JsonEnum
    public String getDescription() {
        return description;
    }

    public static RoleType findByType(int type) {
        for (RoleType roleType:RoleType.values()){
            if (roleType.getType()==type){
                return roleType;
           }
        }
        return null;
    }
}
