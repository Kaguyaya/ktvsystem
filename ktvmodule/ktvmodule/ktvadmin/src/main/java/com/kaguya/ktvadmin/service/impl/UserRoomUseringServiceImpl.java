package com.kaguya.ktvadmin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kaguya.ktvadmin.mapper.UserRoomUseringMapper;
import com.kaguya.ktvadmin.pojo.UserRoomUsering;
import com.kaguya.ktvadmin.service.UserRoomUseringService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.management.Query;
import java.util.List;

@Service
public class UserRoomUseringServiceImpl implements UserRoomUseringService {
    @Autowired
    private UserRoomUseringMapper userRoomUseringMapper;
    @Override
    public UserRoomUsering setUserRoomUsering(UserRoomUsering userRoomUsering) {
        int insert = userRoomUseringMapper.insert(userRoomUsering);
        return userRoomUsering;
    }

    @Override
    public List<UserRoomUsering> getUserRoomUseringList() {
        List<UserRoomUsering> userRoomUserings = userRoomUseringMapper.selectList(null);
        return userRoomUserings;
    }

    @Override
    public List<UserRoomUsering> getUseringRoomList() {
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("user_room_status",1);
        List<UserRoomUsering> list = userRoomUseringMapper.selectList(queryWrapper);
        return list;
    }

    @Override
    public UserRoomUsering changeUserRoomState(UserRoomUsering userRoomUsering) {
        int i = userRoomUseringMapper.updateById(userRoomUsering);
        return userRoomUsering;
    }
}
