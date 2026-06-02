package com.microinfra.parking.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 停车场出入通道基本信息
 * </p>
 *
 * @author albert pi
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ParkingChannel implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 通道的主键
	 */
	@TableId(value = "id", type = IdType.AUTO)
	private Long id;

	/**
	 * 运营商ID
	 */
	@TableField("tenant_id")
	private Long tenantId;

	/**
	 * 运营商名称
	 */
	@TableField("tenant_name")
	private String tenantName;

	/**
	 * 项目的id
	 */
	@TableField("item_id")
	private String itemId;

	/**
	 * 车场编号
	 */
	@TableField("park_code")
	private String parkCode;

	/**
	 * 通道编号
	 */
	@TableField("channel_id")
	private String channelId;

	/**
	 * 通道名字
	 */
	@TableField("channel_name")
	private String channelName;

	/**
	 * 通道出入类型: 大车场入口 = 1,大车场出口 = 2,小车场入口 = 3,小车场出口 = 4
	 */
	@TableField("inorout_type")
	private Integer inoroutType;

	/**
	 * 创建时间
	 */
	@TableField("create_time")
	private LocalDateTime createTime;

	/**
	 * 修改时间
	 */
	@TableField("update_time")
	private LocalDateTime updateTime;

	/**
	 * 备用列
	 */
	private String extr;

	/**
	 * 车牌验证停留时间:秒
	 */
	@TableField("keep_time")
	private Integer keepTime;

	/**
	 * 相同车牌过滤时间:秒
	 */
	@TableField("same_time")
	private Integer sameTime;

	/**
	 * 对讲设备MAC
	 */
	@TableField("talk_mac")
	private String talkMac;

	/**
	 * 通道唯一标识
	 */
	@TableField("way_uuid")
	private String wayUuid;

	/**
	 * 网络扫码枪ip
	 */
	@TableField("gun_ip")
	private String gunIp;

	/**
	 * 岗亭主键id
	 */
	@TableField("sentry_id")
	private Integer sentryId;

	/**
	 * 车牌数据采集模式 0车牌识别 1ETC 2车牌识别或ETC 3其他
	 */
	private Integer collect;

	/**
	 * 通道的id
	 */
	@TableField("way_id")
	private Integer wayId;

	/**
	 * uuid唯一标识
	 */
	private String uuid;

	/**
	 * 逻辑删除(1:已删除,0:未删除)
	 */
	@TableField("is_deleted")
	private Boolean isDeleted;

	/**
	 * 是否开启无人值守：0:未开启 1:已开启
	 */
	@TableField("is_unmember")
	private Integer isUnmember;

	/**
	 * 通道类型：0:小车场 1:大车场
	 */
	@TableField("chan_type")
	private Integer chanType;

	/**
	 * 通道属性：0:入口 1:出口
	 */
	@TableField("chan_prop")
	private Integer chanProp;

}
