package com.microinfra.parking.service;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.microinfra.parking.entity.ParkingOrder;
import com.microinfra.parking.mapper.ParkingOrderMapper;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author albert pi
 * @since 1.0.0
 */
@Service
public class ParkingOrderService extends ServiceImpl<ParkingOrderMapper, ParkingOrder> {

}
