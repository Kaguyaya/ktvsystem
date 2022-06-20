package com.kaguya.ktvadmin.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("ktv_item")

public class KtvItem implements Serializable {
    @TableId
    private String itemId;
    private String itemName;
    private Double itemPrice;
    @ApiModelProperty(value="推出时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date itemEndtime;
    private Date itemCreatetime;
    private Integer itemCount;
    private Date itemUpdatetime;
    private Boolean itemState;
    private String itemUnit;
    private String itemTypeid;
    private Integer itemConsume;
    private String itemImg;
}

