package com.kaguya.ktvadmin.controller;

import com.kaguya.ktvadmin.dto.PageDto;
import com.kaguya.ktvadmin.entity.CommonResult;
import com.kaguya.ktvadmin.pojo.KtvItem;
import com.kaguya.ktvadmin.service.KtvItemService;
import com.kaguya.ktvadmin.utils.MinIoUtil;
import com.kaguya.ktvadmin.vo.PageVo;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@CrossOrigin
@Api("物品库存")
@RequestMapping("/KtvItem")
public class KtvItemController {
    @Autowired
    KtvItemService ktvItemService;

    @PostMapping("/addItem")
    public CommonResult addItem(@RequestBody KtvItem ktvItem){
        ktvItemService.addItem(ktvItem);
     return CommonResult.ok(ktvItem);
    }

    @PostMapping("/uploadImg")
    public String uploadImg(@RequestBody MultipartFile file,String itemName)throws Exception{
        String upload = MinIoUtil.upload("image", itemName+".png", file.getInputStream());
        System.out.println(itemName);
        KtvItem ktvItem = ktvItemService.searchItemById(itemName);
        ktvItem.setItemImg(upload);
        ktvItemService.altItem(ktvItem);
        return upload;
    }
    @PostMapping("/delItem")
    public CommonResult delItem(@RequestBody KtvItem ktvItem){
        ktvItemService.delItem(ktvItem);
        return CommonResult.ok(ktvItem);
    }

    @PostMapping("/altItem")
    public CommonResult altItem(@RequestBody KtvItem ktvItem){
        ktvItemService.altItem(ktvItem);
        return CommonResult.ok(ktvItem);
    }

    @PostMapping("/searchAllItem")
    public CommonResult searchAllItem(){
        List<KtvItem> ktvItems = ktvItemService.searchAllItem();
        return CommonResult.ok(ktvItems);
    }

    @PostMapping("/searchItemsById")
    public CommonResult searchItemsById(@RequestBody String id){
        KtvItem ktvItem = ktvItemService.searchItemById(id);
        return CommonResult.ok(ktvItem);
    }

    @PostMapping("/searchItemByPage")
    public CommonResult searchItemByPage(@RequestBody PageDto pageDto){
        PageVo<KtvItem> ktvItemPageVo = ktvItemService.queryByPage(pageDto);
        return CommonResult.ok(ktvItemPageVo);
    }

    @PostMapping("/searchItemByTypeId")
    public CommonResult searchItemByTypeId(@RequestBody String id){
        List<KtvItem> ktvItems = ktvItemService.searchItemByTypeId(id);
        return CommonResult.ok(ktvItems);
    }

    @PostMapping("/searchItemByPageTypeId")
    public CommonResult searchItemByPageTypeId(@RequestBody PageDto pageDto){
        PageVo<KtvItem> ktvItemPageVo = ktvItemService.queryByPageByTypeId(pageDto);
        return CommonResult.ok(ktvItemPageVo);
    }

    @PostMapping("/mixItemIdAndItemType")
    public CommonResult mixItemIdAndItemType(){
        return  null;
    }
}


