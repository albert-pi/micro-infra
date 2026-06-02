package com.microinfra.parking.message;

import lombok.Data;

@Data
public class StatusMessage {

	/**
	 * 车辆状态
	 * <ul>
	 * <li>0 - 无车</li>
	 * <li>1 - 有车</li>
	 * </ul>
	 */
	private Integer carState;

	/**
	 * 水位状态
	 * <ul>
	 * <li>0 - 正常</li>
	 * <li>1 - 水位过高</li>
	 * </ul>
	 */
	private Integer waterState;

	/**
	 * 锁状态
	 * <ul>
	 * <li>0 - 升到位</li>
	 * <li>1 - 降到位</li>
	 * <li>2 - 降锁故障</li>
	 * <li>3 - 升锁故障</li>
	 * <li>4 - 其它故障</li>
	 * </ul>
	 */
	private Integer lockState;

	/**
	 * 锁当前的角度。-1表示未知
	 */
	private Integer lockAngle;

	/**
	 * 降锁原因
	 * <ul>
	 * <li>0 - 指令</li>
	 * <li>1 - 进水</li>
	 * <li>2 - 断网</li>
	 * <li>3 - 无车</li>
	 * <li>4 - 低电量</li>
	 * </ul>
	 */
	private Integer lockSource;

	/**
	 * 电池电压。单位：毫伏
	 */
	private Integer batV;

	/**
	 * 电池电量。电池电量的百分比
	 */
	private Integer batSOC;

	/**
	 * 充电状态
	 * <ul>
	 * <li>0 - 未充电</li>
	 * <li>1 - 充电中</li>
	 * <li>2 - 充电结束</li>
	 * <li>3 - 充电器断开</li>
	 * </ul>
	 */
	private Integer chargeState;

	/**
	 * 太阳能输出电压。单位：毫伏
	 */
	private Integer vinV;

	/**
	 * 主板运行时间。单位：秒
	 */
	private Integer runTime;

	/**
	 * 蓝牙状态
	 * <ul>
	 * <li>-1 - 蓝牙设备错误</li>
	 * <li>0 - 未连接</li>
	 * <li>1 - 已连接</li>
	 * </ul>
	 */
	private Integer btState;

	/**
	 * 4G信号质量，大于16时表示信号比较好
	 * <ul>
	 * <li>-1 - 蓝牙设备错误</li>
	 * <li>0 - 未连接</li>
	 * <li>1 - 已连接</li>
	 * </ul>
	 */
	private Integer rssi;

	/**
	 * 停车序号
	 */
	private Integer parkNumber;

	/**
	 * 系统模式
	 * <ul>
	 * <li>0 - 运输模式</li>
	 * <li>1 - 运营模式</li>
	 * <li>2 - 维护模式</li>
	 * </ul>
	 */
	private Integer sysMode;

	/**
	 * 在线状态
	 * <ul>
	 * <li>0 - 离线</li>
	 * <li>1 - 在线</li>
	 * </ul>
	 */
	private Integer online;

	/**
	 * 车辆底盘距离地面高度。-1表示高度未知。单位：毫米
	 * <ul>
	 * <li>0 - 离线</li>
	 * <li>1 - 在线</li>
	 * </ul>
	 */
	private Integer height;

}
