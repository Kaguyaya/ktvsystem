package com.kaguya.ktvadmin.vo;

import lombok.Data;

import java.util.Date;

@Data
public class KtvRoomVo {
    private String roomId;
    private String roomName;
    private String roomOpentime;
    private Date roomEndtime;
    private Date roomCreatetime;
    private Date roomUpdatetime;
    private String roomUsetime;
}
