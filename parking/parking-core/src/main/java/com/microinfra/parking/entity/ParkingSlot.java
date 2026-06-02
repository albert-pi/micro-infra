package com.microinfra.parking.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableField;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 停车位信息
 * </p>
 *
 * @author albert pi
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ParkingSlot implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	private Long id;

	/**
	 * 车位编号
	 */
	private String code;

	/**
	 * 类型。1-平行车位；2-垂直车位；3-斜向车位；
	 */
	private Integer type;

	/**
	 * 车位图片地址。图片地址列表的JSON字符串
	 */
	private String imgs;

	/**
	 * 车位纬度坐标
	 */
	private BigDecimal lat;

	/**
	 * 车位经度坐标
	 */
	private BigDecimal lng;

	/**
	 * geo hash
	 */
	@TableField("geo_hash")
	private String geoHash;

	/**
	 * 方向。1-东西向；2-西东向；3-南北向；4-北南向；
	 */
	private Integer direct;

	/**
	 * 二维码内容
	 */
	@TableField("qr_code")
	private String qrCode;

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
	 * 车场ID
	 */
	@TableField("parking_lot_id")
	private Long parkingLotId;

	/**
	 * 车场名称
	 */
	@TableField("parking_lot_name")
	private String parkingLotName;

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
	 * 所在省份或自治区行政区划编码
	 */
	@TableField("province_code")
	private String provinceCode;

	/**
	 * 所在城市行政区划编码
	 */
	@TableField("city_code")
	private String cityCode;

	/**
	 * 所在区县行政区划编码
	 */
	@TableField("district_code")
	private String districtCode;

	/**
	 * 备注说明
	 */
	private String remark;

	/**
	 * 锁定状态（车位上安装了锁设备时）。-1-无锁；0-降锁；1-升锁；
	 */
	@TableField("locking_status")
	private Integer lockingStatus;

	/**
	 * 停车状态。0-无车；1-有车；
	 */
	@TableField("parking_status")
	private Integer parkingStatus;

	/**
	 * 是否可充电。0-不提供充电枪；1-提供充电枪；
	 */
	@TableField("charging_flag")
	private Integer chargingFlag;

	/**
	 * 状态。0-正常；1-禁用；
	 */
	private Integer status;

	/**
	 * 删除标记。0-未删除；1-已删除；
	 */
	@TableField("is_deleted")
	private Integer isDeleted;

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
