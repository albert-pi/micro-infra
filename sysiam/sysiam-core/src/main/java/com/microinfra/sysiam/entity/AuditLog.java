package com.microinfra.sysiam.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * <p>
 * 操作审计 Mapper接口
 * </p>
 *
 * @author albert pi
 * @since 1.0.0
 */
@Data
@TableName(value = "iam_audit_log")
public class AuditLog implements Serializable {

	private static final long serialVersionUID = 7664590075240751477L;

	/**
	 * ID
	 */
	private Long id;

	/**
	 * 账号名称
	 */
	private String name;

	/**
	 * 用户姓名或昵称
	 */
	private String nick;

	/**
	 * 运营商ID。如果账号为运营商账号，不为空
	 */
	@TableField("tenant_id")
	private Long tenantId;

	/**
	 * 平台服务编码或标识符
	 */
	@TableField("service_id")
	private String serviceId;

	/**
	 * 操作名称
	 */
	private String title;

	/**
	 * 操作提交的数据
	 */
	private String data;

	/**
	 * 操作类型。1-新增；2-删除；3-修改；4-查询；9-其它；
	 */
	private Integer type;

	/**
	 * 地点
	 */
	private String location;

	/**
	 * IP地址
	 */
	@TableField("client_ip")
	private String clientIp;

	/**
	 * 客户端类型。1-浏览器；2-小程序；3-APP；
	 */
	@TableField("client_type")
	private Integer clientType;

	/**
	 * 客户端名称。比如：Chrome浏览器
	 */
	@TableField("client_name")
	private String clientName;

	/**
	 * 操作系统名称
	 */
	private String os;

	/**
	 * 操作状态。0-成功；1-失败；
	 */
	private Integer status;

	/**
	 * 操作结果。如果失败，记录失败详细信息
	 */
	private String result;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 操作时间
	 */
	@TableField("create_time")
	private Date createTime;

}