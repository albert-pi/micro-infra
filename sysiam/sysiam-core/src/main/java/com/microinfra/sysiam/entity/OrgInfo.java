package com.microinfra.sysiam.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * <p>
 * 组织机构信息
 * </p>
 *
 * @author albert pi
 * @since 1.0.0
 */
@Data
@TableName(value = "iam_org_info")
public class OrgInfo implements Serializable {

	private static final long serialVersionUID = -4107729936367324675L;

	/**
	 * ID
	 */
	private Long id;

	/**
	 * 组织机构名称
	 */
	private String name;

	/**
	 * 上级机构ID。0表示第一级机构
	 */
	@TableField("parent_id")
	private Long parentId;

	/**
	 * 类型。1-平台所属机构；2-运营商所属机构；...
	 */
	private Integer type;

	/**
	 * 运营商ID。如果为运营商机构，值不为空
	 */
	@TableField("tenant_id")
	private Long tenantId;

	/**
	 * 排序
	 */
	private Integer sort;

	/**
	 * 状态。0-正常；1-禁用；
	 */
	private Integer status;

	/**
	 * 删除状态。0-未删除；1-已删除；
	 */
	@TableField("delete_status")
	private Integer deleteStatus;

	/**
	 * 组织机构描述或说明
	 */
	private String remark;

	/**
	 * 创建时间
	 */
	@TableField("create_time")
	private Date createTime;

	/**
	 * 创建者账号名称
	 */
	@TableField("create_by")
	private String createBy;

}