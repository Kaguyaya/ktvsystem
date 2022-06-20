package com.kaguya.ktvadmin.service;

import com.kaguya.ktvadmin.dto.PageDto;
import com.kaguya.ktvadmin.pojo.KtvUser;
import com.kaguya.ktvadmin.vo.PageVo;

import java.util.List;

public interface KtvUserService {
    List<KtvUser> queryall();
    KtvUser queryById(String id);
    KtvUser verifyPassword(String username);
    KtvUser userRecharge(KtvUser ktvUser,Double money);
    PageVo<KtvUser> queryByPage(PageDto pageDto);
    KtvUser updateuserinfo(KtvUser ktvUser);
    KtvUser queryByOpenid(String openid);
    KtvUser updateLoginTime(String userid);
    KtvUser registerUserByWechat(KtvUser ktvUser);
}
