package com.microinfra.framework;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StrIdParam {

	@NotBlank(message = "ID参数不能为空")
	private String id;

}