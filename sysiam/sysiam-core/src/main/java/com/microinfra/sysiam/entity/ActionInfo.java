package com.microinfra.sysiam.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * <p>
 * 系统应用提供的功能接口
 * </p>
 *
 * @author albert pi
 * @since 1.0.0
 */
@Data
@TableName(value = "iam_action_info")
public class ActionInfo implements Serializable {

	private static final long serialVersionUID = 8712329169136804677L;

	/**
	 * ID
	 */
	private Long id;

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 标题
	 */
	private String title;

	/**
	 * 路由地址
	 */
	private String path;

	/**
	 * 系统应用编码
	 */
	@TableField("app_code")
	private String appCode;

	/**
	 * 类型。1-新增；2-删除；3-修改；4-查询；9-其它；
	 */
	private Integer type;

	/**
	 * 状态。0-正常；1-禁用；
	 */
	private Integer status;

	/**
	 * 备注。功能描述或说明
	 */
	private String remark;

}