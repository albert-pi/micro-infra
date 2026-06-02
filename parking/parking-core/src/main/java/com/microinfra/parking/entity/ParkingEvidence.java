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
 * 停车取证表
 * </p>
 *
 * @author albert pi
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ParkingEvidence implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 停车取证id
	 */
	@TableId(value = "id", type = IdType.AUTO)
	private Long id;

	/**
	 * 应用id
	 */
	@TableField("app_id")
	private String appId;

	/**
	 * 项目id
	 */
	@TableField("item_id")
	private String itemId;

	/**
	 * 停车记录id
	 */
	@TableField("park_id")
	private Long parkId;

	/**
	 * 停车编号
	 */
	@TableField("park_no")
	private String parkNo;

	/**
	 * 车牌号码
	 */
	@TableField("plate_no")
	private String plateNo;

	/**
	 * 车牌颜色，1、蓝牌车，2、黄牌车，3、黑牌车，4、白牌车，5、绿牌车
	 */
	@TableField("plate_color")
	private Integer plateColor;

	/**
	 * 车辆类型，1、临时车辆，2、用户车辆，3、包月车辆
	 */
	@TableField("plate_type")
	private Integer plateType;

	/**
	 * 收费员id
	 */
	@TableField("pda_id")
	private Integer pdaId;

	/**
	 * 收费员编号
	 */
	@TableField("pda_no")
	private Integer pdaNo;

	/**
	 * 联系方式
	 */
	@TableField("pda_phone")
	private String pdaPhone;

	/**
	 * 收费员姓名
	 */
	@TableField("pda_name")
	private String pdaName;

	/**
	 * H5取证审核状态 0未通过，1已通过
	 */
	private Integer state;

	/**
	 * 用户id
	 */
	@TableField("user_id")
	private String userId;

	/**
	 * 取证方式,0:人工，1：自动，2：ETC
	 */
	private Integer method;

	/**
	 * 取证状态，0、未取证，1、已取证，2、超时未取证，3、超时取证
	 */
	private Integer status;

	/**
	 * 取证图片
	 */
	private String img;

	/**
	 * 创建时间
	 */
	@TableField("create_time")
	private LocalDateTime createTime;

	/**
	 * 更新时间
	 */
	@TableField("update_time")
	private LocalDateTime updateTime;

	/**
	 * 备用列1
	 */
	private String ext1;

	/**
	 * 备用列2
	 */
	private String ext2;

}
