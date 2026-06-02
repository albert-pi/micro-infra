package com.microinfra.framework;

import java.io.Serializable;

import lombok.Data;

/**
 * <p>
 * 文件上传结果
 * </p>
 *
 * @author albert pi
 * @since 1.0.0
 */
@Data
public class UploadResult implements Serializable {

	private static final long serialVersionUID = -5209294387030973746L;

	/**
	 * 文件URL
	 */
	private String fileUrl;

}
