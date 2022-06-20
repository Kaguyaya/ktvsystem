package com.kaguya.ktvadmin.service;

import com.kaguya.ktvadmin.dto.PageDto;
import com.kaguya.ktvadmin.pojo.KtvItem;
import com.kaguya.ktvadmin.pojo.KtvUser;
import com.kaguya.ktvadmin.vo.PageVo;

import java.util.List;

public interface KtvItemService {
   KtvItem addItem(KtvItem ktvItem);
   KtvItem delItem(KtvItem ktvItem);
   KtvItem altItem(KtvItem ktvItem);
   List<KtvItem> searchItemByTypeId(String typeid);
   List<KtvItem> searchAllItem();
   KtvItem searchItemById(String id);
   PageVo<KtvItem> queryByPage(PageDto pageDto);
   PageVo<KtvItem> queryByPageByTypeId(PageDto pageDto);
}
