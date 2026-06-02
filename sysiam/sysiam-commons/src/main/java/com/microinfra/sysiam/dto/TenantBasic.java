package com.microinfra.sysiam.dto;

import java.util.Date;

import com.alibaba.fastjson2.annotation.JSONField;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TenantBasic {

	private Long id;

	@ApiModelProperty("运营商名称")
	private String name;

	@ApiModelProperty("运营商主体")
	private String corp;

	@ApiModelProperty("联系人名称")
	private String contact;

	@ApiModelProperty("联系电话")
	private String phone;

	@ApiModelProperty("所在省份行政编码")
	private String provinceCode;

	private String provinceName;

	@ApiModelProperty("所在城市行政编码")
	private String cityCode;

	@ApiModelProperty("所在城市名称")
	private String cityName;

	@ApiModelProperty("所在区县行政编码")
	private String districtCode;

	@ApiModelProperty("所在区、县名称")
	private String districtName;

	@ApiModelProperty("详细地址")
	private String address;

	@ApiModelProperty("纬度坐标")
	private Double lat;

	@ApiModelProperty("经度坐标")
	private Double lng;

	@ApiModelProperty("geo hash值")
	private String geoHash;

	@ApiModelProperty("有效期开始时间")
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date beginTime;

	@ApiModelProperty("有效期结束时间")
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date endTime;

	@ApiModelProperty("备注")
	private String remark;

	@ApiModelProperty("创建时间")
	private Date createTime;

	@ApiModelProperty("创建者账号名称")
	private String createBy;

}