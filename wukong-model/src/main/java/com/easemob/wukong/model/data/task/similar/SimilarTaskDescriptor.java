package com.easemob.wukong.model.data.task.similar;

import com.easemob.wukong.model.data.task.TaskDescriptor;
import lombok.Data;

/**
 * Created by dongwentao on 16/10/19.
 */
@Data
public class SimilarTaskDescriptor extends TaskDescriptor {
    private String sentence1;
    private String sentence2;
    private String similarity;
    private String tenant;
}
