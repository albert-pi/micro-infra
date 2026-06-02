package com.microinfra.parking.facade;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson2.JSON;

import io.thingshub.client.DeviceInfo;
import io.thingshub.client.DeviceQueryCriterions;
import io.thingshub.client.ThingshubClient;
import io.thingshub.commons.model.Page;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 停车设备管理
 * </p>
 *
 * @author albert pi
 * @since 1.0.0
 */
@Service
@Slf4j
public class ParkingDeviceManager {

	@Resource
	private ThingshubClient thingshubClient;

	@PostConstruct
	public void init() {
		try {
			DeviceQueryCriterions queryCriterions = new DeviceQueryCriterions();
			queryCriterions.setProductCode("HY-001");

			Page<DeviceInfo> devices = thingshubClient.queryDevice(queryCriterions);
			log.info("devices: " + JSON.toJSONString(devices));

			DeviceInfo deviceInfo = thingshubClient.getDeviceInfo("HY-001", "320027880006");
			log.info("device info: " + JSON.toJSONString(deviceInfo));

			Map<String, Object> updateParams = new HashMap<>();
			updateParams.put("url1", "http://www.test.com/qlock-app-v4.0.bin");
			thingshubClient.publish("HY-001", "320027880006", "update", updateParams);

//		thingshubClient.publish("HY-001", "320027880006", "status_req", null);

//		thingshubClient.publish("HY-001", "320027880006", "status_req", null, new ReplyHandler<StatusMessage>() {
//
//			@Override
//			public void onSuccess(StatusMessage data) {
//				log.info("device {} status: {}", "320027880006", JSON.toJSONString(data));
//			}
//
//			@Override
//			public void onFailure(Throwable cause) {
//				log.error("", cause);
//			}
//
//			@Override
//			public void onTimeout() {
//				// handle reply timeout
//				log.error("reply timeout");
//			}
//
//			@Override
//			public void onComplete() {
//				// do something finally whether success or not
//			}
//
//		});
		} catch (Exception e) {
			log.error("", e);
		}
	}

}