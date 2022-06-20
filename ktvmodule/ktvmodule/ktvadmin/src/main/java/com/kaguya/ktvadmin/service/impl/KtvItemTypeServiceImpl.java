package com.kaguya.ktvadmin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kaguya.ktvadmin.dto.PageDto;
import com.kaguya.ktvadmin.mapper.KtvItemMapper;
import com.kaguya.ktvadmin.mapper.KtvItemTypeMapper;
import com.kaguya.ktvadmin.pojo.KtvItem;
import com.kaguya.ktvadmin.pojo.KtvItemType;
import com.kaguya.ktvadmin.service.KtvItemTypeService;
import com.kaguya.ktvadmin.vo.PageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KtvItemTypeServiceImpl extends ServiceImpl<KtvItemTypeMapper,KtvItemType> implements KtvItemTypeService {

    @Autowired
    KtvItemTypeMapper ktvItemTypeMapper;
    @Autowired
    KtvItemMapper ktvItemMapper;
    @Override
    public List<KtvItemType> searchAll() {
        List<KtvItemType> ktvItemTypes = ktvItemTypeMapper.selectList(null);
        return ktvItemTypes;
    }

    @Override
    public KtvItemType insertOne(KtvItemType ktvItemType) {
        ktvItemTypeMapper.insert(ktvItemType);
        return ktvItemType;
    }

    @Override
    public KtvItemType delOne(KtvItemType ktvItemType) {
        List<KtvItem> ktvItems = ktvItemMapper.selectList(null);
        for (KtvItem temp:ktvItems
             ) {
            if (temp.getItemTypeid().equals(ktvItemType.getTypeId())) {
                ktvItemMapper.deleteById(temp.getItemId());
            }
        }
        ktvItemTypeMapper.deleteById(ktvItemType);
        return ktvItemType;
    }

    @Override
    public List<KtvItemType> getItemAndType() {
        return ktvItemTypeMapper.getItemAndType();
    }

    @Override
    public PageVo<KtvItemType> queryByPage(PageDto pageDto) {
        IPage<KtvItemType> page = new Page(pageDto.getCurrentPage(), pageDto.getPageSize());
        QueryWrapper<KtvItemType> ktvItemQueryWrapper = new QueryWrapper<>();
        IPage<KtvItemType> ktvItemIPage = ktvItemTypeMapper.selectPage(page, ktvItemQueryWrapper);
        List<KtvItemType> records = ktvItemIPage.getRecords();
        return new PageVo<KtvItemType>(records,(int)ktvItemIPage.getTotal());
    }


}
