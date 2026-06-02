package com.microinfra.sysiam.vo;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson2.annotation.JSONField;
import com.microinfra.commons.lang.LongIdWriter;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 菜单列表数据
 * </p>
 *
 * @author albert pi
 * @since 1.0.0
 */
@Data
@ApiModel(value = "MenuRecord", description = "菜单列表数据，呈树状结构")
public class MenuRecord implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty("ID")
	@JSONField(serializeUsing = LongIdWriter.class)
	private Long id;

	@ApiModelProperty("父节点ID，0表示第一级")
	@JSONField(serializeUsing = LongIdWriter.class)
	private Long parentId;

	@ApiModelProperty("名称")
	private String name;

	@ApiModelProperty("路由地址")
	private String path;

	@ApiModelProperty("权限标识")
	private String authorityId;

	@ApiModelProperty("图标")
	private String icon;

	@ApiModelProperty("类型。1-菜单目录；2-菜单项；3-页面按钮；9-其它；")
	private Integer type;

	@ApiModelProperty("排序")
	private Integer sort;

	@ApiModelProperty("备注。功能描述或说明")
	private String remark;

	@ApiModelProperty("状态，默认为0。0-正常；1-禁用；")
	private Integer status = 0;

	@ApiModelProperty("子节点")
	private List<MenuRecord> children;

}
