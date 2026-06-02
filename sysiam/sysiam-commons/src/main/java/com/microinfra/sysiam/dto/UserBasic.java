package com.microinfra.sysiam.dto;

import java.util.Date;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UserBasic {

	private Long id;

	@ApiModelProperty("登录账号名称")
	private String name;

	@ApiModelProperty("账号密码的MD5码")
	private String password;

	@ApiModelProperty("手机号码")
	private String mobile;

	@ApiModelProperty("用户姓名或昵称")
	private String nick;

	@ApiModelProperty("用户类别。1-平台用户；2-运营商用户；")
	private Integer type;

	@ApiModelProperty("运营商ID。如果是用户类别为运营商用户，值不为空")
	private Long tenantId;

	@ApiModelProperty("运营商名称。如果是用户类别为运营商用户，值不为空")
	private String tenantName;

	@ApiModelProperty("超级管理员标记。0-否；1-是；")
	private Integer adminFlag;

	@ApiModelProperty("备注")
	private String remark;

	@ApiModelProperty("所属组织机构ID")
	private List<Long> orgIds;

	@ApiModelProperty("创建时间")
	private Date createTime;

	@ApiModelProperty("创建者账号名称")
	private String createBy;

}