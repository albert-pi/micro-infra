package com.microinfra.parking.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableField;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 充电站状态统计数据
 * </p>
 *
 * @author albert pi
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ParkingStats implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键ID
	 */
	private Long id;

	/**
	 * 充电站ID
	 */
	@TableField("station_id")
	private String stationId;

	/**
	 * 充电站名称
	 */
	@TableField("station_name")
	private String stationName;

	/**
	 * 状态。0-未知；1-建设中；5-关闭下线；6-维护中；50-正常使用；
	 */
	private Integer status;

	/**
	 * 运营商ID
	 */
	@TableField("operator_id")
	private String operatorId;

	/**
	 * 运营商名称
	 */
	@TableField("operator_name")
	private String operatorName;

	/**
	 * 所属APP ID
	 */
	@TableField("app_id")
	private String appId;

	/**
	 * 所属项目编号
	 */
	@TableField("item_id")
	private String itemId;

	/**
	 * 充电站国家代码，比如CN
	 */
	@TableField("country_code")
	private String countryCode;

	/**
	 * 所在省份行政区划编码
	 */
	@TableField("province_code")
	private String provinceCode;

	/**
	 * 所在省份
	 */
	@TableField("province_name")
	private String provinceName;

	/**
	 * 所在城市行政区划编码
	 */
	@TableField("city_code")
	private String cityCode;

	/**
	 * 所在城市
	 */
	@TableField("city_name")
	private String cityName;

	/**
	 * 所在辖区行政区划编码
	 */
	@TableField("district_code")
	private String districtCode;

	/**
	 * 所在辖区
	 */
	@TableField("district_name")
	private String districtName;

	/**
	 * 详细地址
	 */
	private String address;

	/**
	 * 是否可以停车。0-不提供停车，只充电；1-可停车；
	 */
	private Integer parkable;

	/**
	 * 充电站内车位总数
	 */
	private Integer parks;

	/**
	 * 可充电停车位数量，0表示未知
	 */
	@TableField("chargeable_parks")
	private Integer chargeableParks;

	/**
	 * 当前空闲车位数
	 */
	@TableField("idle_parks")
	private Integer idleParks;

	/**
	 * 充电设备总数
	 */
	private Integer equipments;

	/**
	 * 充电接口总数
	 */
	private Integer connectors;

	/**
	 * 离线状态的接口总数
	 */
	@TableField("offline_connectors")
	private Integer offlineConnectors;

	/**
	 * 故障状态的接口总数
	 */
	@TableField("fault_connectors")
	private Integer faultConnectors;

	/**
	 * 正在充电的接口总数
	 */
	@TableField("charging_connectors")
	private Integer chargingConnectors;

	/**
	 * 空闲的接口总数
	 */
	@TableField("idle_connectors")
	private Integer idleConnectors;

	/**
	 * 被占用（未充电）的接口总数
	 */
	@TableField("holding_connectors")
	private Integer holdingConnectors;

	/**
	 * 交流电充电接口总数
	 */
	@TableField("ac_connectors")
	private Integer acConnectors;

	/**
	 * 当前空闲的交流电充电接口数
	 */
	@TableField("idle_ac_connectors")
	private Integer idleAcConnectors;

	/**
	 * 直流电充电接口总数
	 */
	@TableField("dc_connectors")
	private Integer dcConnectors;

	/**
	 * 当前空闲的直流电充电接口数
	 */
	@TableField("idle_dc_connectors")
	private Integer idleDcConnectors;

	/**
	 * 订单总数
	 */
	private Integer orders;

	/**
	 * 最后更新时间
	 */
	@TableField("updated_at")
	private LocalDateTime updatedAt;

}
