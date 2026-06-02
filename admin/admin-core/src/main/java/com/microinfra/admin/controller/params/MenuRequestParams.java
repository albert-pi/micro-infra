package com.microinfra.admin.controller.params;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

public class MenuRequestParams {

	@Data
	@ApiModel(value = "MenuFormParams", description = "菜单信息表单参数")
	public static class MenuFormParams {

		@ApiModelProperty("ID。修改菜单时不为空")
		private Long id;

		@ApiModelProperty(required = true, value = "父菜单项ID。0表示顶层菜单")
		private Long parentId = 0L;

		@NotBlank(message = "所属平台服务不能为空")
		@ApiModelProperty(required = true, value = "所属平台服务编码或标识符")
		private String serviceId;

		@NotBlank(message = "菜单名称不能为空")
		@ApiModelProperty(required = true, value = "菜单名称")
		private String name;

		@ApiModelProperty("路由地址")
		private String path;

		@NotNull(message = "类型不能为空")
		@ApiModelProperty(required = true, value = "类型。1-菜单目录；2-菜单项；3-页面按钮；9-其它；")
		private Integer type;

		@ApiModelProperty("图标样式或图标地址")
		private String icon;

		@ApiModelProperty("权限标识")
		private String authorityId;

		@ApiModelProperty("排序")
		private Integer sort = 0;

		@ApiModelProperty("备注。功能描述或说明")
		private String remark;

	}

}