package com.microinfra.admin.controller.params;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.alibaba.fastjson2.annotation.JSONField;
import com.microinfra.commons.bean.PageParams;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

public class TenantRequestParams {

	@Data
	@EqualsAndHashCode(callSuper = false)
	@ApiModel(value = "QueryTenantParams", description = "运营商信息查询参数")
	public static class QueryTenantParams extends PageParams {

		@ApiModelProperty("名称")
		private String name;

		@ApiModelProperty("联系人电话")
		private String phone;

	}

	@Data
	@ApiModel(value = "TenantFormParams", description = "运营商信息表单参数")
	public static class TenantFormParams {

		@ApiModelProperty("ID。修改运营商时不为空")
		private Long id;

		@NotBlank(message = "运营商名称不能为空")
		@ApiModelProperty(required = true, value = "运营商名称")
		private String name;

		@ApiModelProperty("运营商主体")
		private String corp;

		@NotBlank(message = "联系人不能为空")
		@ApiModelProperty(required = true, value = "联系人名称")
		private String contact;

		@NotBlank(message = "联系电话不能为空")
		@ApiModelProperty(required = true, value = "联系电话")
		private String phone;

		@NotBlank(message = "省份不能为空")
		@ApiModelProperty(required = true, value = "所在省份行政编码")
		private String provinceCode;

		@NotBlank(message = "城市不能为空")
		@ApiModelProperty(required = true, value = "所在城市行政编码")
		private String cityCode;

		@NotBlank(message = "区县不能为空")
		@ApiModelProperty(required = true, value = "所在区县行政编码")
		private String districtCode;

		@NotBlank(message = "详细地址不能为空")
		@ApiModelProperty(required = true, value = "详细地址")
		private String address;

		@ApiModelProperty("纬度坐标")
		private Double lat;

		@ApiModelProperty("经度坐标")
		private Double lng;

		@NotNull(message = "有效期开始时间不能为空")
		@ApiModelProperty(required = true, value = "有效期开始时间。格式：yyyy-MM-dd HH:mm:ss")
		@JSONField(format = "yyyy-MM-dd HH:mm:ss")
		private Date beginTime;

		@NotNull(message = "有效期结束时间不能为空")
		@ApiModelProperty(position = 12, required = true, value = "有效期结束时间。格式：yyyy-MM-dd HH:mm:ss")
		@JSONField(format = "yyyy-MM-dd HH:mm:ss")
		private Date endTime;

		@ApiModelProperty(position = 14, value = "备注。描述或说明")
		private String remark;

	}

	@Data
	@ApiModel(value = "ServiceItemAuthorizingParams", description = "运营商服务项目授权参数")
	public static class ServiceItemAuthorizingParams {

		@NotNull(message = "运营商不能为空")
		@ApiModelProperty(required = true, value = "运营商ID")
		private Long tenantId;

		@NotEmpty(message = "服务项目不能为空")
		@ApiModelProperty(required = true, value = "授权给运营商使用的服务项目ID列表")
		private List<Long> serviceItemIds;

	}

	@Data
	@ApiModel(value = "TenantAdministratorParams", description = "运营商管理员信息参数")
	public class TenantAdministratorParams {

		@NotNull(message = "运营商不能为空")
		@ApiModelProperty(required = true, value = "运营商ID")
		private Long tenantId;

		@NotBlank(message = "登录账号不能为空")
		@ApiModelProperty(required = true, value = "登录账号名称")
		private String name;

		@NotBlank(message = "账号密码。创建管理员时不能为空")
		@ApiModelProperty(value = "账号密码")
		private String password;

		@NotBlank(message = "手机号码不能为空")
		@Pattern(regexp = "^1[3,4,5,6,7,8,9][0-9]{9}$", message = "手机号码错误")
		@ApiModelProperty(required = true, value = "手机号码")
		private String mobile;

	}

}