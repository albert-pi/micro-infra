package com.microinfra.admin.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.microinfra.commons.bean.UserProfile;
import com.microinfra.commons.lang.ServiceException;
import com.microinfra.framework.CurrentUser;
import com.microinfra.store.facade.StoreService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import springfox.documentation.annotations.ApiIgnore;

@Slf4j
@Api(tags = "文件上传和下载接口")
@RestController
@Validated
public class FileController {

	@Resource
	private StoreService storeService;

	@ApiOperation(consumes = "multipart/form-data", value = "上传文件", notes = "上传文件")
	@PostMapping(value = "/file/upload", headers = "content-type=multipart/form-data")
	public Map<String, Long> upload(HttpServletRequest request, @ApiIgnore @CurrentUser UserProfile userProfile) throws ServiceException {
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();

		Map<String, Long> dataMap = new HashMap<>();
		if (fileMap != null && fileMap.size() > 0) {
			for (Map.Entry<String, MultipartFile> fileEntry : fileMap.entrySet()) {
				MultipartFile thefile = fileEntry.getValue();
				Long storeId = storeService.upload(userProfile.getTenantId(), "admin", thefile);
				dataMap.put(thefile.getName(), storeId);
			}
		}

		return dataMap;
	}

	@ApiOperation(produces = "application/json", value = "获取文件的访问地址")
	@GetMapping("/file/access-url")
	public String getAccessUrl(@RequestParam(name = "storeId") Long storeId) throws ServiceException {
		return storeService.getAccessUrl(storeId);
	}

//	@ApiOperation("下载文件")
//	@GetMapping(value = "/file/download")
//	public void download(HttpServletRequest request, HttpServletResponse response, @RequestParam(name = "storeId") Long storeId) {
//		InputStream ins = null;
//		OutputStream ous = null;
//
//		try {
//			StoreRecord storeRecord = storeService.getStoreRecord(storeId);
//
//			response.setContentType("application/octet-stream");
//			response.setHeader("Content-Disposition", "attachment;filename=" + storeRecord.getName());
//			response.addHeader("Pargam", "no-cache");
//			response.addHeader("Cache-Control", "no-cache");
//
//			ins = storeService.getContent(storeId);
//			ous = response.getOutputStream();
//			byte[] buffer = new byte[1024];
//			int len = 0;
//			while ((len = ins.read(buffer)) != -1) {
//				ous.write(buffer, 0, len);
//			}
//
//			ous.flush();
//		} catch (Exception e) {
//			log.error("", e);
//			throw new SysException("下载文件错误");
//		} finally {
//			if (ins != null) {
//				try {
//					ins.close();
//				} catch (IOException e) {
//					log.error("", e);
//				}
//			}
//			if (ous != null) {
//				try {
//					ous.close();
//				} catch (IOException e) {
//					log.error("", e);
//				}
//			}
//		}
//	}

}