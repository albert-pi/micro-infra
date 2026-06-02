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
 * 组织机构详细信息
 * </p>
 *
 * @author albert pi
 * @since 1.0.0
 */
@Data
@ApiModel(value = "OrgDetails", description = "组织机构详细信息")
public class OrgDetails implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty("ID")
	@JSONField(serializeUsing = LongIdWriter.class)
	private Long id;

	@ApiModelProperty("组织机构名称")
	private String name;

	@ApiModelProperty("上级组织机构ID")
	@JSONField(serializeUsing = LongIdWriter.class)
	private Long parentId;

	@ApiModelProperty("上级组织机构名称")
	private String parentName;

	@ApiModelProperty("运营商ID。如果为运营商组织机构，值不为空")
	@JSONField(serializeUsing = LongIdWriter.class)
	private Long tenantId;

	@ApiModelProperty("运营商名称。如果为运营商组织机构，值不为空")
	private String tenantName;

	@ApiModelProperty("状态，默认为0。0-正常；1-禁用；")
	private Integer status = 0;

	@ApiModelProperty("组织机构描述或说明")
	private String remark;

	@ApiModelProperty("创建者账号名称")
	private String createBy;

	@ApiModelProperty("创建时间")
	private Date createTime;

}
