package com.microinfra.parking.message.processor;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson2.JSON;
import com.microinfra.parking.message.StatusMessage;

import io.thingshub.client.MessageProcessor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class StatusReqAckMessageProcessor implements MessageProcessor<StatusMessage> {

	@Override
	public String getMessageName() {
		return "status_req_ack";
	}

	@Override
	public void process(String sn, String messageId, StatusMessage payload) {
		log.info("device {} status: {}", sn, JSON.toJSONString(payload));
	}

	@Override
	public void onError(String sn, Integer code, String error) {
		log.error("query device {} status error: {}", sn, error);
	}

}