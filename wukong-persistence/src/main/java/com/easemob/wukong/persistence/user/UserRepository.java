package com.easemob.wukong.persistence.user;

import com.easemob.wukong.model.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by dongwentao on 16/9/24.
 */
public interface UserRepository extends JpaRepository<User,Integer> {
    User findByName(String name);
    User findByEmail(String email);
    User findByMobile(String mobile);
    User findById(int id);
    List<User> findByRole(int role);
}
