package com.kaguya.ktvadmin.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kaguya.ktvadmin.dto.PageDto;
import com.kaguya.ktvadmin.mapper.KtvSongMapper;
import com.kaguya.ktvadmin.pojo.KtvSong;
import com.kaguya.ktvadmin.pojo.KtvUser;
import com.kaguya.ktvadmin.service.KtvSongService;
import com.kaguya.ktvadmin.vo.PageVo;
import com.sun.tools.jconsole.JConsoleContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class KtvSongServiceImpl implements KtvSongService {
    @Autowired
    private KtvSongMapper ktvSongMapper;

    @Override
    public KtvSong setSongInfo(KtvSong ktvSong) {
        ktvSongMapper.insert(ktvSong);
        return ktvSong;
    }

    @Override
    public List<KtvSong> getSongList() {
        List<KtvSong> ktvSongs = ktvSongMapper.selectList(null);
        return ktvSongs;
    }

    @Override
    public int deleteSong(KtvSong ktvSong) {
        int delete = ktvSongMapper.deleteById(ktvSong);
        return delete;
    }

    @Override
    public int updateSong(KtvSong ktvSong) {
       return ktvSongMapper.updateById(ktvSong);
    }

    @Override
    public PageVo<KtvSong> queryByPage(PageDto pageDto) {
        IPage<KtvSong> page=new Page(pageDto.getCurrentPage(),pageDto.getPageSize());
        QueryWrapper<KtvSong> QueryWrapper = new QueryWrapper<>();
        IPage<KtvSong> ktvUserIPage=ktvSongMapper.selectPage(page,null);
        List<KtvSong> records = ktvUserIPage.getRecords();
        return new PageVo<KtvSong>(records,(int)ktvUserIPage.getTotal());
    }

    public PageVo<KtvSong> queryByPagelanguageandtype(PageDto pageDto){
        IPage<KtvSong> page=new Page(pageDto.getCurrentPage(),pageDto.getPageSize());
        QueryWrapper<KtvSong> QueryWrapper = new QueryWrapper<>();
        JSONObject jsonObject= JSON.parseObject(pageDto.getQueryString());
        String language = jsonObject.getObject("language", String.class);
        String type = jsonObject.getObject("type", String.class);
        System.out.println(language);
        System.out.println(type);
        if (language.equals("")&&type.equals("")){
            System.out.println("1");
            IPage<KtvSong> ktvUserIPage=ktvSongMapper.selectPage(page,null);
            List<KtvSong> records = ktvUserIPage.getRecords();
            return new PageVo<KtvSong>(records,(int)ktvUserIPage.getTotal());
        }
        else if (!language.equals("")&&type.equals("")) {
            System.out.println("2");
            QueryWrapper.eq("song_language", language);
            IPage<KtvSong> ktvUserIPage = ktvSongMapper.selectPage(page, QueryWrapper);
            List<KtvSong> records = ktvUserIPage.getRecords();
            return new PageVo<KtvSong>(records, (int) ktvUserIPage.getTotal());
        }
        else if (language.equals("")&&!type.equals("")){
            System.out.println("3");
            QueryWrapper.eq("song_type",type);
            IPage<KtvSong> ktvUserIPage = ktvSongMapper.selectPage(page, QueryWrapper);
            List<KtvSong> records = ktvUserIPage.getRecords();
            return new PageVo<KtvSong>(records, (int) ktvUserIPage.getTotal());
        }
        else if (!language.equals("")&&!type.equals("")){
            System.out.println("4");
            HashMap<String, String> stringStringHashMap = new HashMap<>();
            stringStringHashMap.put("song_language",language);
            stringStringHashMap.put("song_type",type);
            QueryWrapper.allEq(stringStringHashMap);
            IPage<KtvSong> ktvUserIPage = ktvSongMapper.selectPage(page, QueryWrapper);
            List<KtvSong> records = ktvUserIPage.getRecords();
            return new PageVo<KtvSong>(records, (int) ktvUserIPage.getTotal());
        }
        else{
            return null;
        }

    }

    @Override
    public KtvSong queryById(String songid) {
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("song_id",songid);
        KtvSong ktvSong = ktvSongMapper.selectOne(queryWrapper);
        return ktvSong;
    }
}
