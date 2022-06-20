package com.kaguya.ktvadmin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kaguya.ktvadmin.dto.PageDto;
import com.kaguya.ktvadmin.mapper.KtvItemOrderMapper;
import com.kaguya.ktvadmin.pojo.KtvItem;
import com.kaguya.ktvadmin.pojo.KtvItemOrder;
import com.kaguya.ktvadmin.service.KtvItemOrderService;
import com.kaguya.ktvadmin.vo.PageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KtvItemOrderServiceImpl implements KtvItemOrderService {
    @Autowired
    private KtvItemOrderMapper ktvItemOrderMapper;
    @Override
    public List<KtvItemOrder> searchAll() {
        List<KtvItemOrder> ktvItemOrders = ktvItemOrderMapper.selectList(null);
        return ktvItemOrders;
    }

    @Override
    public KtvItemOrder searchaById(String id) {
        KtvItemOrder ktvItemOrder = ktvItemOrderMapper.selectById(id);
        return ktvItemOrder;
    }

    @Override
    public PageVo<KtvItemOrder> queryByPage(PageDto pageDto) {
        IPage<KtvItemOrder> page = new Page(pageDto.getCurrentPage(), pageDto.getPageSize());
        QueryWrapper<KtvItemOrder> ktvItemQueryWrapper = new QueryWrapper<>();
        IPage<KtvItemOrder> ktvItemIPage = ktvItemOrderMapper.selectPage(page, ktvItemQueryWrapper);
        List<KtvItemOrder> records = ktvItemIPage.getRecords();
        return new PageVo<KtvItemOrder>(records,(int)ktvItemIPage.getTotal());
    }

    @Override
    public KtvItemOrder insertOne(KtvItemOrder ktvItemOrder) {
        ktvItemOrderMapper.insert(ktvItemOrder);
        return ktvItemOrder;
    }

    @Override
    public KtvItemOrder delOne(KtvItemOrder ktvItemOrder) {
        ktvItemOrderMapper.deleteById(ktvItemOrder);
        return ktvItemOrder;
    }

    public List<KtvItemOrder> searchByUserId(String id){
        QueryWrapper<KtvItemOrder> ktvItemOrderQueryWrapper = new QueryWrapper<>();
        ktvItemOrderQueryWrapper.eq("ktv_user_id",id);
        List<KtvItemOrder> ktvItemOrders = ktvItemOrderMapper.selectList(ktvItemOrderQueryWrapper);
        return ktvItemOrders;
    }
}
