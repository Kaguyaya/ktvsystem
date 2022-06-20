package com.kaguya.ktvadmin.vo;

import lombok.Data;

import java.util.Date;

@Data
public class KtvUserVo {
    private String userId;
    private String userNickname;
    private double userBalance;
    private double userPlaytime;
    private Date userBirthday;
}
