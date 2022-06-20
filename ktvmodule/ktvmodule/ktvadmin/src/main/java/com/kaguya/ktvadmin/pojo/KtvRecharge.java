package com.kaguya.ktvadmin.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("ktv_recharge")
public class KtvRecharge implements Serializable {
    @TableId
    private String rechargeId;
    private String rechargeuserId;
    private Date rechargeTime;
    private double rechargeAmount;
    private String rechargeUsername;
}
