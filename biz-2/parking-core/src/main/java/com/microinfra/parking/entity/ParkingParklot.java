package com.microinfra.parking.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableField;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 停车场信息
 * </p>
 *
 * @author albert pi
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ParkingParklot implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	private Long id;

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 编号
	 */
	private String code;

	/**
	 * 类型。1-停车场；2-路边停车区；
	 */
	private Integer type;

	/**
	 * 许可证编号
	 */
	@TableField("license_id")
	private String licenseId;

	/**
	 * 车场图片地址。图片地址列表的JSON字符串
	 */
	private String imgs;

	/**
	 * 联系人
	 */
	private String contact;

	/**
	 * 联系电话
	 */
	private String phone;

	/**
	 * 服务电话
	 */
	@TableField("service_tel")
	private String serviceTel;

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
	 * 所在省份或自治区行政区划编码
	 */
	@TableField("province_code")
	private String provinceCode;

	/**
	 * 所属省份或自治区名称
	 */
	@TableField("province_name")
	private String provinceName;

	/**
	 * 所在城市行政区划编码
	 */
	@TableField("city_code")
	private String cityCode;

	/**
	 * 所在城市名称
	 */
	@TableField("city_name")
	private String cityName;

	/**
	 * 所在区县行政区划编码
	 */
	@TableField("district_code")
	private String districtCode;

	/**
	 * 所在区县名称
	 */
	@TableField("district_name")
	private String districtName;

	/**
	 * 详细地址
	 */
	private String address;

	/**
	 * 经度
	 */
	private Double lng;

	/**
	 * 纬度
	 */
	private Double lat;

	/**
	 * geo hash
	 */
	@TableField("geo_hash")
	private String geoHash;

	/**
	 * 车位总数量
	 */
	@TableField("total_spots")
	private Integer totalSpots;

	/**
	 * 空闲车位数
	 */
	@TableField("idle_spots")
	private Integer idleSpots;

	/**
	 * 是否可充电。0-不提供充电枪；1-提供充电枪；
	 */
	@TableField("charging_flag")
	private Integer chargingFlag;

	/**
	 * 备注说明
	 */
	private String remark;

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
