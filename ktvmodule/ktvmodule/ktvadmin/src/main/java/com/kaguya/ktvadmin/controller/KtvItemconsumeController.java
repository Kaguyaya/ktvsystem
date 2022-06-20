package com.kaguya.ktvadmin.controller;

import com.kaguya.ktvadmin.dto.PageDto;
import com.kaguya.ktvadmin.entity.CommonResult;
import com.kaguya.ktvadmin.pojo.KtvItemconsume;
import com.kaguya.ktvadmin.service.KtvItemconsumeService;
import com.kaguya.ktvadmin.utils.OrderSocketServer;
import com.kaguya.ktvadmin.utils.WebSocketServer;
import com.kaguya.ktvadmin.vo.PageVo;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin
@Api
@RequestMapping("/Itemconsume")
public class KtvItemconsumeController {
    @Autowired
    KtvItemconsumeService ktvItemconsumeService;
    @PostMapping("/searchAll")
    public CommonResult searchAll(){
        List<KtvItemconsume> ktvItemconsumes = ktvItemconsumeService.searchAll();
        return CommonResult.ok(ktvItemconsumes);
    }

    @PostMapping("/searchById")
    public CommonResult searchById(@RequestBody String id){
        KtvItemconsume ktvItemconsume = ktvItemconsumeService.searchaById(id);
        return CommonResult.ok(ktvItemconsume);
    }

    @PostMapping("/searchByIdList")
    public CommonResult searchByIdList(@RequestBody String id){
        List<KtvItemconsume> ktvItemconsumes = ktvItemconsumeService.searchByIdList(id);
        return CommonResult.ok(ktvItemconsumes);
    }

    @PostMapping("/insertById")
    public CommonResult insertById(@RequestBody KtvItemconsume ktvItemconsume) {
        KtvItemconsume ktvItemconsume1 = ktvItemconsumeService.insertOne(ktvItemconsume);
        return CommonResult.ok(ktvItemconsume1);
    }

    @PostMapping("/tips")
    public  CommonResult tips(){
        try {
            OrderSocketServer.sendInfo(CommonResult.ok("订单提示"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return CommonResult.ok("订单提示");
    }

    @PostMapping("/delById")
    public CommonResult delById(@RequestBody KtvItemconsume ktvItemconsume){
        KtvItemconsume ktvItemconsume1 = ktvItemconsumeService.delOne(ktvItemconsume);
        return CommonResult.ok(ktvItemconsume1);
    }

    @PostMapping("/searchItemconsumeByPageTypeId")
    public CommonResult searchItemByPageTypeId(@RequestBody PageDto pageDto){
        PageVo<KtvItemconsume> ktvItemconsumePageVo = ktvItemconsumeService.queryByPage(pageDto);
        return CommonResult.ok(ktvItemconsumePageVo);
    }

    @PostMapping("/searchItemConsumeByPageById")
    public CommonResult queryByPageById(@RequestBody PageDto pageDto){
        PageVo<KtvItemconsume> ktvItemconsumePageVo = ktvItemconsumeService.queryByPage(pageDto);
        return CommonResult.ok(ktvItemconsumePageVo);
    }
}
