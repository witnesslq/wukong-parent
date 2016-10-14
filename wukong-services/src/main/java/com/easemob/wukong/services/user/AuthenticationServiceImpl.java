package com.easemob.wukong.services.user;

import com.easemob.wukong.model.data.user.LoginRegRequest;
import com.easemob.wukong.model.data.usertask.Encryptype;
import com.easemob.wukong.model.entity.user.User;
import com.easemob.wukong.persistence.user.UserRepository;
import com.easemob.wukong.utils.wukong.EncryptUtiles;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by dongwentao on 16/9/25.
 */
@Service
@Slf4j
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
        if (user != null){
            try {
                EncryptUtiles.encryptEncode(password,Encryptype.MD5.toString()).equals(user.getPassword());
            } catch (Exception e) {
                log.error("user {} password login failed ",loginRegRequest.getName());
                e.printStackTrace();
            }
            return user;
        }
        return null;

    }


    @Override
    public User regist(LoginRegRequest loginRegRequest) {
        User regist = new User();
        if (StringUtils.isNotEmpty(loginRegRequest.getName()) && StringUtils.isNotEmpty(loginRegRequest.getPassword()) && StringUtils.isNotEmpty(loginRegRequest.getEmail()) && StringUtils.isNotEmpty(loginRegRequest.getMobile())) {
            regist.setName(loginRegRequest.getName());
            try {
                regist.setPassword(EncryptUtiles.encryptEncode(loginRegRequest.getPassword(), Encryptype.MD5.toString()));
            } catch (Exception e) {
                log.error("user {} password save failed ",loginRegRequest.getName());
                e.printStackTrace();
            }
            regist.setEmail(loginRegRequest.getEmail());
            regist.setMobile(loginRegRequest.getMobile());
            regist.setRole(1);
            User user = userRepository.save(regist);

            if (null != user) {
                user.setPassword(null);
                return user;
            }
        }
        return null;
    }

    @Override
    public boolean islogin(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return false;
        }
        return  false;
    }
}
