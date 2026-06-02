package com.microinfra.uid.api;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.microinfra.commons.lang.LongId;
import com.microinfra.commons.lang.ServiceException;
import com.microinfra.uid.UidException;
import com.microinfra.uid.client.UidFeignClient;
import com.microinfra.uid.generator.UidGenerator;

@RestController
public class UidFeignController implements UidFeignClient {

	@Resource
	private UidGenerator uidGenerator;

	@Override
	public Long generate() throws ServiceException {
		return uidGenerator.generate();
	}

	@Override
	public String parse(@RequestBody LongId uid) throws ServiceException {
		if (uid == null || uid.getId() == null) {
			throw new UidException("ID不能为空");
		}

		return uidGenerator.parse(uid);
	}

}