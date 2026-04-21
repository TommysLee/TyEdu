package com.ty.logic.spring.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * SpringBoot应用启动的监听器
 *
 * @Author Tommy
 * @Date 2022/10/16
 */
@Component
@Slf4j
public class ApplicationListener implements ApplicationRunner {

    /**
     * 项目启动完毕后，执行此方法
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("请访问：http://localhost");
    }
}
