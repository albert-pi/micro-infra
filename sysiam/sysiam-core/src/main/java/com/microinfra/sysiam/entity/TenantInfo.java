package com.microinfra.sysiam.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * <p>
 * 运营商（使用平台服务进行独立运营的公司主体）信息
 * </p>
 *
 * @author albert pi
 * @since 1.0.0
 */
@Data
@TableName(value = "iam_tenant_info")
public class TenantInfo implements Serializable {

	private static final long serialVersionUID = -87030862538372266L;

	/**
	 * ID
	 */
	private Long id;

	/**
	 * 运营商名称
	 */
	private String name;

	/**
	 * 运营商主体名称（运营商公司名称）
	 */
	private String corp;

	/**
	 * 联系人
	 */
	private String contact;

	/**
	 * 联系电话
	 */
	private String phone;

	/**
	 * 所在省份或自治区行政区划编码
	 */
	@TableField("province_code")
	private String provinceCode;

	/**
	 * 所在省份或自治区名称
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
	 * 所在区、县行政区划编码
	 */
	@TableField("district_code")
	private String districtCode;

	/**
	 * 所在区、县名称
	 */
	@TableField("district_name")
	private String districtName;

	/**
	 * 详细地址
	 */
	private String address;

	/**
	 * 经度坐标
	 */
	private Double lng;

	/**
	 * 纬度坐标
	 */
	private Double lat;

	/**
	 * geo hash
	 */
	@TableField("geo_hash")
	private String geoHash;

	/**
	 * 平台使用有效期开始时间
	 */
	@TableField("begin_time")
	private Date beginTime;

	/**
	 * 平台使用有效期结束时间
	 */
	@TableField("end_time")
	private Date endTime;

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
	 * 备注
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