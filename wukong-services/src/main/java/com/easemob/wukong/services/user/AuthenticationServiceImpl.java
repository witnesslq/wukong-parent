package com.easemob.wukong.services.user;

import com.easemob.wukong.model.data.user.LoginRegRequest;
import com.easemob.wukong.model.data.usertask.Encryptype;
import com.easemob.wukong.model.entity.user.User;
import com.easemob.wukong.persistence.user.UserRepository;
import com.easemob.wukong.utils.wukong.EncryptUtiles;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by dongwentao on 16/9/25.
 */
@Service
@Slf4j
@Transactional
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User login(LoginRegRequest loginRegRequest) {
        if (loginRegRequest == null) {
            return null;
        }
        String userName = loginRegRequest.getName();
        String password = loginRegRequest.getPassword();
        User user = userRepository.findByName(userName);
        System.out.println(user.toString());
        try {
            if (user != null && EncryptUtiles.encryptEncode(password, Encryptype.MD5.toString()).equals(user.getPassword())) {
                return user;
            }
        } catch (Exception e) {
            log.error("user {} password login failed ", loginRegRequest.getName());
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public User regist(LoginRegRequest loginRegRequest) {
        if (StringUtils.isNotEmpty(loginRegRequest.getName()) && StringUtils.isNotEmpty(loginRegRequest.getPassword()) && StringUtils.isNotEmpty(loginRegRequest.getEmail()) && StringUtils.isNotEmpty(loginRegRequest.getMobile())) {
            User regist = new User();
            BeanUtils.copyProperties(loginRegRequest,regist);
            try {
                regist.setPassword(EncryptUtiles.encryptEncode(loginRegRequest.getPassword(), Encryptype.MD5.toString()));
            } catch (Exception e) {
                log.error("user {} password save failed ", loginRegRequest.getName());
                e.printStackTrace();
            }
            User user = userRepository.saveAndFlush(regist);
            return user;
        }
        return null;
    }

    @Override
    public boolean islogin(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return false;
        }
        return true;
    }
}
