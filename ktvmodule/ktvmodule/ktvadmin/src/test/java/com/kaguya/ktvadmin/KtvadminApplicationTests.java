package com.kaguya.ktvadmin;

import com.kaguya.ktvadmin.mapper.KtvItemMapper;
import com.kaguya.ktvadmin.mapper.KtvUserMapper;
import com.kaguya.ktvadmin.pojo.KtvItem;
import com.kaguya.ktvadmin.pojo.KtvUser;
import com.kaguya.ktvadmin.service.KtvUserService;
import com.kaguya.ktvadmin.service.impl.KtvUserServiceImpl;
import com.kaguya.ktvadmin.vo.KtvUserVo;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class KtvadminApplicationTests {

    @Autowired
    private KtvUserServiceImpl ktvUserService;
    @Test
    void contextLoads() {
        List<KtvUser> queryall = ktvUserService.queryall();
        System.out.println(queryall);
    }

}
