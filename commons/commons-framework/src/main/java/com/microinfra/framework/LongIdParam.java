package com.microinfra.framework;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LongIdParam {

	@NotNull(message = "ID参数不能为空")
	private Long id;

}