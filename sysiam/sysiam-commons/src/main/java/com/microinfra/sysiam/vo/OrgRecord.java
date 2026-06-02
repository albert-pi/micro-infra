package com.microinfra.sysiam.vo;

import java.io.Serializable;
import java.util.Date;

import com.alibaba.fastjson2.annotation.JSONField;
import com.microinfra.commons.lang.LongIdWriter;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 组织机构列表数据
 * </p>
 *
 * @author albert pi
 * @since 1.0.0
 */
@Data
@ApiModel(value = "OrgRecord", description = "组织机构列表数据")
public class OrgRecord implements Serializable {

	private static final long serialVersionUID = -6611592574647929439L;

	@ApiModelProperty("ID")
	@JSONField(serializeUsing = LongIdWriter.class)
	private Long id;

	@ApiModelProperty("组织机构名称")
	private String name;

	@ApiModelProperty("上级组织机构名称")
	private String parentName;

	@ApiModelProperty("运营商名称")
	private String tenantName;

	@ApiModelProperty("状态。0-正常；1-禁用；")
	private Integer status;

	@ApiModelProperty("创建时间")
	private Date createTime;

}
