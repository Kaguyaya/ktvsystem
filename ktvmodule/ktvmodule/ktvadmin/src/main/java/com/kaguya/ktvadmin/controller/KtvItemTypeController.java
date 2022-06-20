package com.kaguya.ktvadmin.controller;

import com.kaguya.ktvadmin.dto.PageDto;
import com.kaguya.ktvadmin.entity.CommonResult;
import com.kaguya.ktvadmin.pojo.KtvItemType;
import com.kaguya.ktvadmin.service.KtvItemTypeService;
import com.kaguya.ktvadmin.vo.PageVo;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api
@RequestMapping("/ktvItemType")
@CrossOrigin
public class KtvItemTypeController {
    @Autowired
    KtvItemTypeService ktvItemTypeService;

    @PostMapping("/searchAll")
    public CommonResult searchAll(){
        List<KtvItemType> ktvItemTypes = ktvItemTypeService.searchAll();
        return CommonResult.ok(ktvItemTypes);
    }

    @PostMapping("/insertOne")
    public CommonResult insertOne(@RequestBody KtvItemType ktvItemType){
        KtvItemType ktvItemType1 = ktvItemTypeService.insertOne(ktvItemType);
        return CommonResult.ok(ktvItemType1);
    }
    @PostMapping("/delOne")
    public CommonResult delOne(@RequestBody KtvItemType ktvItemType){
        KtvItemType ktvItemType1 = ktvItemTypeService.delOne(ktvItemType);
        return CommonResult.ok(ktvItemType1);
    }
    @PostMapping("/queryByPage")
public  CommonResult queryByPage(@RequestBody PageDto pageDto){
        PageVo<KtvItemType> ktvItemTypePageVo = ktvItemTypeService.queryByPage(pageDto);
        return CommonResult.ok(ktvItemTypePageVo);
    }

    @PostMapping("/getItemAndType")
    public CommonResult getItemAndType(){
        return CommonResult.ok(ktvItemTypeService.getItemAndType());
    }
}
