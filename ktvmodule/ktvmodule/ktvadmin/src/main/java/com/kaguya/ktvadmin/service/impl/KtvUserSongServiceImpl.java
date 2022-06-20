package com.kaguya.ktvadmin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kaguya.ktvadmin.entity.CommonResult;
import com.kaguya.ktvadmin.mapper.KtvSongMapper;
import com.kaguya.ktvadmin.mapper.KtvUserSongMapper;
import com.kaguya.ktvadmin.pojo.KtvSong;
import com.kaguya.ktvadmin.pojo.KtvUser;
import com.kaguya.ktvadmin.pojo.KtvUserSong;
import com.kaguya.ktvadmin.service.KtvUserSongService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class KtvUserSongServiceImpl implements KtvUserSongService {
    @Autowired
    private KtvUserSongMapper ktvUserSongMapper;

    //根据用户id搜索
    @Override
    public List<KtvUserSong> getUserTimes(KtvUserSong ktvUserSong) {
        QueryWrapper<KtvUserSong> QueryWrapper = new QueryWrapper<>();
        QueryWrapper.eq("userid",ktvUserSong.getUserid());
        List<KtvUserSong> ktvUserSongs = ktvUserSongMapper.selectList(QueryWrapper);
        return ktvUserSongs;
    }

    //提供数据插入数据库
    @Override
    public KtvUserSong setUserSong(KtvUserSong ktvUserSong) {
        QueryWrapper<KtvUserSong> wrapper = new QueryWrapper<>();
        Map<String,String> eq=new HashMap<>();
        eq.put("userid",ktvUserSong.getUserid());
        eq.put("songid",ktvUserSong.getSongid());
        wrapper.allEq(eq);
        List<KtvUserSong> ktvUserSongs = ktvUserSongMapper.selectList(wrapper);
        if (ktvUserSongs.size()==0){
            ktvUserSong.setTimes(1.0);
            ktvUserSongMapper.insert(ktvUserSong);
        }else
        {
            KtvUserSong ktvUserSong1 = ktvUserSongs.get(0);
            double times = ktvUserSong1.getTimes();
            times=times+1;
            ktvUserSong1.setTimes(times);
            ktvUserSongMapper.updateById(ktvUserSong1);
            return ktvUserSong1;
        }
        return ktvUserSong;
    }

    @Override
    public List<KtvUserSong> getAllUserTimes() {
        QueryWrapper<KtvUserSong> wrapper = new QueryWrapper<>();
        wrapper.select("DISTINCT userid");
        List<KtvUserSong> ktvUserSongs = ktvUserSongMapper.selectList(wrapper);
        return ktvUserSongs;
    }

    @Override
    public List<KtvUserSong> getUserTimes() {
        QueryWrapper<KtvUserSong> wrapper= new QueryWrapper<>();
        wrapper.select("DISTINCT userid,sum(times) as times");
        wrapper.groupBy("userid");
        List<KtvUserSong> ktvUserSongs = ktvUserSongMapper.selectList(wrapper);
        return ktvUserSongs;
    }

    @Override
    public List<KtvUserSong> getSongTimes() {
        QueryWrapper<KtvUserSong> wrapper = new QueryWrapper<>();
        wrapper.select("DISTINCT songid,sum(times) as times");
        wrapper.groupBy("songid");
        List<KtvUserSong> ktvUserSongs = ktvUserSongMapper.selectList(wrapper);
        return ktvUserSongs;
    }


    public HashMap<String, Double> getUserCount() {
        QueryWrapper<KtvUserSong> wrapper = new QueryWrapper<>();
        wrapper.select("DISTINCT userid,sum(times) as times");
        wrapper.groupBy("userid");
        //每个userid的次数
        List<KtvUserSong> ktvUserSongs = ktvUserSongMapper.selectList(wrapper);
        //全部userid
        List<KtvUserSong> userSongList = ktvUserSongMapper.selectList(null);

        for (KtvUserSong temp:userSongList
             ) {
            for (KtvUserSong temp2:ktvUserSongs
                 ) {

                if (temp.getUserid().equals(temp2.getUserid()))
                {
                    QueryWrapper<KtvUserSong> QueryWrapper = new QueryWrapper<>();
                    QueryWrapper.select("userid,times/"+temp2.getTimes()+" as times");
                    QueryWrapper.eq("userid",temp.getUserid());
                    List<KtvUserSong> userSongList1 = ktvUserSongMapper.selectList(QueryWrapper);
                    System.out.println(userSongList1);
                }
            }
        }

        return null;
    }


    public Double getAllSongtimes(){
        QueryWrapper<KtvUserSong> wrapper = new QueryWrapper<>();
        wrapper.select("sum(times) as times");
        KtvUserSong userSong = ktvUserSongMapper.selectOne(wrapper);
        return userSong.getTimes();
    }


    public List<KtvUserSong> getAll(){
        List<KtvUserSong> userSongList = ktvUserSongMapper.selectList(null);
        return userSongList;
    }

}
