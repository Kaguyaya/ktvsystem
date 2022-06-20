package com.kaguya.ktvadmin.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.kaguya.ktvadmin.dto.PageDto;
import com.kaguya.ktvadmin.entity.CommonResult;
import com.kaguya.ktvadmin.pojo.KtvUser;
import com.kaguya.ktvadmin.service.KtvUserService;
import com.kaguya.ktvadmin.utils.JwtUtils;
import com.kaguya.ktvadmin.utils.WeChatUtil;
import com.kaguya.ktvadmin.vo.PageVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/user")
@CrossOrigin
@Api("用户接口")
public class KtvUserController {
    @Autowired
    private KtvUserService ktvUserService;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private HttpServletResponse httpServletResponse;
    @PostMapping("/list")
    @ApiOperation("获得用户列表")
    public CommonResult<List<KtvUser>> getUserList(){
        List<KtvUser> queryall = ktvUserService.queryall();
        return CommonResult.ok(queryall);
    }

    @ApiOperation("登录以及jwt功能")
    @PostMapping("/login")
    public CommonResult userLogin(@RequestBody KtvUser user){
        KtvUser ktvUser = ktvUserService.verifyPassword(user.getUserUsername());
        if (ktvUser==null){
            return CommonResult.error("用户名或密码错误");
        }
        else
        {
            String jwtToken = JwtUtils.getJwtToken(ktvUser.getUserId(), ktvUser.getUserNickname());
            httpServletResponse.addHeader("Access-Control-Expose-Headers","authorization");
            httpServletResponse.addHeader("authorization",jwtToken);
            return CommonResult.ok(jwtToken);
        }
    }
    @ApiOperation("根据ID搜索用户")
    @PostMapping("/getUserById")
    public CommonResult getUserById(@RequestBody String userId){
        KtvUser ktvUser = ktvUserService.queryById(userId);
        return CommonResult.ok(ktvUser);
    }
    @ApiOperation("根据用户进行充值")
    @PostMapping("/setUserMoney")
    public CommonResult setUserMoney(@RequestBody String json){
        JSONObject jsonObject = JSON.parseObject(json);
        KtvUser ktvuser = jsonObject.getObject("ktvuser", KtvUser.class);
        String money = jsonObject.getObject("money", String.class);
        KtvUser ktvUser = ktvUserService.userRecharge(ktvuser, Double.parseDouble(money));
        return CommonResult.ok(ktvUser);
    }

    @ApiOperation("分页查询用户")
    @PostMapping("/queryByPage")
    public CommonResult queryListByPage(@RequestBody PageDto pageDto){
        PageVo<KtvUser> ktvUserPageVo = ktvUserService.queryByPage(pageDto);
        return CommonResult.ok(ktvUserPageVo);
    }

    @PostMapping("/getSessionAndOpen")
    public CommonResult getSessionKeyAndOpenId(@RequestBody String json){
        WeChatUtil weChatUtil=new WeChatUtil();
        JSONObject jsonObject = JSON.parseObject(json);
        String code = jsonObject.getObject("code", String.class);
        String encryptedData = jsonObject.getObject("encryptedData", String.class);
        String iv = jsonObject.getObject("iv", String.class);
        JSONObject sessionKeyAndOpenid = weChatUtil.getSessionKeyAndOpenid(code);
        String session_key = sessionKeyAndOpenid.getObject("session_key", String.class);
        String openid = sessionKeyAndOpenid.getObject("openid", String.class);
        String userInfo = WeChatUtil.getUserInfo(encryptedData, session_key, iv);
        KtvUser queryuser = ktvUserService.queryByOpenid(openid);
        JSONObject infoJson = JSON.parseObject(userInfo);
        System.out.println(queryuser);
        if (queryuser==null){
            KtvUser user=new KtvUser();
            user.setUserNickname(infoJson.getObject("nickName",String.class));
            user.setUserWechatOpenid(openid);
            KtvUser registeruser = ktvUserService.registerUserByWechat(user);
            System.out.println("register:"+registeruser);
            return CommonResult.ok(registeruser);
        }
        else{
            return CommonResult.ok(queryuser);
        }
    }

    @ApiOperation("获取用户统计数量")
    @PostMapping
    public CommonResult getStatistics(){
        List<KtvUser> queryall = ktvUserService.queryall();
        for (KtvUser k:queryall
             ) {
            System.out.println(redisTemplate.opsForValue().get(k.getUserId()));
        }
        return CommonResult.ok();
    }


    @ApiOperation("更新用户数据")
    @PostMapping("/updateuser")
    public CommonResult updateuserById(@RequestBody KtvUser ktvUser){
        KtvUser updateuserinfo = ktvUserService.updateuserinfo(ktvUser);
        return CommonResult.ok(updateuserinfo);
    }

    @ApiOperation("刷新用户登录时间")
    @PostMapping("/updatelogintime")
    public CommonResult updateloginTime(@RequestBody String userid){
        KtvUser user = ktvUserService.updateLoginTime(userid);
        return CommonResult.ok(user);
    }
}
