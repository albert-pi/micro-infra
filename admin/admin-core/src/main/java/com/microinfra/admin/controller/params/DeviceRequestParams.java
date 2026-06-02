package com.microinfra.admin.controller.params;

import com.microinfra.commons.bean.PageParams;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

public class DeviceRequestParams {

	@Data
	@EqualsAndHashCode(callSuper = false)
	@ApiModel(value = "QueryDeviceParams", description = "设备查询参数")
	public static class QueryDeviceParams extends PageParams {

		@ApiModelProperty("运营商ID")
		private String tenantId;

		@ApiModelProperty("客户ID")
		private String customerId;

		@ApiModelProperty("地区行政编码")
		private String region;

		@ApiModelProperty("设备分组")
		private String group;

		@ApiModelProperty("产品编号")
		private String productCode;

		@ApiModelProperty("设备序列号")
		private String sn;

	}

	@Data
	@ApiModel(value = "DeviceKeyParams", description = "设备标识参数")
	public static class DeviceKeyParams {

		@ApiModelProperty("产品编号")
		private String productCode;

		@ApiModelProperty("设备序列号")
		private String sn;

	}

}