package com.kaguya.ktvadmin.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.kaguya.ktvadmin.dto.PageDto;
import com.kaguya.ktvadmin.entity.CommonResult;
import com.kaguya.ktvadmin.pojo.KtvRecharge;
import com.kaguya.ktvadmin.pojo.KtvUser;
import com.kaguya.ktvadmin.service.KtvRechargeService;
import com.kaguya.ktvadmin.vo.PageVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.util.Json;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@Api("充值接口")
@RequestMapping("/recharge")
public class KtvRechargeController {
    @Autowired
    private KtvRechargeService rechargeService;
    @ApiOperation("获取充值记录")
    @PostMapping("/list")
    public CommonResult<List<KtvRecharge>> getRechargeList(){
        List<KtvRecharge> ktvRecharges = rechargeService.queryAll();
        return CommonResult.ok(ktvRecharges);
    }

    @ApiOperation("设置充值金额记录")
    @PostMapping("/rechargeRecord")
    public CommonResult setRecharge(@RequestBody String json){
        JSONObject jsonObject = JSON.parseObject(json);
        KtvUser ktvuser = jsonObject.getObject("ktvuser", KtvUser.class);
        String money = jsonObject.getObject("money", String.class);
        KtvRecharge ktvRecharge = rechargeService.setRecharge(ktvuser, Double.parseDouble(money));
        return  CommonResult.ok(ktvRecharge);
    }

    @ApiOperation("分页查询")
    @PostMapping("/listPage")
    public CommonResult queryByPage(@RequestBody PageDto pageDto){
        PageVo<KtvRecharge> ktvRechargePageVo = rechargeService.queryByPage(pageDto);
        return CommonResult.ok(ktvRechargePageVo);
    }

    @ApiOperation("通过用户id查询")
    @PostMapping("/queryByUserId")
    public CommonResult queryByUserId(@RequestBody String userid){
        List<KtvRecharge> ktvRecharges = rechargeService.queryByuserId(userid);
        return CommonResult.ok(ktvRecharges);
    }
}
