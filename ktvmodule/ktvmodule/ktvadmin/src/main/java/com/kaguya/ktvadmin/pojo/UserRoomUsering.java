package com.kaguya.ktvadmin.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel("用户使用房间表")
@TableName("user_room_usering")
public class UserRoomUsering implements Serializable {
    @TableId
   private String userRoomId;
   private String userRoomUserid;
   private String userRoomRoomid;
   private Date userRoomUpdatetime;
   private Date userRoomCreatetime;
   private int userRoomStatus;
}
