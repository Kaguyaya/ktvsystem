package com.kaguya.ktvadmin.controller;

import com.kaguya.ktvadmin.entity.CommonResult;
import com.kaguya.ktvadmin.pojo.KtvRoom;
import com.kaguya.ktvadmin.service.KtvRoomService;
import com.kaguya.ktvadmin.utils.QRCodeUtil;
import com.kaguya.ktvadmin.utils.WebSocketServer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.sql.Time;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@CrossOrigin
@RestController
@Api("房间列表")
@RequestMapping("/room")
@Slf4j
public class KtvRoomController {
    @Autowired
    private KtvRoomService ktvRoomService;

    private WebSocketServer webSocketServer;

    @ApiOperation("获取房间列表")
    @PostMapping("/getRoomlist")
    public CommonResult getRoomList(){
        List<KtvRoom> roomList = ktvRoomService.getRoomList();
        return CommonResult.ok(roomList);
    }

    @ApiOperation("创建房间")
    @PostMapping("/createRoom")
    public CommonResult createRoom(@RequestBody KtvRoom ktvRoom){
        KtvRoom room = ktvRoomService.createRoom(ktvRoom);
        return CommonResult.ok(room);
    }

    @ApiOperation("获取随机id")
    @PostMapping("/getRandomId")
    public CommonResult getRandomId(){
        UUID uuid = UUID.randomUUID();
        return CommonResult.ok(uuid.toString().replace("-",""));
    }

    @ApiOperation("获取二维码")
    @GetMapping("/qrcode")
    public void getQRCode(HttpServletResponse response) throws Exception {
        try {
            /*
             * 调用工具类生成二维码并输出到输出流中
             */
            QRCodeUtil.createCodeToOutputStream("helloworld", response.getOutputStream());
            log.info("成功生成二维码!");
        } catch (IOException e) {
            log.error("发生错误， 错误信息是：{}！", e.getMessage());
        }
    }

    @ApiOperation("获取房间二维码")
    @PostMapping("/getRoomQRcode")
    public void getRoomQRcode(HttpServletResponse response,@RequestBody KtvRoom room){
        try {

            System.out.println(room.getRoomId());
                QRCodeUtil.createCodeToOutputStream(room.getRoomId(), response.getOutputStream());
                log.info("成功生成二维码!");

        } catch (IOException e) {
            log.error("发生错误， 错误信息是：{}！", e.getMessage());
        }
    }

    @ApiOperation("通过id获取房间对象")
    @PostMapping("/searchRoomById")
    public CommonResult searchRoomById(@RequestBody String id){
        KtvRoom ktvRoom = ktvRoomService.searchRoom(id);
        return CommonResult.ok(ktvRoom);
    }

    @ApiOperation("开启房间使用时间")
    @PostMapping("/startRoom")
    public CommonResult startRoom(@RequestBody KtvRoom ktvRoom) throws IOException {
        ktvRoom.setRoomIsopen(true);

        ktvRoom.setRoomUsetime((int) (ktvRoom.getRoomEndtime().getTime()-ktvRoom.getRoomOpentime().getTime()));
        //毫秒
        KtvRoom Room = ktvRoomService.startRoom(ktvRoom);
        String message="open";
        WebSocketServer.sendInfo(CommonResult.ok(message));
        return CommonResult.ok(ktvRoom);
    }

    @ApiOperation("关闭房间")
    @PostMapping("/closeRoom")
    public CommonResult closeRoom(@RequestBody KtvRoom ktvRoom){
        KtvRoom ktvRoom1 = ktvRoomService.closeRoom(ktvRoom);
        return CommonResult.ok(ktvRoom1);
    }
}
