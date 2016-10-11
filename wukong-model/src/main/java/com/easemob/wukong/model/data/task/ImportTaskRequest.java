package com.easemob.wukong.model.data.task;

import com.easemob.wukong.model.data.CommonRequest;
import lombok.Data;

/**
 * Created by dongwentao on 16/9/27.
 */
@Data
public class ImportTaskRequest extends CommonRequest {
    private int taskType;
    private String taskBody;
}
