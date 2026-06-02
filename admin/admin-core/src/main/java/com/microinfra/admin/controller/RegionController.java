package com.microinfra.admin.controller;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.microinfra.commons.lang.Regions;
import com.microinfra.commons.lang.Regions.Region;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "行政区划数据接口")
@RestController
@Validated
public class RegionController {

	@PostConstruct
	public void init() {
		Regions.loadRegions();
	}

	@ApiOperation(produces = "application/json", value = "获取下辖的行政区划列表")
	@GetMapping("/region/name")
	public String getRegionName(@RequestParam(name = "code") String regionCode) {
		return Regions.getRegionName(regionCode);
	}

	@ApiOperation(produces = "application/json", value = "获取下辖的行政区划列表")
	@GetMapping("/region/sub-regions")
	public List<Region> getSubRegions(@RequestParam(name = "code") String parentRegionCode) {
		return Regions.getSubRegions(parentRegionCode);
	}

}