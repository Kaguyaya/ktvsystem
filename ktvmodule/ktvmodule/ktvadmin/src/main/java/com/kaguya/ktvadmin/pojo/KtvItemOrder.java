package com.kaguya.ktvadmin.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

@Data
public class KtvItemOrder {
    @TableId
    private String itemOrderId;
    private String roomId;
    private String ktvUserId;
    private Date createtime;
}
