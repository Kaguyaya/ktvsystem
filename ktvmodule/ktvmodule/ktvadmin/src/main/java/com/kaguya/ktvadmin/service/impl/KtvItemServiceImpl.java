package com.kaguya.ktvadmin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kaguya.ktvadmin.dto.PageDto;
import com.kaguya.ktvadmin.mapper.KtvItemMapper;
import com.kaguya.ktvadmin.pojo.KtvItem;
import com.kaguya.ktvadmin.service.KtvItemService;
import com.kaguya.ktvadmin.vo.PageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KtvItemServiceImpl implements KtvItemService {

    @Autowired
    KtvItemMapper ktvItemMapper;

    @Override
    public KtvItem addItem(KtvItem ktvItem) {
        ktvItemMapper.insert(ktvItem);
        return ktvItem;
    }

    @Override
    public KtvItem delItem(KtvItem ktvItem) {
        ktvItemMapper.deleteById(ktvItem);
        return ktvItem;
    }

    @Override
    public KtvItem altItem(KtvItem ktvItem) {
        ktvItemMapper.updateById(ktvItem);
        return ktvItem;
    }

    @Override
    public List<KtvItem> searchItemByTypeId(String typeid) {
        QueryWrapper<KtvItem> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("item_typeid",typeid);
        List<KtvItem> ktvItems = ktvItemMapper.selectList(queryWrapper);
        return ktvItems;
    }

    @Override
    public List<KtvItem> searchAllItem() {
        List<KtvItem> ktvItems = ktvItemMapper.selectList(null);
        return ktvItems;
    }

    @Override
    public KtvItem searchItemById(String id) {
        KtvItem ktvItem = ktvItemMapper.selectById(id);
        return ktvItem;
    }

    @Override
    public PageVo<KtvItem> queryByPage(PageDto pageDto) {
        IPage<KtvItem> page = new Page(pageDto.getCurrentPage(), pageDto.getPageSize());
        QueryWrapper<KtvItem> ktvItemQueryWrapper = new QueryWrapper<>();
        IPage<KtvItem> ktvItemIPage = ktvItemMapper.selectPage(page, ktvItemQueryWrapper);
        List<KtvItem> records = ktvItemIPage.getRecords();
        return new PageVo<KtvItem>(records,(int)ktvItemIPage.getTotal());
    }

    @Override
    public PageVo<KtvItem> queryByPageByTypeId(PageDto pageDto) {

        IPage<KtvItem> page = new Page(pageDto.getCurrentPage(), pageDto.getPageSize());
        QueryWrapper<KtvItem> ktvItemQueryWrapper = new QueryWrapper<>();
        ktvItemQueryWrapper.eq("item_typeid",pageDto.getQueryString());
        IPage<KtvItem> ktvItemIPage = ktvItemMapper.selectPage(page, ktvItemQueryWrapper);
        List<KtvItem> records = ktvItemIPage.getRecords();
        return new PageVo<KtvItem>(records,(int)ktvItemIPage.getTotal());
    }
}
