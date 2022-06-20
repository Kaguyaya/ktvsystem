package com.kaguya.ktvadmin.pojo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;

@Data
@TableName("ktv_room")
public class KtvRoom implements Serializable {
    @TableId
    private String roomId;
    private String roomName;
    private Date roomOpentime;
    private Date roomEndtime;
    private Date roomCreatetime;
    private Date roomUpdatetime;
    private int roomUsetime;
    private boolean roomIsopen;
}
