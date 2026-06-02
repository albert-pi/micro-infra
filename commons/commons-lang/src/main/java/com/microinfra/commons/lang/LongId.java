package com.microinfra.commons.lang;

import com.alibaba.fastjson2.annotation.JSONField;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LongId {

	@JSONField(serializeUsing = LongIdWriter.class)
	private Long id;

}