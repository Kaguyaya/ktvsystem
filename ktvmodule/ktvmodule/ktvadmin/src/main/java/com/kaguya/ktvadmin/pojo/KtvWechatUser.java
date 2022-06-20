package com.kaguya.ktvadmin.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("ktv_wechat_user")
public class KtvWechatUser {
    @TableId
    private String id;
    private String nickname;
    private String avatar;
    private String mobile;
    private Date createtime;
    private Date updatetime;
}
