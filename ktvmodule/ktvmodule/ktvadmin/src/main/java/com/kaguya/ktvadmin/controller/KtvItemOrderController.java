package com.kaguya.ktvadmin.controller;

import com.kaguya.ktvadmin.dto.PageDto;
import com.kaguya.ktvadmin.entity.CommonResult;
import com.kaguya.ktvadmin.pojo.KtvItemOrder;
import com.kaguya.ktvadmin.pojo.KtvItemconsume;
import com.kaguya.ktvadmin.pojo.KtvUser;
import com.kaguya.ktvadmin.service.KtvItemOrderService;
import com.kaguya.ktvadmin.vo.PageVo;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@Api
@RequestMapping("/KtvItemOrder")
public class KtvItemOrderController {
    @Autowired
    private KtvItemOrderService ktvItemOrderService;
    @PostMapping("/searchAll")
    public CommonResult searchAll(){
        List<KtvItemOrder> ktvItemconsumes = ktvItemOrderService.searchAll();
        return CommonResult.ok(ktvItemconsumes);
    }

    @PostMapping("/searchById")
    public CommonResult searchById(@RequestBody String id){
        KtvItemOrder ktvItemconsume = ktvItemOrderService.searchaById(id);
        return CommonResult.ok(ktvItemconsume);
    }

    @PostMapping("/insert")
    public CommonResult insertById(@RequestBody KtvItemOrder ktvItemOrder){
        KtvItemOrder ktvItemconsume1 = ktvItemOrderService.insertOne(ktvItemOrder);
        return CommonResult.ok(ktvItemconsume1);
    }

    @PostMapping("/delById")
    public CommonResult delById(@RequestBody KtvItemOrder ktvItemconsume){
        KtvItemOrder ktvItemconsume1 = ktvItemOrderService.delOne(ktvItemconsume);
        return CommonResult.ok(ktvItemconsume1);
    }

    @PostMapping("/searchItemconsumeByPageTypeId")
    public CommonResult searchItemByPageTypeId(@RequestBody PageDto pageDto){
        PageVo<KtvItemOrder> ktvItemconsumePageVo = ktvItemOrderService.queryByPage(pageDto);
        return CommonResult.ok(ktvItemconsumePageVo);
    }

    @PostMapping("/searchByUserId")
    public CommonResult searchByUserId(@RequestBody String id){
        List<KtvItemOrder> ktvItemOrders = ktvItemOrderService.searchByUserId(id);
        return CommonResult.ok(ktvItemOrders);
    }
}
