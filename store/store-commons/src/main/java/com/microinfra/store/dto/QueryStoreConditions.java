package com.microinfra.store.dto;

import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class QueryStoreConditions {

	@ApiModelProperty("存储服务提供商名称")
	private String provider;

	@ApiModelProperty("开始时间")
	private Date storeStart;

	@ApiModelProperty("结束时间")
	private Date storeEnd;

	@ApiModelProperty("名称")
	private String name;

	@ApiModelProperty("分页号")
	private Integer page = 1;

	@ApiModelProperty("返回记录数量")
	private Integer size = 10;

}