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
 * 用户列表数据
 * </p>
 *
 * @author albert pi
 * @since 1.0.0
 */
@Data
@ApiModel(value = "UserRecord", description = "用户列表数据")
public class UserRecord implements Serializable {

	private static final long serialVersionUID = -7071037603016626136L;

	@ApiModelProperty("ID")
	@JSONField(serializeUsing = LongIdWriter.class)
	private Long id;

	@ApiModelProperty("账号名称")
	private String name;

	@ApiModelProperty("姓名或昵称")
	private String nick;

	@ApiModelProperty("手机号码")
	private String mobile;

	@ApiModelProperty("所属运营商ID")
	@JSONField(serializeUsing = LongIdWriter.class)
	private Long tenantId;

	@ApiModelProperty("所属运营商名称")
	private String tenantName;

	@ApiModelProperty("被授权使用的平台服务项目")
	private List<String> services;

	@ApiModelProperty("状态。0-正常；1-禁用；")
	private Integer status;

	@ApiModelProperty("创建时间")
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;

	@ApiModelProperty("创建者账号名称")
	private String createBy;

}
