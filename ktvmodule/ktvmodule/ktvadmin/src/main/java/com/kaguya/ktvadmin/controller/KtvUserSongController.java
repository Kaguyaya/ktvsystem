package com.kaguya.ktvadmin.controller;

import com.kaguya.ktvadmin.entity.CommonResult;
import com.kaguya.ktvadmin.pojo.KtvSong;
import com.kaguya.ktvadmin.pojo.KtvUser;
import com.kaguya.ktvadmin.pojo.KtvUserSong;
import com.kaguya.ktvadmin.service.KtvUserService;
import com.kaguya.ktvadmin.service.KtvUserSongService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.*;

@RestController
@CrossOrigin
@RequestMapping("/UserSong")
@Api("用户次数计算")
public class KtvUserSongController {
    @Autowired
    private KtvUserSongService ktvUserSongService;

    @ApiOperation("通过用户id获取用户访问次数")
    @PostMapping("/getUserSongInfo")
    public CommonResult getUserSongInfoByUserid(@RequestBody KtvUserSong ktvUserSong){
        List<KtvUserSong> userTimes = ktvUserSongService.getUserTimes(ktvUserSong);
        return CommonResult.ok(userTimes);
    }

    @ApiOperation("增加用户访问次数")
    @PostMapping("/setUserSongInfo")
    public CommonResult setUserSongInfo(@RequestBody KtvUserSong ktvUserSong){
        KtvUserSong ktvUserSong1 = ktvUserSongService.setUserSong(ktvUserSong);
        return CommonResult.ok(ktvUserSong1);
    }

    @ApiOperation("获取所有用户访问次数")
    @PostMapping("/getAllUserInfo")
    public CommonResult getAllUserInfo(){
        List<KtvUserSong> allUserTimes = ktvUserSongService.getAllUserTimes();
        for (KtvUserSong temp:allUserTimes
             ) {
            HashMap<String, Double> userCount = ktvUserSongService.getUserCount();

        }
        return CommonResult.ok(allUserTimes);
    }

    @ApiOperation("获取每个用户访问次数")
    @PostMapping("/getuserlinkinfo")
    public CommonResult getuserlinkinfo(){
        List<KtvUserSong> userTimes = ktvUserSongService.getUserTimes();

        return CommonResult.ok(userTimes);
    }

    @ApiOperation("获取每个歌曲访问次数")
    @PostMapping("/getsonglinkinfo")
    public CommonResult getsonglinkinfo(){
        List<KtvUserSong> songTimes = ktvUserSongService.getSongTimes();
        Double allSongtimes = ktvUserSongService.getAllSongtimes();
        HashMap<String,Double> map=new HashMap<>();
        for (KtvUserSong temp:songTimes
             ) {
            double value=(double)temp.getTimes()/(double)allSongtimes;
            map.put(temp.getSongid(),new BigDecimal(value).setScale(2, RoundingMode.HALF_UP).doubleValue());
        }

        return CommonResult.ok(map);
    }

    @PostMapping("/getRecommendList")
    public CommonResult getRecommendList(@RequestBody String id){
        /**
         * 此方法使用协同过滤算法，大概意思就是从数据库拿到所有的用户收藏和当前的用户收藏作为比对，找出和当前用户收藏夹最接近的一个用户，
         * 然后将那个用户的收藏中当前登录用户没有的展示给当前用户
         * 例:比如当前登录userid = 1
         * 用户关联表中数据，
         * 用户1关联歌曲｛2，3｝（当前登录用户收藏的歌曲）
         * 用户2关联歌曲｛2，3，4｝
         * 用户3关联歌曲｛3，4，5｝
         * 用户4关联歌曲｛1，5｝
         **/
        KtvUserSong userSong = new KtvUserSong();
        userSong.setUserid(id);
        List<KtvUserSong> userTimes = ktvUserSongService.getUserTimes(userSong);
        List<KtvUserSong> all = ktvUserSongService.getAll();
        HashMap<String,List<String>> userRecommend=new HashMap<>();
        for (int i=0;i<all.size();i++){
            String userId=all.get(i).getUserid();
            String songId=all.get(i).getSongid();
            if (userRecommend.containsKey(userId)){
                List<String> recommendTemp = userRecommend.get(userId);
                recommendTemp.add(songId);
                userRecommend.put(userId,recommendTemp);
            }else{
                List<String>recommendTemp=new ArrayList<>();
                recommendTemp.add(songId);
                userRecommend.put(userId,recommendTemp);
            }
        }

        List<String> myRecommend=new ArrayList<>();
        if (userRecommend.containsKey(id)){
            myRecommend=userRecommend.get(id);
        }else{
            myRecommend=new ArrayList<>();
        }



        HashSet<String>myRecommendSet=new HashSet<String>(myRecommend);
        double maxvalue=0;
        String maxId="-1";
        for (String key:userRecommend.keySet()){
            if (key.equals(id)){
                continue;
            }
            List<String> thisRecommend=userRecommend.get(key);
            HashSet<String> thisRecommendSet=new HashSet<>(thisRecommend);
            HashSet<String> intersection=new HashSet<>(myRecommendSet);
            intersection.retainAll(thisRecommendSet);
            HashSet<String> union=new HashSet<>(myRecommendSet);
            union.addAll(thisRecommendSet);
            if (union.size()==0){
                continue;
            }else{
                double ratio=(double)intersection.size()/union.size();
                if (ratio>maxvalue){
                    maxvalue=ratio;
                    maxId=key;
                }
            }
        }
        System.out.println("maxvalue"+maxvalue);
        System.out.println("maxid"+maxId);

        List<String> SongRecommendList=new ArrayList<>();
        if (maxId.equals("-1")){
            maxId=id;
        }
        else{
            HashSet<String> differenceTemp=new HashSet<>(userRecommend.get(maxId));
            differenceTemp.removeAll(myRecommend);
            System.out.println(differenceTemp);
            SongRecommendList= new ArrayList<>(differenceTemp);
        }

        DecimalFormat df=new DecimalFormat("0.00");
        String similar = "歌单相似度："+df.format((float)myRecommendSet.size()/(myRecommendSet.size()+SongRecommendList.size())*100)+"%";
        System.out.println(similar);
        return CommonResult.ok(SongRecommendList);
    }
}
