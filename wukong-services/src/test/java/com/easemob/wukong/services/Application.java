package com.easemob.wukong.services;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created by dongwentao on 16/9/26.
 */
@SpringBootApplication(scanBasePackages = "com.easemob.wukong")
@EnableJpaRepositories("com.easemob.wukong")
@EntityScan("com.easemob.wukong")
@EnableTransactionManagement
public class Application {
}
