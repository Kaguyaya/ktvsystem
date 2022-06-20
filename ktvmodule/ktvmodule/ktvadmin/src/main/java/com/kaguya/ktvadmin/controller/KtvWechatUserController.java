package com.kaguya.ktvadmin.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.kaguya.ktvadmin.entity.CommonResult;
import com.kaguya.ktvadmin.pojo.KtvWechatUser;
import com.kaguya.ktvadmin.service.KtvWechatUserService;
import com.kaguya.ktvadmin.utils.WeChatUtil;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@Api
@RequestMapping("/wechat")
public class KtvWechatUserController {
    @Autowired
    private KtvWechatUserService ktvWechatUserService;
    @PostMapping("/setUserinfo")
    public CommonResult setUserInfo(@RequestBody String ktvWechatUser){
//        KtvWechatUser ktvWechatUser1 = ktvWechatUserService.setUserInfo(ktvWechatUser);
        return  CommonResult.ok(ktvWechatUser);
    }

    @GetMapping("/getUserinfo")
    public CommonResult getUserInfo(){
        List<KtvWechatUser> userInfo = ktvWechatUserService.getUserInfo();
        return CommonResult.ok(userInfo);
    }


    @PostMapping("/getUserinfoById")
    public CommonResult getUserInfoById(@RequestBody KtvWechatUser ktvWechatUser){
        KtvWechatUser ktvWechatUser1 = ktvWechatUserService.searchUserInfoById(ktvWechatUser.getId());
        return CommonResult.ok(ktvWechatUser1);
    }

    @PostMapping("/getSessionAndOpen")
    public CommonResult getSessionKeyAndOpenId(@RequestBody String json){
        WeChatUtil weChatUtil=new WeChatUtil();
        System.out.println(json);
        JSONObject jsonObject = JSON.parseObject(json);
        String code = jsonObject.getObject("code", String.class);
        String encryptedData = jsonObject.getObject("encryptedData", String.class);
        String iv = jsonObject.getObject("iv", String.class);
        System.out.println(encryptedData);
        System.out.println(iv);
        JSONObject sessionKeyAndOpenid = weChatUtil.getSessionKeyAndOpenid(code);
        System.out.println(sessionKeyAndOpenid);
        String session_key = sessionKeyAndOpenid.getObject("session_key", String.class);
        String openid = sessionKeyAndOpenid.getObject("openid", String.class);
        String userInfo = WeChatUtil.getUserInfo(encryptedData, session_key, iv);
        String nickName = JSON.parseObject(userInfo).getObject("nickName", String.class);
        System.out.println(openid);
        System.out.println(nickName);
        System.out.println(userInfo);
        return CommonResult.ok(userInfo);
    }
}
