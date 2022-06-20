package com.kaguya.ktvadmin.service;

import com.kaguya.ktvadmin.pojo.KtvWechatUser;

import java.util.List;

public interface KtvWechatUserService {
   KtvWechatUser setUserInfo(KtvWechatUser ktvWechatUser);
   List<KtvWechatUser> getUserInfo();
   KtvWechatUser searchUserInfoById(String id);
}
