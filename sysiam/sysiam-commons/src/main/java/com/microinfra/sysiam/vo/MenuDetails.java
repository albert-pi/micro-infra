package com.microinfra.sysiam.vo;

import java.io.Serializable;
import java.util.Date;

import com.alibaba.fastjson2.annotation.JSONField;
import com.microinfra.commons.lang.LongIdWriter;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 菜单详细信息
 * </p>
 *
 * @author albert pi
 * @since 1.0.0
 */
@Data
@ApiModel(value = "MenuDetails", description = "菜单详细信息")
public class MenuDetails implements Serializable {

	private static final long serialVersionUID = -3039288242399658114L;

	@ApiModelProperty("ID")
	@JSONField(serializeUsing = LongIdWriter.class)
	private Long id;

	@ApiModelProperty("名称")
	private String name;

	@ApiModelProperty("路由地址")
	private String path;

	@ApiModelProperty("系统应用编码")
	private String appCode;

	@ApiModelProperty("系统应用名称")
	private String appName;

	@ApiModelProperty("服务项目ID")
	private Long serviceId;

	@ApiModelProperty("服务项目名称")
	private String serviceName;

	@ApiModelProperty("上级菜单ID")
	@JSONField(serializeUsing = LongIdWriter.class)
	private Long parentId = 0L;

	@ApiModelProperty("类型。1-菜单目录；2-菜单项；3-页面按钮；9-其它；")
	private Integer type;

	@ApiModelProperty("权限标识")
	private String authorityId;

	@ApiModelProperty("关联的应用接口。当类型为3或9、需要请求后台接口时，值不为空")
	@JSONField(serializeUsing = LongIdWriter.class)
	private Long actionId;

	@ApiModelProperty("排序。默认为1")
	private Integer sort = 1;

	@ApiModelProperty("图标样式或图标地址")
	private String icon;

	@ApiModelProperty("备注。功能描述或说明")
	private String remark;

	@ApiModelProperty("状态，默认为0。0-正常；1-禁用；")
	private Integer status = 0;

	@ApiModelProperty("创建者账号名称")
	private String createBy;

	@ApiModelProperty("创建时间")
	private Date createTime;

}
