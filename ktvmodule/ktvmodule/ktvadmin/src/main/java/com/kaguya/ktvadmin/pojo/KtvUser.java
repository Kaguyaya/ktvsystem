package com.kaguya.ktvadmin.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@TableName("ktv_user")
@ApiModel("用户对象模型")
public class KtvUser implements Serializable {
    @TableId
    @ApiModelProperty(value = "用户id",required = true,example = "1")
    private String userId;
    @ApiModelProperty(value = "用户名",required = true,example = "467241156")
    private String userUsername;
    @ApiModelProperty(value = "用户昵称",required = true,example = "张三")
    private String userNickname;
    @ApiModelProperty(value = "用户密码",required = true,example = "vbhgnmccw")
    private String userPassword;
    @ApiModelProperty(value = "用户注册时间",required = true,example = "2002-07-24 17:04:23")
    private Date userRegistertime;
    @ApiModelProperty(value = "用户更新时间",required = true,example = "2003-07-24 17:04:23")
    private Date userUpdatetime;
    @ApiModelProperty(value = "用户余额",required = true,example = "100")
    private double userBalance;
    @ApiModelProperty(value = "用户使用时长",required = true,example = "10")
    private double userPlaytime;
    @ApiModelProperty(value = "用户生日",required = true,example = "2002-9-12")
    private LocalDate userBirthday;
    @ApiModelProperty(value = "用户生日",required = true,example = "123456")
    private String userWechatOpenid;
}
