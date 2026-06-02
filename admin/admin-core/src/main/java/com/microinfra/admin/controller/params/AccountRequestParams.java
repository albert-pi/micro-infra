package com.microinfra.admin.controller.params;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.microinfra.commons.bean.PageParams;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

public class AccountRequestParams {

	@Data
	@EqualsAndHashCode(callSuper = true)
	@ApiModel(value = "QueryUserParams", description = "查询用户信息参数")
	public static class QueryUserParams extends PageParams {

		@ApiModelProperty("账号名称")
		private String name;

		@ApiModelProperty("手机号码")
		private String mobile;

		@ApiModelProperty("姓名或昵称")
		private String nick;

		@ApiModelProperty("状态。0-正常；1-禁用；")
		private Integer status;

	}

	@Data
	@ApiModel(value = "UserFormParams", description = "用户信息表单参数")
	public class UserFormParams {

		@ApiModelProperty("ID。修改用户信息时不能为空")
		private Long id;

		@NotBlank(message = "登录账号不能为空")
		@ApiModelProperty(required = true, value = "登录账号名称")
		private String name;

		@ApiModelProperty("账号密码，提交时取密码明文的MD5码。创建用户时不能为空")
		private String password;

		@NotBlank(message = "手机号码不能为空")
		@Pattern(regexp = "^1[3,4,5,6,7,8,9][0-9]{9}$", message = "手机号码错误")
		@ApiModelProperty(required = true, value = "手机号码")
		private String mobile;

		@NotBlank(message = "用户姓名或昵称不能为空")
		@ApiModelProperty(required = true, value = "用户姓名或昵称")
		private String nick;

		@ApiModelProperty("备注")
		private String remark;

		@ApiModelProperty("部门ID")
		private List<Long> deptIds;

	}

	@Data
	@ApiModel(value = "ChangePasswordParams", description = "用户修改密码参数")
	public static class ChangePasswordParams {

		@NotBlank(message = "原密码不能为空")
		@ApiModelProperty(required = true, value = "原密码")
		private String oldPwd;

		@NotBlank(message = "新密码不能为空")
		@ApiModelProperty(required = true, value = "新密码")
		private String newPwd;

	}

	@Data
	@ApiModel(value = "RoleAssigningParams", description = "用户角色信息参数")
	public static class RoleAssigningParams {

		@NotNull(message = "用户ID不能为空")
		@ApiModelProperty(required = true, value = "用户ID")
		private Long userId;

		@NotEmpty(message = "角色不能为空")
		@ApiModelProperty(required = true, value = "角色ID列表")
		private List<Long> roleIds;

	}

	@Data
	@ApiModel(value = "AuthorizedTenantsParams", description = "授权用户管理运营商参数")
	public static class AuthorizedTenantsParams {

		@NotNull(message = "用户ID不能为空")
		@ApiModelProperty(required = true, value = "用户ID")
		private Long userId;

		@NotEmpty(message = "运营商不能为空")
		@ApiModelProperty(required = true, value = "授权给用户管理的运营商ID列表")
		private List<Long> tenantIds;

	}

}