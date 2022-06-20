package com.kaguya.ktvadmin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kaguya.ktvadmin.dto.PageDto;
import com.kaguya.ktvadmin.mapper.KtvItemconsumeMapper;
import com.kaguya.ktvadmin.pojo.KtvItem;
import com.kaguya.ktvadmin.pojo.KtvItemconsume;
import com.kaguya.ktvadmin.service.KtvItemconsumeService;
import com.kaguya.ktvadmin.vo.PageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KtvItemconsumeServiceImpl implements KtvItemconsumeService {
    @Autowired
    KtvItemconsumeMapper ktvItemconsumeMapper;
    @Override
    public List<KtvItemconsume> searchAll() {
        List<KtvItemconsume> ktvItemconsumes = ktvItemconsumeMapper.selectList(null);
        return ktvItemconsumes;
    }

    @Override
    public List<KtvItemconsume> searchByIdList(String id) {
        QueryWrapper<KtvItemconsume> ktvItemconsumeQueryWrapper = new QueryWrapper<>();
        ktvItemconsumeQueryWrapper.eq("consume_order_id",id);
        List<KtvItemconsume> ktvItemconsumes = ktvItemconsumeMapper.selectList(ktvItemconsumeQueryWrapper);
        return ktvItemconsumes;
    }

    @Override
    public KtvItemconsume searchaById(String id) {
        KtvItemconsume ktvItemconsume = ktvItemconsumeMapper.selectById(id);
        return ktvItemconsume;
    }

    @Override
    public PageVo<KtvItemconsume> queryByPage(PageDto pageDto) {
        IPage<KtvItemconsume> page = new Page(pageDto.getCurrentPage(), pageDto.getPageSize());
        QueryWrapper<KtvItemconsume> ktvItemQueryWrapper = new QueryWrapper<>();
        IPage<KtvItemconsume> ktvItemIPage = ktvItemconsumeMapper.selectPage(page, ktvItemQueryWrapper);
        List<KtvItemconsume> records = ktvItemIPage.getRecords();
        return new PageVo<KtvItemconsume>(records,(int)ktvItemIPage.getTotal());
    }

    @Override
    public PageVo<KtvItemconsume> queryByPageById(PageDto pageDto) {
        IPage<KtvItemconsume> page = new Page(pageDto.getCurrentPage(), pageDto.getPageSize());
        QueryWrapper<KtvItemconsume> ktvItemQueryWrapper = new QueryWrapper<>();
        ktvItemQueryWrapper.eq("consume_order_id",pageDto.getQueryString());
        IPage<KtvItemconsume> ktvItemIPage = ktvItemconsumeMapper.selectPage(page, ktvItemQueryWrapper);
        List<KtvItemconsume> records = ktvItemIPage.getRecords();
        return new PageVo<KtvItemconsume>(records,(int)ktvItemIPage.getTotal());
    }

    @Override
    public KtvItemconsume insertOne(KtvItemconsume ktvItemconsume) {
        ktvItemconsume.setConsumeState(true);
        ktvItemconsumeMapper.insert(ktvItemconsume);
        KtvItemconsume ktvItemconsume1 = ktvItemconsumeMapper.selectById(ktvItemconsume);
        return ktvItemconsume1;
    }

    @Override
    public KtvItemconsume delOne(KtvItemconsume ktvItemconsume) {
        ktvItemconsumeMapper.deleteById(ktvItemconsume);
        return ktvItemconsume;
    }
}
