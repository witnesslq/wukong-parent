package com.easemob.wukong.services.user;

import com.easemob.wukong.model.entity.user.User;

import java.util.List;

/**
 * Created by dongwentao on 16/10/18.
 */
public interface UserService {
    List<User> getAllUser();
    List<User> getUserByRole(int role);
    User getUserById(int id);
}
