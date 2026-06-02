package com.microinfra.sysiam.dto;

import java.util.Date;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RoleBasic {

	private Long id;

	@ApiModelProperty("角色名称")
	private String name;

	@ApiModelProperty("角色关联的服务项目ID及菜单ID")
	private List<ServiceMenuOption> serviceMenus;

	@ApiModelProperty("类型。1-平台角色；2-运营商角色")
	private Integer type;

	@ApiModelProperty("运营商ID")
	private Long tenantId;

	@ApiModelProperty("状态。0-正常；1-禁用；")
	private Integer status;

	@ApiModelProperty("备注")
	private String remark;

	@ApiModelProperty("创建时间")
	private Date createTime;

	@ApiModelProperty("创建者账号名称")
	private String createBy;

}