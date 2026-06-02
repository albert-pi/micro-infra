package com.microinfra.sysiam.service;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.microinfra.sysiam.entity.AuditLog;
import com.microinfra.sysiam.mapper.AuditLogMapper;

/**
 * <p>
 * 操作审计日志 服务实现类
 * </p>
 *
 * @author albert pi
 * @since 1.0.0
 */
@Service
public class AuditLogService extends ServiceImpl<AuditLogMapper, AuditLog> {

}