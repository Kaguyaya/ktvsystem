package com.kaguya.ktvadmin.controller;

import com.kaguya.ktvadmin.entity.CommonResult;
import com.kaguya.ktvadmin.pojo.UserRoomUsering;
import com.kaguya.ktvadmin.service.UserRoomUseringService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api("用户使用房间")
@CrossOrigin
@RequestMapping("/UserRoomUsering")
public class UserRoomUseringController {
    @Autowired
    private UserRoomUseringService userRoomUseringService;

    @PostMapping("/inser")
    public CommonResult insertUserRoomUsering(@RequestBody UserRoomUsering userRoomUsering){
        UserRoomUsering userRoomUsering1 = userRoomUseringService.setUserRoomUsering(userRoomUsering);
        return CommonResult.ok(userRoomUsering1);
    }

    @PostMapping("/changeState")
    public CommonResult changeState(@RequestBody UserRoomUsering userRoomUsering){
        UserRoomUsering userRoomUsering1 = userRoomUseringService.changeUserRoomState(userRoomUsering);
        return CommonResult.ok(userRoomUsering1);
    }

    @PostMapping("/getList")
    public CommonResult getList(){
        List<UserRoomUsering> userRoomUseringList = userRoomUseringService.getUserRoomUseringList();
        return CommonResult.ok(userRoomUseringList);
    }

    @PostMapping("/getUseringList")
    public CommonResult getUseringList(){
        List<UserRoomUsering> useringRoomList = userRoomUseringService.getUseringRoomList();
        return CommonResult.ok(useringRoomList);
    }


}
