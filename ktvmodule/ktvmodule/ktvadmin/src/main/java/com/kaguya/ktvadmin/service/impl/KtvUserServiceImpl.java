package com.kaguya.ktvadmin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kaguya.ktvadmin.dto.PageDto;
import com.kaguya.ktvadmin.mapper.KtvUserMapper;
import com.kaguya.ktvadmin.pojo.KtvUser;
import com.kaguya.ktvadmin.service.KtvUserService;
import com.kaguya.ktvadmin.vo.KtvUserVo;
import com.kaguya.ktvadmin.vo.PageVo;
import org.apache.catalina.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class KtvUserServiceImpl implements KtvUserService {
    @Autowired
    private KtvUserMapper ktvUserMapper;
    @Override
    public List<KtvUser> queryall() {
        List<KtvUser> ktvUsers = ktvUserMapper.selectList(null);
    return ktvUsers;
    }

    @Override
    public KtvUser verifyPassword(String username) {
        QueryWrapper queryWrapper= new QueryWrapper<>().eq("user_username",username);
        KtvUser ktvUser = ktvUserMapper.selectOne(queryWrapper);
        return ktvUser;
    }

    @Override
    public KtvUser userRecharge(KtvUser ktvUser,Double money) {
        ktvUser.setUserBalance(ktvUser.getUserBalance()+money);
        ktvUserMapper.updateById(ktvUser);
        return ktvUser;
    }

    @Override
    public PageVo<KtvUser> queryByPage(PageDto pageDto) {
        IPage<KtvUser> page= new Page(pageDto.getCurrentPage(),pageDto.getPageSize());
        QueryWrapper<KtvUser> ktvUserQueryWrapper = new QueryWrapper<>();

        IPage<KtvUser> ktvUserIPage = ktvUserMapper.selectPage(page, ktvUserQueryWrapper);
        List<KtvUser> records = ktvUserIPage.getRecords();
        return new PageVo<KtvUser>(records,(int)ktvUserIPage.getTotal());
    }

    @Override
    public KtvUser updateuserinfo(KtvUser ktvUser) {
        int i = ktvUserMapper.updateById(ktvUser);
        return ktvUser;
    }


    @Override
    public KtvUser queryByOpenid(String openid) {
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("user_wechat_openid",openid);
        KtvUser ktvUser = ktvUserMapper.selectOne(queryWrapper);
        return ktvUser;
    }

    @Override
    public KtvUser updateLoginTime(String userid) {
        KtvUser user = ktvUserMapper.selectById(userid);
        user.setUserUpdatetime(new Date());
        ktvUserMapper.updateById(user);
        return user;
    }

    public KtvUser registerUserByWechat(KtvUser ktvUser){
        int insert = ktvUserMapper.insert(ktvUser);
        return ktvUser;
    }

    @Override
    public KtvUser queryById(String id) {
        KtvUser ktvUser = ktvUserMapper.selectById(id);
        return ktvUser;
    }

}
