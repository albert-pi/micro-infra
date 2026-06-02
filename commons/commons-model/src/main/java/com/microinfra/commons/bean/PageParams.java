package com.microinfra.commons.bean;

import lombok.Data;

@Data
public abstract class PageParams {

	protected Integer page = 1;

	protected Integer size = 10;

}
