package com.kaguya.ktvadmin.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.List;

@Data
@TableName("ktv_item_type")
public class KtvItemType {
    @TableId
    private String typeId;
    private String typeName;
    @TableField(exist = false)
    private List<KtvItem> ktvItems;
}
