package com.microinfra.parking.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author albert pi
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ParkingOrder implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 应用的id
	 */
	@TableField("app_id")
	private String appId;

	/**
	 * 项目的id
	 */
	@TableField("item_id")
	private String itemId;

	/**
	 * 主键
	 */
	@TableId(value = "order_id", type = IdType.AUTO)
	private Long orderId;

	/**
	 * 进出泊位详情的id
	 */
	@TableField("detail_id")
	private Long detailId;

	/**
	 * 订单号
	 */
	@TableField("order_no")
	private String orderNo;

	/**
	 * 停车场名称
	 */
	@TableField("parking_name")
	private String parkingName;

	/**
	 * 车牌号
	 */
	@TableField("plate_no")
	private String plateNo;

	/**
	 * 车牌颜色，1、蓝牌车，2、黄牌车，3、黑牌车，4、白牌车，5、绿牌车 6 无牌车
	 */
	@TableField("plate_color")
	private Integer plateColor;

	/**
	 * 车辆类型，1、临时车辆，2、用户车辆，3、月租车 4:储值车
	 */
	@TableField("plate_type")
	private Integer plateType;

	/**
	 * 车位组的id
	 */
	@TableField("group_id")
	private Long groupId;

	/**
	 * 金额
	 */
	private BigDecimal amount;

	/**
	 * 车位的id
	 */
	@TableField("stall_id")
	private Long stallId;

	/**
	 * 支付时间
	 */
	@TableField("pay_time")
	private LocalDateTime payTime;

	/**
	 * 支付方式0、现金 1、微信，2、支付宝
	 */
	@TableField("pay_type")
	private Integer payType;

	/**
	 * 0、待支付，1、已支付，2、已关闭，3、已取消 4、支付中
	 */
	@TableField("order_status")
	private Integer orderStatus;

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
	private String ext;

	/**
	 * 备用列2
	 */
	private String ext1;

	/**
	 * 不在泊位开始时间
	 */
	@TableField("no_berth_intime")
	private LocalDateTime noBerthIntime;

	/**
	 * 不在泊位结束时间
	 */
	@TableField("no_berth_outtime")
	private LocalDateTime noBerthOuttime;

}
