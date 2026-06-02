package com.microinfra.sysiam.vo;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 用户登录记录
 * </p>
 *
 * @author albert pi
 * @since 1.0.0
 */
@Data
@ApiModel(value = "LoginRecord", description = "用户登录记录")
public class LoginRecord implements Serializable {

	private static final long serialVersionUID = -1207682457249573820L;

	@ApiModelProperty("账号名称")
	private String name;

	@ApiModelProperty("用户姓名或昵称")
	private String nick;

	@ApiModelProperty("登录地点")
	private String location;

	@ApiModelProperty("IP地址")
	private String clientIp;

	@ApiModelProperty("客户端类型。1-浏览器；2-小程序；3-app；")
	private Integer clientType;

	@ApiModelProperty("客户端名称。比如：Chrome浏览器")
	private String clientName;

	@ApiModelProperty("操作系统")
	private String os;

	@ApiModelProperty("类型。1-登录；2-登出；")
	private Integer type;

	@ApiModelProperty("登录状态。0-成功；1-失败；")
	private Integer status;

	@ApiModelProperty("登录登出的结果。如果失败，记录错误详细信息")
	private String result;

	@ApiModelProperty("登录或登出时间")
	private Date logTime;

}
