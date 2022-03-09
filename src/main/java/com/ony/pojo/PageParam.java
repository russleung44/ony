package com.ony.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;

/**
 * @author Tony
 * @date 2021/8/10
 */
@Data
@ApiModel("分页包装")
public class PageParam<T> {

    @ApiModelProperty("页码，默认1")
    private Integer pageNum = 1;

    @ApiModelProperty("页大小，默认10")
    private Integer pageSize = 10;

    @ApiModelProperty("排序-为空则不排序")
    private String orderBy;

    @Valid
    @ApiModelProperty("参数")
    private T data;

}
