package com.easemob.wukong.services.user;

import com.easemob.wukong.model.data.user.LoginRegRequest;
import com.easemob.wukong.model.entity.user.User;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by dongwentao on 16/9/25.
 */
public interface AuthenticationService {
    User login(LoginRegRequest loginRegRequest);
    User regist(LoginRegRequest loginRegRequest);
    boolean islogin(HttpServletRequest request);
}
