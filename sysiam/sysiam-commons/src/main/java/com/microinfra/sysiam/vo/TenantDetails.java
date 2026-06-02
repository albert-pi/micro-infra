package com.microinfra.sysiam.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson2.annotation.JSONField;
import com.microinfra.commons.bean.DataOption;
import com.microinfra.commons.lang.LongIdWriter;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 运营商详细信息
 * </p>
 *
 * @author albert pi
 * @since 1.0.0
 */
@Data
@ApiModel(value = "TenantDetails", description = "运营商详细信息")
public class TenantDetails implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty("ID")
	@JSONField(serializeUsing = LongIdWriter.class)
	private Long id;

	@ApiModelProperty("运营商名称")
	private String name;

	@ApiModelProperty("运营商主体名称（运营商公司名称）")
	private String corp;

	@ApiModelProperty("联系人")
	private String contact;

	@ApiModelProperty("联系电话")
	private String phone;

	@ApiModelProperty("所在省份或自治区行政区划编码")
	private String provinceCode;

	@ApiModelProperty("所在省份或自治区名称")
	private String provinceName;

	@ApiModelProperty("所在城市行政区划编码")
	private String cityCode;

	@ApiModelProperty("所在城市名称")
	private String cityName;

	@ApiModelProperty("所在区、县行政区划编码")
	private String districtCode;

	@ApiModelProperty("所在区、县名称")
	private String districtName;

	@ApiModelProperty("详细地址")
	private String address;

	@ApiModelProperty("经度坐标")
	private Double lng;

	@ApiModelProperty("纬度坐标")
	private Double lat;

	@ApiModelProperty("平台使用有效期开始时间")
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date beginTime;

	@ApiModelProperty("平台使用有效期结束时间")
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date endTime;

	@ApiModelProperty("状态。0-正常；1-禁用；")
	private Integer status = 0;

	@ApiModelProperty("备注")
	private String remark;

	@ApiModelProperty("被授权使用的平台服务列表。平台服务信息包括服务编码或标识符、服务名称")
	private List<DataOption> services;

	@ApiModelProperty("创建者账号名称")
	private String createBy;

	@ApiModelProperty("创建时间")
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;

}
