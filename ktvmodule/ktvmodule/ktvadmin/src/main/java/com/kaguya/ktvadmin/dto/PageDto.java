package com.kaguya.ktvadmin.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PageDto {
    @ApiModelProperty(value="当前页",required = true)
    private Integer currentPage;
    @ApiModelProperty(value="每页大小",required = true)
    private Integer pageSize;

    @ApiModelProperty(value="查询条件",required = false)
    private String queryString;
    @ApiModelProperty(value="用户状态： 0禁用 1启用",required = false)
    private String status;
}
