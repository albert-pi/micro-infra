package com.microinfra.uid.facade;

import com.microinfra.commons.lang.LongId;
import com.microinfra.commons.lang.ServiceException;

public interface UidService {

	public Long generate() throws ServiceException;

	public String parse(LongId uid) throws ServiceException;

}