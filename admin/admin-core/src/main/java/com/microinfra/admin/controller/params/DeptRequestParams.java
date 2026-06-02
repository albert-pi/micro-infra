package com.microinfra.admin.controller.params;

import javax.validation.constraints.NotBlank;

import com.microinfra.commons.bean.PageParams;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

public class DeptRequestParams {

	@Data
	@EqualsAndHashCode(callSuper = false)
	@ApiModel(value = "QueryOrgParams", description = "组织机构信息查询参数")
	public static class QueryOrgParams extends PageParams {

		@ApiModelProperty("组织机构名称")
		private String name;

	}

	@Data
	@ApiModel(value = "OrgFormParams", description = "组织机构信息表单参数")
	public static class OrgFormParams {

		@ApiModelProperty("ID。修改组织机构时不为空")
		private Long id;

		@NotBlank(message = "组织机构名称不能为空")
		@ApiModelProperty(required = true, value = "组织机构名称")
		private String name;

		@ApiModelProperty("备注")
		private String remark;

	}

}