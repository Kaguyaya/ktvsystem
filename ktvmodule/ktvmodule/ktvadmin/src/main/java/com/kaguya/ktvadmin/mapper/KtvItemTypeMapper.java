package com.kaguya.ktvadmin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kaguya.ktvadmin.pojo.KtvItem;
import com.kaguya.ktvadmin.pojo.KtvItemType;

import java.util.List;

public interface KtvItemTypeMapper extends BaseMapper<KtvItemType> {
     List<KtvItemType> getItemAndType();
}
