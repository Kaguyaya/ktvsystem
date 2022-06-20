package com.kaguya.ktvadmin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kaguya.ktvadmin.dto.PageDto;
import com.kaguya.ktvadmin.pojo.KtvItem;
import com.kaguya.ktvadmin.pojo.KtvItemType;
import com.kaguya.ktvadmin.vo.PageVo;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface KtvItemTypeService extends IService<KtvItemType> {
    List<KtvItemType> searchAll();
    KtvItemType insertOne(KtvItemType ktvItemType);
    KtvItemType delOne(KtvItemType ktvItemType);
    List<KtvItemType> getItemAndType();
    PageVo<KtvItemType> queryByPage(PageDto pageDto);
}
