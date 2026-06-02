package com.microinfra.framework;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.ObjectUtils;

import com.alibaba.fastjson2.JSON;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ServletHelper {

	private static final long TIME_GAP = 60000L;// 时间差：60秒

	/**
	 * 请求时间戳是否有效
	 * 
	 * @param timestamp
	 * @return
	 */
	public static boolean isRequestTimestampValid(String timestamp) {
		Date date = new Date();
		long currentTimestamp = date.getTime();
		long reqTime = Long.valueOf(timestamp).longValue();

		if (Math.abs(currentTimestamp - reqTime) <= TIME_GAP)
			return true;

		return false;
	}

	public static void outputJSON(HttpServletResponse resp, Object respObject) {
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("application/json; charset=utf-8");
		PrintWriter pw = null;
		try {
			String content = JSON.toJSONString(respObject);

			pw = resp.getWriter();
			pw.append(content);
			resp.flushBuffer();
		} catch (IOException e) {
			log.error("error:", e);
		} finally {
			if (pw != null)
				pw.close();
		}
	}

	public static void resp2BadRequest(HttpServletResponse resp, Object result) {
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("application/json; charset=utf-8");

		try {
			String content = JSON.toJSONString(result);

			resp.sendError(400, content);
		} catch (IOException e) {
			log.error("error:", e);
		}
	}

	public static void resp2UnauthorizedRequest(HttpServletResponse resp, Object result) {
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("application/json; charset=utf-8");

		try {
			String content = JSON.toJSONString(result);

			resp.sendError(401, content);
		} catch (IOException e) {
			log.error("error:", e);
		}
	}

	public static String getCookie(HttpServletRequest request, String cookieKey) throws UnsupportedEncodingException {
		Cookie[] cookies = request.getCookies();
		if (cookies == null || cookies.length <= 0)
			return null;
		for (Cookie cookie : cookies) {
			if (cookieKey.equals(cookie.getName()))
				return URLDecoder.decode(cookie.getValue(), "UTF-8");
		}
		return null;
	}

	public static String getToken(HttpServletRequest request, String tokenKey) throws UnsupportedEncodingException {
		Cookie[] cookies = request.getCookies();
		if (cookies == null || cookies.length <= 0)
			return null;
		for (Cookie cookie : cookies) {
			if (tokenKey.equals(cookie.getName()))
				return cookie.getValue();
		}
		return null;
	}

	public static void addCookie(HttpServletResponse resp, String cookieKey, String cookieValue, int expiry) {
		Cookie cookie = new Cookie(cookieKey, cookieValue);
		cookie.setPath("/");
		cookie.setMaxAge(expiry);
		resp.addCookie(cookie);
	}

	public static void clearCookie(HttpServletRequest request, HttpServletResponse response, String cookieKey) {
		Cookie[] cookies = request.getCookies();
		if (cookies == null || cookies.length <= 0)
			return;

		for (Cookie cookie : cookies) {
			if (cookieKey.equals(cookie.getName())) {
				cookie.setMaxAge(0);
				response.addCookie(cookie);
			}
		}
	}

	public static void resetCookieMaxAge(HttpServletRequest request, HttpServletResponse response, String cookieKey, int expiry) {
		Cookie[] cookies = request.getCookies();
		if (cookies == null || cookies.length <= 0)
			return;

		for (Cookie cookie : cookies) {
			if (cookieKey.equals(cookie.getName())) {
				cookie.setMaxAge(expiry);
				response.addCookie(cookie);
			}
		}
	}

	public static String getClientIp(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");
		if (!ObjectUtils.isEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
			// 多次反向代理后会有多个ip值，第一个ip才是真实ip
			int index = ip.indexOf(",");
			if (index != -1) {
				return ip.substring(0, index);
			} else {
				return ip;
			}
		}
		ip = request.getHeader("X-Real-IP");
		if (!ObjectUtils.isEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
			return ip;
		}

		return request.getRemoteAddr();
	}
}