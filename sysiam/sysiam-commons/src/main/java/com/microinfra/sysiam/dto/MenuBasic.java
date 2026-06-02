package com.microinfra.sysiam.dto;

import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MenuBasic {

	@ApiModelProperty("ID。修改菜单时不为空")
	private Long id;

	@ApiModelProperty("父菜单项ID。0表示顶层菜单")
	private Long parentId = 0L;

	@ApiModelProperty("所属平台服务编码或标识符")
	private String serviceId;

	@ApiModelProperty("菜单名称")
	private String name;

	@ApiModelProperty("路由地址")
	private String path;

	@ApiModelProperty("类型。1-菜单目录；2-菜单项；3-页面按钮；9-其它；")
	private Integer type;

	@ApiModelProperty("图标样式或图标地址")
	private String icon;

	@ApiModelProperty("权限标识")
	private String authorityId;

	@ApiModelProperty("排序")
	private Integer sort = 0;

	@ApiModelProperty("备注。功能描述或说明")
	private String remark;

	@ApiModelProperty("创建时间")
	private Date createTime;

	@ApiModelProperty("创建者账号名称")
	private String createBy;

}