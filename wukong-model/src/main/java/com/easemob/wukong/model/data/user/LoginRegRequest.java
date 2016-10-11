package com.easemob.wukong.model.data.user;

import com.easemob.wukong.model.data.CommonRequest;
import lombok.Data;

/**
 * Created by dongwentao on 16/9/26.
 */
@Data
public class LoginRegRequest extends CommonRequest{
    private String name;
    private String password;
    private String email;
    private String mobile;
    private int role;
}
