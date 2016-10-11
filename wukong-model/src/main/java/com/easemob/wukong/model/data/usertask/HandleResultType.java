package com.easemob.wukong.model.data.usertask;

import com.easemob.wukong.model.annotation.JsonEnum;
import com.easemob.wukong.model.data.EnumSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Created by dongwentao on 16/9/30.
 */
@JsonSerialize(using = EnumSerializer.class)
public enum HandleResultType implements IHandleResultType{

    NO_HANDLE(0,"no_handle","未处理"),
    PASS(1,"pass","通过处理"),
    NO_PASS(2,"no_pass","不通过处理")

    ;

    private int type;
    private String name;
    private String description;

    HandleResultType(int type,String name,String description){
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

    public static HandleResultType findByType(int type) {
        for (HandleResultType handleResultType:HandleResultType.values()){
            if (handleResultType.getType()==type){
                return handleResultType;
            }
        }
        return null;
    }
}
