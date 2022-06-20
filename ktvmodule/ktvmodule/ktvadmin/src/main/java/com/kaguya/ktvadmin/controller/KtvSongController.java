package com.kaguya.ktvadmin.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.kaguya.ktvadmin.config.SongProperties;
import com.kaguya.ktvadmin.dto.PageDto;
import com.kaguya.ktvadmin.entity.CommonResult;
import com.kaguya.ktvadmin.pojo.KtvSong;
import com.kaguya.ktvadmin.pojo.KtvUserSong;
import com.kaguya.ktvadmin.service.KtvSongService;
import com.kaguya.ktvadmin.utils.MinIoUtil;
import com.kaguya.ktvadmin.utils.ReturnSocketServer;
import com.kaguya.ktvadmin.utils.WebSocketServer;
import com.kaguya.ktvadmin.vo.PageVo;

import com.sun.tools.jconsole.JConsoleContext;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

import java.util.*;

@Api("歌曲列表")
@CrossOrigin
@RestController
@RequestMapping("/song")
public class KtvSongController {
    @Autowired
    private KtvSongService ktvSongService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private AmqpTemplate amqpTemplate;

    private WebSocketServer webSocketServer;

    private ReturnSocketServer returnSocketServer;
    @PostMapping("/upload")
    @ApiOperation("歌曲上传")
    public String songupload(@RequestParam MultipartFile file,String language) throws IOException{
        String upload = MinIoUtil.upload(language, file.getOriginalFilename(), file.getInputStream());
        return upload;
    }

    @PostMapping("/getSongList")
    @ApiOperation("获得歌曲文件")
    public CommonResult getSongList() {
        List<KtvSong> songList = ktvSongService.getSongList();
       return CommonResult.ok(songList);
    }

    @PostMapping("/setSongInfo")
    @ApiOperation("设置歌曲信息")
    public KtvSong  setSongInfo(@RequestBody KtvSong ktvSong){
        KtvSong ktvSong1 = ktvSongService.setSongInfo(ktvSong);
        return ktvSong1;
    }

    @Autowired
    private SongProperties songProperties;
    @ApiOperation("获取歌曲类型")
    @PostMapping("/getSongType")
    public String[] getSongType(){
        return songProperties.getSongTypes();
    }

    @ApiOperation("获取歌曲语言")
    @PostMapping("/getSongLanguage")
    public Map<String,String> getSongLanguage(){
        return songProperties.getSongLanguage();
    }

    @ApiOperation("更新歌曲")
    @PostMapping("/updateSong")
    public CommonResult updateSong(@RequestBody KtvSong ktvSong){
        int i = ktvSongService.updateSong(ktvSong);
        if (i>0){
            return CommonResult.ok();
        }
        else{
            return CommonResult.error("400");
        }
    }

    @ApiOperation("删除歌曲")
    @PostMapping("/deleteSong")
    public CommonResult deleteSong(@RequestBody KtvSong ktvSong){
        MinIoUtil.deleteFile(ktvSong.getSongLanguage(),ktvSong.getSongName());
        int i = ktvSongService.deleteSong(ktvSong);
        if (i>0){
            return CommonResult.ok("删除成功");
        }
        else{
            return CommonResult.error("400");
        }
    }

    @ApiOperation("分页搜索歌曲")
    @PostMapping("/querySongByPage")
    public CommonResult querySongByPage(@RequestBody PageDto pageDto){
        PageVo<KtvSong> ktvSongPageVo = ktvSongService.queryByPage(pageDto);
        return CommonResult.ok(ktvSongPageVo);
    }

    @ApiOperation("添加播放列表")
    @PostMapping("/insertPlayList")
    public CommonResult insertPlayList(@RequestBody KtvSong ktvSong) throws IOException {
        String fileUrl = MinIoUtil.getFileUrl(ktvSong.getSongLanguage(), ktvSong.getSongName());
        ktvSong.setSongUrl(fileUrl);
        List<KtvSong> list = redisTemplate.opsForList().range("playlist", 0, -1);
        System.out.println("list前:"+list);
        if (list.size()==0){
            redisTemplate.opsForList().leftPush("playlist",ktvSong);
            List<KtvSong> play = redisTemplate.opsForList().range("playlist", 0, -1);
            webSocketServer.sendInfo(CommonResult.ok(play.get(0)));
            return CommonResult.ok("初始化播放列表");
        }
        else
        {
            for (KtvSong songlist:
                 list) {
                if (songlist.getSongId().equals(ktvSong.getSongId())){
                    return CommonResult.ok("添加失败，已有相同歌曲");
                }
            }
        }
        redisTemplate.opsForList().leftPush("playlist",ktvSong);
        List<KtvSong> playlist = redisTemplate.opsForList().range("playlist", 0, -1);
        System.out.println("list后:"+playlist);
//        if (playlist.size()==1){
//                webSocketServer.sendInfo(CommonResult.ok(playlist.get(0)));
//            return CommonResult.ok("播放列表自动播放");
//        }
        return CommonResult.ok("添加["+ktvSong.getSongName()+"]成功");
    }

    @ApiOperation("弹出播放列表")
    @PostMapping("/popPlayList")
    public CommonResult popPlayList(){
        redisTemplate.opsForList().rightPop("playlist");
        return CommonResult.ok("自动播放下一首");
    }

    @ApiOperation("播放下一首")
    @PostMapping("/nextPlay")
    public CommonResult nextPlay() throws IOException {
        redisTemplate.opsForList().rightPop("playlist");
        List<KtvSong> playlist = redisTemplate.opsForList().range("playlist", 0, -1);
        System.out.println(playlist);
        if(playlist.size()==0){
            returnSocketServer.sendInfo(CommonResult.ok(playlist));
            return CommonResult.error("只有一首歌无法继续下一首歌曲");
        }
        else{
                webSocketServer.sendInfo(CommonResult.ok(playlist.get(playlist.size()-1)));
                returnSocketServer.sendInfo(CommonResult.ok(playlist));

        }

        return CommonResult.ok("播放下一首");
    }

    @ApiOperation("顶歌到播放列表最上面")
    @PostMapping("/headPlayMusic")
    public CommonResult headPlayMusic(@RequestBody KtvSong ktvSong){
        List<KtvSong> playlist = redisTemplate.opsForList().range("playlist", 0, -1);
        int i=0;
        for (KtvSong songlist:playlist
             ) {
            i++;
            if (ktvSong.getSongId()==songlist.getSongId()){
                KtvSong temp=(KtvSong) redisTemplate.opsForList().range("playlist",0,-1).get(1);
                redisTemplate.opsForList().set("playlist",1,songlist);
                redisTemplate.opsForList().set("playlist",i,temp);
            }
        }
        return CommonResult.ok("置顶成功");
    }

    @ApiOperation("获取播放列表")
    @GetMapping("/getPlayList")
    public CommonResult getPlayList(){
        List<KtvSong> list = redisTemplate.opsForList().range("playlist", 0, -1);
        return CommonResult.ok(list);
    }

    @RequestMapping("/send")
    public String send(){
        KtvSong playlist = (KtvSong) redisTemplate.opsForList().rightPop("playlist");
        String content="Date:"+new Date();
        amqpTemplate.convertAndSend("lyhTest1",playlist);
        return content;
    }

    @PostMapping("/sendAllWebSocket")
    public String test() {
        KtvSong playlist = (KtvSong) redisTemplate.opsForList().rightPop("playlist");
        String text = "websocket connect";
        try {
            webSocketServer.sendInfo(playlist);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return text;
    }

    @ApiOperation("清空播放列表")
    @GetMapping("/clearlist")
    public CommonResult clearlist(){
         redisTemplate.opsForList().trim("playlist",1,0);
        List<KtvSong> playlist = redisTemplate.opsForList().range("playlist", 0, -1);
        return CommonResult.ok(playlist);
    }

    @ApiOperation("统计用户点击歌曲次数")
    @PostMapping("/statistics")
    public CommonResult Statistics(@RequestBody String json){
        JSONObject jsonObject = JSON.parseObject(json);
        String userid = jsonObject.getObject("userid", String.class);
        String songid = jsonObject.getObject("songid", String.class);

        return CommonResult.ok();
    }

    @ApiOperation("根据语言和类型分页")
    @PostMapping("/queryByPagelanguageandtype")
    public CommonResult queryByPagelanguageandtype(@RequestBody PageDto pageDto){
        PageVo<KtvSong> ktvSongPageVo = ktvSongService.queryByPagelanguageandtype(pageDto);
        return CommonResult.ok(ktvSongPageVo);
    }

    @ApiOperation("根据推荐歌单歌曲id制作推荐列表")
    @PostMapping("/getRecommendSongList")
    public CommonResult RecommendSongList(@RequestBody ArrayList<String> list){
        ArrayList<KtvSong> arrayList=new ArrayList<>();
        for (String listvalue:list
             ) {
            KtvSong ktvSong = ktvSongService.queryById(listvalue);
            arrayList.add(ktvSong);
        }
        return CommonResult.ok(arrayList);
    }

}
