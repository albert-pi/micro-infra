package com.microinfra.sysiam.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.microinfra.sysiam.entity.LoginLog;
import com.microinfra.sysiam.vo.LoginRecord;

/**
 * <p>
 * 用户登录日志 Mapper接口
 * </p>
 *
 * @author albert pi
 * @since 1.0.0
 */
public interface LoginLogMapper extends BaseMapper<LoginLog> {

	@Select(" SELECT name, nick, location, client_ip, client_type, client_name, os, type, status, result, log_time FROM iam_login_log ${ew.customSqlSegment} ")
	List<LoginRecord> selectLoginRecordsByParams(@Param(Constants.WRAPPER) QueryWrapper<LoginRecord> wrapper);

}
