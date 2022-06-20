package com.kaguya.ktvadmin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kaguya.ktvadmin.dto.PageDto;
import com.kaguya.ktvadmin.mapper.KtvRechargeMapper;
import com.kaguya.ktvadmin.pojo.KtvRecharge;
import com.kaguya.ktvadmin.pojo.KtvUser;
import com.kaguya.ktvadmin.service.KtvRechargeService;
import com.kaguya.ktvadmin.vo.KtvRechargeVo;
import com.kaguya.ktvadmin.vo.PageVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class KtvRechargeServiceImpl implements KtvRechargeService {
    @Autowired
    private KtvRechargeMapper rechargeMapper;
    @Override
    public List<KtvRecharge> queryAll() {
        List list = rechargeMapper.selectList(null);
        return list;
    }

    @Override
    public KtvRecharge setRecharge(KtvUser ktvUser, double money) {
        KtvRecharge ktvRecharge=new KtvRecharge();
        ktvRecharge.setRechargeuserId(ktvUser.getUserId());
        ktvRecharge.setRechargeAmount(money);
        ktvRecharge.setRechargeUsername(ktvUser.getUserNickname());
        ktvRecharge.setRechargeTime(new Date());
        rechargeMapper.insert(ktvRecharge);
        return ktvRecharge;
    }

    @Override
    public PageVo<KtvRecharge> queryByPage(PageDto pageDto) {
        IPage<KtvRecharge> page = new Page(pageDto.getCurrentPage(), pageDto.getPageSize());
        QueryWrapper<KtvRecharge>queryWrapper=new QueryWrapper<>();
        if (pageDto.getQueryString()!=null){
            queryWrapper.like("recharge_username",pageDto.getQueryString());
        }
        IPage<KtvRecharge> rechargeIPage  = rechargeMapper.selectPage(page, queryWrapper);
        List<KtvRecharge> records = rechargeIPage.getRecords();
        return new PageVo<KtvRecharge>(records,(int)rechargeIPage.getTotal());
    }

    @Override
    public List<KtvRecharge> queryByuserId(String userid) {
        QueryWrapper<KtvRecharge> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("rechargeuser_id",userid);
        List<KtvRecharge> ktvRecharges = rechargeMapper.selectList(queryWrapper);
        return ktvRecharges;
    }

}
