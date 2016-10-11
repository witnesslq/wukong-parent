package com.easemob.wukong.server;

import com.easemob.wukong.rest.interceptor.AbstractInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.Collections;
import java.util.TreeSet;

/**
 * Created by dongwentao on 16/9/26.
 */
@SpringBootApplication(scanBasePackages = "com.easemob.wukong")
@EnableJpaRepositories("com.easemob.wukong")
@EntityScan("com.easemob.wukong")
@EnableTransactionManagement
public class WuKongServer extends WebMvcConfigurerAdapter {

    private TreeSet<AbstractInterceptor> abstractInterceptors = new TreeSet<>();

    @Autowired
    private void setAbstractInterceptors(AbstractInterceptor[]  abstractInterceptors){
        Collections.addAll(this.abstractInterceptors,abstractInterceptors);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        for (AbstractInterceptor abstractInterceptor:abstractInterceptors){
            registry.addInterceptor(abstractInterceptor);
        }
    }

    public static void main(String [] args){
        SpringApplication.run(WuKongServer.class,args);
    }


}
