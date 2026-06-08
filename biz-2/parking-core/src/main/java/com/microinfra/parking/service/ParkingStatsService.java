package com.microinfra.parking.service;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.microinfra.parking.entity.ParkingStats;
import com.microinfra.parking.mapper.ParkingStatsMapper;

/**
 * <p>
 * 充电站状态统计数据 服务实现类
 * </p>
 *
 * @author albert pi
 * @since 1.0.0
 */
@Service
public class ParkingStatsService extends ServiceImpl<ParkingStatsMapper, ParkingStats> {

}
