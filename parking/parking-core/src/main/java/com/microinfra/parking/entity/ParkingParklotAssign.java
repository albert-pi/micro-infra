package com.microinfra.parking.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableField;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 停车场管理的分配
 * </p>
 *
 * @author albert pi
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ParkingParklotAssign implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	private Long id;

	/**
	 * 用户ID
	 */
	@TableField("staff_id")
	private Long staffId;

	/**
	 * 运营商ID。0表示所有运营商
	 */
	@TableField("tenant_id")
	private Long tenantId;

	/**
	 * 项目ID。0表示所有项目
	 */
	@TableField("project_id")
	private Long projectId;

	/**
	 * 创建时间
	 */
	@TableField("create_at")
	private LocalDateTime createAt;

	/**
	 * 创建者账号名称
	 */
	@TableField("create_by")
	private String createBy;

}
