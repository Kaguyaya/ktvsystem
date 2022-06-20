package com.kaguya.ktvadmin.service;

import com.kaguya.ktvadmin.dto.PageDto;

import com.kaguya.ktvadmin.pojo.KtvItemOrder;
import com.kaguya.ktvadmin.vo.PageVo;

import java.util.List;

public interface KtvItemOrderService {
    List<KtvItemOrder> searchAll();
    KtvItemOrder searchaById(String id);
    PageVo<KtvItemOrder> queryByPage(PageDto pageDto);
    KtvItemOrder insertOne(KtvItemOrder ktvItemOrder);
    KtvItemOrder delOne(KtvItemOrder ktvItemconsume);
     List<KtvItemOrder> searchByUserId(String id);
}
