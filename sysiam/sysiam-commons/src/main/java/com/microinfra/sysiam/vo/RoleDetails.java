package com.microinfra.sysiam.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson2.annotation.JSONField;
import com.microinfra.commons.lang.LongIdWriter;
import com.microinfra.commons.lang.TreeNode;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 角色详细信息
 * </p>
 *
 * @author albert pi
 * @since 1.0.0
 */
@Data
@ApiModel(value = "RoleDetails", description = "角色详细信息")
public class RoleDetails implements Serializable {

	private static final long serialVersionUID = -9219996705396806521L;

	@ApiModelProperty("角色ID")
	@JSONField(serializeUsing = LongIdWriter.class)
	private Long id;

	@ApiModelProperty("角色名称")
	private String name;

	@ApiModelProperty("类型。1-平台角色；2-运营商角色；")
	private Integer type;

	@ApiModelProperty("运营商ID")
	@JSONField(serializeUsing = LongIdWriter.class)
	private Long tenantId;

	@ApiModelProperty("运营商名称")
	private String tenantName;

	@ApiModelProperty("备注")
	private String remark;

	@ApiModelProperty("状态。0-正常；1-禁用；")
	private Integer status;

	@ApiModelProperty("分配的菜单权限，菜单以树状结构进行组织")
	private List<TreeNode> menus;

	@ApiModelProperty("创建时间")
	private Date createTime;

	@ApiModelProperty("创建者账号名称")
	private String createBy;

}
