package com.kaguya.ktvadmin.service;

import com.kaguya.ktvadmin.dto.PageDto;
import com.kaguya.ktvadmin.pojo.KtvItem;
import com.kaguya.ktvadmin.pojo.KtvItemconsume;
import com.kaguya.ktvadmin.vo.PageVo;

import java.util.List;

public interface KtvItemconsumeService {
    List<KtvItemconsume> searchAll();
    List<KtvItemconsume> searchByIdList(String id);
    KtvItemconsume searchaById(String id);
    PageVo<KtvItemconsume> queryByPage(PageDto pageDto);
    PageVo<KtvItemconsume> queryByPageById(PageDto pageDto);
    KtvItemconsume insertOne(KtvItemconsume ktvItemconsume);
    KtvItemconsume delOne(KtvItemconsume ktvItemconsume);
}
