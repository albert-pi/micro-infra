package com.microinfra.parking.message;

import lombok.Data;

@Data
public class StartupMessage {

	/**
	 * SIM卡标识符
	 */
	private String iccid;

	/**
	 * 蓝牙名称
	 */
	private String bt;

	/**
	 * 硬件版本号
	 */
	private String hvr;

	/**
	 * 引导程序版本号
	 */
	private String bvr;

	/**
	 * 应用程序版本号
	 */
	private String avr;

	/**
	 * 纬度坐标
	 */
	private Double lat;

	/**
	 * 经度坐标
	 */
	private Double lng;

}
