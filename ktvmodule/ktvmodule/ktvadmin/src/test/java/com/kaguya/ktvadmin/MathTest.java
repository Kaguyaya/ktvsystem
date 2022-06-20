package com.kaguya.ktvadmin;

import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

@Slf4j
public class MathTest {
    @Autowired
    private RedisTemplate redisTemplate;
    @Test
    public void test(){
        redisTemplate.opsForValue().set("key",666);
        String key = redisTemplate.opsForValue().get("key").toString();
        log.info(key);
    }
}
