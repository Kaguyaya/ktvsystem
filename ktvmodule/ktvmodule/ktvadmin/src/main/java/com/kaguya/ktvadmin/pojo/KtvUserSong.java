package com.kaguya.ktvadmin.pojo;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("ktv_user_song")
public class KtvUserSong {
    @TableId
    private String id;
    private String userid;
    private String songid;
    private Double times;
}
