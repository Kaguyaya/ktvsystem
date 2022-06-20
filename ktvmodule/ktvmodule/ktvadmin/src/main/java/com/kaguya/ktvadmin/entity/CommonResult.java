package com.kaguya.ktvadmin.entity;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

/**
 * @author: Linwei
 * @date 2021/6/15
 * @Description: 通用接口返回对象
 */

@Data
@Builder
@AllArgsConstructor
@ApiModel("接口通用返回对象")
public class CommonResult<T> {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(required = true,notes = "结果码", example = "200")
    private int code;
    @ApiModelProperty(required = true,notes = "返回信息", example = "Success")
    private String message;
    @ApiModelProperty(required = false,notes = "返回数据", example = "{\"name:\":\"Jack\"}")
    private T data;

    @Tolerate
    public CommonResult() {
    }

    public static CommonResult ok() {
        return CommonResult.builder().code(200).message("请求成功").build();
    }

    public static CommonResult ok(Object data) {
        return CommonResult.builder().code(200).message("请求成功").data(data).build();
    }

    public static CommonResult error(String message) {
        return CommonResult.builder().code(500).message("响应异常").build();
    }
}