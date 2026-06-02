package com.zhongzhi.code.generator;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;

@Configuration
public class Config {

	/**
	 * 数据源URL
	 */
	@Value("${spring.datasource.url}")
	@Getter
	private String dsUrl;

	/**
	 * 数据库用户名
	 */
	@Value("${spring.datasource.username}")
	@Getter
	private String userName;

	/**
	 * 数据库密码
	 */
	@Value("${spring.datasource.password}")
	@Getter
	private String password;

	/**
	 * 模块
	 */
	@Value("${code.output.dir}")
	@Getter
	private String codeOutputDir;

	/**
	 * 模块
	 */
	@Value("${code.module}")
	@Getter
	private String module;

	/**
	 * 表名
	 */
	@Value("${code.tables}")
	@Getter
	private String tables;

}
