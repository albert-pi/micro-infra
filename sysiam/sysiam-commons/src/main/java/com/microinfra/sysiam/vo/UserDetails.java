package com.microinfra.sysiam.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson2.annotation.JSONField;
import com.microinfra.commons.bean.DataOption;
import com.microinfra.commons.bean.ServiceItemOption;
import com.microinfra.commons.lang.LongIdWriter;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 用户详细信息
 * </p>
 *
 * @author albert pi
 * @since 1.0.0
 */
@Data
@ApiModel(value = "UserDetails", description = "用户详细信息")
public class UserDetails implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty("用户ID")
	@JSONField(serializeUsing = LongIdWriter.class)
	private Long id;

	@ApiModelProperty("账号名称")
	private String name;

	@ApiModelProperty("姓名或昵称")
	private String nick;

	@ApiModelProperty("手机号码")
	private String mobile;

	@ApiModelProperty("头像地址")
	private String avatar;

	@ApiModelProperty("用户类别。1-平台用户；2-运营商用户；")
	private Integer type;

	@ApiModelProperty("所属运营商ID。如果是运营商用户，值不为空")
	@JSONField(serializeUsing = LongIdWriter.class)
	private Long tenantId;

	@ApiModelProperty("所属运营商名称。如果是运营商用户，值不为空")
	private String tenantName;

	@ApiModelProperty("所属组织机构")
	private List<DataOption> orgs;

	@ApiModelProperty("被授权使用的服务项目")
	private List<ServiceItemOption> serviceItems;

	@ApiModelProperty("被授权管理的运营商列表")
	private List<DataOption> tenants;

	@ApiModelProperty("被分配的角色")
	private List<DataOption> roles;

	@ApiModelProperty("备注")
	private String remark;

	@ApiModelProperty("状态。0-正常；1-停用；")
	private Integer status = 0;

	@ApiModelProperty("创建时间")
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;

	@ApiModelProperty("创建者账号名称")
	private String createBy;

}
