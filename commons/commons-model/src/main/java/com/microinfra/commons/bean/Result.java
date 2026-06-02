package com.microinfra.commons.bean;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Result<T> implements Serializable {

	private static final long serialVersionUID = -7775374129710881822L;

	/**
	 * 返回信息码，默认0。 0：成功；其它:错误码
	 */
	@Builder.Default
	private int code = 0;

	/**
	 * 返回信息，默认OK
	 */
	@Builder.Default
	private String msg = "success";

	/**
	 * 服务器时间戳
	 */
	private long timestamp;

	/**
	 * 响应返回数据对象
	 */
	private T data;

	public static <D> Result<D> success() {
		return Result.<D>builder().timestamp(System.currentTimeMillis()).build();
	}

	public static <D> Result<D> success(D data) {
		return Result.<D>builder().data(data).timestamp(System.currentTimeMillis()).build();
	}

	public static <D> Result<D> error(Integer code, String msg) {
		return Result.<D>builder().code(code).msg(msg).timestamp(System.currentTimeMillis()).build();
	}

	public static <D> Result<D> error(Integer code, String msg, D data) {
		return Result.<D>builder().code(code).msg(msg).data(data).timestamp(System.currentTimeMillis()).build();
	}

}
