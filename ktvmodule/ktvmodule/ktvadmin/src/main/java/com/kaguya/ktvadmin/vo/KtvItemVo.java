package com.kaguya.ktvadmin.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

@Data
public class KtvItemVo {
    private String itemId;
    private String itemName;
    private Double itemPrice;
    private Date itemEndtime;
    private Date itemCreatetime;
    private int itemCount;
    private Date itemUpdatetime;
}
