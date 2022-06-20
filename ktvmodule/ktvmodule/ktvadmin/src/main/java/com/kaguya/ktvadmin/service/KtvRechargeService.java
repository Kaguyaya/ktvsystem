package com.kaguya.ktvadmin.service;

import com.kaguya.ktvadmin.dto.PageDto;
import com.kaguya.ktvadmin.pojo.KtvRecharge;
import com.kaguya.ktvadmin.pojo.KtvUser;
import com.kaguya.ktvadmin.vo.KtvRechargeVo;
import com.kaguya.ktvadmin.vo.PageVo;

import java.util.List;

public interface KtvRechargeService {
    List<KtvRecharge> queryAll();
    KtvRecharge setRecharge(KtvUser ktvUser,double money);
    PageVo<KtvRecharge>queryByPage(PageDto pageDto);
    List<KtvRecharge>queryByuserId(String userid);
}
