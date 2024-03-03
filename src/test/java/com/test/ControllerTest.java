package com.test;

import com.achilles.SpringBootMySqlRedisApplication;
import com.achilles.server.controller.DiaryQryController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes= SpringBootMySqlRedisApplication.class)//测试启动类入口，这里填写自己的
public class ControllerTest{

    @Autowired
    DiaryQryController mysqlController;

    @Test
    public void  test(){

    }
}
