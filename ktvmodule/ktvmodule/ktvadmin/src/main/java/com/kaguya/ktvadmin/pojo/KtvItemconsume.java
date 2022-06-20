package com.kaguya.ktvadmin.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class KtvItemconsume implements Serializable {
    @TableId
    private String consumeId;
    private String itemId;
    private String itemName;
    private String consumeOrderId;
    private Integer consumeCount;
    private String consumeUserid;
    private Boolean consumeState;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date consumeCreatetime;
    private Double consumePrice;
}
