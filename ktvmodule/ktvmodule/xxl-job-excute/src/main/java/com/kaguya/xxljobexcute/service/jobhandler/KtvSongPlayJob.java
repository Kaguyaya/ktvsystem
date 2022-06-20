package com.kaguya.xxljobexcute.service.jobhandler;


import com.xxl.job.core.handler.annotation.XxlJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class KtvSongPlayJob {

    @Autowired
    private RedisTemplate redisTemplate;
    @XxlJob("SongPlay")
    public void SongPlay() throws Exception{
        System.out.println("songplay");

    }
}
