package com.microinfra.uid.worker;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.microinfra.uid.entity.WorkerNode;
import com.microinfra.uid.mapper.WorkerNodeMapper;

/**
 * <p>
 * 节点信息 服务实现类
 * </p>
 *
 * @author albert pi
 * @since 1.0.0
 */
@Service
public class WorkerNodeService extends ServiceImpl<WorkerNodeMapper, WorkerNode> {

}