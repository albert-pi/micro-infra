package com.microinfra.parking.entity;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.Data;

@Data
public class ParkingDeviceBinding {

	/**
	 * 主键id
	 */
	@TableId(value = "id", type = IdType.AUTO)
	private Long id;

	/**
	 * 设备ID
	 */
	@TableField("device_id")
	private Long deviceId;

	/**
	 * 设备序列号或编号
	 */
	@TableField("device_sn")
	private String deviceSn;

	/**
	 * 设备的产品名称
	 */
	@TableField("device_name")
	private String deviceName;

	/**
	 * 运营商ID
	 */
	@TableField("tenant_id")
	private Long tenantId;

	/**
	 * 运营商名称
	 */
	@TableField("tenant_name")
	private String tenantName;

	/**
	 * 停车场ID
	 */
	@TableField("parking_area_id")
	private Long parkingAreaId;

	/**
	 * 停车场名称
	 */
	@TableField("parking_area_name")
	private String parkingAreaName;

	/**
	 * 路段ID
	 */
	@TableField("road_id")
	private Long roadId;

	/**
	 * 路段名称
	 */
	@TableField("road_name")
	private String roadName;

	/**
	 * 车位ID
	 */
	@TableField("parking_spot_id")
	private Long parkingSpotId;

	/**
	 * 车位编号
	 */
	@TableField("parking_spot_code")
	private String parkingSpotCode;

	/**
	 * 创建时间
	 */
	@TableField("create_time")
	private LocalDateTime createTime;

	/**
	 * 创建者账号名称
	 */
	@TableField("create_by")
	private String createBy;

}
