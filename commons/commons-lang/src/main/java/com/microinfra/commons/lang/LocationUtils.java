package com.microinfra.commons.lang;

import com.alibaba.fastjson2.JSONObject;

import cn.hutool.core.net.NetUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 获取地理位置信息
 * 
 */
@Slf4j
public class LocationUtils {

	public static final String IP_URL = "http://whois.pconline.com.cn/ipJson.jsp";

	public static final String UNKNOWN = "未知地点";

	public static String getRealAddressByIP(String ip) {
		if (NetUtil.isInnerIP(ip)) {
			return "内网";
		}

		try {
			String resp = getIPInfo(ip);
			if (StrUtil.isEmpty(resp)) {
				log.error("根据IP地址获取地理位置错误。IP：{}", ip);
				return UNKNOWN;
			}

			JSONObject obj = JSONObject.parseObject(resp);
			String region = obj.getString("pro");
			String city = obj.getString("city");

			return String.format("%s %s", region, city);
		} catch (Exception e) {
			log.error("", e);
			log.error("根据IP地址获取地理位置错误。IP：{}", ip);

			return UNKNOWN;
		}
	}

	public static String getCityByIP(String ip) {
		if (NetUtil.isInnerIP(ip)) {
			return "内网";
		}

		String resp = getIPInfo(ip);
		if (resp == null || resp.length() == 0) {
			return UNKNOWN;
		}

		JSONObject obj = JSONObject.parseObject(resp);
		String city = obj.getString("city");

		return city;
	}

	private static String getIPInfo(String ip) {
		try {
			return HttpUtil.get(IP_URL + "?ip=" + ip + "&json=true", CharsetUtil.CHARSET_GBK);
		} catch (Exception e) {
			log.error("", e);
			log.error("根据IP地址获取地理位置错误。IP：{}", ip);

			throw new RuntimeException("根据IP地址获取地理位置错误");
		}
	}
}
