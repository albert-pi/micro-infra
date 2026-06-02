package com.microinfra.sysiam.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson2.annotation.JSONField;
import com.microinfra.commons.lang.LongIdWriter;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 运营商列表数据
 * </p>
 *
 * @author albert pi
 * @since 1.0.0
 */
@Data
@ApiModel(value = "TenantRecord", description = "运营商列表数据")
public class TenantRecord implements Serializable {

	private static final long serialVersionUID = -8074597870185985036L;

	@ApiModelProperty("运营商ID")
	@JSONField(serializeUsing = LongIdWriter.class)
	private Long id;

	@ApiModelProperty("运营商名称")
	private String name;

	@ApiModelProperty("运营商主体（运营商公司名称）")
	private String corp;

	@ApiModelProperty("联系人")
	private String contact;

	@ApiModelProperty("联系电话")
	private String phone;

	@ApiModelProperty("详细地址")
	private String address;

	@ApiModelProperty("平台使用有效期开始时间")
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date beginTime;

	@ApiModelProperty("平台使用有效期结束时间")
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date endTime;

	@ApiModelProperty("状态。0-正常；1-禁用；")
	private Integer status;

	@ApiModelProperty("被授权使用的平台服务列表")
	private List<String> services;

	@ApiModelProperty("创建时间")
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;

}
