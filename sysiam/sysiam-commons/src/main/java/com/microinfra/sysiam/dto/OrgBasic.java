package com.microinfra.sysiam.dto;

import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class OrgBasic {

	private Long id;

	@ApiModelProperty("上级组织机构ID")
	private Long parentId;

	@ApiModelProperty("组织机构名称")
	private String name;

	@ApiModelProperty("类型。1-平台所属机构；2-运营商所属机构；...")
	private Integer type;

	@ApiModelProperty("运营商ID")
	private Long tenantId;

	@ApiModelProperty("备注")
	private String remark;

	@ApiModelProperty("创建时间")
	private Date createTime;

	@ApiModelProperty("创建者账号名称")
	private String createBy;

}