package com.kaguya.ktvadmin.vo;

import lombok.Data;

import java.util.Date;

@Data
public class KtvRechargeVo {
    private String rechargeId;
    private Date rechargeTime;
    private double rechargeAmount;
    private String rechargeUsername;
}
