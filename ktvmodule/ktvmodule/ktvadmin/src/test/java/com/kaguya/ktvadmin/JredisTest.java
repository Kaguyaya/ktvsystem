package com.kaguya.ktvadmin;

import org.junit.jupiter.api.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class JredisTest {

    @Test
    public void redisTest(){
        Jedis jedis = new Jedis("47.102.141.182",4396,2000);
        jedis.auth("Vbhgnmccw4672");

        System.out.println("服务正在运行"+jedis.ping());
    }
}
