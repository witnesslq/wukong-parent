package com.easemob.wukong.services.user;

import com.easemob.wukong.model.entity.user.User;
import com.easemob.wukong.persistence.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by dongwentao on 16/10/18.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    @Override
    public List<User> getUserByRole(int role) {
        return userRepository.findByRole(role);
    }

    @Override
    public User getUserById(int id) {
        return userRepository.findById(id);
    }
}
