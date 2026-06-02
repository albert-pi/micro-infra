package com.microinfra.admin.controller.params;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.microinfra.commons.bean.PageParams;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

public class RoleRequestParams {

	@Data
	@EqualsAndHashCode(callSuper = false)
	@ApiModel(value = "QueryRoleParams", description = "角色信息查询参数")
	public static class QueryRoleParams extends PageParams {

		@ApiModelProperty("角色名称")
		private String name;

	}

	@Data
	@ApiModel(value = "RoleFormParams", description = "角色表单参数")
	public static class RoleFormParams {

		@ApiModelProperty("ID。修改角色时不能为空")
		private Long id;

		@NotBlank(message = "角色名称不能为空")
		@ApiModelProperty(required = true, value = "角色名称")
		private String name;

		@NotNull(message = "分配的菜单权限不能为空")
		@ApiModelProperty(required = true, value = "分配的服务项目ID及菜单ID列表。如果要取消用户所有分配的菜单，可以设置为空数组")
		private List<ServiceMenuParams> serviceMenus;

		@ApiModelProperty("角色名称")
		private String remark;

	}

	@Data
	@ApiModel(value = "ServiceMenuParams", description = "服务项目ID及菜单ID")
	public static class ServiceMenuParams {

		@NotNull(message = "服务项目不能为空")
		@ApiModelProperty(required = true, value = "服务项目ID")
		private Long serviceItemId;

		@NotBlank(message = "菜单不能为空")
		@ApiModelProperty(required = true, value = "角色名称")
		private Long menuId;

	}

}