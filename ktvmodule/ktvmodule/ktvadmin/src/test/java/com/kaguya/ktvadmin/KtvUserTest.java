package com.kaguya.ktvadmin;

import com.kaguya.ktvadmin.pojo.KtvUser;
import com.kaguya.ktvadmin.service.KtvUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest(classes = KtvadminApplication.class)
public class KtvUserTest {
    @Autowired
    private KtvUserService ktvUserService;
    @Test
    private void test(){
        List<KtvUser> queryall = ktvUserService.queryall();
        System.out.println(queryall);
    }
}
