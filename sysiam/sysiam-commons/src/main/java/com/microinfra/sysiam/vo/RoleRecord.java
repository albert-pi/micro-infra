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
 * 角色列表数据
 * </p>
 *
 * @author albert pi
 * @since 1.0.0
 */
@Data
@ApiModel(value = "RoleRecord", description = "角色列表数据")
public class RoleRecord implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty("角色ID")
	@JSONField(serializeUsing = LongIdWriter.class)
	private Long id;

	@ApiModelProperty("角色名称")
	private String name;

	@ApiModelProperty("状态。0-正常；1-禁用；")
	private Integer status;

	@ApiModelProperty("备注")
	private String remark;

	@ApiModelProperty("创建时间")
	private Date createTime;

}
