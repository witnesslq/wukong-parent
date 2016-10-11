package com.easemob.wukong.services.user;

import com.easemob.wukong.model.data.user.LoginRegRequest;
import com.easemob.wukong.model.entity.user.User;
import com.easemob.wukong.services.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by dongwentao on 16/9/26.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
@SpringApplicationConfiguration(Application.class)
public class UserLoginServiceTest {

    @Autowired
    private AuthenticationService authenticationServiceImpl;

    @Test
    public void testLogin(){
        LoginRegRequest loginRegRequest = new LoginRegRequest();
        loginRegRequest.setName("wentao");
        loginRegRequest.setPassword("1234");
        User user = authenticationServiceImpl.login(loginRegRequest);
        System.out.println(user);
    }
}
