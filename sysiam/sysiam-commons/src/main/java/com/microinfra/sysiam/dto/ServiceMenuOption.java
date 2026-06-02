package com.microinfra.sysiam.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceMenuOption {

	@ApiModelProperty("服务项目ID")
	private Long serviceItemId;

	@ApiModelProperty("菜单ID")
	private Long menuId;

}