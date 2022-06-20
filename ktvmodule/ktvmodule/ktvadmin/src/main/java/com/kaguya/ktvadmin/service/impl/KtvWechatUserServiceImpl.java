package com.kaguya.ktvadmin.service.impl;

import com.kaguya.ktvadmin.mapper.KtvWechatUserMapper;
import com.kaguya.ktvadmin.pojo.KtvWechatUser;
import com.kaguya.ktvadmin.service.KtvWechatUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KtvWechatUserServiceImpl implements KtvWechatUserService {
    @Autowired
    private KtvWechatUserMapper ktvWechatUserMapper;
    @Override
    public KtvWechatUser setUserInfo(KtvWechatUser ktvWechatUser) {
        ktvWechatUserMapper.insert(ktvWechatUser);
        return ktvWechatUser;
    }

    @Override
    public List<KtvWechatUser> getUserInfo() {
        List<KtvWechatUser> ktvWechatUsers = ktvWechatUserMapper.selectList(null);
        return ktvWechatUsers;
    }

    @Override
    public KtvWechatUser searchUserInfoById(String id) {
        KtvWechatUser ktvWechatUser = ktvWechatUserMapper.selectById(id);
        return ktvWechatUser;
    }
}
