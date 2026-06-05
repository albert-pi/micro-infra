package com.microinfra.admin.controller;

import javax.annotation.Resource;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.microinfra.admin.controller.params.DeviceRequestParams.QueryDeviceParams;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.thingshub.Page;
import io.thingshub.client.DeviceInfo;
import io.thingshub.client.DeviceQueryCriterions;
import io.thingshub.client.ThingshubClient;
import lombok.extern.slf4j.Slf4j;

@Api(tags = "设备管理接口")
@RestController
@Validated
@Slf4j
public class DeviceController {

	@Resource
	private ThingshubClient thingshubClient;

	@ApiOperation(produces = "application/json", value = "查询设备信息")
	@GetMapping("/device/query")
	public Page<DeviceInfo> queryDevice(@Validated QueryDeviceParams params) {
		DeviceQueryCriterions queryCriterions = new DeviceQueryCriterions();
		queryCriterions.setTenantId(params.getTenantId());
		queryCriterions.setCustomerId(params.getCustomerId());
		queryCriterions.setRegion(params.getRegion());
		queryCriterions.setGroup(params.getGroup());
		queryCriterions.setProductCode(params.getProductCode());
		queryCriterions.setSn(params.getSn());

		return thingshubClient.queryDevice(queryCriterions);
	}

	@ApiOperation(produces = "application/json", value = "获取设备详细信息")
	@GetMapping("/device/details")
	public DeviceInfo getDeviceDetails(@RequestParam(name = "productCode") String productCode, @RequestParam(name = "sn") String sn) {
		return thingshubClient.getDeviceInfo(productCode, sn);
	}

}