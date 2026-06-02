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
 * 停车记录
 * </p>
 *
 * @author albert pi
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ParkingRecord implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId(value = "id", type = IdType.AUTO)
	private Long id;

	/**
	 * 腾达编号
	 */
	@TableField("record_no")
	private String recordNo;

	/**
	 * 停车记录编号
	 */
	private String no;

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
	 * 项目名称(停车场名称)
	 */
	@TableField("item_name")
	private String itemName;

	/**
	 * 车牌号
	 */
	@TableField("plate_no")
	private String plateNo;

	/**
	 * 车牌颜色
	 */
	@TableField("plate_color")
	private Integer plateColor;

	/**
	 * 车辆颜色
	 */
	private Integer color;

	/**
	 * 类型
	 */
	@TableField("car_type")
	private Integer carType;

	/**
	 * 车牌具体类型
	 */
	@TableField("car_sub_type")
	private Integer carSubType;

	/**
	 * 在场状态 1=在场 2=离场
	 */
	@TableField("park_status")
	private Integer parkStatus;

	/**
	 * 缴费状态
	 */
	@TableField("purchase_status")
	private Integer purchaseStatus;

	/**
	 * 缴费时间
	 */
	@TableField("purchase_date")
	private LocalDateTime purchaseDate;

	/**
	 * 进场时间
	 */
	@TableField("enter_time")
	private LocalDateTime enterTime;

	/**
	 * 出场时间
	 */
	@TableField("exit_time")
	private LocalDateTime exitTime;

	/**
	 * 停车时长
	 */
	@TableField("park_time")
	private Integer parkTime;

	/**
	 * 入口通道
	 */
	private String entrance;

	/**
	 * 入场操作员
	 */
	private String operator;

	/**
	 * 进场图片
	 */
	@TableField("enter_pic")
	private String enterPic;

	/**
	 * 出场图片
	 */
	@TableField("exit_pic")
	private String exitPic;

	/**
	 * 放行操作，1-正常放行, 2-免费/异常放行
	 */
	@TableField("release_type")
	private Integer releaseType;

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

	/**
	 * 预约订单号
	 */
	@TableField("appointment_no")
	private String appointmentNo;

	/**
	 * 车位id
	 */
	@TableField("stall_id")
	private Long stallId;

	/**
	 * 车位组id
	 */
	@TableField("group_id")
	private Long groupId;

	/**
	 * 车位组名称
	 */
	@TableField("group_name")
	private String groupName;

	/**
	 * 车辆类型 1、临时车辆，2、用户车辆，3、月租车 4: 储值车
	 */
	@TableField("plate_type")
	private Integer plateType;

	/**
	 * 出场通道名称
	 */
	@TableField("exit_channel_name")
	private String exitChannelName;

	/**
	 * 出场通道id
	 */
	@TableField("exit_channel")
	private String exitChannel;

	/**
	 * 出场操作员
	 */
	@TableField("exit_operator")
	private String exitOperator;

	/**
	 * 出场操作员id
	 */
	@TableField("exit_op_id")
	private Integer exitOpId;

	/**
	 * 抬闸备注
	 */
	@TableField("open_gate_remark")
	private String openGateRemark;

	/**
	 * 车组唯一标识
	 */
	@TableField("group_uuid")
	private String groupUuid;

	/**
	 * 实收金额
	 */
	@TableField("actual_fee")
	private BigDecimal actualFee;

	/**
	 * 总金额
	 */
	@TableField("payable_fee")
	private BigDecimal payableFee;

	/**
	 * 优惠金额
	 */
	@TableField("discount_fee")
	private BigDecimal discountFee;

	/**
	 * 入场操作员id
	 */
	@TableField("enter_op_id")
	private Integer enterOpId;

	/**
	 * 放行原因
	 */
	@TableField("release_reason")
	private String releaseReason;

}
