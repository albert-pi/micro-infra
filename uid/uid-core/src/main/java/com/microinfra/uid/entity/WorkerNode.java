package com.microinfra.uid.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 节点信息
 * </p>
 *
 * @author albert pi
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("uid_worker_node")
public class WorkerNode implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@TableId(value = "id", type = IdType.AUTO)
	private Long id;

	/**
	 * 主机名
	 */
	@TableField("host")
	private String host;

	/**
	 * 端口
	 */
	@TableField("port")
	private String port;

	@TableField("launch_time")
	private Date launchTime;

	@TableField("update_time")
	private Date updateTime;

	@TableField("create_time")
	private Date createTime;

}